package com.example.taxidata.ui.heatpower;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.HeatPointRequestBody;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.util.GsonUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
}
