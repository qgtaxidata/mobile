package com.example.taxidata.ui.taxidemand;

import android.content.Context;

import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiDemandInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaxiDemandPresent implements TaxiDemandContract.TaxiDemandPresent {

    private TaxiDemandContract.TaxiDemandModel model;
    private TaxiDemandContract.TaxiDemandView view;

    @Override
    public void getTaxiDemandInfo(Context context, int areaId, String time) {
        model.getTaxiDemandInfo(areaId, time).subscribe(new Observer<TaxiDemandInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TaxiDemandInfo taxiDemandInfo) {
                view.showChart(taxiDemandInfo.getData());
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

    @Override
    public void attachView(TaxiDemandContract.TaxiDemandView view) {
        model = new TaxiDemandModel();
        this.view = view;
    }

    @Override
    public void detachView() {
        model = null;
        view = null;
    }
}
