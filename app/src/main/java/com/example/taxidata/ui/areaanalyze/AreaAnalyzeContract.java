package com.example.taxidata.ui.areaanalyze;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.AreaAnalyzeInfo;

import io.reactivex.Observable;

public interface AreaAnalyzeContract {

    interface AreaAnalyzeView extends BaseView {

    }

    interface AreaAnalyzeModel extends BaseModel {
        Observable<AreaAnalyzeInfo> getAreaAnalyzeInfo(int area, String date);
    }

    interface AreaAnalyzePresent extends BasePresent<AreaAnalyzeView> {
        void getAreaAnalyzeInfo(Context context, int area, String date);

    }
}
