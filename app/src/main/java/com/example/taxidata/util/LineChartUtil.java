package com.example.taxidata.util;

import android.content.Context;
import android.graphics.Color;

import com.example.taxidata.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ODM
 * @date: 2019/8/21
 */
public class LineChartUtil {



    private static String lineName1 = null;
    private static String lineName2 = null;

    /**
     * 创建一条折线
     * @param context 上下文
     * @param mLineChart 对象
     * @param count X轴的数据
     * @param datas Y轴的数据
     * @return LineData
     */
    public static LineData initSingleLineChart(Context context, LineChart mLineChart, int count, float[] datas) {

        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues.add(new Entry(i ,datas[i]));
        }
        //设置折线的样式
        LineDataSet dataSet = new LineDataSet(yValues, lineName1);
        //用y轴的集合来设置参数
        dataSet.setLineWidth(2f); // 线宽
        dataSet.setCircleSize(3f);// 显示的圆形大小
        dataSet.setColor(Color.rgb(89, 194, 230));// 折线显示颜色
        dataSet.setCircleColor(Color.rgb(89, 194, 230));// 圆形折点的颜色
        dataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色
        dataSet.setHighlightEnabled(true);
        dataSet.setValueTextColor(R.color.black_color); //数值显示的颜色
        dataSet.setValueTextSize(10f);     //数值显示的大小

        List<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(dataSet);
        //构建一个LineData  将dataSets放入
        return new LineData(lineDataSets);
    }

    /**
     * @param context    上下文
     * @param mLineChart 折线图控件
     * @param count      折线在x轴的值
     * @param datas1     折线在y轴的值
     * @param datas2     另一条折线在y轴的值
     * @Description:创建两条折线
     */
    public static LineData initDoubleLineChart(Context context, LineChart mLineChart, int count, float[] datas1, float[] datas2) {

        // y轴的数据
        ArrayList<Entry> yValues1 = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues1.add(new Entry(i ,datas1[i] ));
        }
        // y轴的数据
        ArrayList<Entry> yValues2 = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            yValues2.add(new Entry( i,datas2[i]));
        }

        LineDataSet dataSet1 = new LineDataSet(yValues1, lineName1);
        //dataSet.enableDashedLine(10f, 10f, 0f);//将折线设置为曲线(即设置为虚线)
        //用y轴的集合来设置参数
        dataSet1.setLineWidth(2f); // 线宽
        dataSet1.setCircleSize(3f);// 显示的圆形大小
        dataSet1.setColor(Color.rgb(89, 194, 230));// 折线显示颜色
        dataSet1.setCircleColor(Color.rgb(89, 194, 230));// 圆形折点的颜色
        dataSet1.setHighLightColor(Color.GREEN); // 高亮的线的颜色
        dataSet1.setHighlightEnabled(true);
        dataSet1.setValueTextColor(R.color.black_color); //数值显示的颜色
        dataSet1.setValueTextSize(11f);     //数值显示的大小

        LineDataSet dataSet2 = new LineDataSet(yValues2, lineName2);

        //用y轴的集合来设置参数
        dataSet2.setLineWidth(2f);
        dataSet2.setCircleSize(2f);
        dataSet2.setColor(Color.rgb(252, 76, 122));
        dataSet2.setCircleColor(Color.rgb(252, 76, 122));
        dataSet2.setHighLightColor(Color.GREEN);
        dataSet2.setHighlightEnabled(true);
        dataSet2.setValueTextColor(R.color.black_color);
        dataSet2.setValueTextSize(11f);

        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        //将数据加入dataSets
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        //构建一个LineData  将dataSets放入
        return new LineData(dataSets);
    }

    /**
     * @Description:初始化图表的样式
     */
    public static void initDataStyle(LineChart lineChart, LineData lineData, Context context) {
        //设置点击折线点时，显示其数值,自定义布局
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        mLineChart.setMarkerView(mv);
        lineChart.setDrawBorders(false); //在折线图上添加边框
        //lineChart.setDescription("时间/数据"); //数据描述
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false); //表格颜色
        lineChart.setGridBackgroundColor(Color.GRAY & 0x70FFFFFF); //表格的颜色，设置一个透明度
        lineChart.setTouchEnabled(true); //可点击
        lineChart.setDragEnabled(true);  //可拖拽
        lineChart.setScaleEnabled(true);  //可缩放
        lineChart.setPinchZoom(false);
        lineChart.setBackgroundColor(Color.WHITE); //设置背景颜色
        lineChart.setData(lineData);
        Legend mLegend = lineChart.getLegend(); //设置标示，就是一组y的value的
        mLegend.setForm(Legend.LegendForm.SQUARE); //样式
        mLegend.setFormSize(6f); //字体
        mLegend.setTextColor(Color.GRAY); //颜色
        XAxis xAxis = lineChart.getXAxis();  //x轴的标示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x轴位置
        xAxis.setTextColor(Color.GRAY);    //字体的颜色
        xAxis.setAxisMinimum(0);
        xAxis.setTextSize(10f); //字体大小
        xAxis.setGridColor(Color.GRAY);//网格线颜色
        xAxis.setDrawGridLines(false); //不显示网格线
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
        xAxis.setLabelCount(24);
        List<String> xList = new ArrayList<>();
        //x 轴显示 从 0 -23
        for(int i= 0 ;i < 24 ;i++) {
            xList.add(String.valueOf(i));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        xAxis.setAvoidFirstLastClipping(true); //禁止超出x轴

        YAxis axisLeft = lineChart.getAxisLeft(); //y轴左边标示
        YAxis axisRight = lineChart.getAxisRight(); //y轴右边标示
        //将y轴坐标系分成了 0 - 100 十份
        axisLeft.setAxisMaximum(100f);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setLabelCount(10 ,false);
        axisLeft.setTextColor(Color.GRAY); //字体颜色
        axisLeft.setTextSize(10f); //字体大小
        axisLeft.setGridColor(Color.GRAY); //网格线颜色

        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);

        //设置动画效果
        lineChart.animateY(1400, Easing.EasingOption.Linear);
        lineChart.animateX(1400, Easing.EasingOption.Linear);
        lineChart.invalidate();
    }

    /**
     * @param name 折线名
     *
     * 设置折线的名称
     */
    public static void setLineNameOne (String name) {
        lineName1 = name;
    }

    /**
     * @param name 折线名
     * @Description:设置另一条折线的名称
     */
    public static void setLineNameTwo(String name) {
        lineName2 = name;
    }

}
