package com.example.taxidata.bean;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class HotSpotHint {

    private String  hotSpotName;

    private String hotSpotLocation;

    private double longitude;

    private double latitute;

    public HotSpotHint(String hotSpotName ,String hotSpotLocation ,double longitude , double latitute) {
        this.hotSpotName =hotSpotName ;
        this.hotSpotLocation = hotSpotLocation;
        this.longitude = longitude;
        this.latitute = latitute ;
    }

    public String getHotSpotName() {
        return hotSpotName;
    }

    public void setHotSpotName(String hotSpotName) {
        this.hotSpotName = hotSpotName;
    }

    public String getHotSpotLocation() {
        return hotSpotLocation;
    }

    public void setHotSpotLocation(String hotSpotLocation) {
        this.hotSpotLocation = hotSpotLocation;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }
}
