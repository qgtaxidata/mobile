package com.example.taxidata.ui.passengerpath.model;

import com.aserbao.aserbaosandroid.functions.database.greenDao.db.OriginEndInfoDao;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.ui.passengerpath.contract.OriginEndShowContract;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OriginEndShowModel implements OriginEndShowContract.OriginEndShowModel {

    @Override
    public void saveOriginEnd(OriginEndInfo info) {
        //将历史记录
        GreenDaoManager.getInstance().getDaoSession().insertOrReplace(info);
    }

    @Override
    public List<OriginEndInfo> getOriginEnd() {
        return GreenDaoManager.getInstance().getDaoSession().loadAll(OriginEndInfo.class);
    }

    @Override
    public Observable<PathInfo> requestPath(OriginEndInfo info) {
        //TODO 待设计
        double latOrigin = info.getOriginLat();
        double lngOrigin = info.getOriginLng();
        double latEnd = info.getEndLat();
        double lngEnd = info.getEndLng();
        return RetrofitManager.getInstance()
                .getHttpService()
                .getPath(lngOrigin,latOrigin,lngEnd,latEnd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void delete(OriginEndInfo info) {
        QueryBuilder<OriginEndInfo> infoBuilder = GreenDaoManager.getInstance().getDaoSession().queryBuilder(OriginEndInfo.class)
                .where(OriginEndInfoDao.Properties.Origin.eq(info.getOrigin()));
        List<OriginEndInfo> originEndQuery = infoBuilder.list();
        if (originEndQuery.size() >= 0) {
            return;
        }
        GreenDaoManager.getInstance()
                .getDaoSession()
                .delete(info);
    }
}
