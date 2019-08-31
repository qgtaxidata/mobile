package com.example.taxidata.bean;

import java.util.List;

public class RoadQualityInfo {

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

        private AverageTimeBean average_time;
        private DensityBean density;
        private FlowBean flow;

        public AverageTimeBean getAverage_time() {
            return average_time;
        }

        public void setAverage_time(AverageTimeBean average_time) {
            this.average_time = average_time;
        }

        public DensityBean getDensity() {
            return density;
        }

        public void setDensity(DensityBean density) {
            this.density = density;
        }

        public FlowBean getFlow() {
            return flow;
        }

        public void setFlow(FlowBean flow) {
            this.flow = flow;
        }

        public static class AverageTimeBean {

            private String type;
            private List<List<String>> x;
            private List<List<Float>> y;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<List<String>> getX() {
                return x;
            }

            public void setX(List<List<String>> x) {
                this.x = x;
            }

            public List<List<Float>> getY() {
                return y;
            }

            public void setY(List<List<Float>> y) {
                this.y = y;
            }
        }

        public static class DensityBean {

            private String type;
            private List<List<String>> x;
            private List<List<Float>> y;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<List<String>> getX() {
                return x;
            }

            public void setX(List<List<String>> x) {
                this.x = x;
            }

            public List<List<Float>> getY() {
                return y;
            }

            public void setY(List<List<Float>> y) {
                this.y = y;
            }
        }

        public static class FlowBean {

            private String type;
            private List<List<String>> x;
            private List<List<Float>> y;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<List<String>> getX() {
                return x;
            }

            public void setX(List<List<String>> x) {
                this.x = x;
            }

            public List<List<Float>> getY() {
                return y;
            }

            public void setY(List<List<Float>> y) {
                this.y = y;
            }
        }
    }
}
