package com.example.taxidata.ui.TaxiPath;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface TaxiPathContract {


    interface TaxiPathView extends BaseView{
        void showPath(List<TaxiPathInfo.DataBean> listInfo);
        void initList(List<TaxiInfo.DataBean> taxiInfoList);
    }

    interface TaxiPathModel extends BaseModel{
        Observable<TaxiInfo> getTaxiInfo(int area, String time);
        Observable<TaxiPathInfo> getTaxiPathInfo(String time, String licenseplateno);
    }

    interface TaxiPathPresent extends BasePresent<TaxiPathView>{
        void getTaxiInfo(Context context, int area, String time);
        void getTaxiPathInfo(Context context,String time, String licenseplateno);
    }
}
