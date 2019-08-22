package com.example.taxidata.ui.IncomeRanking;

import android.content.Context;
import android.util.Log;

import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.widget.ChooseTaxiDialog;
import com.example.taxidata.widget.DriverConditionDialog;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class IncomeRankingPresent implements IncomeRankingContract.IncomeRankingPresent {

    private IncomeRankingContract.IncomeRankingModel model;
    private IncomeRankingContract.IncomeRankingView view;

    @Override
    public void getIncomeRankingInfo(Context context, int area, String date) {
        view.showLoadingView();
        model.getIncomeRankingInfo(area, date).subscribe(new Observer<IncomeRankingInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("wx","subscribe");
            }

            @Override
            public void onNext(IncomeRankingInfo incomeRankingInfo) {
                if(incomeRankingInfo.getCode() == 1){
                    Logger.d(incomeRankingInfo.getData());
                    view.showIncomeList(incomeRankingInfo.getData());
                }else {
                    view.hideLoadingView();
                    StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,incomeRankingInfo.getMsg());
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
                view.hideLoadingView();
            }
        });
    }

    @Override
    public void getDriverConditionInfo(Context context, int area, String date, String driverID, int rank, double income) {
        view.showLoadingView();
        model.getDriverConditionInfo(area, date, driverID).subscribe(new Observer<DriverConditionInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DriverConditionInfo driverConditionInfo) {
                if(driverConditionInfo.getCode() == 1&&driverConditionInfo.getData()!=null){
                    final DriverConditionDialog driverConditionDialog = new DriverConditionDialog(context, R.style.dialog,driverConditionInfo.getData(), rank, driverID, income);
                    driverConditionDialog.show();
                }else {
                    view.hideLoadingView();
                    StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,driverConditionInfo.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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
    public void attachView(IncomeRankingContract.IncomeRankingView view) {
        this.view = view;
        model = new IncomeRankingModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
