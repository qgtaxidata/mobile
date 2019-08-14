package com.example.taxidata.ui.TaxiPath;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taxidata.R;
import com.example.taxidata.adapter.TaxiOnClickListener;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.widget.ChooseTaxiDialog;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.ResourceObserver;
import okhttp3.ResponseBody;

public class TaxiPathPresent implements TaxiPathContract.TaxiPathPresent{

    private TaxiPathContract.TaxiPathModel model;
    private TaxiPathContract.TaxiPathView view;


    @Override
    public void getTaxiInfo(Context context,int area, String time) {
        Log.d("wxP1", time);
        if(model != null){
            model.getTaxiInfo(area, time).subscribe(new Observer<TaxiInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                }
                @Override
                public void onNext(TaxiInfo taxiInfo) {
                    //初始化dialog并弹出
                    final ChooseTaxiDialog chooseTaxiDialog = new ChooseTaxiDialog(context, R.style.dialog,taxiInfo.getData());
                    chooseTaxiDialog.show();
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
    public void getTaxiPathInfo(Context context,String time, String licenseplateno) {
        Log.d("wxP1", time);
        if(model != null){
            model.getTaxiPathInfo(time, licenseplateno).subscribe(new Observer<TaxiPathInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d("wxP2", time);
                }

                @Override
                public void onNext(TaxiPathInfo taxiPathInfo) {
                    Log.d("wxP3", time);
                    view.showPath(taxiPathInfo.getData());
                    Log.d("wxP4", time);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Logger.d(e.getMessage());
                    StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "网络连接超时！请重试。");
                }

                @Override
                public void onComplete() {
                    Log.d("wxP6", time);
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
