package com.example.taxidata.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class TaxiInfo {


    /**
     * msg : success
     * code : 1
     * data : [{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AP199K"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AU1E24"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AY2K42"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AT2R45"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AT1Q15"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AQ0J64"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AV3W64"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AM5C49"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AL1T50"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A0JW49"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AK5E04"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AR0Y51"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A5EY52"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AR2N42"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AZ7M04"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A4KY40"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A1PZ47"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AU1J17"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AG9P41"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AL2N20"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AG579J"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A2633U"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A7048F"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AN4S75"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤AX4U34"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A1799G"},{"longitude":null,"latitude":null,"time":null,"licenseplateno":"粤A164SC"}]
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

    public static class DataBean implements Serializable {
        /**
         * longitude : null
         * latitude : null
         * time : null
         * licenseplateno : 粤AP199K
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
