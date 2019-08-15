package com.example.taxidata.ui.passengerpath.present;

import com.example.taxidata.ui.passengerpath.contract.OriginContract;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;
import com.example.taxidata.ui.passengerpath.model.OriginModel;

import java.util.ArrayList;
import java.util.List;

public class OriginPresent implements OriginContract.OriginPresent {

    OriginContract.OriginView view;
    OriginContract.OriginModel model;
    List<OriginInfo> originInfoList;

    public OriginPresent() {
        model = new OriginModel();
    }

    @Override
    public void saveHistory(OriginInfo history) {
        if (model != null) {
            //保存历史记录
            model.saveHistory(history);
        }
    }

    @Override
    public void showHistory() {
        if (model != null){
            //获取历史记录
            originInfoList = model.getHistory();
            if (view != null){
                List<String> historyNameList = new ArrayList<>();
                for (int i = 0; i < originInfoList.size(); i++) {
                    //获得历史记录的名称
                    String historyName = originInfoList.get(i).getOrigin();
                    historyNameList.add(historyName);
                }
                //展示历史记录
                view.showList(historyNameList);
            }
        }
    }

    @Override
    public void showTips(String newText) {

    }

    @Override
    public void attachView(OriginContract.OriginView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
