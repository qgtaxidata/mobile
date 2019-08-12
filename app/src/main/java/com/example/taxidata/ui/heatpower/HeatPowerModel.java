package com.example.taxidata.ui.heatpower;

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
        HeatPointRequestBody info = new HeatPointRequestBody();
        info.setArea(area);
        // FIXME 时间写死
        info.setTime("2017-02-01 17:00:00");
        String latLngJson = GsonUtil.GsonString(info);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),latLngJson);
        return RetrofitManager.getInstance()
                .getHttpService()
                .getHeatPoint(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
