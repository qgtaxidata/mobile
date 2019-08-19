package com.example.taxidata.application;

import android.app.Application;
import android.content.Context;

import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.common.SharedPreferencesManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TaxiApp extends Application {

    private static Context context;
    private static TaxiApp taxiApp;
    private static final String TAG = "TaxiApp";
    private RefWatcher refWatcher;
    /**
     * app默认时间为2017-02-06
     */
    public static String DEFAULT_TIME = "2017-02-06 10:00:00";
    /**
     * 手机系统与app默认时间校准时间
     */
    private static long standardTime;

    private static String defaultTime;

    /**
     * 是否第一次初始化时间
     */
    private boolean isFirst = true;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        //初始化TaxiApp
        taxiApp = this;
        //Logger框架初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        initGreenDao();
        initAppTime();
        //LeakCanary 监控者
        refWatcher =  LeakCanary.install(this);
    }

    public static Context getContext(){
        return context;
    }

    /**
     * 全局初始化GreenDao.
     */
    public void  initGreenDao(){
        GreenDaoManager.getInstance().init(getContext());
    }

    /**
     * 获取app当前时间(毫秒)
     * @return long
     */
    public static long getMillionTime(){
        return System.currentTimeMillis() - standardTime;
    }

    /**
     * 获取app当前时间
     * @return String
     */
    public static String getAppNowTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获得当前系统时间
        long systemNowTime = System.currentTimeMillis();
        //转化为当前app时间
        long appNowTime = systemNowTime - standardTime;
        //转化为字符串
        String time = format.format(new Date(appNowTime));

        return time;
    }

    /**
     * 获取当前时间的中文版
     * @return String
     */
    public static String getAppNowChineseTime(){
        StringBuffer timeBuilder = new StringBuffer(getAppNowTime());
        timeBuilder.setCharAt(timeBuilder.indexOf("-"),'年');
        timeBuilder.setCharAt(timeBuilder.indexOf("-"),'月');
        timeBuilder.insert(timeBuilder.indexOf(" "),"日");
        return timeBuilder.toString();
    }

    /**
     * 初始化app的时间
     */
    private void initAppTime(){
        //设置时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //第一次初始化时间,从本地或默认时间加载
        if (isFirst){
            //默认时间
            defaultTime = SharedPreferencesManager.getManager()
                    .getString(SharedPreferencesManager.CONST_NOW_APP_TIME,DEFAULT_TIME);
            isFirst = false;
        }
        //获取系统时间
        long systemTime = System.currentTimeMillis();
        try {
            //app默认时间转为毫秒数
            long appDefaultTime = format.parse(defaultTime).getTime();
            //校准时间，以后系统时间减去改时间可获得当前时间
            standardTime = systemTime - appDefaultTime;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置app当前时间
     * @param defaultTime  设置时间
     */
    public void setDefaultTime(String defaultTime) {
        TaxiApp.defaultTime = defaultTime;
        //重新初始化当前时间
        initAppTime();
    }

    /**
     * 获取taxiApp实例
     * @return TaxiApp
     */
    public static TaxiApp getTaxiApp(){
        return taxiApp;
    }

    public static RefWatcher getRefWatcher(Context context) {
        TaxiApp application = (TaxiApp) context.getApplicationContext();
        return application.refWatcher;
    }
}
