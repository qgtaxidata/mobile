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
import com.example.taxidata.common.eventbus.EventFactory;
import com.example.taxidata.constant.Area;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.GsonUtil;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.SimpleLoadingDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.taxidata.constant.Area.BAI_YUN;
import static com.example.taxidata.constant.Area.CONG_HUA;
import static com.example.taxidata.constant.Area.GUANG_ZHOU;
import static com.example.taxidata.constant.Area.HAI_ZHU;
import static com.example.taxidata.constant.Area.HUANG_PU;
import static com.example.taxidata.constant.Area.HUA_DU;
import static com.example.taxidata.constant.Area.LI_WAN;
import static com.example.taxidata.constant.Area.NAN_SHA;
import static com.example.taxidata.constant.Area.PAN_YU;
import static com.example.taxidata.constant.Area.TIAN_HE;
import static com.example.taxidata.constant.Area.YUE_XIU;
import static com.example.taxidata.constant.Area.ZENG_CHENG;

/**
 * @author: ODM
 * @date: 2019/8/20
 */
public class AbnormalAnalyzeFragment extends BaseFragment {


    @BindView(R.id.btn_refresh_abnormal_analyze)
    Button btnRefresh;
    @BindView(R.id.select_area_abnormal_analyze)
    DropDownSelectView selectViewArea;
    @BindView(R.id.tv_abnormal_analyze_summary)
    TextView tvSummary;
    @BindView(R.id.tv_abnormal_detail)
    TextView tvDetail;
    @BindView(R.id.chart_abnormal_detail)
    BarChart chartDetail;
    @BindView(R.id.chart_abnormal_analyze_summary)
    PieChart chartSummary;
    private static String[] colors1 = {"#51b46d", "#F37997", "#4c93fd", "#AA99ED", "#79D2FF", "#49C9C9","#BBBBBB"};
    ArrayList<String> areaList = new ArrayList<>();
    SimpleLoadingDialog loading;
    private XAxis xAxis;
    private YAxis yAxis;
    private AbnormalInfo abnormalInfo;
    private SimpleLoadingDialog loadingDialog;
    //下拉框所选子项对应的序号
    private int selectIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abnormal_analyze, container, false);
        ButterKnife.bind(this, view);
        initAreaList();
        initViews();
        //防止home返回后再次请求
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initViews() {
        loadingDialog = new SimpleLoadingDialog(getContext(), "正在加载服务器数据", R.drawable.dialog_image_loading);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float areaAbnormal = abnormalInfo.getData().getPies().getPie().get(selectIndex).getAbnormal() * 100;
                float areaNormal = abnormalInfo.getData().getPies().getPie().get(selectIndex).getNormal() * 100;
                initChartSummary(new float[]{ areaNormal ,areaAbnormal});
            }
        });
        selectViewArea.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //将全局变量 所选的 index 变为 当前位置的序号
                selectIndex = position;
                tvSummary.setText(areaList.get(position).concat("异常车辆分布"));
            }
        });
    }

    /**
     * 加载页面所需的所有数据
     *只请求一次
     */
    private void  initData() {
        showLoadingDialog();
        //简单请求数据
        RetrofitManager.getInstance()
                .getHttpService()
                .getAbnormalinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AbnormalInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(AbnormalInfo info) {
                        cancelLoadingDialong();
                        abnormalInfo = info;
                        //给另外一个碎片发送异常车辆信息
                        BaseEvent baseEvent = EventFactory.getInstance();
                        baseEvent.type = "异常车辆信息";
                        baseEvent.object = abnormalInfo.getData().getCars();
                        EventBusUtils.postSticky(baseEvent);
                        //获取到网络数据后，加载下面的柱状图
                        initBarChart(abnormalInfo);
                        //获取到网络数据后，自动加载上面的饼状图
                        selectIndex = 0;
                        float areaAbnormal = abnormalInfo.getData().getPies().getPie().get(selectIndex).getAbnormal() * 100;
                        float areaNormal = abnormalInfo.getData().getPies().getPie().get(selectIndex).getNormal() * 100;
                        initChartSummary(new float[]{ areaNormal, areaAbnormal });
                    }
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showLongToastBottom("出错了，原因 :"+ e.getMessage());
                        cancelLoadingDialong();

                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initChartSummary(float[] dataArray) {
        chartSummary.setUsePercentValues(true);
        chartSummary.getDescription().setEnabled(false);
        chartSummary.setExtraOffsets(5, 10, 5, 5);
        chartSummary.setDragDecelerationFrictionCoef(0.95f);
        chartSummary.setDrawHoleEnabled(false);
        chartSummary.setHoleColor(Color.WHITE);
        chartSummary.setTransparentCircleColor(Color.WHITE);
        chartSummary.setTransparentCircleAlpha(110);
        chartSummary.setTransparentCircleRadius(61f);
        chartSummary.setDrawCenterText(true);
        chartSummary.setRotationAngle(0);
        chartSummary.setRotationEnabled(true);
        chartSummary.setHighlightPerTapEnabled(true);
        // 设置饼状点击事件
//        chartSummary.setOnChartValueSelectedListener(this);
        chartSummary.animateY(1400);
        Legend l = chartSummary.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(0f);
        l.setYOffset(5f);
        // 圆环内的标签设置
        chartSummary.setEntryLabelColor(Color.WHITE);
        chartSummary.setEntryLabelTextSize(15f);
        initPieData(dataArray);
    }

    //初始化区域popupWindow
    private void initAreaList() {
        areaList.add(GUANG_ZHOU);
        areaList.add(HUA_DU);
        areaList.add(NAN_SHA);
        areaList.add(ZENG_CHENG);
        areaList.add(CONG_HUA);
        areaList.add(PAN_YU);
        areaList.add(BAI_YUN);
        areaList.add(HUANG_PU);
        areaList.add(LI_WAN);
        areaList.add(HAI_ZHU);
        areaList.add(TIAN_HE);
        areaList.add(YUE_XIU);
        selectViewArea.setItemsData(areaList, 7);
    }

    /**
     * 图表数据设置
     *
     * @param
     * @return
     */
    private void  initPieData (float[] dataArray) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) dataArray[0] ,"正常") );
        entries.add(new PieEntry((float) dataArray[1] ,"异常") );
        PieDataSet dataSet = new PieDataSet(entries ,"");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor(colors1[0]));
        colors.add(Color.parseColor(colors1[1]));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                //构造方法的字符格式这里如果小数不足2位,会以0补足
                DecimalFormat decimalFormat = new DecimalFormat(".00");
               //format 返回的是字符串
                String p = decimalFormat.format(value);
                String str = p + "%";
                if (value == 0) {
                    str = "";
                }
                return str;
            }
        });
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        chartSummary.setData(data);
        // undo all highlights
        chartSummary.highlightValues(null);
        chartSummary.invalidate();
    }

    public void initBarChart(AbnormalInfo info) {
        chartDetail.setDrawBarShadow(false);
        chartDetail.setDrawValueAboveBar(true);
        chartDetail.getDescription().setEnabled(false);
        // 扩展现在只能分别在x轴和y轴
        chartDetail.setPinchZoom(false);
        //是否显示表格颜色
        chartDetail.setDrawGridBackground(false);
        xAxis = chartDetail.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setAxisMaximum(12f);
        xAxis.setAxisMinimum(0f);
        String[] str = {"","花都区","南沙区","增城区","从化区","番禺区","白云区","黄浦区","荔湾区","海珠区","天河区","越秀区"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(str));
        xAxis.setTextSize(8f);
        yAxis = chartDetail.getAxisLeft();
        yAxis.setLabelCount(6, false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(20f);
        //这个替换setStartAtZero(true)
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(1200f);
        //取消右方y轴显示
        chartDetail.getAxisRight().setEnabled(false);
        //取消图例的显示
        Legend legend = chartDetail.getLegend();
        legend.setEnabled(false);
        initBarChartData(info);
    }

    public void initBarChartData(AbnormalInfo info) {
        List<BarEntry> Vals = new ArrayList<>();
        //将数据装填在柱状图所需的数据里
        for(int i = 0 ; i < info.getData().getBar().getX().size() ; i++) {
            Vals.add(new BarEntry(info.getData().getBar().getX().get(i) , info.getData().getBar().getY().get(i))) ;
        }
        BarDataSet barDataSet;
        barDataSet = new BarDataSet(Vals,"区域异常车辆数量");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor(colors1[3]));
        barDataSet.setColors(colors);
        barDataSet.setBarBorderWidth(0.5f);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet);
        BarData barData = new BarData(dataSets);
        barData.setValueTextColor(R.color.qmui_config_color_red);
        barData.setValueTextSize(10f);
        barData.setBarWidth(0.5f);
        //设置数据
        chartDetail.setData(barData);
        chartDetail.animateXY(1400 , 1400);
        chartDetail.invalidate();
    }

    public void showLoadingDialog() {

        loadingDialog.show();
    }

    public void cancelLoadingDialong() {

        loadingDialog.dismiss();
    }

}
