package com.example.taxidata.ui.IncomeRanking;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.IncomeRankingInfo;

import java.util.List;

import io.reactivex.Observable;

public interface IncomeRankingContract {

    interface IncomeRankingView extends BaseView {
        void showIncomeList(List<IncomeRankingInfo.DataBean> list);
        void showLoadingView();
        void hideLoadingView();
    }

    interface IncomeRankingModel extends BaseModel {
        Observable<IncomeRankingInfo> getIncomeRankingInfo( int area, String date);
        Observable<DriverConditionInfo> getDriverConditionInfo(int area, String date, String driverID);
    }

    interface IncomeRankingPresent extends BasePresent<IncomeRankingView> {
        void getIncomeRankingInfo(Context context, int area, String date);
        void getDriverConditionInfo(Context context,int area, String date, String driverID, int rank, double income);
    }
}
