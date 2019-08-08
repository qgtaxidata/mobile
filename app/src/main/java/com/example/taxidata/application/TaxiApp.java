package com.example.taxidata.application;

import android.app.Application;
import android.content.Context;

public class TaxiApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
