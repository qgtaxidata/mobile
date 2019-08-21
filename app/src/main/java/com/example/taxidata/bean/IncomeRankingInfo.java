package com.example.taxidata.bean;

import java.util.List;

public class IncomeRankingInfo {
    /**
     * msg : success
     * code : 1
     * data : [{"rank":1,"driverID":"00071703","income":693},{"rank":2,"driverID":"00037016","income":681},{"rank":3,"driverID":"00075495","income":648},{"rank":4,"driverID":"00023851","income":560},{"rank":5,"driverID":"00072963","income":527},{"rank":6,"driverID":"00065737","income":516},{"rank":7,"driverID":"00065377","income":480},{"rank":8,"driverID":"00016446","income":466},{"rank":9,"driverID":"00073813","income":462},{"rank":10,"driverID":"00065471","income":431},{"rank":11,"driverID":"00066108","income":430},{"rank":12,"driverID":"00001869","income":425},{"rank":13,"driverID":"00055681","income":422},{"rank":14,"driverID":"00014035","income":418},{"rank":15,"driverID":"00076221","income":416},{"rank":16,"driverID":"00031664","income":416},{"rank":17,"driverID":"00073686","income":404},{"rank":18,"driverID":"00075564","income":397},{"rank":19,"driverID":"00039918","income":395},{"rank":20,"driverID":"00076412","income":393}]
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
         * rank : 1
         * driverID : 00071703
         * income : 693.0
         */

        private int rank;
        private String driverID;
        private double income;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getDriverID() {
            return driverID;
        }

        public void setDriverID(String driverID) {
            this.driverID = driverID;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }
    }


}
