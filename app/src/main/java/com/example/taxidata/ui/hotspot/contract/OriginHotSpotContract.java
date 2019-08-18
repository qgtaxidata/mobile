package com.example.taxidata.ui.hotspot.contract;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.bean.HotSpotRequestInfo;
import com.example.taxidata.bean.HotSpotRouteInfo;
import com.example.taxidata.bean.HotSpotRouteRequest;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * The interface Origin hot spot contract.
 *
 * author: ODM
 * date: 2019 /8/12
 */

public interface OriginHotSpotContract {

    /**
     * The interface Origin hot spot model.
     */
    interface OriginHotSpotModel  extends BaseModel {


        /**
         * Request route data observable.
         *
         * @param request the request
         * @return the observable
         */
         Observable<PathInfo> requestRouteData(HotSpotRouteRequest  request);

        /**
         * Gets history origin list.
         *
         * @return the history origin list
         */
         List<HotSpotOrigin> getHistoryOriginList();

        /**
         * 发送请求获取提示列表
         *
         * @param keyword the keyword
         */
         void getHintList(String keyword) ;

        /**
         * Save hot spoy origin history.
         *
         * @param historyOrigin the history origin
         */
         void  saveHotSpoyOriginHistory(String historyOrigin);

        /**
         * Remove origin history.
         *
         * @param hotSpotOrigin the hot spot origin
         */
         void removeOriginHistory(HotSpotOrigin hotSpotOrigin) ;


    }

    /**
     * The interface Origin hot spot view.
     */
    interface OriginHotSpotView  extends BaseView {

        /**
         * Show history origin list.
         *
         * @param hotSpotOrigins the hot spot origins
         */
         void showHistoryOriginList(List<HotSpotOrigin> hotSpotOrigins);

        /**
         * 呈现 提示列表
         *
         * @param hintList the hint list
         */
         void showHintHotSpotList(List<HotSpotHint> hintList);

        /**
         * 请求热点路径信息成功
         *
         * @param info the info
         */
         void  requestSuccess(PathInfo info);
    }

    /**
     * The interface Origin hot spot presenter.
     */
    interface OriginHotSpotPresenter extends BasePresent<OriginHotSpotView> {


        /**
         * Gets 路径 data.
         *
         * @param request the request
         */
         void  getRouteData(HotSpotRouteRequest request );

        /**
         * Save origin hot spot history.
         *
         * @param orignHistory the orign history
         */
         void saveOriginHotSpotHistory(String orignHistory);

        /**
         * Gets history origin list.
         *
         * @return the history origin list
         */
         List<HotSpotOrigin> getHistoryOriginList();

        /**
         * 尝试获取 提示列表
         *
         * @param keyword the keyword
         */
         void getHintList(String keyword) ;


        /**
         * 将地址转换成坐标
         *
         * @param originAddress the origin address
         * @param request       the request
         * @param geocodeSearch the geocode search
         */
         void convertToLocation(String originAddress,HotSpotRouteRequest request, GeocodeSearch geocodeSearch);

        /**
         * 成功获取到了 提示的列表
         *
         * @param hintList the hint list
         */
         void getHintListSuccess(List<HotSpotHint> hintList);


        /**
         * Remove origin history.
         *
         * @param hotSpotOrigin the hot spot origin
         */
         void removeOriginHistory(HotSpotOrigin hotSpotOrigin) ;
    }
}
