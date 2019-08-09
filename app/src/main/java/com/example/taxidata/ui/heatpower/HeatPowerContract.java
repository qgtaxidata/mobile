package com.example.taxidata.ui.heatpower;

import com.amap.api.maps.model.LatLng;
import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HeatPointInfo;

import java.util.List;

import io.reactivex.Observable;

public interface HeatPowerContract {
    interface HeatPowerView extends BaseView {
        /**
         * 获取屏幕中心经纬度
         * @return LatLng
         */
        LatLng getCentralLongitudeLatitude();

        /**
         * 获取时间
         * @return String
         */
        String getTime();

        /**
         * 显示热力
         * @param heatPointList 热力坐标集合
         */
        void showHeatPower(List<LatLng> heatPointList);
    }

    interface HeatPowerPresent extends BasePresent<HeatPowerView> {

        /**
         * 更新热力数据
         * @param LongitudeLatitude 经纬度
         * @param time 时间
         */
        void updataHeatPoint(LatLng LongitudeLatitude,String time);
    }

    interface HeatPowerModel extends BaseModel{

        /**
         * 请求热点数据
         * @param point 热点
         * @param time 时间
         * @return Observable<HeatPointInfo>
         */
        Observable<HeatPointInfo> requestHeatPoint(LatLng point, String time);
    }
}
