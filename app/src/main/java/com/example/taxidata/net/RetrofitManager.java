package com.example.taxidata.net;


import android.util.Log;

import com.example.taxidata.common.SharedPreferencesManager;
import com.example.taxidata.constant.Api;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 线程安全的单例类,用于请求网络
 */
public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private Retrofit retrofit;
    private HttpService service;
    public static int DEFAULT_TIME_OUT = 8;
    /**
     * 超时时间，默认为8秒
     */
    private static int timeoutTime = SharedPreferencesManager.getManager().getInt(SharedPreferencesManager.CONST_TIME_OUT,DEFAULT_TIME_OUT);
    /**
     * 服务器ip地址
     */
    private static String baseUrl = Api.CONST_BASE_URL;
    public final static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private RetrofitManager(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        cookieStore.put(httpUrl.host(),list);
                    }
                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(timeoutTime, TimeUnit.SECONDS)
                .writeTimeout(timeoutTime,TimeUnit.SECONDS)
                .readTimeout(timeoutTime,TimeUnit.SECONDS);

        //创建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(HttpService.class);
    }

    /**
     * 获取网络管理的manager
     * @return 该单例类
     */
    public static RetrofitManager getInstance(){
        if(retrofitManager == null){
            synchronized (Object.class){
                if(retrofitManager == null){
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    /**
     * 获取访问http的service
     * @return HttpService
     */
    public HttpService getHttpService() {
        return service;
    }

    /**
     * 重新设置服务器和超时时间
     * @param timeout 超时时间
     * @param url 服务器地址
     */
    public static void setTimeoutAndUrl(int timeout,String url){
        timeoutTime = timeout;
        Log.d("RetorfitManager","" + timeoutTime);
        baseUrl = url;
        //重新生成service
        retrofitManager = new RetrofitManager();
    }

    public static int getTimeoutTime(){
        return timeoutTime;
    }
}
