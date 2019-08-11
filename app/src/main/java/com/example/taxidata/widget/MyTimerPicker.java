package com.example.taxidata.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.adapter.CustomOnclick;
import com.example.taxidata.adapter.TimeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTimerPicker extends FrameLayout {

    /**
     * 2月的日数
     */
    private static final List<String> mDayList = new ArrayList<>();

    /**
     * 一天24小时
     */
    private static final List<String> mHourList = new ArrayList<>();

    /**
     * 一小时60分钟
     */
    private static final List<String> mMinuteList = new ArrayList<>();

    /**
     * 只显示4 ~  20日
     */
    private static final int CONST_DAY_COUNT = 18;

    /**
     * 一天24小时
     */
    private static final int CONST_HOUR_COUNT = 25;

    /**
     * 一小时60分钟
     */
    private static final int CONST_MINUTE_COUNT = 61;

    /**
     * 时间选择的中心index
     */
    private static final int CONST_CENTER = 1;

    /**
     * 初始化数据源
     */
    static {
        for (int i = 0; i <= CONST_DAY_COUNT; i++){
            if (i == 0) {
                mDayList.add("");
            }else if (i != CONST_DAY_COUNT){
                if (i <= 6){
                    mDayList.add("0" + (i + 3));
                }else {
                    mDayList.add(i + 3 + "");
                }
            }else {
                mDayList.add("");
            }
        }
        for (int i = 0; i <= CONST_HOUR_COUNT; i++){
            if (i == 0) {
                mHourList.add("");
            }else if (i != CONST_HOUR_COUNT) {
                if (i < 10){
                    mHourList.add("0" + i);
                }else {
                    mHourList.add("" + i);
                }
            }else {
                mHourList.add("");
            }
        }
        for (int i = 0; i <= CONST_MINUTE_COUNT; i++){
            if (i == 0) {
                mMinuteList.add("");
            }else if (i != CONST_MINUTE_COUNT) {
                if (i < 10){
                    mMinuteList.add("0" + i);
                }else {
                    mMinuteList.add("" + i);
                }
            }else {
                mMinuteList.add("");
            }
        }
    }

    /**
     * 天数滑动组件
     */
    private RecyclerView mDayRv;

    /**
     * 小时滑动组件
     */
    private RecyclerView mHourRv;

    /**
     * 分钟滑动组件
     */
    private RecyclerView mMinuteRv;

    /**
     * day适配器
     */
    private TimeAdapter mDayAdapter;

    /**
     * hour适配器
     */
    private TimeAdapter mHourAdapter;

    /**
     * minute适配器
     */
    private TimeAdapter mMinuteAdapter;

    /**
     * day recyclerview manager
     */
    private LinearLayoutManager mDayManager;

    /**
     * hour recyclerview manager
     */
    private LinearLayoutManager mHourManager;

    /**
     * minute recyclerview manager
     */
    private LinearLayoutManager mMinuteManager;

    /**
     * 绑定dayRv的滑动
     */
    private LinearSnapHelper mDaySnapHelper;

    /**
     * 绑定hourRv的滑动
     */
    private LinearSnapHelper mHourSnapHelper;

    /**
     * 绑定minute的滑动
     */
    private LinearSnapHelper mMinuteHelper;

    /**
     * 取消按钮
     */
    private TextView cancelTv;

    /**
     * 确认按钮
     */
    private TextView configTv;

    /**
     * 时间状态栏
     */
    private LinearLayout timeLl;

    /**
     * 时间选择器
     */
    private LinearLayout detailTimePicker;

    /**
     * 时间选择器最外层父布局
     */
    private FrameLayout timePickerFl;

    /**
     * 时间状态栏里面的时间
     */
    private TextView calendarTv;

    /**
     * 时间状态栏的点击事件
     */
    private CustomOnclick timeStatusBarClick;

    /**
     * 确认按钮点击事件
     */
    private CustomOnclick configClick;

    /**
     * 取消按钮点击事件
     */
    private CustomOnclick cancelClick;

    public MyTimerPicker(Context context) {
        this(context,null);
    }

    public MyTimerPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_timer_picker,this);
        //获取时间状态栏实例
        timeLl = view.findViewById(R.id.ll_choose_time);
        //获取时间选择器的实例
        detailTimePicker = view.findViewById(R.id.ll_timer_picker);
        //获取时间选择器里时间状态的实例
        calendarTv = view.findViewById(R.id.tv_calendar);
        //获取时间选择器最外层布局
        timePickerFl = view.findViewById(R.id.fl_time_picker);
        //获取按钮实例
        cancelTv = findViewById(R.id.tv_choose_time_cancel);
        configTv = findViewById(R.id.tv_choose_time_config);
        //获取recyclerview实例
        mDayRv = view.findViewById(R.id.rv_day_choose);
        mHourRv = view.findViewById(R.id.rv_time_choose);
        mMinuteRv = view.findViewById(R.id.rv_minute_choose);
        initRecyclerView(context);
        initScroll();
        initClick();
    }

    /**
     * 初始化滑动组件
     * @param context context
     */
    private void initRecyclerView(Context context){
        //创建适配器
        mDayAdapter = new TimeAdapter(mDayList);
        mHourAdapter = new TimeAdapter(mHourList);
        mMinuteAdapter = new TimeAdapter(mMinuteList);
        //创建布局管理器
        mDayManager = new LinearLayoutManager(context);
        mHourManager = new LinearLayoutManager(context);
        mMinuteManager = new LinearLayoutManager(context);
        //设置布局管理器
        mDayRv.setLayoutManager(mDayManager);
        mHourRv.setLayoutManager(mHourManager);
        mMinuteRv.setLayoutManager(mMinuteManager);
        //设置适配器
        mDayRv.setAdapter(mDayAdapter);
        mHourRv.setAdapter(mHourAdapter);
        mMinuteRv.setAdapter(mMinuteAdapter);
        //设置滑动定位
        mDaySnapHelper = new LinearSnapHelper();
        mHourSnapHelper = new LinearSnapHelper();
        mMinuteHelper = new LinearSnapHelper();
        //绑定滑动组件
        mDaySnapHelper.attachToRecyclerView(mDayRv);
        mHourSnapHelper.attachToRecyclerView(mHourRv);
        mMinuteHelper.attachToRecyclerView(mMinuteRv);
    }

    private void initScroll(){
        mDayRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    /**
     * 初始化所有点击事件
     */
    private void initClick(){
        timeLl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (timeStatusBarClick != null) {
                    timeStatusBarClick.onClick(MyTimerPicker.this);
                }
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (cancelClick != null) {
                    cancelClick.onClick(MyTimerPicker.this);
                }
            }
        });
        configTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (configClick != null) {
                    configClick.onClick(MyTimerPicker.this);
                }
            }
        });
    }

    /**
     * 展示详细时间选择器
     */
    public void showDetailTimePicker(){
        timePickerFl.setBackgroundResource(R.drawable.shape_time_picker);
        detailTimePicker.setVisibility(VISIBLE);
    }

    /**
     * 隐藏时间选择器
     */
    public void hideDetailTimePicker(){
        detailTimePicker.setVisibility(GONE);
        timePickerFl.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置时间状态栏内的时间
     * @param time
     */
    public void setTimeStausTime(String time){
        calendarTv.setText(time);
    }

    /**
     * 获取时间选择器上选择的时间
     * @return String
     */
    public String getTime(){
        //获取viewHolder
        View dayView = mDayRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder dayHolder = (TimeAdapter.ViewHolder) mDayRv.getChildViewHolder(dayView);
        View hourView = mHourRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder hourHolder = (TimeAdapter.ViewHolder) mHourRv.getChildViewHolder(hourView);
        View minuteView = mMinuteRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder minuteHolder = (TimeAdapter.ViewHolder) mMinuteRv.getChildViewHolder(minuteView);
        //获取字符串
        StringBuffer timeBuffer = new StringBuffer();
        timeBuffer.append("2017-02-").append(dayHolder.timeTv.getText().toString()).append(" ")
                .append(hourHolder.timeTv.getText().toString()).append(":").append(minuteHolder.timeTv.getText().toString());
        Log.d("MyTimerPicker",timeBuffer.toString());
        return timeBuffer.toString();
    }

    /**
     * 获取时间选择器转换为中文时间
     * @return String
     */
    public String getChineseTime(){
        //获取viewHolder
        View dayView = mDayRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder dayHolder = (TimeAdapter.ViewHolder) mDayRv.getChildViewHolder(dayView);
        View hourView = mHourRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder hourHolder = (TimeAdapter.ViewHolder) mHourRv.getChildViewHolder(hourView);
        View minuteView = mMinuteRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder minuteHolder = (TimeAdapter.ViewHolder) mMinuteRv.getChildViewHolder(minuteView);
        //获取字符串
        StringBuffer timeBuffer = new StringBuffer();
        timeBuffer.append("2017年02月").append(dayHolder.timeTv.getText().toString()).append("日 ")
                .append(hourHolder.timeTv.getText().toString()).append(":").append(minuteHolder.timeTv.getText().toString());
        Log.d("MyTimerPicker",timeBuffer.toString());
        return timeBuffer.toString();
    }

    public void setTimeStatusBarClick(CustomOnclick timeStatusBarClick) {
        this.timeStatusBarClick = timeStatusBarClick;
    }

    public void setConfigClick(CustomOnclick configClick) {
        this.configClick = configClick;
    }

    public void setCancelClick(CustomOnclick cancelClick) {
        this.cancelClick = cancelClick;
    }
}
