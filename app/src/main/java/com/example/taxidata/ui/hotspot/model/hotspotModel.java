package com.example.taxidata.ui.hotspot.model;

import com.example.taxidata.bean.hotSpotCallBackInfo;
import com.example.taxidata.ui.hotspot.contract.hotspotContract;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/9
 */
public class hotspotModel implements hotspotContract.Model {

    @Override
    public List<hotSpotCallBackInfo> requestHotSpot(double longitude, double latitude, String time) {
        return null;
    }
}
