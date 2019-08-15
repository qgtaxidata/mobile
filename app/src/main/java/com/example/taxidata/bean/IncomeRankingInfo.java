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


//    /**
//     * msg : “success”
//     * code : 1
//     * income : [{"rank":1,"driverID":"00051787","income":835},{"rank":2,"driverID":"00045034","income":603},{"rank":3,"driverID":"00050287","income":389},{"rank":4,"driverID":"00028693","income":374},{"rank":5,"driverID":"00058295","income":359},{"rank":6,"driverID":"00040588","income":359},{"rank":7,"driverID":"00077521","income":358},{"rank":8,"driverID":"00018486","income":348},{"rank":9,"driverID":"00065367","income":342},{"rank":10,"driverID":"00072742","income":332},{"rank":11,"driverID":"00064109","income":319},{"rank":12,"driverID":"00052468","income":312},{"rank":13,"driverID":"00057818","income":311},{"rank":14,"driverID":"00014729","income":304},{"rank":15,"driverID":"00035585","income":300},{"rank":16,"driverID":"00051587","income":299},{"rank":17,"driverID":"00033041","income":287},{"rank":18,"driverID":"00076333","income":285},{"rank":19,"driverID":"00062276","income":274},{"rank":20,"driverID":"00050375","income":269}]
//     */
//
//    private String msg;
//    private String code;
//    private List<IncomeBean> income;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public List<IncomeBean> getIncome() {
//        return income;
//    }
//
//    public void setIncome(List<IncomeBean> income) {
//        this.income = income;
//    }
//
//    public static class IncomeBean {
//        /**
//         * rank : 1
//         * driverID : 00051787
//         * income : 835.0
//         */
//
//        private int rank;
//        private String driverID;
//        private double income;
//
//        public int getRank() {
//            return rank;
//        }
//
//        public void setRank(int rank) {
//            this.rank = rank;
//        }
//
//        public String getDriverID() {
//            return driverID;
//        }
//
//        public void setDriverID(String driverID) {
//            this.driverID = driverID;
//        }
//
//        public double getIncome() {
//            return income;
//        }
//
//        public void setIncome(double income) {
//            this.income = income;
//        }
//    }
}
