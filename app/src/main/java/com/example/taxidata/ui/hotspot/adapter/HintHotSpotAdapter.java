package com.example.taxidata.ui.hotspot.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.taxidata.R;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class HintHotSpotAdapter extends BaseQuickAdapter<HotSpotHint, BaseViewHolder> {

    public HintHotSpotAdapter (int layoutResId , List<HotSpotHint> data) {
        super(layoutResId , data);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotSpotHint item) {
        if(helper != null) {
            if(item != null) {
                helper.setText(R.id.tv_hotspothint_name ,item.getHotSpotName());
                helper.setText(R.id.tv_hotspothint_location ,item.getHotSpotLocation());
            } else {
                Logger.d("item 为空，大问题");
            }

        } else {
            Logger.d("help为空，出大问题！");
        }

    }
}
