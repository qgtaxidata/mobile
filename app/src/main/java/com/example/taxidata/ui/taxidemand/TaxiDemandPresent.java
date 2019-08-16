package com.example.taxidata.ui.taxidemand;

import android.content.Context;
import android.util.Log;

import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiDemandInfo;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaxiDemandPresent implements TaxiDemandContract.TaxiDemandPresent {

    private TaxiDemandContract.TaxiDemandModel model;
    private TaxiDemandContract.TaxiDemandView view;

    @Override
    public void getTaxiDemandInfo(Context context, int areaId, String time) {
        if(model!=null) {
            model.getTaxiDemandInfo(areaId, time).subscribe(new Observer<TaxiDemandInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d("subscribe", time);
                }

                @Override
                public void onNext(TaxiDemandInfo taxiDemandInfo) {
                    Log.d("next", time);
                    view.showChart(taxiDemandInfo.getData());
                    Log.d("next", time);
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("error", time);
                    e.printStackTrace();
                    Logger.d(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
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
