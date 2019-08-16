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
     * data : [{"route":[{"lng":113.35485108537304,"lat":22.98518138200364},{"lng":113.60881831645261,"lat":23.427348283487405},{"lng":113.22967956725822,"lat":23.083167964816138},{"lng":113.29521107768714,"lat":23.12343575347576},{"lng":113.23164749634579,"lat":23.08303074892986},{"lng":113.35078366915705,"lat":23.085251183627626},{"lng":113.27209661077315,"lat":23.12241792205843},{"lng":113.38708156173223,"lat":23.003491003560264},{"lng":113.28242027292747,"lat":23.208719466544952},{"lng":113.58939575366549,"lat":22.635539958485683},{"lng":113.32042772909656,"lat":23.03584072287595},{"lng":113.29566021934356,"lat":23.12425720119194},{"lng":113.2711044965782,"lat":23.125681528092855},{"lng":113.58888500999923,"lat":22.6359230445728},{"lng":113.22176051211754,"lat":23.410866799777054},{"lng":113.45617091918334,"lat":23.156956786984896},{"lng":113.05677893622983,"lat":23.402886409368595}],"time":30,"distance":30},{"route":[{"lng":113.35485108537304,"lat":22.98518138200364},{"lng":113.60881831645261,"lat":23.427348283487405},{"lng":113.22967956725822,"lat":23.083167964816138},{"lng":113.29521107768714,"lat":23.12343575347576},{"lng":113.23164749634579,"lat":23.08303074892986},{"lng":113.35078366915705,"lat":23.085251183627626},{"lng":113.27209661077315,"lat":23.12241792205843},{"lng":113.38708156173223,"lat":23.003491003560264},{"lng":113.28242027292747,"lat":23.208719466544952},{"lng":113.58939575366549,"lat":22.635539958485683},{"lng":113.32042772909656,"lat":23.03584072287595},{"lng":113.29566021934356,"lat":23.12425720119194},{"lng":113.2711044965782,"lat":23.125681528092855},{"lng":113.58888500999923,"lat":22.6359230445728},{"lng":113.22176051211754,"lat":23.410866799777054},{"lng":113.45617091918334,"lat":23.156956786984896},{"lng":113.05677893622983,"lat":23.402886409368595}],"time":30,"distance":30},{"route":[{"lng":113.35485108537304,"lat":22.98518138200364},{"lng":113.60881831645261,"lat":23.427348283487405},{"lng":113.22967956725822,"lat":23.083167964816138},{"lng":113.29521107768714,"lat":23.12343575347576},{"lng":113.23164749634579,"lat":23.08303074892986},{"lng":113.35078366915705,"lat":23.085251183627626},{"lng":113.27209661077315,"lat":23.12241792205843},{"lng":113.38708156173223,"lat":23.003491003560264},{"lng":113.28242027292747,"lat":23.208719466544952},{"lng":113.58939575366549,"lat":22.635539958485683},{"lng":113.32042772909656,"lat":23.03584072287595},{"lng":113.29566021934356,"lat":23.12425720119194},{"lng":113.2711044965782,"lat":23.125681528092855},{"lng":113.58888500999923,"lat":22.6359230445728},{"lng":113.22176051211754,"lat":23.410866799777054},{"lng":113.45617091918334,"lat":23.156956786984896},{"lng":113.05677893622983,"lat":23.402886409368595}],"time":30,"distance":30}]
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

        private int time;
        private int distance;
        private List<RouteBean> route;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public List<RouteBean> getRoute() {
            return route;
        }

        public void setRoute(List<RouteBean> route) {
            this.route = route;
        }

        public static class RouteBean {

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
