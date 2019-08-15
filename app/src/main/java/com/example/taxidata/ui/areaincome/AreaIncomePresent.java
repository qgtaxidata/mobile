package com.example.taxidata.ui.areaincome;

import android.content.Context;

import com.example.taxidata.bean.AreaIncomeInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AreaIncomePresent implements AreaIncomeContract.AreaIncomePresent {

    private AreaIncomeContract.AreaIncomeModel model;
    private AreaIncomeContract.AreaIncomeView view;

    @Override
    public void getAreaIncomeInfo(Context context, int area, String date) {
        model.getAreaIncomeInfo(area, date).subscribe(new Observer<AreaIncomeInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AreaIncomeInfo areaIncomeInfo) {

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
    public void attachView(AreaIncomeContract.AreaIncomeView view) {
        this.view = view;
        model = new AreaIncomeModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
