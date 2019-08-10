package com.example.taxidata.ui.hotspot.model;

import android.util.Log;

import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotRequestInfo;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.ui.hotspot.contract.HotSpotContract;
import com.example.taxidata.util.GsonUtil;
import com.orhanobut.logger.Logger;

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
public class HotSpotModel implements HotSpotContract.Model {

    private static final String TAG = "HotSpotModel";

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
}
