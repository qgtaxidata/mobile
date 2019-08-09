package com.example.taxidata.ui.hotspot.presenter;

import com.example.taxidata.bean.hotSpotCallBackInfo;
import com.example.taxidata.ui.hotspot.contract.hotspotContract;
import com.example.taxidata.ui.hotspot.model.hotspotModel;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/9
 */
public class hotspotPresenter implements hotspotContract.Presenter {

    hotspotModel mModel = new hotspotModel();

    @Override
    public List<hotSpotCallBackInfo> getHotSpot(double longitude, double latitude, String time) {
        return mModel.requestHotSpot(longitude,latitude,time);
    }
}
