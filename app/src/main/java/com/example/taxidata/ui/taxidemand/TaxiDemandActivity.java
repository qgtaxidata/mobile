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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
        //初始化图表
        initChart();
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
    private void initChart(){
        taxiDemandAnalyzeLineChart.setDrawGridBackground(false);
        taxiDemandAnalyzeLineChart.setDragEnabled(false);  //禁止缩放
        taxiDemandAnalyzeLineChart.setScaleEnabled(false);  //禁止推动
        taxiDemandAnalyzeLineChart.setDrawBorders(false);    //设置四周是否有边框
        taxiDemandAnalyzeLineChart.setDescription(null);   //设置右下角说明文字
        //x轴的相关设置
        xAxis = taxiDemandAnalyzeLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //x轴显示位置
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisLineWidth(3f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setLabelCount(3);  //x轴上有多少个刻度
        //Y轴的相关设置
        taxiDemandAnalyzeLineChart.getAxisRight().setEnabled(false);
        YAxis leftY = taxiDemandAnalyzeLineChart.getAxisLeft();
        leftY.setEnabled(true);
        leftY.setAxisLineWidth(3f);
        leftY.setDrawGridLines(false);
        leftY.setAxisMinimum(0f);
        leftY.setDrawAxisLine(true);
        leftY.setTextColor(Color.BLACK);
        leftY.setAxisLineColor(Color.BLACK);
        present.getTaxiDemandInfo(TaxiDemandActivity.this, areaId, "2017-02-03 11:11:11");
    }

    //展示列表
    @Override
    public void showChart(TaxiDemandInfo.DataBean dataBeans) {
        if(dataBeans!=null){
            taxiDemandAnalyzeTitleTv.setText(dataBeans.getGraph_title());
            List<TaxiDemandInfo.DataBean.GraphDataBean> dataList = dataBeans.getGraph_data();
            //设置X轴数据
            String[] xValues = new String[3];
            for(int j = 2; j>=0; j--){
            xValues[j] = dataList.get(j).getTitle();
        }
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues[(int)value];
            }
        };
            xAxis.setValueFormatter(formatter);
            //添加Y轴数据
            ArrayList<Entry> values = new ArrayList<>();
            values.add(new Entry(0,0));
            for(int i = 0; i<3; i++){
            values.add(new Entry(i, dataList.get(i).getDemand()));
        }
            LineDataSet lineDataSet;
            if(taxiDemandAnalyzeLineChart.getData() != null && taxiDemandAnalyzeLineChart.getData().getDataSetCount() > 0){
                lineDataSet = (LineDataSet)taxiDemandAnalyzeLineChart.getData().getDataSetByIndex(0);
                lineDataSet.setValues(values);
                taxiDemandAnalyzeLineChart.getData().notifyDataChanged();
                taxiDemandAnalyzeLineChart.notifyDataSetChanged();
            }else {
            lineDataSet = new LineDataSet(values, null);
            lineDataSet.setLineWidth(2f);
            lineDataSet.setCircleRadius(3f);
            lineDataSet.setDrawCircleHole(false);   //实心还是空心
            lineDataSet.setValueTextSize(14f);
            lineDataSet.setDrawFilled(false);
            lineDataSet.setFormLineWidth(5f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            lineDataSet.setFormSize(15.f);
            //添加数据集
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(lineDataSet);
            //创建数据集的数据对象
            LineData data = new LineData(dataSets);
            taxiDemandAnalyzeLineChart.setData(data);
        }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }

}
