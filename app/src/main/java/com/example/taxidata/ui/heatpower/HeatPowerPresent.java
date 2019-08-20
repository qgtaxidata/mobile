package com.example.taxidata.ui.heatpower;

import android.util.Log;
import android.widget.Button;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.common.SharedPreferencesManager;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.StatusBar;
import com.example.taxidata.widget.StatusToast;

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
    private static final int DEFAULT_POLLING_TIME = 3;
    /**
     * 轮询间隔时间
     */
    private static int pollingTime = SharedPreferencesManager.getManager().getInt(SharedPreferencesManager.CONST_POLLING,DEFAULT_POLLING_TIME);
    private StatusBar statusBar;
    private Button hideButton;
    /**
     * 是否暂停轮询，默认为false，即不暂停轮询
     */
    private boolean isPaused = false;

    public HeatPowerPresent(StatusBar statusBar,Button hideButton){
        heatPowerModel = new HeatPowerModel();
        this.statusBar = statusBar;
        this.hideButton = hideButton;
    }

    @Override
    public void showRealTimeHeatPower(int area) {
        if (heatPowerModel != null){
            //不暂停轮询
            isPaused = false;
            //每3秒轮询一次
            heatPowerView.showHideButton();
            Observable.interval(pollingTime,TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong)throws Exception{
                            //TaxiApp.getAppNowTime()获得当前时间
                            Log.d(TAG,"appNowTime" + TaxiApp.getAppNowTime());
                            heatPowerModel.requestHeatPoint(area, TaxiApp.getAppNowTime())
                                    .subscribe(new Observer<HeatPointInfo>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(HeatPointInfo heatPointInfo) {
                                            if (heatPointInfo.getCode() == 1){
                                                List<WeightedLatLng> weightedLatLngs = getWeightedLatLng(heatPointInfo);
                                                heatPowerView.showHeatPower(weightedLatLngs);
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
                                            Log.d(TAG,"异常:" + e.getMessage());
                                            e.printStackTrace();
                                            //轮询结束，清空热力图，并显示显示热力图的按钮
                                            heatPowerView.hideHeatPower();
                                            //停止轮询
                                            pause();
                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.d(TAG,"onComplete");
                                            //已暂停，清空热力图
                                            if (isPaused){
                                                //轮询结束，清空热力图，并显示显示热力图的按钮
                                                heatPowerView.hideHeatPower();
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
                            heatPowerView.showError();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "对Complete事件作出响应");
                            //轮询结束，清空热力图，并显示显示热力图的按钮
                            heatPowerView.hideHeatPower();
                            heatPowerView.showError();
                        }
                    });
        }
    }

    @Override
    public void showHistoryHeatPower(int area, String time) {
        heatPowerModel.requestHeatPoint(area,time)
                .subscribe(new Observer<HeatPointInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HeatPointInfo heatPointInfo) {
                        if (heatPointInfo.getCode() == 1){
                            List<WeightedLatLng> weightedLatLngList = getWeightedLatLng(heatPointInfo);
                            heatPowerView.showHeatPower(weightedLatLngList);
                        }else {
                            StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,heatPointInfo.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        heatPowerView.showError();
                        heatPowerView.hideHeatPower();
                        heatPowerView.showError();
                    }

                    @Override
                    public void onComplete() {
                        //已切换状态,清空地图
                        if (statusBar.getStatus() != StatusBar.HISTORY){
                            heatPowerView.hideHeatPower();
                        }
                        if (hideButton.getText().toString().equals("显示")){
                            heatPowerView.hideHeatPower();
                        }
                    }
                });
    }

    @Override
    public void showFeatureHeatPower(int area, String featureTime, int algorithm) {
        heatPowerModel.requestFeatureHeatPoint(area,TaxiApp.getAppNowTime(),featureTime,algorithm)
                .subscribe(new Observer<HeatPointInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HeatPointInfo info) {
                        if (info.getCode() == 1){
                            List<WeightedLatLng> weightedLatLngList = getWeightedLatLng(info);
                            heatPowerView.showHeatPower(weightedLatLngList);
                        }else {
                            StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,info.getMsg());
                            heatPowerView.hideHeatPower();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //异常则清除热力图
                        heatPowerView.hideHeatPower();
                        heatPowerView.showError();
                    }

                    @Override
                    public void onComplete() {
                        if (statusBar.getStatus() != StatusBar.FEATURE){
                            heatPowerView.hideHeatPower();
                        }
                        if (hideButton.getText().toString().equals("显示")){
                            heatPowerView.hideHeatPower();
                        }
                    }
                });

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

    private List<WeightedLatLng> getWeightedLatLng(HeatPointInfo heatPointInfo){
        //获取热点数据集
        List<HeatPointInfo.DataBean> heatPointList = heatPointInfo.getData();
        //构建热点数据集
        List<WeightedLatLng> longitudeLaitudeList = new ArrayList<>();
        for (HeatPointInfo.DataBean temp : heatPointList) {
            double longitude = temp.getLng();
            double laitude = temp.getLat();
            //权重
            int count = temp.getCount();
            //构建热点经纬度
            LatLng longitudeLaitude = new LatLng(laitude, longitude);
            //构造具有权重的经纬度对象
            WeightedLatLng wLongitudeLaitude = new WeightedLatLng(longitudeLaitude, count);
            //构造具有权重的经纬度对象数组
            longitudeLaitudeList.add(wLongitudeLaitude);
        }
        return longitudeLaitudeList;
    }

    /**
     * 设置轮询时间
     * @param pollingTime 轮询时间
     */
    public static void setPollingTime(int pollingTime){
        HeatPowerPresent.pollingTime = pollingTime;
    }

    /**
     * 获得轮询时间
     * @return int
     */
    public static int getPollingTime(){
        return pollingTime;
    }
}
