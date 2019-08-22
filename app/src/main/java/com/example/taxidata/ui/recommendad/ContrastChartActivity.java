package com.example.taxidata.ui.recommendad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ContrastChartActivity extends BaseActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast_chart);
        barChart = findViewById(R.id.bc_ad_recommend);
        initBarChart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void createChart(List<AdInfo.DataBean> data) {
        refreshChart(data);
    }

    /**
     * 封装数据
     * @param dataList 数据源
     */
    private void refreshChart(List<AdInfo.DataBean> dataList) {
        float groupSpace = 0.08f;
        float barSpace = 0.06f;
        float barWidth = 0.4f;

        List<BarEntry> boardFlow = new ArrayList<>();
        List<BarEntry> boardRate = new ArrayList<>();

        int max = 0;

        for (int i = 0; i < dataList.size(); i++) {
            AdInfo.DataBean temp = dataList.get(i);
            boardFlow.add(new BarEntry(i,temp.getBoardFlow()));
            boardRate.add(new BarEntry(i,temp.getBoradRate() * 100));
            Log.d("ContrastChartActivity",temp.getBoradRate() + "");
            if (max < temp.getBoardFlow()) {
                max = temp.getBoardFlow();
            }
        }

        BarDataSet flowSet,rateSet;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            flowSet = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            rateSet = (BarDataSet)barChart.getData().getDataSetByIndex(1);
            flowSet.setValues(boardFlow);
            rateSet.setValues(boardRate);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            flowSet = new BarDataSet(boardFlow,"车流量(量)");
            flowSet.setColor(Color.parseColor("#f7f79c"));
            rateSet = new BarDataSet(boardRate,"抵达率(%)");
            rateSet.setColor(Color.parseColor("#a5e7ff"));

            BarData barData = new BarData(flowSet,rateSet);
            barData.setValueTextSize(10f);
            barData.setBarWidth(barWidth);

            barChart.setData(barData);
        }

        int valueY = max / 10 * 10 + 10;
        //Y轴的设置
        YAxis leftY = barChart.getAxisLeft();
        if (valueY <= 100) {
            valueY = 100;
        }
        leftY.setAxisMaximum(valueY);
        leftY.setAxisMinimum(0.0f);
        YAxis rightY = barChart.getAxisRight();
        rightY.setAxisMinimum(0f);
        rightY.setAxisMaximum(valueY);
        rightY.setEnabled(false);
        rightY.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value / value * 100 + "%";
            }
        });
        XAxis xAxis = barChart.getXAxis();
        String[] str = {"位置1","位置2","位置3","位置4","位置5"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(str));
        xAxis.setLabelCount(5);

        barChart.groupBars(0.0f,groupSpace,barSpace);
        barChart.invalidate();
    }

    private void initBarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(true);

        //X轴的设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisMaximum(5f);
        xAxis.setAxisMinimum(0f);
        xAxis.setCenterAxisLabels(true);

        //Y轴的设置
        YAxis leftY = barChart.getAxisLeft();
        leftY.setLabelCount(10, false);
        leftY.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftY.setSpaceTop(15f);
        leftY.setAxisMaximum(20f);
        leftY.setAxisMinimum(0f);

        YAxis rightY = barChart.getAxisRight();
        rightY.setAxisMinimum(0f);
        rightY.setAxisMaximum(1f);
        rightY.setDrawGridLines(false);
        rightY.setLabelCount(10, false);
        rightY.setSpaceTop(15f);

        //设置y的value
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(12f);
        legend.setXEntrySpace(8f);
        legend.setEnabled(true);

        //设置显示动画
        barChart.animateY(4000);
    }
}
