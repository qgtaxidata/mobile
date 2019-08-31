package com.example.taxidata.ui.IncomeRanking;

import android.content.Context;

import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.net.RetrofitManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IncomeRankingModel implements IncomeRankingContract.IncomeRankingModel {
    @Override
    public Observable<IncomeRankingInfo> getIncomeRankingInfo(int area, String date) {
        return RetrofitManager.getInstance()
                .getHttpService()
                .getIncomeRankingInfo(area, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<DriverConditionInfo> getDriverConditionInfo(int area, String date, String driverID) {
        return RetrofitManager.getInstance()
                .getHttpService()
                .getDriverConditionInfo(area, date, driverID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
