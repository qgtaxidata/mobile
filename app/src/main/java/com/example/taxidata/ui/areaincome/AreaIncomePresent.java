package com.example.taxidata.ui.areaincome;

import android.content.Context;
import android.util.Log;

import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.AreaIncomeInfo;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AreaIncomePresent implements AreaIncomeContract.AreaIncomePresent {

    private AreaIncomeContract.AreaIncomeModel model;
    private AreaIncomeContract.AreaIncomeView view;

    @Override
    public void getAreaIncomeInfo(Context context, int area, String date) {
        view.showLoadingView();
        Log.d("P",date);
        model.getAreaIncomeInfo(area, date).subscribe(new Observer<AreaIncomeInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("sub",date);
            }

            @Override
            public void onNext(AreaIncomeInfo areaIncomeInfo) {
                Log.d("next", date);
                if(areaIncomeInfo.getCode()== 1&&areaIncomeInfo.getData()!=null){
                    view.showChart(areaIncomeInfo.getData());
                }else {
                    view.hideLoadingView();
                    StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,areaIncomeInfo.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("error",date);
                e.printStackTrace();
                Logger.d(e.getMessage());
                view.hideLoadingView();
                StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,"异常！请重试。");
            }

            @Override
            public void onComplete() {
                view.hideLoadingView();
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
