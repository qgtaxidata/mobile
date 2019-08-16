package com.example.taxidata.ui.passengerpath.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.taxidata.R;
import com.example.taxidata.ui.passengerpath.enity.TimeDistance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPlanCard extends LinearLayout {

    private static final int PLAN_ONE = 0;
    private static final int PLAN_TWO = 1;
    private static final int PLAN_THREE = 2;

    /**
     * 默认为计划一
     */
    private int status = PLAN_ONE;

    @BindView(R.id.tv_plan_one)
    TextView planOneTv;
    @BindView(R.id.tv_time_one)
    TextView timeOneTv;
    @BindView(R.id.tv_distance_one)
    TextView distanceOneTv;
    @BindView(R.id.ll_path_plane_one)
    LinearLayout planPathOneLl;
    @BindView(R.id.tv_plan_two)
    TextView planTwoTv;
    @BindView(R.id.tv_time_two)
    TextView timeTwoTv;
    @BindView(R.id.tv_distance_two)
    TextView distanceTwoTv;
    @BindView(R.id.ll_path_plane_two)
    LinearLayout planPathTwoLl;
    @BindView(R.id.tv_plan_three)
    TextView planThreeTv;
    @BindView(R.id.tv_time_tree)
    TextView timeThreeTv;
    @BindView(R.id.tv_distance_three)
    TextView distanceThreeTv;
    @BindView(R.id.ll_path_plan_three)
    LinearLayout threePathLl;

    private List<TimeDistance> timeDistances;

    private SelectedListener listener;

    public SelectPlanCard(@NonNull Context context) {
        this(context, null);
    }

    public SelectPlanCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LinearLayout.inflate(context, R.layout.view_plan_info_card, this);
        ButterKnife.bind(view);
    }

    public void init(List<TimeDistance> timeDistances) {
        this.timeDistances = timeDistances;
        setText();
    }

    private void setText() {
        //防御式编程
        if (timeDistances.size() >= 0) {
            TimeDistance one = timeDistances.get(0);
            timeOneTv.setText(one.getTime() + "分钟");
            distanceOneTv.setText(one.getDistance() + "公里");
        }
        if (timeDistances.size() >= 1) {
            TimeDistance two = timeDistances.get(1);
            timeTwoTv.setText(two.getTime() + "分钟");
            distanceTwoTv.setText(two.getDistance() + "公里");
        }
        if (timeDistances.size() >= 2) {
            TimeDistance three = timeDistances.get(2);
            timeThreeTv.setText(three.getTime() + "分钟");
            distanceThreeTv.setText(three.getDistance() + "公里");
        }
    }

    @OnClick({R.id.ll_path_plane_one, R.id.ll_path_plane_two,R.id.ll_path_plan_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_path_plane_one:
                clearLastStatus();
                setBlueOne();
                status = PLAN_ONE;
                if (listener != null) {
                    listener.select(0);
                }
                break;
            case R.id.ll_path_plane_two:
                clearLastStatus();
                setBlueTwo();
                status = PLAN_TWO;
                if (listener != null) {
                    listener.select(1);
                }
                break;
            case R.id.ll_path_plan_three:
                clearLastStatus();
                setBlueThree();
                status = PLAN_THREE;
                if (listener != null) {
                    listener.select(2);
                }
                break;
            default:
        }
    }

    private void clearLastStatus() {
        switch (status) {
            case PLAN_ONE :
                setDefaultOne();
                break;
            case PLAN_TWO :
                setDefaultTwo();
                break;
            case PLAN_THREE :
                setDefaultThree();
                break;
            default:
        }
    }

    private void setBlueOne() {
        planOneTv.setTextColor(Color.parseColor("#4c93fd"));
        timeOneTv.setTextColor(Color.parseColor("#4c93fd"));
        distanceOneTv.setTextColor(Color.parseColor("#4c93fd"));
    }

    private void setBlueTwo() {
        planTwoTv.setTextColor(Color.parseColor("#4c93fd"));
        timeTwoTv.setTextColor(Color.parseColor("#4c93fd"));
        distanceTwoTv.setTextColor(Color.parseColor("#4c93fd"));
    }

    private void setBlueThree() {
        planThreeTv.setTextColor(Color.parseColor("#4c93fd"));
        timeThreeTv.setTextColor(Color.parseColor("#4c93fd"));
        distanceThreeTv.setTextColor(Color.parseColor("#4c93fd"));
    }

    private void setDefaultOne() {
        planOneTv.setTextColor(Color.parseColor("#4d4d4d"));
        timeOneTv.setTextColor(Color.parseColor("#4d4d4d"));
        distanceOneTv.setTextColor(Color.parseColor("#4d4d4d"));
    }

    private void setDefaultTwo() {
        planTwoTv.setTextColor(Color.parseColor("#4d4d4d"));
        timeTwoTv.setTextColor(Color.parseColor("#4d4d4d"));
        distanceTwoTv.setTextColor(Color.parseColor("#4d4d4d"));
    }

    private void setDefaultThree() {
        planThreeTv.setTextColor(Color.parseColor("#4d4d4d"));
        timeThreeTv.setTextColor(Color.parseColor("#4d4d4d"));
        distanceThreeTv.setTextColor(Color.parseColor("#4d4d4d"));
    }

    public void setListener(SelectedListener listener) {
        this.listener = listener;
    }

    public int getStatus() {
        return status;
    }
}
