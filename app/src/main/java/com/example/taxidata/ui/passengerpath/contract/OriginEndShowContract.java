package com.example.taxidata.ui.passengerpath.contract;

import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.enity.PointInfo;

import java.util.List;

import io.reactivex.Observable;

public interface OriginEndShowContract {
    interface OriginEndShowView extends BaseView{
        /**
         * 展示历史记录
         * @param history
         */
        void showHistory(List<String> history);

        //TODO 未设计
        void showPath();
    }

    interface OriginEndShowPresent extends BasePresent<OriginEndShowView>{
        /**
         * 获得历史数据
         */
        void getHistory();

        /**
         * 保存历史记录
         * @param origin 起点
         * @param end 终点
         * @param originLng 起点经度
         * @param originLat 起点纬度
         * @param endLng 终点经度
         * @param endLat 终点纬度
         */
        void saveHistory(String origin,String end,
                         double originLng,double originLat,
                         double endLng,double endLat);

        /**
         * 处理路径
         */
        void managePath();
    }

    interface OriginEndShowModel {

        /**
         * 保存历史记录
         * @param origin 起点
         * @param end 终点
         * @param originLng 起点经度
         * @param originLat 起点纬度
         * @param endLng 终点经度
         * @param endLat 终点纬度
         */
        void saveOriginEnd(String origin,String end,
                           double originLng,double originLat,
                           double endLng,double endLat);

        /**
         * 获取历史记录
         * @return List<OriginEndInfo>
         */
        List<OriginEndInfo> getOriginEnd();

        /**
         * 查询路径
         * @return Observable
         */
        Observable<PointInfo> requestPath();
    }
}
