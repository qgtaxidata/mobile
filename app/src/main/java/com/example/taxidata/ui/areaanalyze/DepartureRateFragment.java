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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartureRateFragment extends BaseFragment {

    private TextView departureRateTitle;
    private LineChart departureRateLineChart;
    private XAxis xAxis;
    private YAxis yAxis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departure_rate, container, false);
        EventBus.getDefault().register(this);
        departureRateTitle = view.findViewById(R.id.departure_rate_title);
        departureRateLineChart = view.findViewById(R.id.departure_rate_line_chart);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDepartureRateChart(AreaAnalyzeInfo.DataBean.TimeUtilizationBean timeUtilizationBean) {
        departureRateLineChart.clear();
        Logger.d(timeUtilizationBean.getType());
        //图表初始化
        departureRateLineChart.setDrawGridBackground(false);
        departureRateLineChart.setDragEnabled(true);  //禁止缩放
        departureRateLineChart.setScaleEnabled(true);  //禁止推动
        departureRateLineChart.setDrawBorders(false);    //设置四周是否有边框
        departureRateLineChart.setBackgroundColor(Color.WHITE);
        departureRateLineChart.getAxisRight().setEnabled(false);   //不显示右侧y轴
        departureRateLineChart.getDescription().setEnabled(false);
        departureRateLineChart.getLegend().setEnabled(false);   //不显示图例
        //x轴的相关设置
        xAxis = departureRateLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);   //去掉x轴的网格线
        xAxis.setLabelCount(7);   //设置x轴刻度数量
        //Y轴的相关设置
        yAxis = departureRateLineChart.getAxisLeft();
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
        departureRateTitle.setText(timeUtilizationBean.getType());
        Log.d("show", timeUtilizationBean.getType());
        List<String> xList = new ArrayList<>();
        //添加数据
        Log.d("size",timeUtilizationBean.getX().size()+"");
        for (int i = 0 ; i<timeUtilizationBean.getX().size(); i++){
            xList.add(timeUtilizationBean.getX().get(i));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i <timeUtilizationBean.getY().size(); i++) {
            values.add(new Entry(i, timeUtilizationBean.getY().get(i)));
        }
        //每个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(values, "");
        initLineDataSet(lineDataSet);
        LineData lineData = new LineData(lineDataSet);
        departureRateLineChart.setData(lineData);
        departureRateLineChart.notifyDataSetChanged();
    }

    //初始化折线
    private void initLineDataSet(LineDataSet lineDataSet) {
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(Color.parseColor("#51b46d"));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleColor(Color.parseColor("#51b46d"));
        lineDataSet.setCircleRadius(1f);
        lineDataSet.setDrawCircleHole(false);    //设置曲线值的圆点是实心还是空心
        lineDataSet.setValueTextSize(20f);
        lineDataSet.setValueTextColor(Color.parseColor("#51b46d"));
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
    }
}
