package com.example.taxidata.ui.TaxiPath;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taxidata.bean.TaxiInfo;

import java.io.Serializable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaxiPathPresent implements TaxiPathContract.TaxiPathPresent{

    private TaxiPathContract.TaxiPathModel model;
    private TaxiPathContract.TaxiPathView view;

    @Override
    public void getTaxiInfo(Context context,double longitude, double latitude, String time) {
        Log.d("wxP1", time);
        if(model != null){
            model.getTaxiInfo(longitude, latitude, time).subscribe(new Observer<TaxiInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(TaxiInfo taxiInfo) {
                    Log.d("wxP4", time);
                    Intent intent = new Intent(context, TaxiInfoListActivity.class);
                    intent.putExtra("taxiInfo", (Serializable)taxiInfo.getData());
                    context.startActivity(intent);
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("wxP5", time);
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    Log.d("wxP6", time);
                }
            });
        }
    }


    @Override
    public void getTaxiPathInfo(String time, String licenseplateno) {
        if(model != null){
            model.getTaxiPathInfo(time, licenseplateno).subscribe(new Observer<TaxiInfo>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(TaxiInfo taxiPathInfo) {
                    view.showPath(taxiPathInfo.getData());
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    public void attachView(TaxiPathContract.TaxiPathView view) {
        this.view = view;
        model = new TaxiPathModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
