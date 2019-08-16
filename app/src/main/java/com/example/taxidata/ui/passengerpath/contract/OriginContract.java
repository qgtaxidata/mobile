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
        void showList(List<OriginInfo> history);
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
         * 删除历史记录
         * @param info 历史纪录
         */
        void delete(OriginInfo info);
    }

    interface OriginModel{

        /**
         * 保存历史记录
         * @param info 历史记录
         */
        void saveHistory(OriginInfo info);

        /**
         * 获取历史记录
         * @return List<OriginInfo>
         */
        List<OriginInfo> getHistory();

        /**
         * 删除历史记录
         * @param info 历史记录
         */
        void deleteHistory(OriginInfo info);
    }
}
