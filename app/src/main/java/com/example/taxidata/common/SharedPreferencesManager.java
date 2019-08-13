package com.example.taxidata.common;

import android.content.SharedPreferences;

import com.example.taxidata.application.TaxiApp;

public class SharedPreferencesManager {

    private static SharedPreferencesManager manager;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String CONST_SET_UP_PARAMETER = "setup"

    /**
     * 单例模式
     */
    private SharedPreferencesManager(){
        preferences = TaxiApp.getContext().getSharedPreferences(CONST_SET_UP_PARAMETER,0);
        editor = preferences.edit();
    }

    /**
     * 保存数据
     * @param key 键
     * @param value 键值
     */
    public void save(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }

    /**
     * 读取数据
     * @param key 键
     * @return String
     */
    public String getString(String key){
        return preferences.getString(key,"");
    }

    /**
     * 获取单例类
     * @return SharedPreferencesManager
     */
    public static SharedPreferencesManager getManager(){
        if (manager == null){
            manager = new SharedPreferencesManager();
        }
        return manager;
    }
}
