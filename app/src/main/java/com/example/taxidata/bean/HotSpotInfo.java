package com.example.taxidata.bean;

/**
 * 热点信息实体类
 *
 * @author: ODM
 * @date: 2019/8/11
 */
public class HotSpotInfo {
    //经度
    private double longitude;
    //纬度
    private double latitude;
    //热度
    private int heat;
    //地址名
    private String address;

    public HotSpotInfo() {

    }

    public HotSpotInfo(double longitude ,double latitude ,int heat ,String address) {
        this.longitude = longitude;
        this.latitude = latitude ;
        this.heat = heat;
        this.address =address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
