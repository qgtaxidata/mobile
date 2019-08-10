package com.example.taxidata.ui.TaxiPath;

import android.content.Context;
import android.content.Intent;

import com.example.taxidata.bean.TaxiInfo;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaxiPathPresent implements TaxiPathContract.TaxiPathPresent{

    private TaxiPathContract.TaxiPathModel model;
    private TaxiPathContract.TaxiPathView view;

    @Override
    public void getTaxiInfo(Context context,double longitude, double latitude, String time) {
        if(model != null){
            model.getTaxiInfo(longitude, latitude, time).subscribe(new Observer<TaxiInfo>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(TaxiInfo taxiInfo) {
                    Intent intent = new Intent(context, TaxiInfoFragment.class);
                    intent.putExtra("taxiInfo", (Serializable)taxiInfo.getData());
                    context.startActivity(intent);
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
