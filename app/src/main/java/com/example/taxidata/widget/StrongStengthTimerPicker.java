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
import com.example.taxidata.adapter.TimeAdapter;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.common.OnMultiClickListener;
import com.example.taxidata.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangyuyong
 * @Description 加强版时间选择器，不再绑定状态栏,提供更好的封装性和适用性
 */
public class StrongStengthTimerPicker extends FrameLayout {

    private static final String TAG = "StrongTimerPicker";

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
                if (i <= 4){
                    mDayList.add("0" + (i + 4));
                }else {
                    mDayList.add(i + 4 + "");
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
    private TimePickClickedListener timeStatusBarClick;

    /**
     * 定时器
     */
    private Timer timer = new Timer();

    /**
     * 暂停
     */
    private boolean isStop;

    /**
     * 定时器处理实时时间
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String time = (String) msg.obj;
            //实时状态下更新时间
            calendarTv.setText(time);
        }
    };

    public StrongStengthTimerPicker(@NonNull Context context) {
        this(context,null);
    }

    public StrongStengthTimerPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_strong_time_pick,this);
        //获取时间状态栏实例
        timeLl = view.findViewById(R.id.ll_choose_time_strong);
        //获取时间选择器的实例
        detailTimePicker = view.findViewById(R.id.ll_timer_picker_strong);
        //获取时间选择器里时间状态的实例
        calendarTv = view.findViewById(R.id.tv_calendar_strong);
        //获取时间选择器最外层布局
        timePickerFl = view.findViewById(R.id.fl_time_picker_strong);
        //获取按钮实例
        cancelTv = findViewById(R.id.tv_choose_time_cancel_strong);
        configTv = findViewById(R.id.tv_choose_time_config_strong);
        //获取recyclerview实例
        mDayRv = view.findViewById(R.id.rv_day_choose_strong);
        mHourRv = view.findViewById(R.id.rv_time_choose_strong);
        mMinuteRv = view.findViewById(R.id.rv_minute_choose_strong);
        //初始化滑动组件
        initRecyclerView(context);
        //初始化点击事件
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

    /**
     * 获取时间状态栏上选择的时间
     * @return String
     */
    public String getTime(){
        String chineseTime = calendarTv.getText().toString();

        return StringUtil.ChineseToStandardFormat(chineseTime);
    }

    /**
     * 时间状态栏的点击事件
     * @param timeStatusBarClick 点击事件
     */
    public void setTimeStatusBarClick(TimePickClickedListener timeStatusBarClick) {
        this.timeStatusBarClick = timeStatusBarClick;
    }

    /**
     * 显示具体时间选择器
     */
    public void showDetailTimerPicker() {
        timePickerFl.setBackgroundResource(R.drawable.shape_time_picker);
        detailTimePicker.setVisibility(VISIBLE);
    }

    /**
     * 隐藏具体时间选择器
     */
    public void hideDetailTimerPicker() {
        detailTimePicker.setVisibility(GONE);
        timePickerFl.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 注册点击事件
     */
    private void initClick() {
        configTv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                    //为标题栏设置时间
                    setTimeStatusTime(getChineseTime());
                    //隐藏具体时间选择器
                    hideDetailTimerPicker();
            }
        });
        cancelTv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                hideDetailTimerPicker();
            }
        });
        timeLl.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (timeStatusBarClick != null) {
                    //时间状态栏点击事件
                    timeStatusBarClick.onClick(StrongStengthTimerPicker.this);
                }
            }
        });
    }

    /**
     * 设置时间状态栏内的时间
     * @param time
     */
    private void setTimeStatusTime(String time){
        calendarTv.setText(time);
    }

    /**
     * 开始定时
     */
    public void startTimer() {
        isStop = false;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    if (isStop) {
                        return;
                    }
                    Message message = new Message();
                    message.obj = TaxiApp.getAppNowChineseTime();
                    mHandler.sendMessage(message);
                }
            }
        };
        try {
            timer.schedule(task,1000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止定时
     */
    public void stopTimer() {
        isStop = true;
    }

    public String getHistoryTime() {
        return getTime() + ":00";
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
            e.printStackTrace();
        }
        //获取app当前时间
        long appNowTime = TaxiApp.getMillionTime();
        return appNowTime >= selectedTime;
    }


}
