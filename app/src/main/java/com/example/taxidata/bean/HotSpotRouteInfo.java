package com.example.taxidata.bean;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class HotSpotRouteInfo {


    /**
     * msg : success
     * code : 1
     * data : [{"time":123,"lenth":123.2,"route":[{"lng":1.122152,"lat":1.5151517},{"lng":1.122152,"lat":1.5151517}]},{"time":123,"lenth":123.2,"route":[{"lng":1,"lat":1},{"lng":1,"lat":1}]},{"time":123,"lenth":123,"route":[{"lng":1,"lat":1},{"lng":1,"lat":1}]}]
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
         * time : 123
         * lenth : 123.2
         * route : [{"lng":1.122152,"lat":1.5151517},{"lng":1.122152,"lat":1.5151517}]
         */

        private int time;
        private float lenth;
        private List<RouteBean> route;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public float getLenth() {
            return lenth;
        }

        public void setLenth(float lenth) {
            this.lenth = lenth;
        }

        public List<RouteBean> getRoute() {
            return route;
        }

        public void setRoute(List<RouteBean> route) {
            this.route = route;
        }

        public static class RouteBean {
            /**
             * lng : 1.122152
             * lat : 1.5151517
             */

            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }
    }
}
