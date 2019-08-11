package com.example.taxidata.ChartText;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.taxidata.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        barChart = findViewById(R.id.bar_chart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);   //是否显示表格颜色

        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);

        //X轴的设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setAxisMaximum(50f);
        xAxis.setAxisMinimum(0f);
        //xAxis.setValueFormatter(xAxisValueFormatter);

        //Y轴的设置
        YAxis leftY = barChart.getAxisLeft();
        leftY.setLabelCount(8, false);
        leftY.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftY.setSpaceTop(15f);
        leftY.setAxisMaximum(70f);
        leftY.setAxisMinimum(0f);
        YAxis rightY = barChart.getAxisRight();
        rightY.setAxisMinimum(0f);
        rightY.setAxisMaximum(70f);
        rightY.setDrawGridLines(false);
        rightY.setLabelCount(8, false);
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

        ArrayList<BarEntry> value = new ArrayList<BarEntry>();
        value.add(new BarEntry(3,10));
        value.add(new BarEntry(11,20));
        value.add(new BarEntry(19,30));
        value.add(new BarEntry(27,40));
        value.add(new BarEntry(35,50));
        value.add(new BarEntry(43,60));
        setData(value);

        //设置显示动画
        barChart.animateY(2500);
    }

    private void setData(ArrayList value){
        float start = 1f;
        BarDataSet set;
        if(barChart.getData() != null && barChart.getData().getDataSetCount() > 0){
            set =(BarDataSet)barChart.getData().getDataSetByIndex(0);
            set.setValues(value);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        }else {
            set = new BarDataSet(value, "柱状图示例");
            set.setColors(Color.BLUE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(6f);
            barChart.setData(data);
        }
    }

}
