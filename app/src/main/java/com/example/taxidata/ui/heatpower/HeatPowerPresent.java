package com.example.taxidata.ui.heatpower;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HeatPowerPresent implements HeatPowerContract.HeatPowerPresent {

    private HeatPowerContract.HeatPowerModel heatPowerModel;
    private HeatPowerContract.HeatPowerView heatPowerView;
    private static final String TAG = "HeatPowerPresent";
    /**
     * 是否暂停轮询，默认为false，即不暂停轮询
     */
    private boolean isPaused = false;

    public HeatPowerPresent(){
        heatPowerModel = new HeatPowerModel();
    }

    @Override
    public void heatPoint() {
        String time = null;
        if (heatPowerModel != null){
            //不暂停轮询
            isPaused = false;
            //每3秒轮询一次
            heatPowerView.showHideButton();
            Observable.interval(3,TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            heatPowerModel.requestHeatPoint(heatPowerView.getCentralLongitudeLatitude(),time)
                                    .subscribe(new Observer<HeatPointInfo>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(HeatPointInfo heatPointInfo) {
                                            if (heatPointInfo.getCode() == 1){
                                                Log.d(TAG,"onNext");
                                                //获取热点数据集
                                                List<HeatPointInfo.DataBean> heatPointList = heatPointInfo.getData();
                                                //构建热点数据集
                                                List<WeightedLatLng> longitudeLaitudeList = new ArrayList<>();
                                                for (HeatPointInfo.DataBean temp : heatPointList){
                                                    double longitude = temp.getLng();
                                                    double laitude = temp.getLat();
                                                    //权重
                                                    int count = temp.getCount();
                                                    //构建热点经纬度
                                                    LatLng longitudeLaitude = new LatLng(laitude,longitude);
                                                    //构造具有权重的经纬度对象
                                                    WeightedLatLng wLongitudeLaitude = new WeightedLatLng(longitudeLaitude,count);
                                                    //构造具有权重的经纬度对象数组
                                                    longitudeLaitudeList.add(wLongitudeLaitude);
                                                }
                                                //调试数据
                                                for (int i = 0; i < longitudeLaitudeList.size(); i++){
                                                    Log.d("HeatPowerPresent",i +" : " + longitudeLaitudeList.get(i).latLng);
                                                }
                                                Log.d("HeatPowerPresent", "------------------------------------------------------------------------------------------");
                                                heatPowerView.showHeatPower(longitudeLaitudeList);
                                            } else {
                                                //显示错误信息
                                                String errorMessage = heatPointInfo.getMsg();
                                                ToastUtil.showShortToastBottom(errorMessage);
                                                //暂停轮询
                                                pause();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.d(TAG,"异常");
                                            e.printStackTrace();
                                            //轮询结束，清空热力图，并显示显示热力图的按钮
                                            heatPowerView.hideHeatPower();
                                            heatPowerView.showShowButton();
                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.d(TAG,"onComplete");
                                            //已暂停，清空热力图
                                            if (isPaused){
                                                //轮询结束，清空热力图，并显示显示热力图的按钮
                                                heatPowerView.hideHeatPower();
                                                heatPowerView.showShowButton();
                                            }
                                        }
                                    });
                        }
                    })
                    .takeUntil(stopPredicat -> isPaused)
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                            Log.d(TAG,"第" + aLong + "轮询");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "对Error事件作出响应");
                            e.printStackTrace();
                            //出现异常，清空热力图，并显示显示热力图按钮
                            heatPowerView.hideHeatPower();
                            heatPowerView.showShowButton();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "对Complete事件作出响应");
                            //轮询结束，清空热力图，并显示显示热力图的按钮
                            heatPowerView.hideHeatPower();
                            heatPowerView.showShowButton();
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

    /**
     * 暂停轮询
     */
    @Override
    public void pause(){
        isPaused = true;
    }
}
