package com.example.taxidata.ui.taxidemand;

import android.os.Bundle;
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

import java.util.ArrayList;

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
        present.getTaxiDemandInfo(TaxiDemandActivity.this, areaId, currentTime);
    }

    //展示列表
    @Override
    public void showChart(TaxiDemandInfo.DataBean dataBean) {
        taxiDemandAnalyzeTitleTv.setText(dataBean.getGraph_title());

    }
}
