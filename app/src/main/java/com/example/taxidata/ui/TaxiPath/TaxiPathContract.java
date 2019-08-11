package com.example.taxidata.ui.TaxiPath;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiInfo;

import java.util.List;

import io.reactivex.Observable;

public interface TaxiPathContract {


    interface TaxiPathView extends BaseView{
        void showPath(List<TaxiInfo.DataBean> listInfo);
    }

    interface TaxiPathModel extends BaseModel{
        Observable<TaxiInfo> getTaxiInfo(double longitude, double latitude, String time);
        Observable<TaxiInfo> getTaxiPathInfo(String time, String licenseplateno);
    }

    interface TaxiPathPresent extends BasePresent<TaxiPathView>{
        void getTaxiInfo(Context context, double longitude, double latitude, String time);
        void getTaxiPathInfo(String time, String licenseplateno);
    }
}
