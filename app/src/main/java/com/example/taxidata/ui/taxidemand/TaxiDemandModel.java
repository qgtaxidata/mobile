package com.example.taxidata.ui.taxidemand;

import com.example.taxidata.bean.TaxiDemandInfo;
import com.example.taxidata.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TaxiDemandModel implements TaxiDemandContract.TaxiDemandModel {
    @Override
    public Observable<TaxiDemandInfo> getTaxiDemandInfo(int areaId, String time) {
        return RetrofitManager
                .getInstance()
                .getHttpService()
                .getTaxiDemandInfo(areaId, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
