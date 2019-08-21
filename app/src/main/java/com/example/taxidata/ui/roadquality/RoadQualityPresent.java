package com.example.taxidata.ui.roadquality;

import android.content.Context;
import android.util.Log;

import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.RoadQualityInfo;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RoadQualityPresent implements RoadQualityContract.RoadQualityPresent {

    private RoadQualityContract.RoadQualityModel model;
    private RoadQualityContract.RoadQualityView view;

    @Override
    public void getRoadQualityInfo(Context context, int areaId, String date) {
        view.showLoadingView();
        model.getRoadQualityInfo(areaId, date).subscribe(new Observer<RoadQualityInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("p","sub");
            }

            @Override
            public void onNext(RoadQualityInfo roadQualityInfo) {
                Log.d("p","next");
                if(roadQualityInfo.getCode()==1&&roadQualityInfo.getData()!=null){
                    view.sendData(roadQualityInfo.getData());
                }else {
                    view.hideLoadingView();
                    StatusToast.getMyToast().ToastShow(context, null, R.mipmap.ic_sad,roadQualityInfo.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Logger.d(e.getMessage());
                view.hideLoadingView();
                StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,"异常！请重试。");
            }

            @Override
            public void onComplete() {
                Log.d("p","com");
                view.hideLoadingView();
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
