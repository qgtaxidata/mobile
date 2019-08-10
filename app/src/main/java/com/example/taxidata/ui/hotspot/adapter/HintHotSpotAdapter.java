package com.example.taxidata.ui.hotspot.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.taxidata.R;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class HintHotSpotAdapter extends BaseQuickAdapter<HotSpotHint, BaseViewHolder> {

    public HintHotSpotAdapter (int layoutResId , List data) {
        super(layoutResId , data);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotSpotHint item) {
        helper.setText(R.id.tv_hotspot_hint_name ,item.getHotSpotName());
        helper.setText(R.id.tv_hotspot_hint_location ,item.getHotSpotLocation());
    }
}
