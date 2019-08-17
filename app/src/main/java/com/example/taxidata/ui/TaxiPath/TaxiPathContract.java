package com.example.taxidata.ui.TaxiPath;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;

import java.util.List;

import io.reactivex.Observable;

public interface TaxiPathContract {


    interface TaxiPathView extends BaseView{
        void showHistoryPath(List<TaxiPathInfo.DataBean> listInfo);
        void showCurrentPath(List<TaxiPathInfo.DataBean> listInfo);
        void clearMap();
    }

    interface TaxiPathModel extends BaseModel{
        Observable<TaxiInfo> getTaxiInfo(int area, String time);
        Observable<TaxiPathInfo> getHistoryTaxiPathInfo(String time, String licenseplateno);
        Observable<TaxiPathInfo> getCurrentTaxiPathInfo(String time, String licenseplateno);
    }

    interface TaxiPathPresent extends BasePresent<TaxiPathView>{
        void setFlag(boolean flag);
        void getTaxiInfo(Context context, int area, String time);
        void getCurrentTaxiPathInfo(Context context,String time, String licenseplateno);
        void getHistoryTaxiPathInfo(Context context,String time, String licenseplateno);
    }
}
