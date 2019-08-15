package com.example.taxidata.ui.hotspot.model;

import android.util.Log;

import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.aserbao.aserbaosandroid.functions.database.greenDao.db.DaoSession;
import com.aserbao.aserbaosandroid.functions.database.greenDao.db.HotSpotHistorySearchDao;
import com.aserbao.aserbaosandroid.functions.database.greenDao.db.HotSpotOriginDao;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.bean.HotSpotRequestInfo;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.ui.hotspot.contract.HotSpotContract;
import com.example.taxidata.ui.hotspot.presenter.HotSpotPresenter;
import com.example.taxidata.util.GsonUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author: ODM
 * @date: 2019/8/9
 */
public class HotSpotModel implements HotSpotContract.Model , Inputtips.InputtipsListener {

    private static final String TAG = "HotSpotModel";
    private DaoSession  historyDaoSession;
    private List<HotSpotHint> hintList;
    private List<HotSpotHistorySearch>  historySearchList;
    private HotSpotPresenter  mPresnter ;
    public HotSpotModel(HotSpotPresenter  mPresnter) {
        historyDaoSession = GreenDaoManager.getInstance().getDaoSession();
        this.mPresnter = mPresnter;
        hintList = new ArrayList<>();
        historySearchList = new ArrayList<>();
    }


    @Override
   public   Observable<HotSpotCallBackInfo>  requestHotSpotInfo(double longitude, double latitude, String time) {
        time = "2017-02-01 10:21:18";
        HotSpotRequestInfo info = new HotSpotRequestInfo(longitude ,latitude ,time);
        String hotSpotRequestJson = GsonUtil.GsonString(info);
        Log.e(TAG, "requestHotSpotInfo: " + hotSpotRequestJson );
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),hotSpotRequestJson);
        return RetrofitManager.getInstance()
                            .getHttpService()
                            .getHotSpot(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<HotSpotHistorySearch> getHistorySearchList() {
        historySearchList.clear();
        QueryBuilder<HotSpotHistorySearch> queryBuilder =  historyDaoSession.queryBuilder(HotSpotHistorySearch.class);
        List<HotSpotHistorySearch> tempList = queryBuilder.list();
        for(int index = tempList.size() - 1 ; index >= 0 ; index --){
            historySearchList.add(tempList.get(index));
        }
        return  historySearchList;
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
            mPresnter.getHintListSuccess(hintList);
        }
    }

    @Override
    public void saveHotSpotSearchHistory(String historySearch) {
        if(! "".equals(historySearch)) {
            removeDuplicatedHistoryByAddress(historySearch);
            HotSpotHistorySearch history = new HotSpotHistorySearch();
            history.setHotSpotHistory(historySearch);
            historyDaoSession.insertOrReplace(history);
            Log.e(TAG, "存储了热点历史 ： " +  historySearch );
        }
    }


    public void removeDuplicatedHistoryByAddress(String  address) {
        QueryBuilder<HotSpotHistorySearch> queryBuilder =  historyDaoSession.queryBuilder(HotSpotHistorySearch.class);
        QueryBuilder<HotSpotHistorySearch> addressQueryBuilder = queryBuilder.where(HotSpotHistorySearchDao.Properties
                                                                        .HotSpotHistory.eq(address)).orderAsc(HotSpotHistorySearchDao.Properties.HotSpotHistory);
        List<HotSpotHistorySearch> list = addressQueryBuilder.list();
        //数据库中有重复地址的历史，就将它删除
        if (list.size() != 0) {
            for (HotSpotHistorySearch  item : list) {
                historyDaoSession.delete(item);
            }
        }
    }



    @Override
    public void removeHistory(HotSpotHistorySearch historySearch) {
        Logger.d("移除了"+historySearch.getHotSpotHistory());
        historyDaoSession.delete(historySearch);
    }

}
