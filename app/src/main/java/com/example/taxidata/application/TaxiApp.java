package com.example.taxidata.application;

import android.app.Application;
import android.content.Context;

import com.example.taxidata.common.GreenDaoManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class TaxiApp extends Application {

    private static Context context;
    private static final String TAG = "TaxiApp";
    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        //Logger框架初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        initGreenDao();
    }

    public static Context getContext(){
        return context;
    }


    /**
     * 全局初始化GreenDao.
     */
    public void  initGreenDao(){
        GreenDaoManager.getInstance().init(getApplicationContext());
    }
}
