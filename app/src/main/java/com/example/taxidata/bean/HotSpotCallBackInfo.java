package com.example.taxidata.bean;

import java.util.List;

/**
 * 热点反馈信息--实体类
 * @author: ODM
 * @date: 2019/8/9
 */
public class HotSpotCallBackInfo {


    /**
     * msg : success
     * code : 1
     * hot_spot : [{"longitude":113.439721,"latitude":23.190191,"heat":10},{"longitude":113.460731,"latitude":23.172837,"heat":6},{"longitude":113.447561,"latitude":23.169458,"heat":6},{"longitude":113.444407,"latitude":23.180245,"heat":6}]
     */

    private String msg;
    private int code;
    private List<HotSpotBean> hot_spot;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<HotSpotBean> getHot_spot() {
        return hot_spot;
    }

    public void setHot_spot(List<HotSpotBean> hot_spot) {
        this.hot_spot = hot_spot;
    }

    public static class HotSpotBean {
        /**
         * longitude : 113.439721
         * latitude : 23.190191
         * heat : 10
         */

        private double longitude;
        private double latitude;
        private int heat;

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
}
