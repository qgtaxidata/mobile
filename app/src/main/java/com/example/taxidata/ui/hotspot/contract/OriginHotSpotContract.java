package com.example.taxidata.ui.hotspot.contract;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotOrigin;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public interface OriginHotSpotContract {

    interface OriginHotSpotModel  extends BaseModel {

        /**
         * Gets history origin list.
         *
         * @return the history origin list
         */
        public List<HotSpotOrigin> getHistoryOriginList();

        /**
         * 发送请求获取提示列表
         *
         * @param keyword the keyword
         */
        public void getHintList(String keyword) ;

        /**
         * Save hot spoy origin history.
         *
         * @param historyOrigin the history origin
         */
        public void  saveHotSpoyOriginHistory(String historyOrigin);

        public void removeOriginHistory(HotSpotOrigin hotSpotOrigin) ;


    }

    interface OriginHotSpotView  extends BaseView {

        /**
         * Show history origin list.
         *
         * @param hotSpotOrigins the hot spot origins
         */
        public void showHistoryOriginList(List<HotSpotOrigin> hotSpotOrigins);

        /**
         * 呈现 提示列表
         *
         * @param hintList the hint list
         */
        public void showHintHotSpotList(List<HotSpotHint> hintList);
    }

    interface OriginHotSpotPresenter extends BasePresent<OriginHotSpotView> {

        /**
         * Save origin hot spot history.
         *
         * @param orignHistory the orign history
         */
        public void saveOriginHotSpotHistory(String orignHistory);

        /**
         * Gets history origin list.
         *
         * @return the history origin list
         */
        public List<HotSpotOrigin> getHistoryOriginList();

        /**
         * 尝试获取 提示列表
         *
         * @param keyword the keyword
         */
        public void getHintList(String keyword) ;

        public void convertToLocation(String address, GeocodeSearch geocodeSearch);

        /**
         * 成功获取到了 提示的列表
         *
         * @param hintList the hint list
         */
        public void getHintListSuccess(List<HotSpotHint> hintList);


        public void removeOriginHistory(HotSpotOrigin hotSpotOrigin) ;
    }
}
