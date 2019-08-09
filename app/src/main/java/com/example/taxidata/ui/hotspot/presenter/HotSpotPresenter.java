package com.example.taxidata.ui.hotspot.presenter;
import android.util.Log;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.ui.hotspot.contract.HotSpotContract;
import com.example.taxidata.ui.hotspot.model.HotSpotModel;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: ODM
 * @date: 2019/8/9
 */
public class HotSpotPresenter implements HotSpotContract.Presenter {

    HotSpotModel mHotSpotModel = new HotSpotModel();
    HotSpotContract.HotSpotView mHotSpotView;
    List<HotSpotCallBackInfo.HotSpotBean> hotSpotCallBackInfoList = new ArrayList<>();
    private static final String TAG = "HotSpotPresenter";

    @Override
    public void getHotSpotData(double longitude, double latitude, String time) {
        if(mHotSpotModel != null) {
            mHotSpotModel.requestHotSpotInfo(longitude,latitude,time)
                    .subscribe(new Observer<HotSpotCallBackInfo>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Logger.d("热点数据订阅√");
                        }
                        @Override
                        public void onNext(HotSpotCallBackInfo hotSpotCallBackInfo) {
                            Log.e(TAG, "onNext: hotSpotCallBackInfo.toString()" );
                            //Todo 获取了返回的callback对象后,解析获取列表
                            if (hotSpotCallBackInfo.getCode() == 1) {
                                hotSpotCallBackInfoList.addAll(hotSpotCallBackInfo.getHot_spot());
                                if (hotSpotCallBackInfoList.size() > 0) {
                                    mHotSpotView.showHotSpot(hotSpotCallBackInfoList);
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
                            Log.e(TAG, "onError: 热点请求P层请求回调发生错误" );
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: 热点请求P层请求回调收工");
                        }
                    });
        }
    }

    @Override
    public void attachView(HotSpotContract.HotSpotView view) {
        mHotSpotView = view;
    }

    @Override
    public void detachView() {
        mHotSpotView = null;
    }
}
