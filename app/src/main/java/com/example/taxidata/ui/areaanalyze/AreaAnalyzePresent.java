package com.example.taxidata.ui.areaanalyze;

import android.content.Context;

import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.example.taxidata.ui.areaincome.AreaIncomeContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AreaAnalyzePresent implements AreaAnalyzeContract.AreaAnalyzePresent {

    private AreaAnalyzeContract.AreaAnalyzeModel model;
    private AreaAnalyzeContract.AreaAnalyzeView view;

    @Override
    public void getAreaAnalyzeInfo(Context context, int area, String date) {
        model.getAreaAnalyzeInfo(area, date).subscribe(new Observer<AreaAnalyzeInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AreaAnalyzeInfo areaAnalyzeInfo) {

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
    public void attachView(AreaAnalyzeContract.AreaAnalyzeView view) {
        this.view = null;
        model = new AreaAnalyzeModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
