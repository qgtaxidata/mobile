package com.example.taxidata.ui.areaincome;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.taxidata.R;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.AreaIncomeInfo;
import com.example.taxidata.constant.Area;
import com.example.taxidata.util.TimeChangeUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.SimpleLoadingDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.taxidata.constant.Area.BAI_YUN;
import static com.example.taxidata.constant.Area.CONG_HUA;
import static com.example.taxidata.constant.Area.HAI_ZHU;
import static com.example.taxidata.constant.Area.HUANG_PU;
import static com.example.taxidata.constant.Area.HUA_DU;
import static com.example.taxidata.constant.Area.LI_WAN;
import static com.example.taxidata.constant.Area.NAN_SHA;
import static com.example.taxidata.constant.Area.PAN_YU;
import static com.example.taxidata.constant.Area.TIAN_HE;
import static com.example.taxidata.constant.Area.YUE_XIU;
import static com.example.taxidata.constant.Area.ZENG_CHENG;

public class AreaIncomeActivity extends BaseActivity implements AreaIncomeContract.AreaIncomeView {

    @BindView(R.id.area_income_area_select_view)
    DropDownSelectView areaIncomeAreaSelectView;
    @BindView(R.id.area_income_time_select_view)
    DropDownSelectView areaIncomeTimeSelectView;
    @BindView(R.id.area_income_refresh_list_btn)
    Button areaIncomeRefreshListBtn;
    @BindView(R.id.area_income_title_tv)
    TextView areaIncomeTitleTv;
    @BindView(R.id.area_income_line_chart)
    LineChart areaIncomeLineChart;

    private AreaIncomeContract.AreaIncomePresent present;
    private SimpleLoadingDialog loading;
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    private int areaId = 5;
    private String date = "2017-02-05";
    private XAxis xAxis;
    private YAxis yAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_income);
        ButterKnife.bind(this);
        present = new AreaIncomePresent();
        present.attachView(this);
        initAreaList();
        initTimeList();
        getChartInfo();
        //获取用户选择的区域
        areaIncomeAreaSelectView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
        //获取用户选择的时间
        areaIncomeTimeSelectView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String time = timeList.get(position);
                try {
                    date = TimeChangeUtil.transform(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.area_income_refresh_list_btn)
    public void onViewClicked() {
        present.getAreaIncomeInfo(AreaIncomeActivity.this, areaId, date);
    }

    //初始化区域popupWindow
    private void initAreaList() {
        areaList.add(LI_WAN);
        areaList.add(YUE_XIU);
        areaList.add(TIAN_HE);
        areaList.add(HAI_ZHU);
        areaList.add(HUANG_PU);
        areaList.add(HUA_DU);
        areaList.add(BAI_YUN);
        areaList.add(PAN_YU);
        areaList.add(NAN_SHA);
        areaList.add(CONG_HUA);
        areaList.add(ZENG_CHENG);
        areaIncomeAreaSelectView.setItemsData(areaList, 1);
    }

    //初始化时间popupWindow
    private void initTimeList() {
        timeList.add("2017年02月05日");
        timeList.add("2017年02月06日");
        timeList.add("2017年02月07日");
        timeList.add("2017年02月08日");
        timeList.add("2017年02月09日");
        timeList.add("2017年02月10日");
        timeList.add("2017年02月11日");
        timeList.add("2017年02月12日");
        timeList.add("2017年02月13日");
        timeList.add("2017年02月14日");
        timeList.add("2017年02月15日");
        timeList.add("2017年02月16日");
        timeList.add("2017年02月17日");
        timeList.add("2017年02月18日");
        timeList.add("2017年02月19日");
        timeList.add("2017年02月20日");
        timeList.add("2017年02月21日");
        areaIncomeTimeSelectView.setItemsData(timeList, 2);
    }

    //初始化图表(默认显示番禺区和2017年02月05号的数据)
    private void getChartInfo() {
        present.getAreaIncomeInfo(AreaIncomeActivity.this, areaId, date);
    }

    @Override
    public void showChart(AreaIncomeInfo.DataBean dataBean) {
        areaIncomeLineChart.clear();
        initChart();
        //设置标题
        areaIncomeTitleTv.setText(dataBean.getTitle());
        Log.d("show", dataBean.getTitle());
        //添加x轴数据
        List<String> xAnalyzeList = new ArrayList<>();
        xAnalyzeList.addAll(dataBean.getImcome().getX().get(0));
        xAnalyzeList.addAll(dataBean.getImcome().getX().get(1));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAnalyzeList));
        //添加分析数据
        ArrayList<Entry> analyzeValues = new ArrayList<>();
        for (int i = 0; i <dataBean.getImcome().getY().get(0).size(); i++) {
            analyzeValues.add(new Entry(i, dataBean.getImcome().getY().get(0).get(i)));
        }
        //添加预测数据
        ArrayList<Entry> forecastValues = new ArrayList<>();
        forecastValues.add(new Entry(dataBean.getImcome().getY().get(0).size()-1,dataBean.getImcome().getY().get(0).get(dataBean.getImcome().getY().get(0).size()-1)));
        for (int i =0 ;dataBean.getImcome().getY().get(0).size()+i <dataBean.getImcome().getY().get(1).size()+dataBean.getImcome().getY().get(0).size(); i++) {
            forecastValues.add(new Entry(i+dataBean.getImcome().getY().get(0).size(), dataBean.getImcome().getY().get(1).get(i)));
        }
        //每个LineDataSet代表一条线
        LineDataSet analyzeLineDataSet = new LineDataSet(analyzeValues, "分析值");
        LineDataSet forecastLineDataSet = new LineDataSet(forecastValues, "预测值");
        initLineDataSet(analyzeLineDataSet,"#4472c4");
        initLineDataSet(forecastLineDataSet, "#ed7d31");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(analyzeLineDataSet);
        dataSets.add(forecastLineDataSet);
        LineData lineData = new LineData(dataSets);
        areaIncomeLineChart.setData(lineData);
        areaIncomeLineChart.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
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

    //加载loading界面
    @Override
    public void showLoadingView() {
        //初始化loading界面
        loading = new SimpleLoadingDialog(this,"数据正在加载中！",R.drawable.dialog_image_loading);
        loading.show();
    }
    //取消loading界面
    @Override
    public void hideLoadingView() {
        if(loading!=null){
            loading.dismiss();
        }
    }

    private void initChart(){
        //图表初始化
        areaIncomeLineChart.setDrawGridBackground(false);
        areaIncomeLineChart.setDragEnabled(true);  //禁止缩放
        areaIncomeLineChart.setScaleEnabled(true);  //禁止推动
        areaIncomeLineChart.setDrawBorders(false);    //设置四周是否有边框
        areaIncomeLineChart.getAxisRight().setEnabled(false);   //不显示右侧y轴
        areaIncomeLineChart.getDescription().setEnabled(false);
        //设置图例
        Legend legend = areaIncomeLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12);
        legend.setFormSize(10);
        //x轴的相关设置
        xAxis = areaIncomeLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);   //去掉x轴的网格线
        xAxis.setLabelCount(7);   //设置x轴刻度数量
        //Y轴的相关设置
        yAxis = areaIncomeLineChart.getAxisLeft();
        yAxis.removeAllLimitLines();
        yAxis.setAxisLineWidth(1f);
        yAxis.setDrawGridLines(true);      //显示y轴的网格线
        yAxis.enableGridDashedLine(10f, 10f, 0f);   //并设置为破折线
        yAxis.setAxisMinimum(0f);
        yAxis.setDrawAxisLine(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setAxisLineColor(Color.BLACK);
    }
}
