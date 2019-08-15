package com.example.taxidata.ui.passengerpath.present;

import com.example.taxidata.ui.passengerpath.contract.OriginEndShowContract;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.model.OriginEndShowModel;

import java.util.ArrayList;
import java.util.List;

public class OriginEndShowPresent implements OriginEndShowContract.OriginEndShowPresent {

    OriginEndShowContract.OriginEndShowModel model;
    OriginEndShowContract.OriginEndShowView view;
    List<OriginEndInfo> history;

    public OriginEndShowPresent() {
        model = new OriginEndShowModel();
    }

    @Override
    public void getHistory() {
        //获得历史记录
        history = model.getOriginEnd();
        if (history != null){
            List<String> historyNameList = new ArrayList<>();
            for (int i = 0; i < history.size(); i++){
                //获得起点--终点
                String historyName = history.get(i).getOrigin() + "--" + history.get(i).getEnd();
                //加入列表
                historyNameList.add(historyName);
                //展示数据
                if (view != null){
                    view.showHistory(historyNameList);
                }
            }
        }
    }

    @Override
    public void saveHistory(String origin, String end, double originLng, double originLat, double endLng, double endLat) {
        //存储记录
        model.saveOriginEnd(origin,end,originLng,originLat,endLng,endLat);
    }

    @Override
    public void managePath() {
        //TODO 未设计
    }

    @Override
    public void attachView(OriginEndShowContract.OriginEndShowView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
