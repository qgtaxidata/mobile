package com.example.taxidata.bean;

public class GetTaxiInfo {


    /**
     * longitude : 113.29724714053167
     * latitude : 23.152800849399682
     * time : 2017-02-01 17:00:00
     */

    private double longitude;
    private double latitude;
    private String time;

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
