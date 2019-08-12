package com.example.taxidata.ui.hotspot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taxidata.R;
import com.example.taxidata.util.ToastUtil;
import com.orhanobut.logger.Logger;

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
        LayoutInflater.from(context).inflate(R.layout.search_view_widget, this);
//        searchOrigin.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logger.d("点击了起点框");
//            }
//        });
    }
}
