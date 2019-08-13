package com.example.taxidata.ui.hotspot.presenter;

import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.bean.HotSpotRouteInfo;
import com.example.taxidata.bean.HotSpotRouteRequest;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.ui.hotspot.contract.OriginHotSpotContract;
import com.example.taxidata.ui.hotspot.model.OriginHotSpotModel;
import com.example.taxidata.util.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class OriginHotSpotPresenter implements OriginHotSpotContract.OriginHotSpotPresenter ,GeocodeSearch.OnGeocodeSearchListener  {

    private OriginHotSpotModel originHotSpotModel;
    private OriginHotSpotContract.OriginHotSpotView  mView;
    private static final String TAG = "OriginHotSpotPresenter";
    private GeocodeSearch geocodeSearch;
    private HotSpotRouteRequest routeRequest;

    public OriginHotSpotPresenter() {
        super();
        originHotSpotModel = new OriginHotSpotModel(this);
        routeRequest = new HotSpotRouteRequest();
    }


    @Override
    public void getRouteData(HotSpotRouteRequest request) {
        if(originHotSpotModel != null) {
            originHotSpotModel.requestRouteData(routeRequest).subscribe(new Observer<HotSpotRouteInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(HotSpotRouteInfo hotSpotRouteInfo) {
                    mView.requestSuccess(hotSpotRouteInfo);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    ToastUtil.showLongToastBottom("请求热点-起点路径数据出错，请检查网络");
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    public void saveOriginHotSpotHistory(String orignHistory) {
        originHotSpotModel.saveHotSpoyOriginHistory(orignHistory);
    }

    @Override

    public List<HotSpotOrigin> getHistoryOriginList() {
        return originHotSpotModel.getHistoryOriginList();
    }

    @Override
    public void getHintList(String keyword) {
        originHotSpotModel.getHintList(keyword);
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
        originHotSpotModel.removeOriginHistory(hotSpotOrigin);
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
        //接收回调 --将 地址 转换成了 坐标
        if(geocodeResult.getGeocodeAddressList().size() > 0 && routeRequest != null) {
            GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
            LatLonPoint point = geocodeAddress.getLatLonPoint();
            double inputLatitude = point.getLatitude();
            double inputLongitude = point.getLongitude();
            if(routeRequest.getStart() != null) {
                routeRequest.getStart().setLongitute(inputLongitude);
                routeRequest.getStart().setLatitute(inputLatitude);
            } else {
                Logger.d("routeRequest.getStart() 为空");
            }
            Log.e(TAG, "start:longtitude : " + routeRequest.getStart().getLongitute() + "   latitude:  " +routeRequest.getStart().getLatitute()
            +"  end:longitute: "+routeRequest.getEnd().getLongitute() + "     end:latitute:  "+ routeRequest.getEnd().getLatitute());
            //Todo：将转换好的 坐标 打包发送给 M层 给服务器
            originHotSpotModel.requestRouteData(routeRequest);
        } else {
            ToastUtil.showShortToastBottom("您输入的地址有误,系统无法识别,请重新输入");
        }
    }

    @Override
    public void convertToLocation(String originAddress,HotSpotRouteRequest request, GeocodeSearch geocodeSearch) {
        StatusManager.originChosen = originAddress;
        routeRequest.getEnd().setLatitute(request.getEnd().getLatitute());
        routeRequest.getEnd().setLongitute(request.getEnd().getLongitute());
        GeocodeQuery query = new GeocodeQuery(originAddress, "广州");
        this.geocodeSearch = geocodeSearch;
        this.geocodeSearch.setOnGeocodeSearchListener(this);
        this.geocodeSearch.getFromLocationNameAsyn(query);
    }
}
