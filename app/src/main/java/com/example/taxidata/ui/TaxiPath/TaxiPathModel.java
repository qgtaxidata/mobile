package com.example.taxidata.ui.TaxiPath;

import android.util.Log;

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
import okhttp3.ResponseBody;

public class TaxiPathModel implements TaxiPathContract.TaxiPathModel {
    @Override
    public Observable<TaxiInfo> getTaxiInfo(double longitude, double latitude, String time) {
        GetTaxiInfo getTaxiInfo = new GetTaxiInfo();
        getTaxiInfo.setLatitude(latitude);
        getTaxiInfo.setLongitude(longitude);
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
    public Observable<TaxiPathInfo> getTaxiPathInfo(String time, String licenseplateno) {
        GetTaxiPathInfo getTaxiPathInfo = new GetTaxiPathInfo();
        getTaxiPathInfo.setTime(time);
        getTaxiPathInfo.setLicenseplateno(licenseplateno);
        String json = GsonUtil.GsonString(getTaxiPathInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return PathRetrofitManager.getInstance()
                .getHttpService()
                .getTaxiPathInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
