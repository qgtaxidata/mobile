package com.example.taxidata.ui.hotspot.model;

import android.util.Log;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.aserbao.aserbaosandroid.functions.database.greenDao.db.DaoSession;
import com.aserbao.aserbaosandroid.functions.database.greenDao.db.HotSpotOriginDao;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.hotspot.contract.OriginHotSpotContract;
import com.example.taxidata.ui.hotspot.presenter.OriginHotSpotPresenter;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class OriginHotSpotModel implements OriginHotSpotContract.OriginHotSpotModel , Inputtips.InputtipsListener{

    private static final String TAG = "OriginHotSpotModel";
    private DaoSession originDaoSession;
    private List<HotSpotHint> hintList;
    private OriginHotSpotPresenter mPresenter;

    public OriginHotSpotModel(OriginHotSpotPresenter mPresenter) {
        originDaoSession = GreenDaoManager.getInstance().getDaoSession();
        this.mPresenter = mPresenter;
        hintList =new ArrayList<>();
    }


    @Override
    public List<HotSpotOrigin> getHistoryOriginList() {
        return null;
    }

    @Override
    public void getHintList(String keyword) {
        Log.e(TAG, "getHintList: 准备查询 提示列表" );
        InputtipsQuery inputquery = new InputtipsQuery(keyword,"广州");
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(TaxiApp.getContext(), inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        hintList.clear();
        for(Tip tip : list) {
            if(! "".equals(tip.getPoiID()) && tip.getPoint() != null ) {
                //真实存在的地点加入列表
                HotSpotHint hint = new HotSpotHint(tip.getName(),tip.getAddress(),tip.getPoint().getLongitude() ,tip.getPoint().getLatitude());
                hintList.add(hint);
            }
        }
        if(hintList.size() > 0) {
            mPresenter.getHintListSuccess(hintList);
        }
    }

    @Override
    public void saveHotSpoyOriginHistory(String historyOriginString) {
        if (! "".equals(historyOriginString)) {
            removeDuplicatedHistoryOriginByAddress(historyOriginString);
            HotSpotOrigin origin = new HotSpotOrigin();
            origin.setHotSpotOriginHistory(historyOriginString);
            originDaoSession.insert(origin);
            Log.e(TAG, "saveHotSpoyOriginHistory: 存储了热点--起点历史" + historyOriginString );
        }
    }

    @Override
    public void removeOriginHistory(HotSpotOrigin hotSpotOrigin) {
        originDaoSession.delete(hotSpotOrigin);
    }


    //去掉重复的历史记录
    public void removeDuplicatedHistoryOriginByAddress(String  address) {
        QueryBuilder<HotSpotOrigin> queryBuilder =  originDaoSession.queryBuilder(HotSpotOrigin.class);
        QueryBuilder<HotSpotOrigin> addressQueryBuilder = queryBuilder.where(HotSpotOriginDao.Properties
                .HotSpotOriginHistory.eq(address)).orderAsc(HotSpotOriginDao.Properties.HotSpotOriginHistory);
        List<HotSpotOrigin> list = addressQueryBuilder.list();
        //数据库中有重复地址的历史，就将它删除
        if (list.size() != 0) {
            for (HotSpotOrigin  item : list) {
                originDaoSession.delete(item);
            }
        }
    }




}
