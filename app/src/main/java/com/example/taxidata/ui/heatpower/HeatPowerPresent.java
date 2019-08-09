package com.example.taxidata.ui.heatpower;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HeatPowerPresent implements HeatPowerContract.HeatPowerPresent {

    private HeatPowerContract.HeatPowerModel heatPowerModel;
    private HeatPowerContract.HeatPowerView heatPowerView;
    private static final String TAG = "HeatPowerPresent";

    public HeatPowerPresent(){
        heatPowerModel = new HeatPowerModel();
    }

    @Override
    public void updataHeatPoint(LatLng longitudeLatitude, String time) {
        if (heatPowerModel != null){
            heatPowerModel.requestHeatPoint(longitudeLatitude,time)
                    .subscribe(new Observer<HeatPointInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG,"onSubscribe");
                        }
                        @Override
                        public void onNext(HeatPointInfo heatPointInfo) {
                            if (heatPointInfo.getCode() == 1){
                                Log.d(TAG,"onNext");
                                //获取热点数据集
                                List<HeatPointInfo.DataBean> heatPointList = heatPointInfo.getData();
                                //构建热点数据集
                                List<LatLng> longitudeLaitudeList = new ArrayList<>();
                                for (HeatPointInfo.DataBean temp : heatPointList){
                                    double longitude = temp.getLongitude();
                                    double laitude = temp.getLatitude();
                                    //构建热点经纬度
                                    LatLng longitudeLaitude = new LatLng(laitude,longitude);
                                    longitudeLaitudeList.add(longitudeLaitude);
                                }
                                heatPowerView.showHeatPower(longitudeLaitudeList);
                            } else {
                                String errorMessage = heatPointInfo.getMsg();
                                ToastUtil.showShortToastBottom(errorMessage);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG,"异常");
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG,"onComplete");
                        }
                    });
        }
    }

    @Override
    public void attachView(HeatPowerContract.HeatPowerView view) {
        heatPowerView = view;
    }

    @Override
    public void detachView() {
        //防止内存泄漏
        heatPowerView = null;
    }
}
