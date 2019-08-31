package com.example.taxidata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taxidata.R;
import com.example.taxidata.util.ToastUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: ODM
 * @date: 2019/8/12
 */
public class PlanInfoCard extends RelativeLayout {

    @BindView(R.id.hotspot_plan_first)
    TextView PlanFirst;
    @BindView(R.id.hotspot_time_first)
    TextView TimeFirst;
    @BindView(R.id.hotspot_far_first)
    TextView FarFirst;
    @BindView(R.id.ll_plan_first)
    LinearLayout llPlanOne;
    @BindView(R.id.hotspot_plan_second)
    TextView PlanSecond;
    @BindView(R.id.hotspot_time_second)
    TextView TimeSecond;
    @BindView(R.id.hotspot_far_second)
    TextView FarSecond;
    @BindView(R.id.ll_plan_two)
    LinearLayout llPlanTwo;
    @BindView(R.id.hotspot_plan_third)
    TextView PlanThird;
    @BindView(R.id.hotsopt_time_third)
    TextView TimeThird;
    @BindView(R.id.hotspot_far_third)
    TextView FarThird;
    @BindView(R.id.ll_plan_third)
    LinearLayout llPlanThird;

    private Context context;

    public PlanInfoCard(Context context) {
        super(context ,null);
    }

    public PlanInfoCard(Context context, AttributeSet arrs) {
        super(context, arrs);
        View view = LinearLayout.inflate(context, R.layout.view_card_plan, this);
        ButterKnife.bind(view);
    }
}
