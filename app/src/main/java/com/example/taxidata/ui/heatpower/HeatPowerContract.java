package com.example.taxidata.ui.heatpower;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
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
         * 显示热力
         * @param heatPointList 热力坐标集合
         */
        void showHeatPower(List<WeightedLatLng> heatPointList);

        /**
         * 隐藏热力图
         */
        void hideHeatPower();

        /**
         * 显示隐藏按钮
         */
        void showHideButton();

        /**
         * 显示展示按钮
         */
        void showShowButton();
    }

    interface HeatPowerPresent extends BasePresent<HeatPowerView> {

        /**
         * 根据用户点击显示热力图或隐藏热力图
         */
        void heatPoint();

        /**
         * 暂停轮询
         */
        void pause();
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
