package com.example.taxidata.bean;

/**
 * @author: ODM
 * @date: 2019/8/11
 */
public class HotSpotInfo {

    private double longitude;
    private double latitude;
    private int heat;
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
