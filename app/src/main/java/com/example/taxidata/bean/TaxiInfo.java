package com.example.taxidata.bean;

import java.io.Serializable;
import java.util.List;

public class TaxiInfo implements Serializable {


    /**
     * msg : success
     * code : 1
     * data : [{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A715RB"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A4NT07"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A2EJ57"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AY5T53"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A0LU13"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A4HY64"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A464SK"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AY2N41"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AH94B7"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AC49M1"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AJ94C8"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A0XT16"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AY3E24"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AE05L9"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AY0R50"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AH30J2"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AT3R42"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A2AU46"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤A5LS30"},{"longitude":null,"latitude":null,"time":"2017-02-04 10:10:10.0","licenseplateno":"粤AU1Q24"}]
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
         * longitude : null
         * latitude : null
         * time : 2017-02-04 10:10:10.0
         * licenseplateno : 粤A715RB
         */

        private String longitude;
        private String latitude;
        private String time;
        private String licenseplateno;

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
    }
}
