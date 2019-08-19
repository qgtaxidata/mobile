package com.example.taxidata.ui.recommendad;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class ContrastFormatter implements IAxisValueFormatter {

    //防止数组越界

    String[] str = {"","广告牌位置1","广告牌位置2","广告牌位置3","广告牌位置4","广告牌位置5","广告牌位置6"};

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value < 5) {
            return str[(int)value + 1];
        }else {
            return "";
        }
    }
}
