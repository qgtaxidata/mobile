package com.example.taxidata.common;

import android.content.SharedPreferences;

import com.example.taxidata.application.TaxiApp;

public class SharedPreferencesManager {

    private static SharedPreferencesManager manager;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    /**
     * 设置参数存储的文件夹
     */
    private static final String CONST_SET_UP_PARAMETER = "setup";

    /**
     * app当前时间
     */
    public static final String CONST_NOW_APP_TIME = "appnowtime";

    /**
     * 超时时间
     */
    public static final String CONST_TIME_OUT = "timeout";

    /**
     * 轮询时间
     */
    public static final String CONST_POLLING = "polling";

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
     * 保存数据
     * @param key 键
     * @param value 键值
     */
    public void save(String key,int value){
        editor.putInt(key,value);
        editor.apply();
    }

    /**
     * 保存数据
     * @param key jian
     * @param value jian zhi
     */
    public void save(String key,double value) {
        editor.putFloat(key,(float)value);
        editor.apply();
    }

    /**
     * 读取数据
     * @param key 键
     * @param defaultValue  默认值
     * @return double
     */
    public double getDouble(String key,double defaultValue) {
        return preferences.getFloat(key,(float) defaultValue);
    }

    /**
     * 读取数据
     * @param key 键
     * @return String
     */
    public String getString(String key,String defaultValue){
        return preferences.getString(key,defaultValue);
    }

    public int getInt(String key,int defaultValue){
        return preferences.getInt(key,defaultValue);
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
