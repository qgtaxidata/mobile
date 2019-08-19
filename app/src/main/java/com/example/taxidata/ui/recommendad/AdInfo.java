package com.example.taxidata.ui.recommendad;

import java.util.List;

public class AdInfo {

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

        private double boardLon;
        private double boardLat;
        private int boardFlow;
        private double boradRate;

        public double getBoardLon() {
            return boardLon;
        }

        public void setBoardLon(double boardLon) {
            this.boardLon = boardLon;
        }

        public double getBoardLat() {
            return boardLat;
        }

        public void setBoardLat(double boardLat) {
            this.boardLat = boardLat;
        }

        public int getBoardFlow() {
            return boardFlow;
        }

        public void setBoardFlow(int boardFlow) {
            this.boardFlow = boardFlow;
        }

        public double getBoradRate() {
            return boradRate;
        }

        public void setBoradRate(double boradRate) {
            this.boradRate = boradRate;
        }
    }
}
