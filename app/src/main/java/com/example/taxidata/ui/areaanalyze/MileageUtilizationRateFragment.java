package com.example.taxidata.ui.areaanalyze;

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
import com.example.taxidata.base.BaseFragment;
import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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
import butterknife.ButterKnife;

public class MileageUtilizationRateFragment extends BaseFragment {

    private XAxis xAxis;
    private YAxis yAxis;
    private TextView mileageUtilizationRateTitle;
    private LineChart mileageUtilizationRateLineChart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mileage_utilization_rate, container, false);
        EventBus.getDefault().register(this);
        mileageUtilizationRateTitle = view.findViewById(R.id.mileage_utilization_rate_title);
        mileageUtilizationRateLineChart = view.findViewById(R.id.mileage_utilization_rate_line_chart);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMileageUtilizationRateChart(AreaAnalyzeInfo.DataBean.MileageUtilizationBean mileageUtilizationBean) {
        mileageUtilizationRateLineChart.clear();
        initChart();
        //设置标题
        mileageUtilizationRateTitle.setText(mileageUtilizationBean.getType());
        Log.d("show", mileageUtilizationBean.getType());
        //添加x轴数据
        List<String> xList = new ArrayList<>();
        xList.addAll(mileageUtilizationBean.getX().get(0));
        xList.addAll(mileageUtilizationBean.getX().get(1));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        //添加y轴的分析数据
        ArrayList<Entry> analyzeValues = new ArrayList<>();
        for (int i = 0; i <mileageUtilizationBean.getY().get(0).size(); i++) {
            analyzeValues.add(new Entry(i, mileageUtilizationBean.getY().get(0).get(i)));
        }
        //添加y轴的预测数据
        ArrayList<Entry> forecastValues = new ArrayList<>();
        forecastValues.add(new Entry(mileageUtilizationBean.getY().get(0).size()-1, mileageUtilizationBean.getY().get(0).get(mileageUtilizationBean.getY().get(0).size()-1)));
        for (int i =0 ;mileageUtilizationBean.getY().get(0).size()+i <mileageUtilizationBean.getY().get(0).size()+mileageUtilizationBean.getY().get(1).size(); i++) {
            forecastValues.add(new Entry(i+mileageUtilizationBean.getY().get(0).size(), mileageUtilizationBean.getY().get(1).get(i)));
        }
        //每个LineDataSet代表一条线
        LineDataSet analyzeLineDataSet = new LineDataSet(analyzeValues, "历史值(单位：%)");
        LineDataSet forecastLineDataSet = new LineDataSet(forecastValues, "预测值(单位：%)");
        initLineDataSet(analyzeLineDataSet,"#4472c4");
        initLineDataSet(forecastLineDataSet, "#ed7d31");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(analyzeLineDataSet);
        dataSets.add(forecastLineDataSet);
        LineData lineData = new LineData(dataSets);
        mileageUtilizationRateLineChart.setData(lineData);
        mileageUtilizationRateLineChart.notifyDataSetChanged();
    }

    //初始化图表
    private void initChart(){
        //图表初始化
        mileageUtilizationRateLineChart.setDrawGridBackground(false);
        mileageUtilizationRateLineChart.setDragEnabled(true);  //禁止缩放
        mileageUtilizationRateLineChart.setScaleEnabled(true);  //禁止推动
        mileageUtilizationRateLineChart.setDrawBorders(false);    //设置四周是否有边框
        mileageUtilizationRateLineChart.getAxisRight().setEnabled(false);   //不显示右侧y轴
        mileageUtilizationRateLineChart.getDescription().setEnabled(false);
        //设置图例
        Legend legend = mileageUtilizationRateLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12);
        legend.setFormSize(10);
        //x轴的相关设置
        xAxis = mileageUtilizationRateLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);   //去掉x轴的网格线
        xAxis.setLabelCount(7);   //设置x轴刻度数量
        //Y轴的相关设置
        yAxis = mileageUtilizationRateLineChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setAxisLineWidth(1.5f);
        yAxis.setDrawGridLines(true);      //显示y轴的网格线
        yAxis.enableGridDashedLine(10f, 10f, 0f);   //并设置为破折线
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawAxisLine(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisLineColor(Color.BLACK);
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
