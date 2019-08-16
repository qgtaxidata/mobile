package com.example.taxidata.ui.TaxiPath;

import com.example.taxidata.bean.GetTaxiInfo;
import com.example.taxidata.bean.GetTaxiPathInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.net.PathRetrofitManager;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.util.GsonUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TaxiPathModel implements TaxiPathContract.TaxiPathModel {
    @Override
    public Observable<TaxiInfo> getTaxiInfo(int area, String time) {
        GetTaxiInfo getTaxiInfo = new GetTaxiInfo();
        getTaxiInfo.setArea(area);
        getTaxiInfo.setTime(time);
        String json = GsonUtil.GsonString(getTaxiInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return RetrofitManager.getInstance()
                .getHttpService()
                .getTaxiInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<TaxiPathInfo> getHistoryTaxiPathInfo(String time, String licenseplateno) {
        GetTaxiPathInfo getTaxiPathInfo = new GetTaxiPathInfo();
        getTaxiPathInfo.setTime(time);
        getTaxiPathInfo.setLicenseplateno(licenseplateno);
        String json = GsonUtil.GsonString(getTaxiPathInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return PathRetrofitManager.getInstance()
                .getHttpService()
                .getHistoryTaxiPathInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<TaxiPathInfo> getCurrentTaxiPathInfo(String time, String licenseplateno) {
        GetTaxiPathInfo getTaxiPathInfo = new GetTaxiPathInfo();
        getTaxiPathInfo.setTime(time);
        getTaxiPathInfo.setLicenseplateno(licenseplateno);
        String json = GsonUtil.GsonString(getTaxiPathInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return PathRetrofitManager.getInstance()
                .getHttpService()
                .getCurrentTaxiPathInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
