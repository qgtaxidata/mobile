package com.example.taxidata.ui.roadquality;

import android.content.Context;

import com.example.taxidata.bean.RoadQualityInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RoadQualityPresent implements RoadQualityContract.RoadQualityPresent {

    private RoadQualityContract.RoadQualityModel model;
    private RoadQualityContract.RoadQualityView view;

    @Override
    public void getRoadQualityInfo(Context context, int areaId, String date) {
        model.getRoadQualityInfo(areaId, date).subscribe(new Observer<RoadQualityInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RoadQualityInfo roadQualityInfo) {

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
    public void attachView(RoadQualityContract.RoadQualityView view) {
        this.view = view;
        model = new RoadQualityModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
