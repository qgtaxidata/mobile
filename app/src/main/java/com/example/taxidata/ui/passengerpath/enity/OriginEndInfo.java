package com.example.taxidata.ui.passengerpath.enity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OriginEndInfo {
    @Id(autoincrement = true)
    Long id;

    String origin;

    String end;

    double originLng;

    double originLat;

    double endLng;

    double endLat;

    @Generated(hash = 627014066)
    public OriginEndInfo(Long id, String origin, String end, double originLng,
            double originLat, double endLng, double endLat) {
        this.id = id;
        this.origin = origin;
        this.end = end;
        this.originLng = originLng;
        this.originLat = originLat;
        this.endLng = endLng;
        this.endLat = endLat;
    }

    @Generated(hash = 914269103)
    public OriginEndInfo() {
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

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getOriginLng() {
        return this.originLng;
    }

    public void setOriginLng(double originLng) {
        this.originLng = originLng;
    }

    public double getOriginLat() {
        return this.originLat;
    }

    public void setOriginLat(double originLat) {
        this.originLat = originLat;
    }

    public double getEndLng() {
        return this.endLng;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }

    public double getEndLat() {
        return this.endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }
}
