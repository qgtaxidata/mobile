package com.example.taxidata.ui.roadquality;

import android.util.Log;

import com.example.taxidata.bean.RoadQualityInfo;
import com.example.taxidata.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoadQualityModel implements RoadQualityContract.RoadQualityModel {
    @Override
    public Observable<RoadQualityInfo> getRoadQualityInfo(int areaId, String date) {
        Log.d("m","model");
        return RetrofitManager
                .getInstance()
                .getHttpService()
                .getRoadQualityInfo(areaId, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
