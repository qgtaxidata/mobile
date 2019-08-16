package com.example.taxidata.ui.passengerpath.present;

import com.aserbao.aserbaosandroid.functions.database.greenDao.db.OriginInfoDao;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.OriginContract;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;
import com.example.taxidata.ui.passengerpath.model.OriginModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class OriginPresent implements OriginContract.OriginPresent {

    OriginContract.OriginView view;
    OriginContract.OriginModel model;

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
            List<OriginInfo> originInfoList = model.getHistory();
            if (view != null){
                //展示历史记录
                view.showList(originInfoList);
            }
        }
    }

    @Override
    public void delete(OriginInfo info) {
        QueryBuilder<OriginInfo> infoBuilder = GreenDaoManager.getInstance().getDaoSession().queryBuilder(OriginInfo.class).where(OriginInfoDao.Properties.Origin.eq(info.getOrigin()));
        List<OriginInfo> originQuery = infoBuilder.list();
        if (originQuery.isEmpty()){
            return;
        }else {
            model.deleteHistory(info);
        }
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
