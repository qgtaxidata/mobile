package com.example.taxidata.ui.heatpower;

import android.view.MotionEvent;

import com.amap.api.maps.model.LatLng;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.PointInfo;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.util.GsonUtil;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HeatPowerModel implements HeatPowerContract.HeatPowerModel {

    @Override
    public Observable<HeatPointInfo> requestHeatPoint(LatLng point, String time) {
        PointInfo info = new PointInfo();
        info.setLatitude(23.209000);
        info.setLongitude(113.317390);
        String latLngJson = GsonUtil.GsonString(info);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),latLngJson);
        return RetrofitManager.getInstance()
                .getHttpService()
                .getHeatPoint(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
