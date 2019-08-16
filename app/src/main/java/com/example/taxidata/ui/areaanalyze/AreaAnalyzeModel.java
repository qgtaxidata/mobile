package com.example.taxidata.ui.areaanalyze;

import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.example.taxidata.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AreaAnalyzeModel implements AreaAnalyzeContract.AreaAnalyzeModel {
    @Override
    public Observable<AreaAnalyzeInfo> getAreaAnalyzeInfo(int area, String date) {
        return RetrofitManager.getInstance()
                .getHttpService()
                .getAreaAnalyzeInfo(area, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
