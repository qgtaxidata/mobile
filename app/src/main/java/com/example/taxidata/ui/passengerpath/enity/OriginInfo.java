package com.example.taxidata.ui.passengerpath.enity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OriginInfo {
    @Id(autoincrement = true)
    Long id;

    String origin;

    double lng;

    double lat;

    @Generated(hash = 393139884)
    public OriginInfo(Long id, String origin, double lng, double lat) {
        this.id = id;
        this.origin = origin;
        this.lng = lng;
        this.lat = lat;
    }

    @Generated(hash = 336400288)
    public OriginInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
