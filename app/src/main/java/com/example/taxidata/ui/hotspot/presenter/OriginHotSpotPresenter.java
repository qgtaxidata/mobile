package com.example.taxidata.ui.hotspot.presenter;

import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.bean.HotSpotRouteInfo;
import com.example.taxidata.bean.HotSpotRouteRequest;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.ui.hotspot.contract.OriginHotSpotContract;
import com.example.taxidata.ui.hotspot.model.OriginHotSpotModel;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;
import com.example.taxidata.util.GsonUtil;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class OriginHotSpotPresenter implements OriginHotSpotContract.OriginHotSpotPresenter ,GeocodeSearch.OnGeocodeSearchListener  {

//    private OriginHotSpotModel originHotSpotModel;
    private OriginHotSpotContract.OriginHotSpotModel mModel;
    private OriginHotSpotContract.OriginHotSpotView  mView;
    private static final String TAG = "OriginHotSpotPresenter";
    private GeocodeSearch geocodeSearch;
    private HotSpotRouteRequest routeRequest;

    public OriginHotSpotPresenter() {
        super();
        mModel = new OriginHotSpotModel(this);
        routeRequest = new HotSpotRouteRequest();
    }


    @Override
    public void getRouteData(HotSpotRouteRequest request) {
        Logger.d("P 层 向 M层 请求数据");
        mModel.requestRouteData(routeRequest)
                .subscribe(new Observer<PathInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Logger.d("触发onSubscribe");
                }
                @Override
                public void onNext(PathInfo pathInfo) {
//                    Logger.d("触发 onNext 方法");
//                    if(hotSpotRouteInfo.getData() != null && hotSpotRouteInfo.getData().size() > 0) {
//                        //若请求回来的热点路径数据是非空且有方案列表
//                        Logger.d("M  层 成功 返回 数据 到 P 层 " + GsonUtil.GsonString(hotSpotRouteInfo));
//                        mView.requestSuccess(hotSpotRouteInfo);
//                    }
                    if (pathInfo.getCode() == 1) {
                        mView.requestSuccess(pathInfo);
                    }else {
                        StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,pathInfo.getMsg());
                    }
                }
                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Logger.d("热点路径请求出错。。");
                    String fakeJson ="{\"code\":1,\"data\":[{\"distance\":30,\"route\":[{\"lat\":22.98518138200364,\"lng\":113.35485108537304},{\"lat\":23.427348283487405,\"lng\":113.60881831645261},{\"lat\":23.083167964816138,\"lng\":113.22967956725822},{\"lat\":23.12343575347576,\"lng\":113.29521107768714},{\"lat\":23.08303074892986,\"lng\":113.23164749634579},{\"lat\":23.085251183627626,\"lng\":113.35078366915705},{\"lat\":23.12241792205843,\"lng\":113.27209661077315},{\"lat\":23.003491003560264,\"lng\":113.38708156173223},{\"lat\":23.208719466544952,\"lng\":113.28242027292747},{\"lat\":22.635539958485683,\"lng\":113.58939575366549},{\"lat\":23.03584072287595,\"lng\":113.32042772909656},{\"lat\":23.12425720119194,\"lng\":113.29566021934356},{\"lat\":23.125681528092855,\"lng\":113.2711044965782},{\"lat\":22.6359230445728,\"lng\":113.58888500999923},{\"lat\":23.410866799777054,\"lng\":113.22176051211754},{\"lat\":23.156956786984896,\"lng\":113.45617091918334},{\"lat\":23.402886409368595,\"lng\":113.05677893622983}],\"time\":30},{\"distance\":30,\"route\":[{\"lat\":22.98518138200364,\"lng\":113.35485108537304},{\"lat\":23.427348283487405,\"lng\":113.60881831645261},{\"lat\":23.083167964816138,\"lng\":113.22967956725822},{\"lat\":23.12343575347576,\"lng\":113.29521107768714},{\"lat\":23.08303074892986,\"lng\":113.23164749634579},{\"lat\":23.085251183627626,\"lng\":113.35078366915705},{\"lat\":23.12241792205843,\"lng\":113.27209661077315},{\"lat\":23.003491003560264,\"lng\":113.38708156173223},{\"lat\":23.208719466544952,\"lng\":113.28242027292747},{\"lat\":22.635539958485683,\"lng\":113.58939575366549},{\"lat\":23.03584072287595,\"lng\":113.32042772909656},{\"lat\":23.12425720119194,\"lng\":113.29566021934356},{\"lat\":23.125681528092855,\"lng\":113.2711044965782},{\"lat\":22.6359230445728,\"lng\":113.58888500999923},{\"lat\":23.410866799777054,\"lng\":113.22176051211754},{\"lat\":23.156956786984896,\"lng\":113.45617091918334},{\"lat\":23.402886409368595,\"lng\":113.05677893622983}],\"time\":30},{\"distance\":30,\"route\":[{\"lat\":22.98518138200364,\"lng\":113.35485108537304},{\"lat\":23.427348283487405,\"lng\":113.60881831645261},{\"lat\":23.083167964816138,\"lng\":113.22967956725822},{\"lat\":23.12343575347576,\"lng\":113.29521107768714},{\"lat\":23.08303074892986,\"lng\":113.23164749634579},{\"lat\":23.085251183627626,\"lng\":113.35078366915705},{\"lat\":23.12241792205843,\"lng\":113.27209661077315},{\"lat\":23.003491003560264,\"lng\":113.38708156173223},{\"lat\":23.208719466544952,\"lng\":113.28242027292747},{\"lat\":22.635539958485683,\"lng\":113.58939575366549},{\"lat\":23.03584072287595,\"lng\":113.32042772909656},{\"lat\":23.12425720119194,\"lng\":113.29566021934356},{\"lat\":23.125681528092855,\"lng\":113.2711044965782},{\"lat\":22.6359230445728,\"lng\":113.58888500999923},{\"lat\":23.410866799777054,\"lng\":113.22176051211754},{\"lat\":23.156956786984896,\"lng\":113.45617091918334},{\"lat\":23.402886409368595,\"lng\":113.05677893622983}],\"time\":30}],\"msg\":\"success\"}";
                    PathInfo pathInfo =  GsonUtil.GsonToBean(fakeJson ,PathInfo.class);
                    if(pathInfo != null) {
                        //传入假数据
                        mView.requestSuccess(pathInfo);
                    }
                    ToastUtil.showLongToastBottom("请求热点-起点路径数据出错，请检查网络");
                }
                @Override
                public void onComplete() {

                }
            });

    }

    @Override
    public void saveOriginHotSpotHistory(String orignHistory) {
        mModel.saveHotSpoyOriginHistory(orignHistory);
    }

    @Override

    public List<HotSpotOrigin> getHistoryOriginList() {
        return mModel.getHistoryOriginList();
    }

    @Override
    public void getHintList(String keyword) {
        mModel.getHintList(keyword);
    }

    @Override
    public void getHintListSuccess(List<HotSpotHint> hintList) {
        if (hintList != null) {
            mView.showHintHotSpotList(hintList);
        } else {
            Logger.d("提示列表未初始化！！！");
        }
    }

    @Override
    public void removeOriginHistory(HotSpotOrigin hotSpotOrigin) {
        mModel.removeOriginHistory(hotSpotOrigin);
    }

    @Override
    public void attachView(OriginHotSpotContract.OriginHotSpotView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        //接收回调 --将 用户选择的起点地址 转换成了 起点的坐标
        if(geocodeResult != null) {
            if(geocodeResult.getGeocodeAddressList().size() > 0 && routeRequest != null) {
                GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                LatLonPoint point = geocodeAddress.getLatLonPoint();
                double inputLatitude = point.getLatitude();
                double inputLongitude = point.getLongitude();
                if(routeRequest != null) {
                    routeRequest.setLatOrigin(inputLatitude);
                    routeRequest.setLonOrigin(inputLongitude);
                } else {
                    Logger.d("routeRequest 为空");
                }
                Log.e(TAG, "起点:longtitude : " + routeRequest.getLonOrigin() + " 起点  latitude:  " +routeRequest.getLatOrigin()
                        +"  终点:longitute: "+routeRequest.getLonOrigin() + "    终点:latitute:  "+ routeRequest.getLatDestination());
                //将转换好的 坐标请求对象 打包发送给 M层 给服务器
                getRouteData(routeRequest);
            } else {
                ToastUtil.showShortToastBottom("您输入的地址有误,系统无法识别,请重新输入");
            }
        } else {
            Logger.d("geocodeResult 对象为空");
        }
    }

    @Override
    public void convertToLocation(String originAddress,HotSpotRouteRequest request, GeocodeSearch geocodeSearch) {
        StatusManager.originChosen = originAddress;
        routeRequest.setLatDestination(request.getLatDestination());
        routeRequest.setLonDestination(request.getLonDestination());
        GeocodeQuery query = new GeocodeQuery(originAddress, "广州");
        this.geocodeSearch = geocodeSearch;
        this.geocodeSearch.setOnGeocodeSearchListener(this);
        this.geocodeSearch.getFromLocationNameAsyn(query);
    }
}
