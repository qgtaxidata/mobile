package com.example.taxidata.ui.TaxiPath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.util.ACache;
import com.example.taxidata.util.TimeChangeUtil;
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
    private int n = 0;

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
                        view.hideLoadingView();
                        final ChooseTaxiDialog chooseTaxiDialog = new ChooseTaxiDialog(context, R.style.dialog, taxiInfo);
                        chooseTaxiDialog.show();
                        //储存返回的车牌号数据
                        ACache aCache = ACache.get(context);
                        aCache.put("taxi_number",taxiInfo);
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
                            if(historyTaxiPathInfo.getData() != null&&historyTaxiPathInfo.getCode()==1){
                                view.showHistoryPath(historyTaxiPathInfo.getData());
                            }else {
                                view.hideLoadingView();
                                StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, historyTaxiPathInfo.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Logger.d(e.getMessage());
                            view.clearMap();
                            view.hideLoadingView();
                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                        }

                        @Override
                        public void onComplete() {
                            view.hideLoadingView();
                            view.clearMap();
                        }
                    });
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCurrentTaxiPathInfo(Context context,String time, String licenseplateno) {
        if(model != null){
            flag = false;
            n = 0;
            Observable.interval(10, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            Logger.d(TimeChangeUtil.transformToString(TaxiApp.getMillionTime()+50000*n));
                            Log.d("n:", n+"");
                            model.getCurrentTaxiPathInfo(TimeChangeUtil.transformToString(TaxiApp.getMillionTime()+50000*(n++)), licenseplateno)
                                    .subscribe(new Observer<TaxiPathInfo>() {

                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(TaxiPathInfo currentTaxiPathInfo) {
                                            Log.d("p","next");
                                            if (currentTaxiPathInfo.getCode() == 1&&currentTaxiPathInfo.getData()!=null){
                                                //显示实时路径
                                                view.showCurrentPath(currentTaxiPathInfo.getData());
                                            }else {
                                                Logger.d(currentTaxiPathInfo.getMsg());
                                                Logger.d(currentTaxiPathInfo.getCode());
                                                StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, currentTaxiPathInfo.getMsg());
                                                flag = true;
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            Logger.d(e.getMessage());
                                            flag = true;
                                            view.clearMap();
                                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.d("p","complete");
                                            view.clearMap();
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
                            Logger.d(e.getMessage());
                            view.clearMap();
                            StatusToast.getMyToast().ToastShow(context,null, R.mipmap.ic_sad, "异常！请重试。");
                        }

                        @Override
                        public void onComplete() {
                            Log.d("P","COM");
                            flag = true;
                            view.clearMap();
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
