package com.example.taxidata.ui.recommendad;

import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface RecommendAdContract {

    interface RecommendAdView extends BaseView {
        /**
         * 展示广告牌推荐位置
         */
        void showAdPosition();



        /**
         * 显示加载条
         */
        void showLoading();

        /**
         * 隐藏加载条
         */
        void hideLoading();
    }

    interface RecommendAdPresent extends BasePresent<RecommendAdView> {


    }

    interface RecommendAdModel {
        /**
         * 请求广告牌路径
         * @return Observable
         */
        Observable<ResponseBody> requestAdPosition();
    }
}
