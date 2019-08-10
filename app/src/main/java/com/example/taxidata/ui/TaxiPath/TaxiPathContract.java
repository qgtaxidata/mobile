package com.example.taxidata.ui.TaxiPath;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiInfo;

import io.reactivex.Observable;

public interface TaxiPathContract {


    interface TaxiPathView extends BaseView{

    }

    interface TaxiPathModel extends BaseModel{
        Observable<TaxiInfo> getTaxiInfo(double longitude, double latitude, String time);
    }

    interface TaxiPathPresent extends BasePresent<TaxiPathView>{
        void getTaxiInfo(Context context, double longitude, double latitude, String time);
    }
}
