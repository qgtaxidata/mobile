package com.example.taxidata.bean;

import java.util.List;

public class IncomeRankingInfo {

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
