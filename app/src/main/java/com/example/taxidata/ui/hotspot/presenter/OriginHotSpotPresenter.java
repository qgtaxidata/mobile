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
import com.example.taxidata.ui.hotspot.contract.OriginHotSpotContract;
import com.example.taxidata.ui.hotspot.model.OriginHotSpotModel;
import com.example.taxidata.util.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class OriginHotSpotPresenter implements OriginHotSpotContract.OriginHotSpotPresenter ,GeocodeSearch.OnGeocodeSearchListener  {

    private OriginHotSpotModel originHotSpotModel;
    private OriginHotSpotContract.OriginHotSpotView  mView;
    private static final String TAG = "OriginHotSpotPresenter";
    private GeocodeSearch geocodeSearch;

    public OriginHotSpotPresenter() {
        super();
        originHotSpotModel = new OriginHotSpotModel(this);

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
        if(geocodeResult.getGeocodeAddressList().size() > 0) {
            GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
            LatLonPoint point = geocodeAddress.getLatLonPoint();
            double inputLatitude = point.getLatitude();
            double inputLongitude = point.getLongitude();
            Log.e(TAG, "onGeocodeSearched: "+ "longtitude : " + inputLongitude + "   latitude:  " +inputLatitude );
            //Todo：将转换好的 坐标 打包发送给 M层 给服务器
            //getHotSpotData(inputLongitude,inputLatitude ,"");
        } else {
            ToastUtil.showShortToastBottom("您输入的地址有误,系统无法识别,请重新输入");
        }
    }

    @Override
    public void convertToLocation(String address, GeocodeSearch geocodeSearch) {
        GeocodeQuery query = new GeocodeQuery(address, "广州");
        this.geocodeSearch = geocodeSearch;
        this.geocodeSearch.setOnGeocodeSearchListener(this);
        this.geocodeSearch.getFromLocationNameAsyn(query);
    }
}
