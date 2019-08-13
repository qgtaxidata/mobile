package com.example.taxidata.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MyTimerPicker extends FrameLayout {

    private static final String CONST_NO_SELECTED_TIME = "请选择你要查询的时间";

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
    public static final int CONST_DAY_COUNT = 18;

    /**
     * 一天24小时
     */
    public static final int CONST_HOUR_COUNT = 25;

    /**
     * 一小时60分钟
     */
    public static final int CONST_MINUTE_COUNT = 61;

    /**
     * 时间选择的中心index
     */
    public static final int CONST_CENTER = 1;

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

    /**
     * 绑定状态栏
     */
    private StatusBar statusBar;

    /**
     * 历史状态栏按钮
     */
    private CustomOnclick historyStatusClick;

    /**
     * 实时状态栏按钮
     */
    private CustomOnclick realTimeClick;

    /**
     * 未来状态栏按钮
     */
    private CustomOnclick featureClick;

    /**
     * 定时器
     */
    private Timer timer;

    /**
     * 定时器处理实时时间
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String time = (String) msg.obj;
            if (statusBar != null){
                if (statusBar.getStatus() == 1){
                    //实时状态下更新时间
                    calendarTv.setText(time);
                }
            }
        }
    };


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
                if (statusBar != null){
                    int status = statusBar.getStatus();
                    switch (status){
                        case 0:
                            //历史状态栏可以点击
                            showDetailTimePicker();
                            break;
                        case 1:
                            //实时状态栏不可以点击
                            break;
                        case 2:
                            //未来状态栏可以点击
                            showDetailTimePicker();
                            break;
                        default:
                            //不存在的
                    }
                }else {
                    //如果没有绑定状态栏，则有外界设置点击事件
                    if (timeStatusBarClick != null) {
                        timeStatusBarClick.onClick(MyTimerPicker.this);
                    }
                }
            }
        });
        //取消按钮
        cancelTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hideDetailTimePicker();
            }
        });
        //确认按钮点击事件
        configTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //确认按钮以后，将标题栏数据设置为选择的事件
                setTimeStausTime(getChineseTime());
                hideDetailTimePicker();
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
     * 获取时间状态栏上选择的时间
     * @return String
     */
    public String getTime(){
       /* //获取viewHolder
        View dayView = mDayRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder dayHolder = (TimeAdapter.ViewHolder) mDayRv.getChildViewHolder(dayView);
        View hourView = mHourRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder hourHolder = (TimeAdapter.ViewHolder) mHourRv.getChildViewHolder(hourView);
        View minuteView = mMinuteRv.getChildAt(CONST_CENTER);
        TimeAdapter.ViewHolder minuteHolder = (TimeAdapter.ViewHolder) mMinuteRv.getChildViewHolder(minuteView);*/
        //获取字符串
/*        StringBuffer timeBuilder = new StringBuffer(chineseTime);
        timeBuilder.setCharAt(timeBuilder.indexOf("年"),'-');
        timeBuilder.setCharAt(timeBuilder.indexOf("月"),'-');
        timeBuilder.deleteCharAt(timeBuilder.indexOf("日"));

        Log.d("MyTimerPicker",timeBuilder.toString());
        return timeBuilder.toString();*/
        String chineseTime = calendarTv.getText().toString();

        return StringUtil.ChineseToStandardFormat(chineseTime);
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

    /**
     * 如果绑定状态栏，则开启定时器，不绑定状态栏，不开启定时器
     * @param statusBar 状态栏
     */
    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
        //绑定status同时开启定时器
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                while (true){
                    if (statusBar.getStatus() == 1){
                        //如果是实时状态，每次更新标题栏
                        Message message = new Message();
                        message.obj = TaxiApp.getAppNowChineseTime();
                        mHandler.sendMessage(message);
                    }
                }
            }
        };
        try {
            timer.schedule(timerTask,1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        //顺手绑定点击事件
        statusBar.setHistoryOnClick(new StatusOnClick() {
            @Override
            public void onClick() {
                if (historyStatusClick != null){
                    calendarTv.setText(CONST_NO_SELECTED_TIME);
                    historyStatusClick.onClick(MyTimerPicker.this);
                }
            }
        });
        statusBar.setRealTimeOnClick(new StatusOnClick() {
            @Override
            public void onClick() {
                if (realTimeClick != null){
                    realTimeClick.onClick(MyTimerPicker.this);
                }
            }
        });
        statusBar.setFeatureOnClick(new StatusOnClick() {
            @Override
            public void onClick() {
                if (featureClick != null){
                    calendarTv.setText(CONST_NO_SELECTED_TIME);
                    featureClick.onClick(MyTimerPicker.this);
                }
            }
        });
    }

    public void setHistoryStatusClick(CustomOnclick historyStatusClick) {
        this.historyStatusClick = historyStatusClick;
    }

    public void setRealTimeClick(CustomOnclick realTimeClick) {
        this.realTimeClick = realTimeClick;
    }

    public void setFeatureClick(CustomOnclick featureClick) {
        this.featureClick = featureClick;
    }

    /**
     * 用户是否有选择时间
     * @return boolean
     */
    public boolean isNoSelectedTime(){
        return calendarTv.getText().toString().equals(CONST_NO_SELECTED_TIME);
    }

    /**
     * 用户选择的时间是否是历史时间
     * @return boolean
     */
    public boolean isHistory(){
        //设置时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        long selectedTime = 99999999;
        try {
            selectedTime = format.parse(getTime() + ":00").getTime();
        }catch (Exception e){
            // TODO 失败处理
            e.printStackTrace();
        }
        //获取app当前时间
        long appNowTime = TaxiApp.getMillionTime();
        return appNowTime >= selectedTime;
    }

    /**
     * 用户选择的时间是否是未来时间
     * @return boolean
     */
    public boolean isFeature(){
        //设置时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        long selectedTime = 0;
        try {
            selectedTime = format.parse(getTime() + ":00").getTime();
        }catch (Exception e){
            // TODO 失败处理
            e.printStackTrace();
        }
        //获取app当前时间
        long appNowTime = TaxiApp.getMillionTime();
        return appNowTime <= selectedTime;
    }
}
