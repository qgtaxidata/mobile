package com.example.taxidata.ui.hotspot.contract;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotCallBackInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * The interface Hotspot contract.
 *
 * @author: ODM
 * @date: 2019 /8/9
 */
public interface HotSpotContract {

    /**
     * The interface Model.
     */
    interface Model extends BaseModel {




        /**
         * 向服务器请求获取热点推荐坐标
         *
         * @param longitude the longitude
         * @param latitude  the latitude
         * @param time      the time
         * @return the observable
         */
        public Observable<HotSpotCallBackInfo> requestHotSpotInfo(double longitude, double latitude, String time);

    }

    /**
     * The interface View.
     */
    interface HotSpotView extends BaseView {

        /**
         * 在V层页面上展示获取到的推荐热点
         *
         * @param hotSpotCallBackInfoList the hot spot call back info list
         */
        public void showHotSpot(List<HotSpotCallBackInfo.HotSpotBean>  hotSpotCallBackInfoList);
    }

    /**
     * The interface Presenter.
     */
    interface Presenter extends BasePresent<HotSpotView> {


        /**
         * P层调用M层获取热点列表
         *
         * @param longitude the longitude
         * @param latitude  the latitude
         * @param time      the time
         */
        public void getHotSpotData(double longitude , double latitude , String time) ;


    }
}
