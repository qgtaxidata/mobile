package com.example.taxidata.ui.passengerpath.contract;

import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;

import java.util.List;

import io.reactivex.Observable;

public interface OriginEndShowContract {
    interface OriginEndShowView extends BaseView{
        /**
         * 展示历史记录
         * @param history
         */
        void showHistory(List<OriginEndInfo> history);

        /**
         * 展示路径
         * @param pathPlan 路径
         */
        void showPath(List<PathInfo.DataBean> pathPlan);
    }

    interface OriginEndShowPresent extends BasePresent<OriginEndShowView>{
        /**
         * 获得历史数据
         */
        void getHistory();

        /**
         * 保存历史记录
         * @param info 起始点
         */
        void saveHistory(OriginEndInfo info);

        /**
         * 删除历史记录
         * @param info 历史记录
         */
        void deleteHistory(OriginEndInfo info);

        /**
         * 处理路径
         */
        void managePath(OriginEndInfo info);
    }

    interface OriginEndShowModel {

        /**
         * 保存历史记录
         * @param info 起始点
         */
        void saveOriginEnd(OriginEndInfo info);

        /**
         * 获取历史记录
         * @return List<OriginEndInfo>
         */
        List<OriginEndInfo> getOriginEnd();

        /**
         * 查询路径
         * @param
         * @return Observable
         */
        Observable<PathInfo> requestPath(OriginEndInfo info);

        /**
         * 删除历史记录
         * @param info 历史记录
         */
        void delete(OriginEndInfo info);
    }
}
