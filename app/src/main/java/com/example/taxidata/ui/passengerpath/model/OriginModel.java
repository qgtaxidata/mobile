package com.example.taxidata.ui.passengerpath.model;

import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.OriginContract;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;

import java.util.List;

public class OriginModel implements OriginContract.OriginModel {

    @Override
    public void saveHistory(OriginInfo info) {
        //插入数据库
        GreenDaoManager.getInstance().getDaoSession().insertOrReplace(info);
    }

    @Override
    public List<OriginInfo> getHistory() {
        return GreenDaoManager.getInstance().getDaoSession().loadAll(OriginInfo.class);
    }

    @Override
    public void deleteHistory(OriginInfo info) {
        GreenDaoManager.getInstance().getDaoSession().delete(info);
    }
}
