package com.example.taxidata.ui.passengerpath.model;

import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.EndContract;
import com.example.taxidata.ui.passengerpath.enity.EndInfo;

import java.util.List;

public class EndModel implements EndContract.EndModel {
    @Override
    public void saveHistory(EndInfo info) {
        GreenDaoManager.getInstance().getDaoSession().insertOrReplace(info);
    }

    @Override
    public List<EndInfo> getHistory() {
        return GreenDaoManager.getInstance().getDaoSession()
                .loadAll(EndInfo.class);
    }

    @Override
    public void deleteHistory(EndInfo info) {
        GreenDaoManager.getInstance().getDaoSession()
                .delete(info);
    }
}
