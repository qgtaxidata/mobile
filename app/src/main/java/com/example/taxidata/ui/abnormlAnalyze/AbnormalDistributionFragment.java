package com.example.taxidata.ui.abnormlAnalyze;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taxidata.R;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.base.BaseFragment;
import com.example.taxidata.bean.AbnormalInfo;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.util.LineChartUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: ODM
 * @date: 2019/8/20
 */
public class AbnormalDistributionFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.btn_refresh_abnormal_analyze)
    Button btnRefresh;
    @BindView(R.id.select_area_abnormal_analyze)
    DropDownSelectView selectViewArea;
    @BindView(R.id.tv_abnormal_distribution_summary)
    TextView tvSummary;
    @BindView(R.id.chart_abnormal_distribution_summary)
    LineChart chartSummary;
    AbnormalInfo.DataBean.CarsBean  carsBeanInfo;
    ArrayList<String> carNumberList = new ArrayList<>();
    float[] datasNormal;
    int selectIndex = -1 ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abnormal_distribution, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
    }

    public void initViews() {
         btnRefresh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //点击获取图表后,将对应序号传入画图方法
                 if(selectIndex != -1) {
                     initLineChart(selectIndex);
                 }
             }
         });
         selectViewArea.setOnItemClickListener(new OnItemClickListener() {
             @Override
             public void onItemClick(int position) {
                 Logger.d("用户点击了这辆车" + carNumberList.get(position) );
                 selectIndex = position;
             }
         });
    }

    //初始化车牌下拉框
    private void initCarNumberList(ArrayList<String> list) {
        selectViewArea.setItemsData(list, 8);
    }

    private void initLineChart(int index) {
        int numX = 24;
        //设置y轴的数据
        //所选车辆的数据
        float[] abnormalData = new float[carsBeanInfo.getAbnormal().get(index).getDistribution().size()];
        for(int i = 0 ;i < carsBeanInfo.getAbnormal().get(index).getDistribution().size() ; i++) {
            abnormalData[i] = carsBeanInfo.getAbnormal().get(index).getDistribution().get(i) * 100;
        }
        //设置折线的名称
        LineChartUtil.setLineNameOne("正常车(单位:%)");
        LineChartUtil.setLineNameTwo("异常车(单位:%)");
        //创建两条折线的图表(一条正常 ，一条异常)
        LineData lineData = LineChartUtil.initDoubleLineChart(getContext() ,chartSummary ,numX ,datasNormal,abnormalData);
        LineChartUtil.initDataStyle(chartSummary ,lineData ,getContext());
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void handleEvent(BaseEvent baseEvent) {
        super.handleEvent(baseEvent);
        if("异常车辆信息".equals(baseEvent.type)) {
            Logger.d("收到异常车辆信息");
            //获取异常车辆信息对象
            carsBeanInfo = (AbnormalInfo.DataBean.CarsBean) baseEvent.object;
            //正常车的数据初始化
            datasNormal = new float[carsBeanInfo.getNormal_distribution().size()] ;
            for(int i = 0 ; i < carsBeanInfo.getNormal_distribution().size() ; i++) {

                float f = carsBeanInfo.getNormal_distribution().get(i) * 100;
                datasNormal[i] =  f;
            }
            //车牌列表初始化
            for (int j = 0 ; j < carsBeanInfo.getAbnormal().size() ; j++) {
                carNumberList.add(carsBeanInfo.getAbnormal().get(j).getLicense());
            }
            initCarNumberList(carNumberList);
        }
    }
}
