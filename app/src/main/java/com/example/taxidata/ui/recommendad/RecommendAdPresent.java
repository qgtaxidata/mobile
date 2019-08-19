package com.example.taxidata.ui.recommendad;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.widget.StatusToast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RecommendAdPresent implements RecommendAdContract.RecommendAdPresent, GeocodeSearch.OnGeocodeSearchListener {

    private RecommendAdContract.RecommendAdView view;
    private RecommendAdContract.RecommendAdModel model;
    private GeocodeSearch geocodeSearch;
    private List<AdInfo.DataBean> adPosition;
    private int position;
    private static final String TAG = "RecommendAdPresent";

    public RecommendAdPresent(Context context) {
        model = new RecommendAdModel();
        geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    public void handlePosition(int area, int targetTime, int targetDay) {
        view.showLoading();
        //位置归0
        position = 0;
        model.requestAdPosition(area,targetTime,targetDay)
                .subscribe(new Observer<AdInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AdInfo adInfo) {
                        if (adInfo.getCode() == 1) {
                            adPosition = adInfo.getData();
                            for (AdInfo.DataBean temp : adPosition) {
                                Log.d(TAG,"经度:" + temp.getBoardLat() + ",纬度:" + temp.getBoardLon());
                                LatLonPoint latLng = new LatLonPoint(temp.getBoardLat(),temp.getBoardLon());
                                Log.d(TAG,"经纬度：" + latLng.toString());
                                RegeocodeQuery query = new RegeocodeQuery(latLng,200,GeocodeSearch.AMAP);
                                geocodeSearch.getFromLocationAsyn(query);
                            }
                        }else {
                            StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null,R.mipmap.ic_sad,adInfo.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.hideLoading();
                        StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null,R.mipmap.ic_sad,"网络错误");
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void attachView(RecommendAdContract.RecommendAdView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            view.showAdPosition(address,position);
            position++;
        }else {
            StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,"未知错误");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void positingPosition(int position,String detailAdInfo) {
        DetailAdInfo info = new DetailAdInfo(adPosition.get(position),detailAdInfo);
        EventBus.getDefault().postSticky(info);
    }

    @Override
    public boolean createChart() {
        if (adPosition != null && !adPosition.isEmpty()) {
            EventBus.getDefault().postSticky(adPosition);
            return true;
        }
        return false;
    }
}
