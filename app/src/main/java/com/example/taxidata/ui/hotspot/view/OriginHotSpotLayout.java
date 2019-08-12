package com.example.taxidata.ui.hotspot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taxidata.R;

import butterknife.BindView;

/**
 * @author: ODM
 * @date: 2019/8/11
 */
public class OriginHotSpotLayout extends LinearLayout {

    @BindView(R.id.search_back)
    ImageView searchBack;
    @BindView(R.id.search_origin)
    TextView searchOrigin;
    @BindView(R.id.ll_origin)
    LinearLayout llOrigin;
    @BindView(R.id.search_end_point)
    TextView searchEndPoint;
    @BindView(R.id.search_switch_start_end)
    ImageView searchSwitchStartEnd;

    public OriginHotSpotLayout(Context context, AttributeSet arrs) {
        super(context, arrs);
        LayoutInflater.from(context).inflate(R.layout.view_search_widget, this);

    }
}
