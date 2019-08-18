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

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class TaxiPathPresent implements TaxiPathContract.TaxiPathPresent{

    private TaxiPathContract.TaxiPathModel model;
    private TaxiPathContract.TaxiPathView view;
    private boolean flag = false; //true时暂停轮询

    @Override
    public void getTaxiInfo(Context context,int area, String time) {
        Logger.d(time);
        Logger.d(area);
        if(model != null){
            model.getTaxiInfo(area, time).subscribe(new Observer<TaxiInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d("sub", "wx");
                }
                @Override
                public void onNext(TaxiInfo taxiInfo) {
                    Log.d("TaxiPathPresent",taxiInfo.getMsg());
                    Log.d("TaxiPathPresent",taxiInfo.getCode()+"");
                    if(taxiInfo.getData() !=null){
                        //初始化dialog并弹出
                        final ChooseTaxiDialog chooseTaxiDialog = new ChooseTaxiDialog(context, R.style.dialog, taxiInfo.getData());
                        chooseTaxiDialog.show();
                    } else {
                        StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "数据为空！请重试。");
                    }
                }
                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Logger.d(e.getMessage());
                    StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                }
                @Override
                public void onComplete() {

                }
            });
        }
    }


    @Override
    public void getHistoryTaxiPathInfo(Context context, String time, String licenseplateno) {
        view.showLoadingView();
        Log.d("p",licenseplateno);
        if(model!=null){
            model.getHistoryTaxiPathInfo(time, licenseplateno)
                    .subscribe(new Observer<TaxiPathInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("p","sub");
                        }

                        @Override
                        public void onNext(TaxiPathInfo historyTaxiPathInfo) {
                            Log.d("p","next");
                            if(historyTaxiPathInfo.getData() != null){
                                view.showHistoryPath(historyTaxiPathInfo.getData());
                            }else {
                                StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "数据为空！请重试。");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Logger.d(e.getMessage());
                            view.clearMap();
                            //view.hideLoadingView();
                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                        }

                        @Override
                        public void onComplete() {
                            view.hideLoadingView();
                        }
                    });
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCurrentTaxiPathInfo(Context context,String time, String licenseplateno) {
        flag = false;
        if(model != null){
            Observable.interval(8, TimeUnit.SECONDS)
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
                                            Logger.d(time);
                                            Logger.d(licenseplateno);
                                            Log.d("p","next");
                                            if (currentTaxiPathInfo.getCode() == 1){
                                                //显示实时路径
                                                view.showCurrentPath(currentTaxiPathInfo.getData());
                                            }else {
                                                StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                                                flag = true;
                                                view.clearMap();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            flag = true;
                                            Logger.d(e.getMessage());
                                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                                        }

                                        @Override
                                        public void onComplete() {
                                            view.clearMap();
                                            flag = true;
                                        }
                                    });
                        }
                    })
                    .takeUntil(stop -> flag)
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                            Logger.d("第"+aLong+"次");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            flag = true;
                            Logger.d(e.getMessage());
                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                        }

                        @Override
                        public void onComplete() {
                            view.clearMap();
                            Log.d("P","COM");
                            flag = true;
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

    @Override
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
