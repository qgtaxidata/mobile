package com.example.taxidata.application;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.taxidata.common.GreenDaoManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaxiApp extends Application {

    private static Context context;
    private static final String TAG = "TaxiApp";
    /**
     * app默认时间为2017-02-06
     */
    private static final String defaultTime = "2017-02-06 10:00:00";
    /**
     * 手机系统与app默认时间校准时间
     */
    private static long standardTime;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        //Logger框架初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        initGreenDao();
        initAppTime();
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
}
