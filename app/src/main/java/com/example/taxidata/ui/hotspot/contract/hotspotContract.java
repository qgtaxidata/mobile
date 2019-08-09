package com.example.taxidata.ui.hotspot.contract;

import com.example.taxidata.bean.hotSpotCallBackInfo;

import java.util.List;

/**
 * The interface Hotspot contract.
 *
 * @author: ODM
 * @date: 2019 /8/9
 */
public interface hotspotContract {

    /**
     * The interface Model.
     */
    interface Model {

        /**
         * 向服务器请求获取热点推荐坐标
         *
         * @param longitude the longitude
         * @param latitude  the latitude
         * @param time      the time
         * @return the list
         */
        public List<hotSpotCallBackInfo> requestHotSpot(double longitude ,double latitude ,String time) ;

    }

    /**
     * The interface View.
     */
    interface View {

        /**
         * 在页面上展示获取到的推荐热点
         *
         * @param hotSpotCallBackInfoList the hot spot call back info list
         */
        public void showHotSpot(List<hotSpotCallBackInfo>  hotSpotCallBackInfoList);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter {

        /**
         * P层调用M层获取热点列表
         *
         * @param longitude the longitude
         * @param latitude  the latitude
         * @param time      the time
         * @return the hot spot
         */
        public  List<hotSpotCallBackInfo>  getHotSpot(double longitude ,double latitude ,String time) ;

    }
}
