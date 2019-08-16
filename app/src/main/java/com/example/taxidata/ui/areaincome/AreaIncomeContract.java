package com.example.taxidata.ui.areaincome;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.AreaIncomeInfo;
import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.ui.IncomeRanking.IncomeRankingContract;

import java.util.List;

import io.reactivex.Observable;

public interface AreaIncomeContract {
    interface AreaIncomeView extends BaseView {

    }

    interface AreaIncomeModel extends BaseModel {
        Observable<AreaIncomeInfo> getAreaIncomeInfo(int area, String date);
    }

    interface AreaIncomePresent extends BasePresent<AreaIncomeView> {
        void getAreaIncomeInfo(Context context, int area, String date);

    }
}
