package com.example.taxidata.ui.hotspot.contract;

import com.amap.api.services.geocoder.GeocodeSearch;
import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;


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

        /**
         * 打开数据库获取历史搜索列表
         *
         * @return the history search list
         */
        public List<HotSpotHistorySearch>  getHistorySearchList();




        /**
         * 发送请求获取提示列表
         *
         * @param keyword the keyword
         */
        public void getHintList(String keyword) ;

        /**
         * Save hot spot search history.
         *
         * @param historyHotSpot the history hot spot
         */
        public void  saveHotSpotSearchHistory(String historyHotSpot) ;





        public void  removeHistory(HotSpotHistorySearch historySearch);


    }

    /**
     * The interface View.
     */
    interface HotSpotView extends BaseView {

        /**
         * 在V层页面上展示获取到的推荐热点列表
         *
         * @param hotSpotCallBackInfoList the hot spot call back info list
         */
        public void showHotSpot(List<HotSpotCallBackInfo.DataBean> hotSpotCallBackInfoList);

        /**
         * 呈现 历史搜索列表
         *
         * @param hotSpotHistorySearchList the hot spot history search list
         */
        public void showHistorySearchList(List<HotSpotHistorySearch> hotSpotHistorySearchList);



        /**
         * 呈现 提示列表
         *
         * @param hintList the hint list
         */
        public void showHintHotSpotList(List<HotSpotHint> hintList);

        /**
         * Hot spot chsen success.
         */
        public void  hotSpotChosenSuccess();
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
        public void getHotSpotData(double longitude, double latitude, String time) ;

        /**
         * 获取历史搜索的列表
         *
         * @return the history search list
         */
        public  List<HotSpotHistorySearch>  getHistorySearchList();



        /**
         * 尝试获取 提示列表
         *
         * @param keyword the keyword
         */
        public void getHintList(String keyword) ;

        /**
         * 成功获取到了 提示的列表
         *
         * @param hintList the hint list
         */
        public void getHintListSuccess(List<HotSpotHint> hintList);

        /**
         * Save hot spot search history.
         *
         * @param historyHotSpot the history hot spot
         */
        public void  saveHotSpotSearchHistory(String historyHotSpot) ;



        /**
         * 将地址转换成为地图的坐标
         *
         * @param address       the address
         * @param geocodeSearch the geocode search
         */
        public void convertToLocation(String address ,GeocodeSearch geocodeSearch );


        /**
         * Convert to address name.
         *
         * @param dataBean      the data bean
         * @param geocodeSearch the geocode search
         */
        public void convertToAddressName(HotSpotCallBackInfo.DataBean dataBean , GeocodeSearch geocodeSearch) ;


        public void  removeHistory(HotSpotHistorySearch historySearch);


     }


}
