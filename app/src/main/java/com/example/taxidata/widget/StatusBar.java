package com.example.taxidata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.taxidata.R;

public class StatusBar extends LinearLayout {

    /**
     * ui控件
     */
    private TextView historyTv;
    private TextView realTimeTv;
    private TextView featureTv;

    /**
     * 状态
     */
    public static final int HISTORY = 0;
    public static final int REALTIME = 1;
    public static final int FEATURE = 2;

    /**
     * 点击事件
     */
    private StatusOnClick historyOnClick;
    private StatusOnClick realTimeOnClick;
    private StatusOnClick featureOnClick;

    /**
     * 状态栏状态，默认为实时
     */
    private int status = REALTIME;

    public StatusBar(Context context) {
        this(context,null);
    }

    public StatusBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_status_bar,this);
        //获取实例
        historyTv = view.findViewById(R.id.tv_history);
        realTimeTv = view.findViewById(R.id.tv_real_time);
        featureTv = view.findViewById(R.id.tv_future);
        initClick();
    }

    public void setHistoryOnClick(StatusOnClick historyOnClick) {
        this.historyOnClick = historyOnClick;
    }

    public void setRealTimeOnClick(StatusOnClick realTimeOnClick) {
        this.realTimeOnClick = realTimeOnClick;
    }

    public void setFeatureOnClick(StatusOnClick featureOnClick) {
        this.featureOnClick = featureOnClick;
    }

    public void initClick(){
        historyTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (historyOnClick != null){
                    if (status != HISTORY){
                        //清除上一次状态
                        clearLastStatus();
                        //将状态设置为历史状态
                        status = HISTORY;
                        setHistoryStatus();
                        historyOnClick.onClick();
                    }
                }
            }
        });

        realTimeTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (realTimeOnClick != null){
                    if (status != REALTIME){
                        clearLastStatus();
                        status = REALTIME;
                        setRealTimeStatus();
                        realTimeOnClick.onClick();
                    }
                }
            }
        });

        featureTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (featureOnClick != null){
                    if (status != FEATURE){
                        clearLastStatus();
                        status = FEATURE;
                        setFeatureStatus();
                        featureOnClick.onClick();
                    }
                }
            }
        });
    }

    /**
     * 清除最后一次状态
     */
    private void clearLastStatus(){
        switch (status){
            case HISTORY:
                clearHistoryStatus();
                break;
            case REALTIME:
                clearRealTimeStatus();
                break;
            case FEATURE:
                clearFeatureStatus();
                break;
            default:
        }
    }

    /**
     * 清除历史状态栏被选中状态,下同
     */
    private void clearHistoryStatus(){
        historyTv.setTextColor(0x4c93fd);
        historyTv.setBackgroundColor(0xffffff);
    }

    private void clearRealTimeStatus(){
        realTimeTv.setTextColor(0x4c93fd);
        realTimeTv.setBackgroundColor(0xffffff);
    }

    private void clearFeatureStatus(){
        featureTv.setTextColor(0x4c93fd);
        featureTv.setBackgroundColor(0xffffff);
    }

    /**
     * 设置为历史状态，下同
     */
    private void setHistoryStatus(){
        historyTv.setBackgroundResource(R.drawable.shape_small_button);
        historyTv.setTextColor(0xffffff);
    }

    private void setRealTimeStatus(){
        realTimeTv.setBackgroundResource(R.drawable.shape_small_button);
        realTimeTv.setTextColor(0xffffff);
    }

    private void setFeatureStatus(){
        featureTv.setBackgroundResource(R.drawable.shape_small_button);
        featureTv.setTextColor(0xffffff);
    }

    /**
     * 获取状态栏状态
     * @return 状态
     */
    public int getStatus(){
        return status;
    }
}
