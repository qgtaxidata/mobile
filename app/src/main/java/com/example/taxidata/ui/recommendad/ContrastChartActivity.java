package com.example.taxidata.ui.recommendad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.taxidata.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ContrastChartActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast_chart);
        barChart = findViewById(R.id.bc_ad_recommend);
        initBarChart();

/*        AdInfo.DataBean data1 = new AdInfo.DataBean();
        data1.setBoardFlow(20);
        data1.setBoradRate(0.55);
        AdInfo.DataBean data2 = new AdInfo.DataBean();
        data2.setBoradRate(0.1);
        data2.setBoardFlow(40);
        AdInfo.DataBean data3 = new AdInfo.DataBean();
        data3.setBoradRate(0.1);
        data3.setBoardFlow(40);
        AdInfo.DataBean data4 = new AdInfo.DataBean();
        data4.setBoradRate(0.1);
        data4.setBoardFlow(40);
        AdInfo.DataBean data5 = new AdInfo.DataBean();
        data5.setBoradRate(0.1);
        data5.setBoardFlow(40);
        List<AdInfo.DataBean> dataBeans = new ArrayList<>();
        dataBeans.add(data1);
        dataBeans.add(data2);
        dataBeans.add(data3);
        dataBeans.add(data4);
        dataBeans.add(data5);
        refreshChart(dataBeans);*/

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
            boardRate.add(new BarEntry(i,(float)temp.getBoradRate()));
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
            flowSet = new BarDataSet(boardFlow,"车流量");
            flowSet.setColor(Color.RED);
            rateSet = new BarDataSet(boardRate,"抵达率");
            rateSet.setColor(Color.BLUE);

            BarData barData = new BarData(flowSet,rateSet);
            barData.setValueTextSize(10f);
            barData.setBarWidth(barWidth);

            barChart.setData(barData);
        }

        //Y轴的设置
        YAxis leftY = barChart.getAxisLeft();
        leftY.setAxisMaximum(max + 10);
        leftY.setAxisMinimum(0f);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ContrastFormatter());;

        barChart.groupBars(0f,groupSpace,barSpace);
        barChart.invalidate();
    }

    private void initBarChart() {
        Description description = new Description();
        description.setText("对比图表");

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        //X轴的设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(5);
        xAxis.setAxisMaximum(4f);
        xAxis.setAxisMinimum(0f);
        xAxis.setCenterAxisLabels(true);

        //Y轴的设置
        YAxis leftY = barChart.getAxisLeft();
        leftY.setLabelCount(8, false);
        leftY.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftY.setSpaceTop(15f);
        leftY.setAxisMaximum(20f);
        leftY.setAxisMinimum(0f);
        YAxis rightY = barChart.getAxisRight();
        rightY.setAxisMinimum(0f);
        rightY.setAxisMaximum(1f);
        rightY.setDrawGridLines(false);
        rightY.setLabelCount(5, false);
        rightY.setSpaceTop(15f);

        //设置y的value
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(20f);
        legend.setXEntrySpace(4f);
        legend.setEnabled(false);

        //设置显示动画
        barChart.animateY(4000);
    }
}
