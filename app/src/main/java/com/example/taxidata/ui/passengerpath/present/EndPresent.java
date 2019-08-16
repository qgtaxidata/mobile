package com.example.taxidata.ui.passengerpath.present;

import com.aserbao.aserbaosandroid.functions.database.greenDao.db.EndInfoDao;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.EndContract;
import com.example.taxidata.ui.passengerpath.enity.EndInfo;
import com.example.taxidata.ui.passengerpath.model.EndModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class EndPresent implements EndContract.EndPresent {

    EndContract.EndView view;
    EndContract.EndModel model;

    public EndPresent() {
        model = new EndModel();
    }

    @Override
    public void saveHistory(EndInfo info) {
        if (model != null) {
            //保存历史记录
            model.saveHistory(info);
        }
    }

    @Override
    public void showHistory() {
        if (model != null){
            //获取历史记录
            List<EndInfo> originInfoList = model.getHistory();
            if (view != null){
                //展示历史记录
                view.showList(originInfoList);
            }
        }
    }

    @Override
    public void delete(EndInfo info) {
        QueryBuilder<EndInfo> infoBuilder = GreenDaoManager.getInstance().getDaoSession().queryBuilder(EndInfo.class).where(EndInfoDao.Properties.End.eq(info.getEnd()));
        List<EndInfo> originQuery = infoBuilder.list();
        if (originQuery.isEmpty()){
            return;
        }else {
            model.deleteHistory(info);
        }
    }

    @Override
    public void attachView(EndContract.EndView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
