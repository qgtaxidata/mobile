package com.example.taxidata.ui.areaincome;

import com.example.taxidata.bean.AreaIncomeInfo;
import com.example.taxidata.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AreaIncomeModel implements AreaIncomeContract.AreaIncomeModel {
    @Override
    public Observable<AreaIncomeInfo> getAreaIncomeInfo(int area, String date) {
        return RetrofitManager
                .getInstance()
                .getHttpService()
                .getAreaIncomeInfo(area, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
