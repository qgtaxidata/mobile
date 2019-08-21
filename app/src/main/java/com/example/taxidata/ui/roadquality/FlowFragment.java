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

public class FlowFragment extends Fragment {

    private TextView flowTitle;
    private LineChart flowLineChart;
    private XAxis xAxis;
    private YAxis yAxis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flow, container, false);
        EventBus.getDefault().register(this);
        flowLineChart = view.findViewById(R.id.flow_line_chart);
        flowTitle = view.findViewById(R.id.flow_title);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showFlowChart(RoadQualityInfo.DataBean.FlowBean flowBean){
        flowLineChart.clear();
        Logger.d(flowBean.getType());
        //图表初始化
        flowLineChart.setDrawGridBackground(false);
        flowLineChart.setDragEnabled(true);  //禁止缩放
        flowLineChart.setScaleEnabled(true);  //禁止推动
        flowLineChart.setDrawBorders(false);    //设置四周是否有边框
        flowLineChart.setBackgroundColor(Color.WHITE);
        flowLineChart.getAxisRight().setEnabled(false);   //不显示右侧y轴
        flowLineChart.getDescription().setEnabled(false);
        flowLineChart.getLegend().setEnabled(false);   //不显示图例
        //x轴的相关设置
        xAxis = flowLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);   //去掉x轴的网格线
        xAxis.setLabelCount(7);   //设置x轴刻度数量
        //Y轴的相关设置
        yAxis = flowLineChart.getAxisLeft();
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
        flowTitle.setText(flowBean.getType());
        Log.d("show", flowBean.getType());
        //添加x轴数据
        List<String> xList = new ArrayList<>();
        xList.addAll(flowBean.getX().get(0));
        xList.addAll(flowBean.getX().get(1));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        //添加分析数据
        ArrayList<Entry> analyzeValues = new ArrayList<>();
        for (int i = 0; i <flowBean.getY().get(0).size(); i++) {
            analyzeValues.add(new Entry(i, flowBean.getY().get(0).get(i)));
        }
        //添加预测数据
        ArrayList<Entry> forecastValues = new ArrayList<>();
        for (int i =0 ;flowBean.getY().get(0).size()+i <flowBean.getY().get(0).size()+flowBean.getY().get(1).size(); i++) {
            forecastValues.add(new Entry(i+flowBean.getY().get(0).size(), flowBean.getY().get(1).get(i)));
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
        flowLineChart.setData(lineData);
        flowLineChart.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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
