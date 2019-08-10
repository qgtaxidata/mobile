package com.example.taxidata.ChartText;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import com.example.taxidata.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart_activity);
        chart = findViewById(R.id.chart);
//        chart.setOnChartGestureListener(this);           //设置手势滑动事件
//        chart.setOnChartValueSelectedListener(this);     //设置数值选择监听
        chart.setDrawGridBackground(false);     //设置网格
        chart.getDescription().setEnabled(false);   //设置描述文本
        chart.setTouchEnabled(true);    //设置触控
        chart.setDragEnabled(true);    //设置缩放
        chart.setScaleEnabled(true);   //设置推动
        chart.setPinchZoom(true);

        //折线的相关设置
        LimitLine llXAxis = new LimitLine(10f, "标记");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);   //设置破折线
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        //x轴的相关设置
        XAxis xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMinimum(0f);   //设置x轴的最小值
        xAxis.setAxisMaximum(100f);   //设置x轴的最大值

//        LimitLine goodLL = new LimitLine(150f, "优秀");
//        goodLL.setLineWidth(4f);
//        goodLL.enableDashedLine(10f, 10f, 0f);
//        goodLL.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        goodLL.setTextSize(10f);
//
//        LimitLine badLL = new LimitLine(30f, "不及格");
//        badLL.setLineWidth(4f);
//        badLL.enableDashedLine(10f, 10f, 0f);
//        badLL.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        badLL.setTextSize(10f);

        YAxis leftY = chart.getAxisLeft();
        leftY.removeAllLimitLines();
//        leftY.addLimitLine(goodLL);    //设置优秀线
//        leftY.addLimitLine(badLL);     //设置及格线
        leftY.setAxisMinimum(0f);      //设置Y轴最小值
        leftY.setAxisMaximum(100f);    //设置Y轴最大值
        leftY.enableGridDashedLine(10f, 10f, 0f);
        leftY.setDrawZeroLine(false);
        leftY.setDrawLimitLinesBehindData(true);   //限制数据
        chart.getAxisRight().setEnabled(false);
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0,30));
        values.add(new Entry(10,50));
        values.add(new Entry(20,40));
        values.add(new Entry(30,60));
        values.add(new Entry(40,52));
        values.add(new Entry(50,57));
        values.add(new Entry(60,47));
        values.add(new Entry(70,70));
        values.add(new Entry(80,30));
        values.add(new Entry(90,66));
        values.add(new Entry(100,25));
        setData(values);
        chart.animateX(2500);
        Legend legend = chart.getLegend();         //得到文字
        legend.setForm(Legend.LegendForm.LINE);    //修改文字


    }

    private void setData(ArrayList<Entry> values){
        LineDataSet lineDataSet;
        if(chart.getData() != null && chart.getData().getDataSetCount() > 0){
            lineDataSet = (LineDataSet)chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        }else {
            lineDataSet = new LineDataSet(values, "折线图示例");
            lineDataSet.enableDashedLine(10f, 5f, 0f);
            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
            lineDataSet.setColor(Color.BLACK);
            lineDataSet.setCircleColor(Color.BLACK);
            lineDataSet.setLineWidth(1f);
            lineDataSet.setCircleRadius(3f);
            lineDataSet.setDrawCircleHole(false);   //实心还是空心
            lineDataSet.setValueTextSize(9f);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f},0f));
            lineDataSet.setFormSize(15.f);

//            //填充背景
//            if(Utils.getSDKInt() >= 18){
//                lineDataSet.setFillColor(Color.YELLOW);
//            }else {
//                lineDataSet.setFillColor(Color.BLACK);
//            }

            //添加数据集
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(lineDataSet);

            //创建数据集的数据对象
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }
}
