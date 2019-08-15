package com.example.taxidata.ui.passengerpath.contract;

import com.amap.api.services.help.Inputtips;
import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;

import java.util.List;

public interface OriginContract {
    interface OriginView extends BaseView{

        /**
         * 展示列表数据
         * @param history 历史
         */
        void showList(List<String> history);
    }

    interface OriginPresent extends BasePresent<OriginView>{

        /**
         * 保存历史记录
         * @param info 历史记录
         */
        void saveHistory(OriginInfo info);

        /**
         * 展示历史记录
         */
        void showHistory();

        /**
         * 展示提示
         * @param newText 新字符串
         */
        void showTips(String newText);
    }

    interface OriginModel{

        /**
         * 保存历史记录
         * @param info 历史记录
         */
        void saveHistory(OriginInfo info);

        /**
         * 获取提示
         * @param newText 新字符串
         * @param listener 回调接口
         */
        void getTips(String newText,Inputtips listener);

        /**
         * 获取历史记录
         * @return List<OriginInfo>
         */
        List<OriginInfo> getHistory();
    }
}
