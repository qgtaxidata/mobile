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
     * data : [{"longitude":113.28778860505719,"latitude":23.141783998654773,"heat":10}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * longitude : 113.28778860505719
         * latitude : 23.141783998654773
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
