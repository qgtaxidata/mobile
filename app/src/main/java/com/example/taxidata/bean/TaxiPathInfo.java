package com.example.taxidata.bean;

import java.util.List;

public class TaxiPathInfo {

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
         * longitude : 113.24972485282404
         * latitude : 23.138024500572293
         * time : null
         * licenseplateno : 粤A0LJ51
         */

        private double longitude;
        private double latitude;
        private String time;
        private String licenseplateno;

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

        public String getLicenseplateno() {
            return licenseplateno;
        }

        public void setLicenseplateno(String licenseplateno) {
            this.licenseplateno = licenseplateno;
        }

    }
}
