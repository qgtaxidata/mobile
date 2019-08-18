package com.example.taxidata.ui.taxidemand;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxidata.R;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.TaxiDemandInfo;
import com.example.taxidata.constant.Area;
import com.example.taxidata.widget.DropDownSelectView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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

public class TaxiDemandActivity extends BaseActivity implements TaxiDemandContract.TaxiDemandView {

    @BindView(R.id.taxi_demand_analyze_area_select_view)
    DropDownSelectView taxiDemandAnalyzeAreaSelectView;
    @BindView(R.id.taxi_demand_analyze_btn_refresh_list)
    Button taxiDemandAnalyzeBtnRefreshList;
    @BindView(R.id.taxi_demand_analyze_title_tv)
    TextView taxiDemandAnalyzeTitleTv;
    @BindView(R.id.taxi_demand_analyze_line_chart)
    LineChart taxiDemandAnalyzeLineChart;

    private TaxiDemandContract.TaxiDemandPresent present;
    private TaxiDemandInfo.DataBean dataBean;
    ArrayList<String> areaList = new ArrayList<>();
    private int areaId = 5;
    private String currentTime;
    private XAxis xAxis;
    private YAxis yAxis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_demand);
        ButterKnife.bind(this);
        present = new TaxiDemandPresent();
        present.attachView(this);
        //获取当前时间
        currentTime = TaxiApp.getAppNowTime();
        //初始化区域选择框
        initAreaList();
        //初始化图表(默认显示番禺当前时间)
        getChartInfo();
        //获取用户选择的区域
        taxiDemandAnalyzeAreaSelectView.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
    }

    //点击刷新图表
    @OnClick(R.id.taxi_demand_analyze_btn_refresh_list)
    public void onViewClicked() {
        currentTime = TaxiApp.getAppNowTime();
        present.getTaxiDemandInfo(TaxiDemandActivity.this, areaId, currentTime);
    }

    //初始化区域选择框
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
        taxiDemandAnalyzeAreaSelectView.setItemsData(areaList, 1);
    }

    //初始化图表(默认显示番禺区当前时间的数据)
    private void getChartInfo(){
        present.getTaxiDemandInfo(TaxiDemandActivity.this, areaId, "2017-02-03 11:11:11");
    }

    //展示列表
    @Override
    public void showChart(TaxiDemandInfo.DataBean dataBeans) {
        //图表初始化
        taxiDemandAnalyzeLineChart.setDrawGridBackground(false);
        taxiDemandAnalyzeLineChart.setDragEnabled(false);  //禁止缩放
        taxiDemandAnalyzeLineChart.setScaleEnabled(false);  //禁止推动
        taxiDemandAnalyzeLineChart.setDrawBorders(false);    //设置四周是否有边框
        taxiDemandAnalyzeLineChart.setBackgroundColor(Color.WHITE);
        taxiDemandAnalyzeLineChart.getAxisRight().setEnabled(false);   //不显示右侧y轴
        taxiDemandAnalyzeLineChart.getDescription().setEnabled(false);
        taxiDemandAnalyzeLineChart.getLegend().setEnabled(false);   //不显示图例
        //x轴的相关设置
        xAxis = taxiDemandAnalyzeLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);   //去掉x轴的网格线
        xAxis.setLabelCount(3,false);
        //Y轴的相关设置
        yAxis = taxiDemandAnalyzeLineChart.getAxisLeft();
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
        taxiDemandAnalyzeTitleTv.setText(dataBeans.getGraph_title());
        Log.d("show", dataBeans.getGraph_title());
        List<String> xList = new ArrayList<>();
        //添加数据
        for (int i = 0 ; i<3; i++){
            xList.add(dataBeans.getGraph_data().get(i).getTitle());
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            values.add(new Entry(i, dataBeans.getGraph_data().get(i).getDemand()));
        }
        //每个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(values, "");
        initLineDataSet(lineDataSet);
        LineData lineData = new LineData(lineDataSet);
        taxiDemandAnalyzeLineChart.setData(lineData);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }

    //初始化折线
    private void initLineDataSet(LineDataSet lineDataSet) {
        lineDataSet.setColor(Color.parseColor("#51b46d"));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleColor(Color.parseColor("#51b46d"));
        lineDataSet.setCircleRadius(2f);
        lineDataSet.setDrawCircleHole(false);    //设置曲线值的圆点是实心还是空心
        lineDataSet.setValueTextSize(20f);
        lineDataSet.setValueTextColor(Color.parseColor("#51b46d"));
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
    }
}
