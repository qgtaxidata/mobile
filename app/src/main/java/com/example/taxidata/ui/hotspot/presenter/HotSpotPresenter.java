package com.example.taxidata.ui.hotspot.presenter;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.taxidata.HomePageActivity;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;
import com.example.taxidata.bean.HotSpotInfo;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.common.eventbus.EventFactory;
import com.example.taxidata.constant.EventBusType;
import com.example.taxidata.ui.hotspot.contract.HotSpotContract;
import com.example.taxidata.ui.hotspot.model.HotSpotModel;
import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.GsonUtil;
import com.example.taxidata.util.ToastUtil;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: ODM
 * @date: 2019/8/9
 */
public class HotSpotPresenter implements HotSpotContract.Presenter,GeocodeSearch.OnGeocodeSearchListener  {

    private HotSpotModel mHotSpotModel ;
    private HotSpotContract.HotSpotView mHotSpotView;
    private List<HotSpotCallBackInfo.DataBean> hotSpotRecommandInfoList = new ArrayList<>();
    private static final String TAG = "HotSpotPresenter";
    private GeocodeSearch geocodeSearch ;
    private LatLonPoint latLng;
    private HotSpotCallBackInfo.DataBean  callbackHotSpotInfo;

    public HotSpotPresenter(){
        super();
        mHotSpotModel = new HotSpotModel(this);

    }
    @Override
    public void attachView(HotSpotContract.HotSpotView view) {
        mHotSpotView = view;
    }



    @Override
    public void detachView() {
        mHotSpotView = null;
    }

    @Override
    public void getHotSpotData(double longitude, double latitude, String time) {
        if(mHotSpotModel != null) {
            mHotSpotModel.requestHotSpotInfo(longitude,latitude,time)
                    //发出请求并对网络请求回调进行处理
                    .subscribe(new Observer<HotSpotCallBackInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(HotSpotCallBackInfo hotSpotCallBackInfo) {
                            //Todo 获取了返回的callback对象后,解析获取列表
                            if (hotSpotCallBackInfo.getCode() == 1 && hotSpotCallBackInfo.getData().size() > 0 ) {
                                hotSpotRecommandInfoList.clear();
                                hotSpotRecommandInfoList.addAll(hotSpotCallBackInfo.getData());
                                if (hotSpotRecommandInfoList.size() > 0 ) {
                                    mHotSpotView.showHotSpot(hotSpotRecommandInfoList);
                                } else {
                                    Logger.d("获取了返回的热点数据,但是列表大小为0 ");
                                }
                            } else {
                                String errorMsg = hotSpotCallBackInfo.getMsg();
                                Log.e(TAG, "onNext: 热点 P层,获取热点数据出现异常: " +errorMsg );
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            //假数据--度过服务器关掉的时光
                            hotSpotRecommandInfoList.clear();
                            hotSpotRecommandInfoList.add(new HotSpotCallBackInfo.DataBean(113.056778,23.402886,6));
                            mHotSpotView.showHotSpot(hotSpotRecommandInfoList);
                            e.printStackTrace();
                            ToastUtil.showShortToastCenter("抱歉，网络似乎出现了异常 :(");
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: 热点请求P层请求回调收工");
                        }
                    });
        }
    }


    @Override
    public List<HotSpotHistorySearch> getHistorySearchList() {
        return mHotSpotModel.getHistorySearchList();
    }

    @Override
    public void getHintList(String keyword) {
        mHotSpotModel.getHintList(keyword);
    }

    @Override
    public void getHintListSuccess(List<HotSpotHint> hintList) {
        if (hintList != null) {
            mHotSpotView.showHintHotSpotList(hintList);
        } else {
            Logger.d("提示列表未初始化！！！");
        }
    }

    @Override
    public void saveHotSpotSearchHistory(String historyHotSpot) {
        mHotSpotModel.saveHotSpotSearchHistory(historyHotSpot);
    }


    @Override
    public void convertToLocation(String hotSpotAdress, GeocodeSearch geocodeSearch) {
        if(! "".equals(hotSpotAdress)) {
            StatusManager.hotSpotChosen = hotSpotAdress;
        }
        GeocodeQuery query = new GeocodeQuery(hotSpotAdress, "广州");
        this.geocodeSearch = geocodeSearch;
        this.geocodeSearch.setOnGeocodeSearchListener(this);
        this.geocodeSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void convertToAddressName(HotSpotCallBackInfo.DataBean dataBean, GeocodeSearch geocodeSearch) {
        latLng = new LatLonPoint(dataBean.getLatitude(),dataBean.getLongitude());
        callbackHotSpotInfo = dataBean;
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLng ,100 ,GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
        mHotSpotView.hotSpotChosenSuccess();
    }

    /**
     * 回调函数：将坐标解析成地址名后，让路径展示页可以显示出来
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        Log.e(TAG, "onRegeocodeSearched: 正在将坐标转换成中文地址" );
        if(regeocodeResult.getRegeocodeAddress().getRoads().size() > 0) {
            String addressName =regeocodeResult.getRegeocodeAddress().getDistrict() + regeocodeResult.getRegeocodeAddress().getTownship()+ regeocodeResult.getRegeocodeAddress().getRoads().get(0).getName() ;
            StatusManager.hotSpotChosen = addressName;
            BaseEvent baseEvent = EventFactory.getInstance();
            baseEvent.type = EventBusType.HOTSPOT_CHOSEN;
            HotSpotInfo hotSpotInfo = new HotSpotInfo();
            hotSpotInfo.setAddress(addressName);
            hotSpotInfo.setLatitude(callbackHotSpotInfo.getLatitude());
            hotSpotInfo.setLongitude(callbackHotSpotInfo.getLongitude());
            hotSpotInfo.setHeat(callbackHotSpotInfo.getHeat());
            baseEvent.object = hotSpotInfo;
            EventBusUtils.postSticky(baseEvent);
        } else {
            ToastUtil.showLongToastBottom("无法识别此热点坐标信息:( ，请重新选择热点");
        }
    }

    /**
     * 回调函数：将地址名 解析成 坐标 ，发送给服务器
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if(geocodeResult != null) {
            if(geocodeResult.getGeocodeAddressList().size() > 0) {
                GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                LatLonPoint point = geocodeAddress.getLatLonPoint();
                double inputLatitude = point.getLatitude();
                double inputLongitude = point.getLongitude();
                getHotSpotData(inputLongitude,inputLatitude ,"");
            } else {
                ToastUtil.showShortToastBottom("您输入的地址有误,系统无法识别,请重新输入");
            }
        }
    }

    @Override
    public void removeHistory(HotSpotHistorySearch historySearch) {
        mHotSpotModel.removeHistory(historySearch);
    }

    @Override
    public void  getHotSpotListAgain() {
        if(hotSpotRecommandInfoList != null && hotSpotRecommandInfoList.size() > 0) {
            mHotSpotView.showHotSpot( hotSpotRecommandInfoList);
        }
    }


}
