package com.example.taxidata.bean;

// FIXME generate failure  field _$End99
// FIXME generate failure  field _$End326
/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class HotSpotRouteRequest {

    public HotSpotRouteRequest() {
        start = new startBean();
        end = new endBean();
    }


    /**
     *  start : {"longitute":23.632157,"latitute":23.632157}
     *  end  : {"longitute ":23.632157,"latitute ":23.632157}
     */

    private startBean start;
    private endBean end;
    public startBean getStart() {
        return start;
    }

    public void setStart(startBean start) {
        this.start = start;
    }

    public endBean getEnd() {
        return end;
    }

    public void setEnd(endBean end) {
        this.end = end;
    }
    public  static class startBean {
        /**
         * longitute : 23.632157
         * latitute : 23.632157
         */

        private double longitute;
        private double latitute;

        public double getLongitute() {
            return longitute;
        }

        public void setLongitute(double longitute) {
            this.longitute = longitute;
        }

        public double getLatitute() {
            return latitute;
        }

        public void setLatitute(double latitute) {
            this.latitute = latitute;
        }
    }

    public static class endBean {
        /**
         * longitute  : 23.632157
         * latitute  : 23.632157
         */

        private double longitute;
        private double latitute;

        public double getLongitute() {
            return longitute;
        }

        public void setLongitute(double longitute) {
            this.longitute = longitute;
        }

        public double getLatitute() {
            return latitute;
        }

        public void setLatitute(double latitute) {
            this.latitute = latitute;
        }
    }
}
