package com.example.taxidata.ui.passengerpath.model;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.OriginContract;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;

import java.util.List;

public class OriginModel implements OriginContract.OriginModel {

    @Override
    public void saveHistory(OriginInfo info) {
        //插入数据库
        GreenDaoManager.getInstance().getDaoSession().insert(info);
    }

    @Override
    public List<OriginInfo> getHistory() {
        return GreenDaoManager.getInstance().getDaoSession().loadAll(OriginInfo.class);
    }

    @Override
    public void getTips(String newText, Inputtips listener) {
        InputtipsQuery query = new InputtipsQuery(newText,"广州");
        query.setCityLimit(true);
        //获取回调接口
        listener.requestInputtipsAsyn();
    }
}
