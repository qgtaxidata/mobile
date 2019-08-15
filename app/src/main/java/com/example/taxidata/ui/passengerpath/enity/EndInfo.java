package com.example.taxidata.ui.passengerpath.enity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EndInfo {
    @Id(autoincrement = true)
    Long id;

    String end;

    double lng;

    double lat;

    @Generated(hash = 1101445685)
    public EndInfo(Long id, String end, double lng, double lat) {
        this.id = id;
        this.end = end;
        this.lng = lng;
        this.lat = lat;
    }

    @Generated(hash = 955924336)
    public EndInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
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
