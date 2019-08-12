package com.example.taxidata.bean;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class HotSpotRouteRequest {

    /**
     * start : {"longitute":"23.632157","latitute":"23.632157"}
     * end : {"longitute":"23.632157","latitute":"23.632157"}
     */

    private StartBean start;
    private EndBean end;

    public StartBean getStart() {
        return start;
    }

    public void setStart(StartBean start) {
        this.start = start;
    }

    public EndBean getEnd() {
        return end;
    }

    public void setEnd(EndBean end) {
        this.end = end;
    }

    public static class StartBean {
        /**
         * longitute : 23.632157
         * latitute : 23.632157
         */

        private String longitute;
        private String latitute;

        public String getLongitute() {
            return longitute;
        }

        public void setLongitute(String longitute) {
            this.longitute = longitute;
        }

        public String getLatitute() {
            return latitute;
        }

        public void setLatitute(String latitute) {
            this.latitute = latitute;
        }
    }

    public static class EndBean {
        /**
         * longitute : 23.632157
         * latitute : 23.632157
         */

        private String longitute;
        private String latitute;

        public String getLongitute() {
            return longitute;
        }

        public void setLongitute(String longitute) {
            this.longitute = longitute;
        }

        public String getLatitute() {
            return latitute;
        }

        public void setLatitute(String latitute) {
            this.latitute = latitute;
        }
    }
}
