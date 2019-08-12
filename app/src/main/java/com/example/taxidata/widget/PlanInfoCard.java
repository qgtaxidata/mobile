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
    @BindView(R.id.ll_plan_one)
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

    public PlanInfoCard(Context context, AttributeSet arrs) {
        super(context, arrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_plan_info_card, this, true);

    }

    @OnClick({R.id.hotspot_plan_first, R.id.hotspot_time_first, R.id.hotspot_far_first, R.id.ll_plan_one, R.id.hotspot_plan_second, R.id.hotspot_time_second, R.id.hotspot_far_second, R.id.ll_plan_two, R.id.hotspot_plan_third, R.id.hotsopt_time_third, R.id.hotspot_far_third, R.id.ll_plan_third})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hotspot_plan_first:
                ToastUtil.showShortToastCenter("方案一被点击");
                Logger.d("方案一被点击");
                break;
            case R.id.hotspot_time_first:
                break;
            case R.id.hotspot_far_first:
                break;
            case R.id.ll_plan_one:
                break;
            case R.id.hotspot_plan_second:
                break;
            case R.id.hotspot_time_second:
                break;
            case R.id.hotspot_far_second:
                break;
            case R.id.ll_plan_two:
                break;
            case R.id.hotspot_plan_third:
                break;
            case R.id.hotsopt_time_third:
                break;
            case R.id.hotspot_far_third:
                break;
            case R.id.ll_plan_third:
                break;
                default:
        }
    }
}
