package com.example.taxidata.ui.heatpower;

import android.util.Log;
import android.widget.Button;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.common.SharedPreferencesManager;
import com.example.taxidata.net.RepeatTask;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.StatusBar;
import com.example.taxidata.widget.StatusToast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
    private Queue<RepeatTask> taskQueue = new ArrayDeque<>();
    /**
     * 轮询间隔时间
     */
    private static int pollingTime = SharedPreferencesManager.getManager().getInt(SharedPreferencesManager.CONST_POLLING,DEFAULT_POLLING_TIME);
    private StatusBar statusBar;
    private Button hideButton;
    /**
     * 是否暂停轮询，默认为false，即不暂停轮询
     */
    private volatile boolean isPaused = false;

    public HeatPowerPresent(StatusBar statusBar,Button hideButton){
        heatPowerModel = new HeatPowerModel();
        this.statusBar = statusBar;
        this.hideButton = hideButton;
    }

    @Override
    public void showRealTimeHeatPower(int area) {
        RepeatTask task = new RepeatTask(new RepeatTask.RepeatCallBackListener() {
            @Override
            public void onUpData(HeatPointInfo info) {
                if (info.getCode() == 1){
                    List<WeightedLatLng> weightedLatLngs = getWeightedLatLng(info);
                    heatPowerView.showHeatPower(weightedLatLngs);
                } else {
                    //显示错误信息
                    String errorMessage = info.getMsg();
                    ToastUtil.showShortToastBottom(errorMessage);
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                heatPowerView.hideHeatPower();
            }

            @Override
            public void onFinsh() {
                heatPowerView.hideHeatPower();
            }
        });

        //清除队列中的所有线程
        RepeatTask taskHead = null;
        while (taskQueue.size() != 0) {
            taskHead = taskQueue.remove();
            taskHead.pause();
        }
        taskQueue.offer(task);
        task.setRepeatTime(pollingTime);
        //开启轮询
        task.execute(area);
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
        heatPowerView.mapClear();
        RepeatTask task = taskQueue.poll();
        if (task != null) {
            task.pause();
        }
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

    @Override
    public int getTaskQueue() {
        return taskQueue.size();
    }
}
