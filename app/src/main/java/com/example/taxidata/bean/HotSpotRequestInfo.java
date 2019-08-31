package com.example.taxidata.bean;

/**
 * 请求热点--实体类
 * @author: ODM
 * @date: 2019/8/9
 */
public class HotSpotRequestInfo {
    //经度
    private double longitude;
    //纬度
    private double latitude;
    //时间
    private String time;

    public HotSpotRequestInfo(double longitude , double latitude , String time) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
