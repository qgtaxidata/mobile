package com.example.taxidata.ui.recommendad;

import android.content.Context;

import androidx.core.app.ActivityCompat;

import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

public interface RecommendAdContract {

    interface RecommendAdView extends BaseView {
        /**
         * 展示广告牌推荐位置
         * @param adName 地址名称
         * @param position 位置
         */
        void showAdPosition(String adName,int position);

        /**
         * 显示加载条
         */
        void showLoading();

        /**
         * 隐藏加载条
         */
        void hideLoading();

        /**
         * 获得context
         * @return Context
         */
        Context getActivityContext();
    }

    interface RecommendAdPresent extends BasePresent<RecommendAdView> {

        /**
         * 处理返回数据
         * @param area 地址
         * @param targetTime 时间段
         * @param targetDay 天的类型
         */
        void handlePosition(int area,int targetTime,int targetDay);

        /**
         * 定位广告牌位置
         * @param position 点击的广告牌位置
         * @param detaiAdInfo 详细地址信息
         */
        void positingPosition(int position,String detaiAdInfo);

        /**
         * 生成对比图表
         * return boolean 生成图表是否成功
         */
        boolean createChart();
    }

    interface RecommendAdModel {

        Observable<AdInfo> requestAdPosition(int area, int targetTime,int targetDay);
    }
}
