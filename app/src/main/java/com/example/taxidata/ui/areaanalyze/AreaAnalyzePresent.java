package com.example.taxidata.ui.areaanalyze;

import android.content.Context;
import android.util.Log;

import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.example.taxidata.ui.areaincome.AreaIncomeContract;
import com.orhanobut.logger.Logger;

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
                Log.d("next", areaAnalyzeInfo.getMsg());
                if(areaAnalyzeInfo.getData() != null) {
                    view.sendData(areaAnalyzeInfo.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Logger.d(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void attachView(AreaAnalyzeContract.AreaAnalyzeView view) {
        this.view = view;
        model = new AreaAnalyzeModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
