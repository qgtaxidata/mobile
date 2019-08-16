package com.example.taxidata.ui.TaxiPath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.taxidata.R;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.widget.ChooseTaxiDialog;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
    public void getHistoryTaxiPathInfo(Context context, String time, String licenseplateno) {
        if(model!=null){
            model.getHistoryTaxiPathInfo(time, licenseplateno)

                    .subscribe(new Observer<TaxiPathInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TaxiPathInfo historyTaxiPathInfo) {
                            view.showHistoryPath(historyTaxiPathInfo.getData());
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Logger.d(e.getMessage());
                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                        }

                        @Override
                        public void onComplete() {
                            view.clearMap();
                        }
                    });
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCurrentTaxiPathInfo(Context context,String time, String licenseplateno) {
        if(model != null){
            Observable.interval(15, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            model.getCurrentTaxiPathInfo(time, licenseplateno)

                                    .subscribe(new Observer<TaxiPathInfo>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(TaxiPathInfo currentTaxiPathInfo) {
                                            //显示实时路径
                                            view.showCurrentPath(currentTaxiPathInfo.getData());
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            Logger.d(e.getMessage());
                                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                                        }

                                        @Override
                                        public void onComplete() {
                                            view.clearMap();
                                        }
                                    });
                        }
                    })
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {

                        }

                        @Override
                        public void onError(Throwable e) {

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
