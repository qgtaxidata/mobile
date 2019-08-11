package com.example.taxidata.ui.hotspot.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.taxidata.R;
import com.example.taxidata.bean.HotSpotHistorySearch;

import java.util.List;

/**
 * 适配器Adapter类: 热点搜索历史
 *
 * @author: ODM
 * @date: 2019/8/10
 */
public class HistoryHotspotSearchAdapter extends BaseItemDraggableAdapter<HotSpotHistorySearch , BaseViewHolder> {

    public HistoryHotspotSearchAdapter(int layoutResId , List data) {
        super(layoutResId , data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotSpotHistorySearch item) {
        helper.setText(R.id.tv_hotspot_history ,item.getHotSpotHistory());
    }
}
