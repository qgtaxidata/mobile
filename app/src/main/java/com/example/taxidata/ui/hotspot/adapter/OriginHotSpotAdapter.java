package com.example.taxidata.ui.hotspot.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.taxidata.R;
import com.example.taxidata.bean.HotSpotHistorySearch;
import com.example.taxidata.bean.HotSpotOrigin;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class OriginHotSpotAdapter extends BaseItemDraggableAdapter<HotSpotOrigin, BaseViewHolder> {

    public OriginHotSpotAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotSpotOrigin item) {
        helper.setText(R.id.tv_hotspot_history ,item.getHotSpotOriginHistory());
    }
}
