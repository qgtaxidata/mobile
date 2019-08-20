package com.example.taxidata.ui.setup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.adapter.TimeAdapter;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.example.taxidata.util.StringUtil;
import com.example.taxidata.widget.MyTimerPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUpActivity extends BaseActivity {

    private static final String TAG = "SetUpActivity";

    @BindView(R.id.tv_set_up_calendar)
    TextView calendarTv;
    @BindView(R.id.rv_set_up_choose_day)
    RecyclerView chooseDayRv;
    @BindView(R.id.rv_set_up_choose_hour)
    RecyclerView chooseHourRv;
    @BindView(R.id.rv_set_up_choose_minute)
    RecyclerView chooseMinuteRv;
    @BindView(R.id.tv_timeout)
    TextView timeoutTv;
    @BindView(R.id.rv_set_up_choose_timeout)
    RecyclerView chooseTimeoutRv;
    @BindView(R.id.tv_polling)
    TextView pollingTv;
    @BindView(R.id.rv_set_up_choose_polling)
    RecyclerView choosePollingRv;

    /**
     * 超时最大时间
     */
    private static final int CONST_MAX_TIMEOUT = 60;

    /**
     * 超时最小时间
     */
    private static final int CONST_MIN_TIMEOUT = 8;

    /**
     * 轮询最小时间
     */
    private static final int CONST_MIN_POLLING = 3;

    /**
     * 轮询间隔最大时间
     */
    private static final int CONST_MAX_POLLING = 10;
    @BindView(R.id.btn_cancel)
    Button cancelBtn;
    @BindView(R.id.btn_config)
    Button configBtn;
    @BindView(R.id.edt_web_service)
    EditText webServiceEdt;

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
     * 超时滑动组件的适配器
     */
    private TimeAdapter mTimeoutAdapter;

    /**
     * 轮询组件
     */
    private TimeAdapter mPollingAdapter;

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
     * timeout manager
     */
    private LinearLayoutManager mTimeoutManager;

    /**
     * 轮询manager
     */
    private LinearLayoutManager mPollingManager;

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
     * timeout recyclerview 绑定
     */
    private LinearSnapHelper mTimeoutSnapHelper;

    /**
     * polling time 绑定
     */
    private LinearSnapHelper mPollingSnapHelper;

    /**
     * 2月的日数
     */
    private List<String> mDayList = new ArrayList<>();

    /**
     * 一天24小时
     */
    private List<String> mHourList = new ArrayList<>();

    /**
     * 一小时60分钟
     */
    private List<String> mMinuteList = new ArrayList<>();

    /**
     * 超时时间,限制为8~60
     */
    private List<String> mTimeoutList = new ArrayList<>();

    /**
     * 轮询间隔时间
     */
    private List<String> mPollingList = new ArrayList<>();

    private ScrollListener listener = new ScrollListener();

    /**
     * 超时时间,默认为8
     */
    private int timeout = 8;

    /**
     * 轮询间隔时间，默认为2秒
     */
    private int pollingTime = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        ButterKnife.bind(this);
        calendarTv.setText(TaxiApp.getAppNowChineseTime());
        if (RetrofitManager.getTimeoutTime() < 10){
            timeoutTv.setText("0" + RetrofitManager.getTimeoutTime() + "秒");
        }else {
            timeoutTv.setText(RetrofitManager.getTimeoutTime() + "秒");
        }
        if (HeatPowerPresent.getPollingTime() < 10){
            pollingTv.setText("0" + HeatPowerPresent.getPollingTime() + "秒");
        }else {
            pollingTv.setText(HeatPowerPresent.getPollingTime() + "秒");
        }
        //初始化数据源
        initDataList();
        //初始化滑动组件
        initRecyclerview();

        // TODO url的本地存储
    }

    /**
     * 初始化数据源
     */
    private void initDataList() {
        //初始化天数
        for (int i = 0; i <= MyTimerPicker.CONST_DAY_COUNT; i++) {
            if (i == 0) {
                mDayList.add("");
            } else if (i != MyTimerPicker.CONST_DAY_COUNT) {
                if (i <= 6) {
                    mDayList.add("0" + (i + 3));
                } else {
                    mDayList.add(i + 3 + "");
                }
            } else {
                mDayList.add("");
            }
        }
        //初始化小时
        for (int i = 0; i <= MyTimerPicker.CONST_HOUR_COUNT; i++) {
            if (i == 0) {
                mHourList.add("");
            } else if (i != MyTimerPicker.CONST_HOUR_COUNT) {
                if (i < 10) {
                    mHourList.add("0" + i);
                } else {
                    mHourList.add("" + i);
                }
            } else {
                mHourList.add("");
            }
        }
        //初始化分钟
        for (int i = 0; i <= MyTimerPicker.CONST_MINUTE_COUNT; i++) {
            if (i == 0) {
                mMinuteList.add("");
            } else if (i != MyTimerPicker.CONST_MINUTE_COUNT) {
                if (i < 10) {
                    mMinuteList.add("0" + i);
                } else {
                    mMinuteList.add("" + i);
                }
            } else {
                mMinuteList.add("");
            }
        }
        //初始化超时时间
        for (int i = CONST_MIN_TIMEOUT - 1; i <= CONST_MAX_TIMEOUT + 1; i++) {
            if (i == CONST_MIN_TIMEOUT - 1) {
                mTimeoutList.add("");
            } else if (i != CONST_MAX_TIMEOUT + 1) {
                if (i >= 10) {
                    mTimeoutList.add("" + i);
                } else {
                    mTimeoutList.add("0" + i);
                }
            } else {
                mTimeoutList.add("");
            }
        }
        //初始化轮询间隔时间
        for (int i = CONST_MIN_POLLING - 1; i <= CONST_MAX_POLLING + 1; i++) {
            if (i == CONST_MIN_POLLING - 1) {
                mPollingList.add("");
            } else if (i != CONST_MAX_POLLING + 1) {
                if (i < 10) {
                    mPollingList.add("0" + i);
                } else {
                    mPollingList.add("" + i);
                }
            } else {
                mPollingList.add("");
            }
        }
    }

    /**
     * 初始化滑动组件
     */
    private void initRecyclerview() {
        //初始化adapter
        mDayAdapter = new TimeAdapter(mDayList);
        mHourAdapter = new TimeAdapter(mHourList);
        mMinuteAdapter = new TimeAdapter(mMinuteList);
        mTimeoutAdapter = new TimeAdapter(mTimeoutList);
        mPollingAdapter = new TimeAdapter(mPollingList);
        //初始化布局管理器
        mDayManager = new LinearLayoutManager(this);
        mHourManager = new LinearLayoutManager(this);
        mMinuteManager = new LinearLayoutManager(this);
        mTimeoutManager = new LinearLayoutManager(this);
        mPollingManager = new LinearLayoutManager(this);
        //初始化recyclerview
        chooseDayRv.setLayoutManager(mDayManager);
        chooseHourRv.setLayoutManager(mHourManager);
        chooseMinuteRv.setLayoutManager(mMinuteManager);
        chooseTimeoutRv.setLayoutManager(mTimeoutManager);
        choosePollingRv.setLayoutManager(mPollingManager);
        //设置设配器
        chooseDayRv.setAdapter(mDayAdapter);
        chooseHourRv.setAdapter(mHourAdapter);
        chooseMinuteRv.setAdapter(mMinuteAdapter);
        chooseTimeoutRv.setAdapter(mTimeoutAdapter);
        choosePollingRv.setAdapter(mPollingAdapter);
        //绑定滑动
        mDaySnapHelper = new LinearSnapHelper();
        mHourSnapHelper = new LinearSnapHelper();
        mMinuteHelper = new LinearSnapHelper();
        mTimeoutSnapHelper = new LinearSnapHelper();
        mPollingSnapHelper = new LinearSnapHelper();
        mDaySnapHelper.attachToRecyclerView(chooseDayRv);
        mHourSnapHelper.attachToRecyclerView(chooseHourRv);
        mMinuteHelper.attachToRecyclerView(chooseMinuteRv);
        mTimeoutSnapHelper.attachToRecyclerView(chooseTimeoutRv);
        mPollingSnapHelper.attachToRecyclerView(choosePollingRv);
        //设置滑动监听器
        chooseDayRv.addOnScrollListener(listener);
        chooseHourRv.addOnScrollListener(listener);
        chooseMinuteRv.addOnScrollListener(listener);
        chooseTimeoutRv.addOnScrollListener(listener);
        choosePollingRv.addOnScrollListener(listener);
    }

    public void setCalendar() {
        String day = getCenterHolder(chooseDayRv).timeTv.getText().toString();
        String hour = getCenterHolder(chooseHourRv).timeTv.getText().toString();
        String minute = getCenterHolder(chooseMinuteRv).timeTv.getText().toString();
        StringBuffer timeBuffer = new StringBuffer();
        timeBuffer.append("2017年02月").append(day + "日").append(" ")
                .append(hour + ":").append(minute + ":").append("00");
        calendarTv.setText(timeBuffer.toString());
    }

    public void setTimeout() {
        String timeout = getCenterHolder(chooseTimeoutRv).timeTv.getText().toString();
        this.timeout = Integer.valueOf(timeout);
        timeoutTv.setText(timeout + "秒");
    }

    public void setPolling() {
        String pollingTime = getCenterHolder(choosePollingRv).timeTv.getText().toString();
        this.pollingTime = Integer.valueOf(pollingTime);
        pollingTv.setText(pollingTime + "秒");
    }

    /**
     * 获取viewholder
     *
     * @param rv 滑动组件
     * @return TimeAdapter.ViewHolder
     */
    public TimeAdapter.ViewHolder getCenterHolder(RecyclerView rv) {
        View v = rv.getChildAt(1);
        TimeAdapter.ViewHolder holder = (TimeAdapter.ViewHolder) rv.getChildViewHolder(v);
        return holder;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_config})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_config:
                config();
                break;
            default:
        }
    }

    /**
     * 确认按钮点击事件
     */
    public void config() {
        String newAppTime = StringUtil.ChineseToStandardFormat(calendarTv.getText().toString());
        //重新设置app当前时间
        TaxiApp.getTaxiApp().setDefaultTime(newAppTime);
        //设置热力图轮询间隔时间
        HeatPowerPresent.setPollingTime(pollingTime);
        String url = "http://" + webServiceEdt.getText().toString() + "/";
        //重新生成网络类
        RetrofitManager.setTimeoutAndUrl(timeout,url);
        Log.d("SetUpActivity","" + timeout);
        //结束该界面
        finish();
    }

    class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //停止滚动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取监听到的viewHolder
                TimeAdapter.ViewHolder holder = getCenterHolder(recyclerView);
                switch (recyclerView.getId()) {
                    case R.id.rv_set_up_choose_day:
                    case R.id.rv_set_up_choose_hour:
                    case R.id.rv_set_up_choose_minute:
                        setCalendar();
                        break;
                    case R.id.rv_set_up_choose_timeout:
                        setTimeout();
                        break;
                    case R.id.rv_set_up_choose_polling:
                        setPolling();
                        break;
                    default:
                }
            }
        }
    }
}
