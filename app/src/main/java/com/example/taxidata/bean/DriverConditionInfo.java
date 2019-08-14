package com.example.taxidata.bean;


public class DriverConditionInfo {


    /**
     * msg : success
     * code : 1
     * data : {"companyID":"0200014479","load_mile":378.51,"load_time":703.28,"no_load_mile":57.26,"no_load_time":1005.67}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * companyID : 0200014479
         * load_mile : 378.51
         * load_time : 703.28
         * no_load_mile : 57.26
         * no_load_time : 1005.67
         */

        private String companyID;
        private double load_mile;
        private double load_time;
        private double no_load_mile;
        private double no_load_time;

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public double getLoad_mile() {
            return load_mile;
        }

        public void setLoad_mile(double load_mile) {
            this.load_mile = load_mile;
        }

        public double getLoad_time() {
            return load_time;
        }

        public void setLoad_time(double load_time) {
            this.load_time = load_time;
        }

        public double getNo_load_mile() {
            return no_load_mile;
        }

        public void setNo_load_mile(double no_load_mile) {
            this.no_load_mile = no_load_mile;
        }

        public double getNo_load_time() {
            return no_load_time;
        }

        public void setNo_load_time(double no_load_time) {
            this.no_load_time = no_load_time;
        }
    }
}
