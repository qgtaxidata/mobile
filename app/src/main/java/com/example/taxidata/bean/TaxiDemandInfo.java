package com.example.taxidata.bean;

import java.util.List;

public class TaxiDemandInfo {


    /**
     * msg : success
     * code : 1
     * data : {"graph_data":[{"title":"一个小时前","demand":79},{"title":"当前时间","demand":73},{"title":"一个小时后","demand":92}],"graph_title":"花都区需求分析及预测"}
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
         * graph_data : [{"title":"一个小时前","demand":79},{"title":"当前时间","demand":73},{"title":"一个小时后","demand":92}]
         * graph_title : 花都区需求分析及预测
         */

        private String graph_title;
        private List<GraphDataBean> graph_data;

        public String getGraph_title() {
            return graph_title;
        }

        public void setGraph_title(String graph_title) {
            this.graph_title = graph_title;
        }

        public List<GraphDataBean> getGraph_data() {
            return graph_data;
        }

        public void setGraph_data(List<GraphDataBean> graph_data) {
            this.graph_data = graph_data;
        }

        public static class GraphDataBean {
            /**
             * title : 一个小时前
             * demand : 79
             */

            private String title;
            private int demand;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getDemand() {
                return demand;
            }

            public void setDemand(int demand) {
                this.demand = demand;
            }
        }
    }
}
