package com.example.taxidata.ui.roadquality;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taxidata.R;
import com.example.taxidata.bean.RoadQualityInfo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AverageTimeFragment extends Fragment {

    private TextView averageTimeTitle;
    private LineChart averageTimeLineChart;
    private XAxis xAxis;
    private YAxis yAxis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_average_time, container, false);
        EventBus.getDefault().register(this);
        averageTimeLineChart = view.findViewById(R.id.average_time_line_chart);
        averageTimeTitle = view.findViewById(R.id.average_time_title);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAverageTimeChart(RoadQualityInfo.DataBean.AverageTimeBean averageTimeBean){
        averageTimeLineChart.clear();
        Logger.d(averageTimeBean.getType());
        //图表初始化
        averageTimeLineChart.setDrawGridBackground(false);
        averageTimeLineChart.setDragEnabled(true);  //禁止缩放
        averageTimeLineChart.setScaleEnabled(true);  //禁止推动
        averageTimeLineChart.setDrawBorders(false);    //设置四周是否有边框
        averageTimeLineChart.setBackgroundColor(Color.WHITE);
        averageTimeLineChart.getAxisRight().setEnabled(false);   //不显示右侧y轴
        averageTimeLineChart.getDescription().setEnabled(false);
        //averageTimeLineChart.getLegend().setEnabled(false);   //不显示图例
        //x轴的相关设置
        xAxis = averageTimeLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);   //去掉x轴的网格线
        xAxis.setLabelCount(7);   //设置x轴刻度数量
        //Y轴的相关设置
        yAxis = averageTimeLineChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setAxisLineWidth(1.5f);
        yAxis.setDrawGridLines(true);      //显示y轴的网格线
        yAxis.enableGridDashedLine(10f, 10f, 0f);   //并设置为破折线
        yAxis.setAxisMinimum(0f);
        yAxis.setDrawAxisLine(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisLineColor(Color.BLACK);
        Log.d("show", "wx");
        //设置标题
        averageTimeTitle.setText(averageTimeBean.getType());
        Log.d("show", averageTimeBean.getType());
        //添加x轴数据
        List<String> xList = new ArrayList<>();
        xList.addAll(averageTimeBean.getX().get(0));
        xList.addAll(averageTimeBean.getX().get(1));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        //添加分析数据
        ArrayList<Entry> analyzeValues = new ArrayList<>();
        for (int i = 0; i <averageTimeBean.getY().get(0).size(); i++) {
            analyzeValues.add(new Entry(i, averageTimeBean.getY().get(0).get(i)));
        }
        //添加预测数据
        ArrayList<Entry> forecastValues = new ArrayList<>();
        for (int i =0 ;averageTimeBean.getY().get(0).size()+i <averageTimeBean.getY().get(0).size()+averageTimeBean.getY().get(1).size(); i++) {
            forecastValues.add(new Entry(i+averageTimeBean.getY().get(0).size(), averageTimeBean.getY().get(1).get(i)));
        }
        //每个LineDataSet代表一条线
        LineDataSet analyzeLineDataSet = new LineDataSet(analyzeValues, "平均速度分析");
        LineDataSet forecastLineDataSet = new LineDataSet(forecastValues, "平均速度预测");
        initLineDataSet(analyzeLineDataSet,"#4472c4");
        initLineDataSet(forecastLineDataSet, "#ed7d31");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(analyzeLineDataSet);
        dataSets.add(forecastLineDataSet);
        LineData lineData = new LineData(dataSets);
        averageTimeLineChart.setData(lineData);
        averageTimeLineChart.notifyDataSetChanged();
    }

    //初始化折线
    private void initLineDataSet(LineDataSet lineDataSet, String color) {
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(Color.parseColor(color));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleColor(Color.parseColor(color));
        lineDataSet.setCircleRadius(1f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawCircleHole(false);    //设置曲线值的圆点是实心还是空心
        lineDataSet.setValueTextSize(20f);
        lineDataSet.setValueTextColor(Color.parseColor(color));
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
    }


}
