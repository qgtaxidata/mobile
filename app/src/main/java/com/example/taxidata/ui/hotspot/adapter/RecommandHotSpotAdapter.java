package com.example.taxidata.ui.hotspot.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.taxidata.R;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotRecommandInfo;

import java.util.List;

/**
 * Adapter适配器类：热度推荐地点信息
 *
 * @author: ODM
 * @date: 2019/8/10
 */
public class RecommandHotSpotAdapter extends BaseQuickAdapter<HotSpotCallBackInfo.DataBean, BaseViewHolder> {

    public RecommandHotSpotAdapter(int layoutRedId , List data) {
        super(layoutRedId , data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotSpotCallBackInfo.DataBean item) {
        helper.setText(R.id.tv_hotspot_location ,"北纬"+item.getLatitude()+"  东经" + item.getLongitude());
        helper.setText(R.id.tv_hotspot_heat ,"热度值: " +item.getHeat());
    }
}
