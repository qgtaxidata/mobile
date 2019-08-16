package com.example.taxidata.ui.passengerpath.contract;

import com.example.taxidata.base.BasePresent;
import com.example.taxidata.base.BaseView;
import com.example.taxidata.ui.passengerpath.enity.EndInfo;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;

import java.util.List;

public interface EndContract {
    interface EndView extends BaseView {
        /**
         * 展示列表数据
         * @param history 历史
         */
        void showList(List<EndInfo> history);
    }

    interface EndPresent extends BasePresent<EndView> {

        /**
         * 保存历史记录
         * @param info 历史记录
         */
        void saveHistory(EndInfo info);

        /**
         * 展示历史记录
         */
        void showHistory();

        /**
         * 删除历史记录
         * @param info 历史纪录
         */
        void delete(EndInfo info);
    }

    interface EndModel {
        /**
         * 保存历史记录
         * @param info 历史记录
         */
        void saveHistory(EndInfo info);

        /**
         * 获取历史记录
         * @return List<EndInfo>
         */
        List<EndInfo> getHistory();

        /**
         * 删除历史记录
         * @param info 历史记录
         */
        void deleteHistory(EndInfo info);
    }
}
