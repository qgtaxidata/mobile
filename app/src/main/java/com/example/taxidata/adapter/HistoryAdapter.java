package com.example.taxidata.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.taxidata.R;

import java.util.List;

public class HistoryAdapter extends BaseItemDraggableAdapter<String,BaseViewHolder> {

    public HistoryAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_passenger_path,item);
    }
}
