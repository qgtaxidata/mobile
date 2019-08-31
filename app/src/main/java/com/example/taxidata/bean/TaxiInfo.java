package com.example.taxidata.bean;

import java.io.Serializable;
import java.util.List;

public class TaxiInfo implements Serializable {

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

    public static class DataBean implements Serializable{
        /**
         * longitude : null
         * latitude : null
         * time : 2017-02-04 10:10:10.0
         * licenseplateno : 粤A715RB
         */

        private String longitude;
        private String latitude;
        private String time;
        private String licenseplateno;
        private boolean isSelected;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
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

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
