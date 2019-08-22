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

        mModel.requestRouteData(routeRequest)
                .subscribe(new Observer<PathInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Logger.d("触发onSubscribe");
                }
                @Override
                public void onNext(PathInfo pathInfo) {
                    if (pathInfo.getData() != null && pathInfo.getData().size() > 0) {
                        if (pathInfo.getCode() == 1) {
                            mView.requestSuccess(pathInfo);
                        } else {
                            mView.requestFailed(StatusManager.FAIL_CODE_NONE_DATA);
//                            StatusToast.getMyToast().ToastShow(TaxiApp.getContext(), null, R.mipmap.ic_sad, pathInfo.getMsg());
                        }
                    } else {
                        mView.requestFailed(StatusManager.FAIL_CODE_NONE_DATA);
                    }
                }
                @Override
                public void onError(Throwable e) {
                    Logger.d("热点路径请求出错,原因："+e.getMessage());

                    mView.requestFailed(StatusManager.FAIL_CONNECT_DATA);
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
                    Logger.d("请求对象 为空");
                }
                //将转换好的 坐标请求对象 打包发送给 M层 给服务器
                getRouteData(routeRequest);
            } else {
                ToastUtil.showShortToastBottom("您输入的地址有误,系统无法识别,请重新输入");
            }
        } else {
            //查询地址可能有误，回调让用户重新选择
            mView.requestFailed(StatusManager.FAIL_CODE_WRONG_ADDRESS);
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
