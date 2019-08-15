package com.example.taxidata.ui.passengerpath.model;

import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.OriginEndShowContract;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.enity.PointInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class OriginEndShowModel implements OriginEndShowContract.OriginEndShowModel {

    @Override
    public void saveOriginEnd(String origin,String end, double originLng,double originLat, double endLng,double endLat) {
        OriginEndInfo info = new OriginEndInfo();
        info.setOrigin(origin);
        info.setEnd(end);
        info.setOriginLat(originLat);
        info.setOriginLng(originLng);
        info.setEndLng(endLng);
        info.setEndLat(endLat);
        //将历史记录
        GreenDaoManager.getInstance().getDaoSession().insert(info);
    }

    @Override
    public List<OriginEndInfo> getOriginEnd() {
        return GreenDaoManager.getInstance().getDaoSession().loadAll(OriginEndInfo.class);
    }

    @Override
    public Observable<PointInfo> requestPath() {
        //TODO 待设计
        return null;
    }
}
