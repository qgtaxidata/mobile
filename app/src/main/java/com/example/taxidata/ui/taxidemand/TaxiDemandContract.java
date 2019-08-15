package com.example.taxidata.ui.taxidemand;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiDemandInfo;

import io.reactivex.Observable;

public interface TaxiDemandContract {

    interface TaxiDemandView extends BaseView{
        void showChart(TaxiDemandInfo.DataBean dataBean);
    }

    interface TaxiDemandModel extends BaseModel{
        Observable<TaxiDemandInfo> getTaxiDemandInfo(int areaId, String time);
    }

    interface TaxiDemandPresent extends BasePresent<TaxiDemandView>{
        void getTaxiDemandInfo(Context context, int areaId, String time);
    }
}
