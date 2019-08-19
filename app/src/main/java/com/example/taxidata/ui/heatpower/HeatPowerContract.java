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

        /**
         * 展示错误信息
         */
        void showError();
    }

    interface HeatPowerPresent extends BasePresent<HeatPowerView> {

        /**
         * 实时热力图
         * @param area 区域
         */
        void showRealTimeHeatPower(int area);

        /**
         * 历史热力图
         * @param area
         * @param time
         */
        void showHistoryHeatPower(int area,String time);

        /**
         * 未来热力图
         * @param area 区域
         * @param featureTime 未来时间
         * @param algorithm 算法
         */
        void showFeatureHeatPower(int area,String featureTime,int algorithm);

        /**
         * 暂停轮询
         */
        void pause();
    }

    interface HeatPowerModel extends BaseModel{

        /**
         * 请求热点数据
         * @param area 区域
         * @param time 时间
         * @return Observable<HeatPointInfo>
         */
        Observable<HeatPointInfo> requestHeatPoint(int area, String time);

        /**
         * 请求未来热点
         * @param area 地区
         * @param nowTime 现在时间
         * @param futureTime 未来时间
         * @param algorithm 算法(0~2)
         * @return
         */
        Observable<HeatPointInfo> requestFeatureHeatPoint(int area,String nowTime, String futureTime,int algorithm);
    }
}
