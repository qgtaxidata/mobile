package com.example.taxidata.ui.IncomeRanking;

import android.content.Context;

import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.GetDriverConditionInfo;
import com.example.taxidata.bean.GetIncomeRankingInfo;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.util.GsonUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class IncomeRankingModel implements IncomeRankingContract.IncomeRankingModel {
    @Override
    public Observable<IncomeRankingInfo> getIncomeRankingInfo(int area, String date) {
//        GetIncomeRankingInfo getIncomeRankingInfo = new GetIncomeRankingInfo();
//        getIncomeRankingInfo.setArea(area);
//        getIncomeRankingInfo.setDate(date);
//        String json = GsonUtil.GsonString(getIncomeRankingInfo);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        return RetrofitManager.getInstance()
                .getHttpService()
                .getIncomeRankingInfo(area, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<DriverConditionInfo> getDriverConditionInfo(int area, String date, String driverID) {
//        GetDriverConditionInfo getDriverConditionInfo = new GetDriverConditionInfo();
//        getDriverConditionInfo.setArea(area);
//        getDriverConditionInfo.setDate(date);
//        getDriverConditionInfo.setDriverID(driverID);
//        String json = GsonUtil.GsonString(getDriverConditionInfo);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        return RetrofitManager.getInstance()
                .getHttpService()
                .getDriverConditionInfo(area, date, driverID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
