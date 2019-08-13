package com.example.taxidata.net;

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

public class PathRetrofitManager {

    private static PathRetrofitManager pathRetrofitManager;
    private Retrofit retrofit;
    private HttpService service;
    public final static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private PathRetrofitManager(){
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
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS);

        //创建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.CONST_BASE_URL)
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
    public static PathRetrofitManager getInstance(){
        if(pathRetrofitManager == null){
            synchronized (Object.class){
                if(pathRetrofitManager == null){
                    pathRetrofitManager = new PathRetrofitManager();
                }
            }
        }
        return pathRetrofitManager;
    }

    /**
     * 获取访问http的service
     * @return HttpService
     */
    public HttpService getHttpService() {
        return service;
    }
}
