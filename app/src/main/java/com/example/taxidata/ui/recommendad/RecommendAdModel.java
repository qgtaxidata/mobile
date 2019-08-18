package com.example.taxidata.ui.recommendad;

import com.example.taxidata.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RecommendAdModel implements RecommendAdContract.RecommendAdModel{

    @Override
    public Observable<ResponseBody> requestAdPosition() {
        return RetrofitManager.getInstance()
                .getHttpService()
                .getAdPosition()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
