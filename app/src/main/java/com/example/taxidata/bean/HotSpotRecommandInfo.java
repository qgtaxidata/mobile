package com.example.taxidata.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 热点推荐实体类
 *
 * @author: ODM
 * @date: 2019/8/10
 */


public class HotSpotRecommandInfo {

    /**
     * longitude : 113.28244
     * latitude : 23.144439
     * heat : 10
     */

    @Id(autoincrement = true)
    Long ID;

    private double longitude;
    private double latitude;
    private int heat;

    @Generated(hash = 642849645)
    public HotSpotRecommandInfo(Long ID, double longitude, double latitude,
            int heat) {
        this.ID = ID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.heat = heat;
    }

    @Generated(hash = 2056300943)
    public HotSpotRecommandInfo() {
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

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
