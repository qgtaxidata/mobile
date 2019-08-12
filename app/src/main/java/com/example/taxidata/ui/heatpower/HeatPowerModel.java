package com.example.taxidata.ui.heatpower;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HeatPowerModel implements HeatPowerContract.HeatPowerModel {

    @Override
    public Observable<HeatPointInfo> requestHeatPoint(int area, String time) {
        Log.d("HeatPowerModel",area + "");
        Log.d("HeatPowerModel",time);
        return RetrofitManager.getInstance()
                .getHttpService()
                .getHeatPoint(area,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HeatPointInfo> requestFeatureHeatPoint(int area, String nowTime, String futureTime, int algorithm) {
        return RetrofitManager.getInstance()
                .getHttpService()
                .getFeatureHeatPoint(area,nowTime,futureTime,algorithm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
