package com.example.taxidata.ui.roadquality;

import android.content.Context;

import com.example.taxidata.base.BaseModel;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.RoadQualityInfo;
import com.example.taxidata.bean.TaxiDemandInfo;
import com.example.taxidata.ui.taxidemand.TaxiDemandContract;

import io.reactivex.Observable;

public interface RoadQualityContract {

    interface RoadQualityView extends BaseView {
    }

    interface RoadQualityModel extends BaseModel {
        Observable<RoadQualityInfo> getRoadQualityInfo(int areaId, String date);
    }

    interface RoadQualityPresent extends BasePresent<RoadQualityView> {
        void getRoadQualityInfo(Context context, int areaId, String date);
    }
}
