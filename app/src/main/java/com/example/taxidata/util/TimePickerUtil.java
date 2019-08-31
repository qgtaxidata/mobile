package com.example.taxidata.util;

import android.graphics.Color;

import com.example.taxidata.common.TimeManager;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间选择器工具类
 * @author: ODM
 * @date: 2019/8/9
 */
public class TimePickerUtil implements OnDateSetListener {

    public static TimePickerDialog mTimeDialogAll ;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;

    private TimePickerUtil() {}
    //单例时间选择器初始化
     static  class singletonTimePicker {
        private static final TimePickerUtil mInstance = new TimePickerUtil();
    }

    public static  TimePickerUtil getInstance() {
        return singletonTimePicker.mInstance ;
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
            String pickTime = getDateToString(millseconds);
            TimeManager.Time = pickTime;
    }

    /**
     * 展示时间选择器
     * 类型：年月日时分秒
     */
    public void  showTimePicker() {
         mTimeDialogAll =new TimePickerDialog.Builder().
                 setCallBack(this)
                 .setCancelStringId("Cancel")
                 .setSureStringId("Sure")
                 .setTitleStringId("TimePicker")
                 .setYearText("Year")
                 .setMonthText("Month")
                 .setDayText("Day")
                 .setHourText("Hour")
                 .setMinuteText("Minute")
                 .setCyclic(false)
                 .setMinMillseconds(System.currentTimeMillis())
                 .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                 .setCurrentMillseconds(System.currentTimeMillis())
                 .setThemeColor(Color.BLUE)
                 .setType(Type.ALL)
                 .setWheelItemTextNormalColor(Color.GRAY)
                 .setWheelItemTextSelectorColor(Color.BLACK)
                 .setWheelItemTextSize(12)
                 .build();
    }

    /**
     * Gets date to string.
     *
     * @param time the time
     * @return the date to string
     */
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
