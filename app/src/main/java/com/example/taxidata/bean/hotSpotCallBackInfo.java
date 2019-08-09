package com.example.taxidata.bean;

/**
 * 热点反馈信息--实体类
 * @author: ODM
 * @date: 2019/8/9
 */
public class hotSpotCallBackInfo {



    //经度
    private double longitude;
    //纬度
    private double latitude;
    //热度
    private int  heat;

    public hotSpotCallBackInfo(double longitude ,double latitude ,int heat) {
        this.longitude  =longitude;
        this.latitude = latitude;
        this.heat = heat ;
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

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }
}
