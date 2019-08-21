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
    private static String[] colors1 = {"#ffbb86", "#F37997", "#4c93fd", "#AA99ED", "#79D2FF", "#49C9C9","#BBBBBB"};
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
        Logger.d("onCreateView");
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
                        Logger.d(GsonUtil.GsonString(abnormalInfo));
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
                        //假数据测试
                        AbnormalInfo info = GsonUtil.GsonToBean( "{\n" +
                                "    \"msg\": \"success\",\n" +
                                "    \"code\": 1,\n" +
                                "    \"data\": {\n" +
                                "        \"bar\": {\n" +
                                "            \"title\": \"异常车辆数量分布\",\n" +
                                "            \"x\": [\n" +
                                "                1,\n" +
                                "                2,\n" +
                                "                3,\n" +
                                "                4,\n" +
                                "                5,\n" +
                                "                6,\n" +
                                "                7,\n" +
                                "                8,\n" +
                                "                9,\n" +
                                "                10,\n" +
                                "                11\n" +
                                "            ],\n" +
                                "            \"y\": [\n" +
                                "                382,\n" +
                                "                811,\n" +
                                "                798,\n" +
                                "                955,\n" +
                                "                725,\n" +
                                "                922,\n" +
                                "                46,\n" +
                                "                470,\n" +
                                "                790,\n" +
                                "                460,\n" +
                                "                650\n" +
                                "            ]\n" +
                                "        },\n" +
                                "        \"pies\": {\n" +
                                "            \"title\": \"异常车辆概率分布\",\n" +
                                "            \"pie\": [\n" +
                                "                {\n" +
                                "                    \"area\": 0,\n" +
                                "                    \"abnormal\": 0.6483,\n" +
                                "                    \"normal\": 0.3517\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 1,\n" +
                                "                    \"abnormal\": 0.5222,\n" +
                                "                    \"normal\": 0.4778\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 2,\n" +
                                "                    \"abnormal\": 0.4589,\n" +
                                "                    \"normal\": 0.5411\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 3,\n" +
                                "                    \"abnormal\": 0.29,\n" +
                                "                    \"normal\": 0.71\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 4,\n" +
                                "                    \"abnormal\": 0.1998,\n" +
                                "                    \"normal\": 0.8002\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 5,\n" +
                                "                    \"abnormal\": 0.5476,\n" +
                                "                    \"normal\": 0.4524\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 6,\n" +
                                "                    \"abnormal\": 0.4735,\n" +
                                "                    \"normal\": 0.5265\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 7,\n" +
                                "                    \"abnormal\": 0.9199,\n" +
                                "                    \"normal\": 0.0801\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 8,\n" +
                                "                    \"abnormal\": 0.5073,\n" +
                                "                    \"normal\": 0.4927\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 9,\n" +
                                "                    \"abnormal\": 0.5142,\n" +
                                "                    \"normal\": 0.4858\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 10,\n" +
                                "                    \"abnormal\": 0.4033,\n" +
                                "                    \"normal\": 0.5967\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"area\": 11,\n" +
                                "                    \"abnormal\": 0.9892,\n" +
                                "                    \"normal\": 0.0108\n" +
                                "                }\n" +
                                "            ]\n" +
                                "        },\n" +
                                "        \"cars\": {\n" +
                                "            \"normal_distribution\": [\n" +
                                "                0.06426373,\n" +
                                "                0.3827749,\n" +
                                "                0.8520558,\n" +
                                "                0.37014769,\n" +
                                "                0.42223424,\n" +
                                "                0.56963216,\n" +
                                "                0.63982495,\n" +
                                "                0.70254477,\n" +
                                "                0.20470247,\n" +
                                "                0.06790777,\n" +
                                "                0.99255217,\n" +
                                "                0.73894742,\n" +
                                "                0.65990171,\n" +
                                "                0.57961428,\n" +
                                "                0.07063356,\n" +
                                "                0.59503219,\n" +
                                "                0.89232095,\n" +
                                "                0.88236465,\n" +
                                "                0.78101472,\n" +
                                "                0.7314493,\n" +
                                "                0.31850466,\n" +
                                "                0.062347,\n" +
                                "                0.19801045,\n" +
                                "                0.74821474\n" +
                                "            ],\n" +
                                "            \"abnormal\": [\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A5273\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.0988,\n" +
                                "                        0.3677,\n" +
                                "                        0.9573,\n" +
                                "                        0.7892,\n" +
                                "                        0.8027,\n" +
                                "                        0.8155,\n" +
                                "                        0.473,\n" +
                                "                        0.6067,\n" +
                                "                        0.6387,\n" +
                                "                        0.7532,\n" +
                                "                        0.1587,\n" +
                                "                        0.7943,\n" +
                                "                        0.2529,\n" +
                                "                        0.7567,\n" +
                                "                        0.1728,\n" +
                                "                        0.5663,\n" +
                                "                        0.2777,\n" +
                                "                        0.1354,\n" +
                                "                        0.2971,\n" +
                                "                        0.5901,\n" +
                                "                        0.2507,\n" +
                                "                        0.172,\n" +
                                "                        0.3961,\n" +
                                "                        0.5143\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A4781\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.0285,\n" +
                                "                        0.6864,\n" +
                                "                        0.1494,\n" +
                                "                        0.0855,\n" +
                                "                        0.303,\n" +
                                "                        0.7447,\n" +
                                "                        0.4716,\n" +
                                "                        0.6145,\n" +
                                "                        0.7216,\n" +
                                "                        0.0685,\n" +
                                "                        0.5296,\n" +
                                "                        0.222,\n" +
                                "                        0.1677,\n" +
                                "                        0.936,\n" +
                                "                        0.2552,\n" +
                                "                        0.3112,\n" +
                                "                        0.2415,\n" +
                                "                        0.034,\n" +
                                "                        0.4689,\n" +
                                "                        0.6398,\n" +
                                "                        0.5549,\n" +
                                "                        0.7204,\n" +
                                "                        0.6617,\n" +
                                "                        0.6122\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A6894\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.9983,\n" +
                                "                        0.6478,\n" +
                                "                        0.2701,\n" +
                                "                        0.0341,\n" +
                                "                        0.4205,\n" +
                                "                        0.2859,\n" +
                                "                        0.6579,\n" +
                                "                        0.421,\n" +
                                "                        0.9703,\n" +
                                "                        0.97,\n" +
                                "                        0.8515,\n" +
                                "                        0.0669,\n" +
                                "                        0.4693,\n" +
                                "                        0.0061,\n" +
                                "                        0.1583,\n" +
                                "                        0.8396,\n" +
                                "                        0.0679,\n" +
                                "                        0.5034,\n" +
                                "                        0.6696,\n" +
                                "                        0.237,\n" +
                                "                        0.6135,\n" +
                                "                        0.5293,\n" +
                                "                        0.845,\n" +
                                "                        0.6835\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A8069\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.8352,\n" +
                                "                        0.4846,\n" +
                                "                        0.1378,\n" +
                                "                        0.9787,\n" +
                                "                        0.3448,\n" +
                                "                        0.6047,\n" +
                                "                        0.5754,\n" +
                                "                        0.042,\n" +
                                "                        0.9512,\n" +
                                "                        0.8873,\n" +
                                "                        0.1506,\n" +
                                "                        0.2732,\n" +
                                "                        0.7128,\n" +
                                "                        0.027,\n" +
                                "                        0.465,\n" +
                                "                        0.8524,\n" +
                                "                        0.7375,\n" +
                                "                        0.4462,\n" +
                                "                        0.8054,\n" +
                                "                        0.8291,\n" +
                                "                        0.4844,\n" +
                                "                        0.3507,\n" +
                                "                        0.3021,\n" +
                                "                        0.2851\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A3078\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.1293,\n" +
                                "                        0.5244,\n" +
                                "                        0.9795,\n" +
                                "                        0.5215,\n" +
                                "                        0.8353,\n" +
                                "                        0.825,\n" +
                                "                        0.3063,\n" +
                                "                        0.289,\n" +
                                "                        0.3119,\n" +
                                "                        0.6092,\n" +
                                "                        0.7448,\n" +
                                "                        0.496,\n" +
                                "                        0.2136,\n" +
                                "                        0.7755,\n" +
                                "                        0.2689,\n" +
                                "                        0.4788,\n" +
                                "                        0.9002,\n" +
                                "                        0.6705,\n" +
                                "                        0.5385,\n" +
                                "                        0.98,\n" +
                                "                        0.7109,\n" +
                                "                        0.4419,\n" +
                                "                        0.8315,\n" +
                                "                        0.6198\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A5621\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.1955,\n" +
                                "                        0.0381,\n" +
                                "                        0.0077,\n" +
                                "                        0.3351,\n" +
                                "                        0.502,\n" +
                                "                        0.5227,\n" +
                                "                        0.6222,\n" +
                                "                        0.2901,\n" +
                                "                        0.1335,\n" +
                                "                        0.8176,\n" +
                                "                        0.8332,\n" +
                                "                        0.7523,\n" +
                                "                        0.6883,\n" +
                                "                        0.1125,\n" +
                                "                        0.9112,\n" +
                                "                        0.0665,\n" +
                                "                        0.0744,\n" +
                                "                        0.8145,\n" +
                                "                        0.2766,\n" +
                                "                        0.847,\n" +
                                "                        0.1412,\n" +
                                "                        0.071,\n" +
                                "                        0.9845,\n" +
                                "                        0.338\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A4097\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.0061,\n" +
                                "                        0.7626,\n" +
                                "                        0.4553,\n" +
                                "                        0.9004,\n" +
                                "                        0.8695,\n" +
                                "                        0.7755,\n" +
                                "                        0.2718,\n" +
                                "                        0.7408,\n" +
                                "                        0.3056,\n" +
                                "                        0.6851,\n" +
                                "                        0.207,\n" +
                                "                        0.2619,\n" +
                                "                        0.0511,\n" +
                                "                        0.7704,\n" +
                                "                        0.5842,\n" +
                                "                        0.2446,\n" +
                                "                        0.9319,\n" +
                                "                        0.7158,\n" +
                                "                        0.4052,\n" +
                                "                        0.3791,\n" +
                                "                        0.1715,\n" +
                                "                        0.1239,\n" +
                                "                        0.424,\n" +
                                "                        0.2876\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A7377\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.652,\n" +
                                "                        0.6688,\n" +
                                "                        0.9857,\n" +
                                "                        0.0592,\n" +
                                "                        0.7005,\n" +
                                "                        0.9247,\n" +
                                "                        0.9239,\n" +
                                "                        0.4367,\n" +
                                "                        0.1653,\n" +
                                "                        0.5022,\n" +
                                "                        0.1723,\n" +
                                "                        0.1767,\n" +
                                "                        0.9809,\n" +
                                "                        0.0057,\n" +
                                "                        0.1429,\n" +
                                "                        0.01,\n" +
                                "                        0.7028,\n" +
                                "                        0.5021,\n" +
                                "                        0.6698,\n" +
                                "                        0.8114,\n" +
                                "                        0.6574,\n" +
                                "                        0.4942,\n" +
                                "                        0.4382,\n" +
                                "                        0.2817\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A7996\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.7634,\n" +
                                "                        0.5325,\n" +
                                "                        0.0911,\n" +
                                "                        0.5113,\n" +
                                "                        0.8895,\n" +
                                "                        0.2621,\n" +
                                "                        0.0551,\n" +
                                "                        0.0264,\n" +
                                "                        0.6057,\n" +
                                "                        0.9768,\n" +
                                "                        0.0603,\n" +
                                "                        0.5211,\n" +
                                "                        0.4144,\n" +
                                "                        0.2666,\n" +
                                "                        0.4879,\n" +
                                "                        0.6616,\n" +
                                "                        0.5412,\n" +
                                "                        0.3586,\n" +
                                "                        0.6967,\n" +
                                "                        0.2268,\n" +
                                "                        0.9773,\n" +
                                "                        0.1933,\n" +
                                "                        0.2707,\n" +
                                "                        0.685\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A7204\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.895,\n" +
                                "                        0.5632,\n" +
                                "                        0.7252,\n" +
                                "                        0.8859,\n" +
                                "                        0.3049,\n" +
                                "                        0.2861,\n" +
                                "                        0.1958,\n" +
                                "                        0.1405,\n" +
                                "                        0.4034,\n" +
                                "                        0.707,\n" +
                                "                        0.4015,\n" +
                                "                        0.3665,\n" +
                                "                        0.7357,\n" +
                                "                        0.4375,\n" +
                                "                        0.4199,\n" +
                                "                        0.8295,\n" +
                                "                        0.9709,\n" +
                                "                        0.122,\n" +
                                "                        0.8314,\n" +
                                "                        0.2287,\n" +
                                "                        0.8214,\n" +
                                "                        0.0132,\n" +
                                "                        0.6675,\n" +
                                "                        0.6802\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A2810\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.366,\n" +
                                "                        0.6531,\n" +
                                "                        0.9627,\n" +
                                "                        0.1782,\n" +
                                "                        0.2884,\n" +
                                "                        0.9428,\n" +
                                "                        0.2081,\n" +
                                "                        0.1906,\n" +
                                "                        0.1087,\n" +
                                "                        0.9947,\n" +
                                "                        0.3425,\n" +
                                "                        0.0732,\n" +
                                "                        0.6434,\n" +
                                "                        0.8992,\n" +
                                "                        0.6186,\n" +
                                "                        0.5899,\n" +
                                "                        0.1678,\n" +
                                "                        0.9964,\n" +
                                "                        0.1081,\n" +
                                "                        0.2461,\n" +
                                "                        0.878,\n" +
                                "                        0.225,\n" +
                                "                        0.2009,\n" +
                                "                        0.3367\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A4095\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.4535,\n" +
                                "                        0.2697,\n" +
                                "                        0.911,\n" +
                                "                        0.9552,\n" +
                                "                        0.9648,\n" +
                                "                        0.9526,\n" +
                                "                        0.8674,\n" +
                                "                        0.6851,\n" +
                                "                        0.7738,\n" +
                                "                        0.7475,\n" +
                                "                        0.9179,\n" +
                                "                        0.2896,\n" +
                                "                        0.553,\n" +
                                "                        0.1349,\n" +
                                "                        0.6968,\n" +
                                "                        0.3138,\n" +
                                "                        0.8521,\n" +
                                "                        0.9236,\n" +
                                "                        0.3458,\n" +
                                "                        0.0284,\n" +
                                "                        0.2195,\n" +
                                "                        0.1832,\n" +
                                "                        0.1908,\n" +
                                "                        0.3878\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A8961\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.2359,\n" +
                                "                        0.4211,\n" +
                                "                        0.4599,\n" +
                                "                        0.6673,\n" +
                                "                        0.3137,\n" +
                                "                        0.178,\n" +
                                "                        0.0555,\n" +
                                "                        0.3847,\n" +
                                "                        0.2633,\n" +
                                "                        0.0375,\n" +
                                "                        0.6798,\n" +
                                "                        0.5502,\n" +
                                "                        0.1149,\n" +
                                "                        0.5679,\n" +
                                "                        0.7106,\n" +
                                "                        0.9721,\n" +
                                "                        0.3861,\n" +
                                "                        0.0681,\n" +
                                "                        0.5641,\n" +
                                "                        0.398,\n" +
                                "                        0.2911,\n" +
                                "                        0.2629,\n" +
                                "                        0.7738,\n" +
                                "                        0.8608\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A8969\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.0543,\n" +
                                "                        0.9865,\n" +
                                "                        0.684,\n" +
                                "                        0.3839,\n" +
                                "                        0.4408,\n" +
                                "                        0.3508,\n" +
                                "                        0.4196,\n" +
                                "                        0.5458,\n" +
                                "                        0.5299,\n" +
                                "                        0.1114,\n" +
                                "                        0.382,\n" +
                                "                        0.4918,\n" +
                                "                        0.8714,\n" +
                                "                        0.5443,\n" +
                                "                        0.3874,\n" +
                                "                        0.8467,\n" +
                                "                        0.6933,\n" +
                                "                        0.4575,\n" +
                                "                        0.3947,\n" +
                                "                        0.9293,\n" +
                                "                        0.9003,\n" +
                                "                        0.8522,\n" +
                                "                        0.3863,\n" +
                                "                        0.583\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A5133\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.9303,\n" +
                                "                        0.1415,\n" +
                                "                        0.9856,\n" +
                                "                        0.7562,\n" +
                                "                        0.5389,\n" +
                                "                        0.8638,\n" +
                                "                        0.4646,\n" +
                                "                        0.8098,\n" +
                                "                        0.3325,\n" +
                                "                        0.776,\n" +
                                "                        0.7371,\n" +
                                "                        0.2598,\n" +
                                "                        0.1963,\n" +
                                "                        0.4203,\n" +
                                "                        0.5815,\n" +
                                "                        0.0169,\n" +
                                "                        0.2469,\n" +
                                "                        0.4969,\n" +
                                "                        0.2765,\n" +
                                "                        0.7946,\n" +
                                "                        0.6937,\n" +
                                "                        0.5802,\n" +
                                "                        0.4568,\n" +
                                "                        0.6432\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A2551\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.8089,\n" +
                                "                        0.788,\n" +
                                "                        0.4606,\n" +
                                "                        0.9583,\n" +
                                "                        0.4282,\n" +
                                "                        0.4637,\n" +
                                "                        0.9036,\n" +
                                "                        0.4756,\n" +
                                "                        0.458,\n" +
                                "                        0.6476,\n" +
                                "                        0.6334,\n" +
                                "                        0.6186,\n" +
                                "                        0.271,\n" +
                                "                        0.2889,\n" +
                                "                        0.3658,\n" +
                                "                        0.6863,\n" +
                                "                        0.9404,\n" +
                                "                        0.604,\n" +
                                "                        0.9799,\n" +
                                "                        0.1426,\n" +
                                "                        0.5728,\n" +
                                "                        0.1232,\n" +
                                "                        0.2378,\n" +
                                "                        0.1123\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A1000\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.991,\n" +
                                "                        0.5532,\n" +
                                "                        0.1015,\n" +
                                "                        0.7895,\n" +
                                "                        0.0108,\n" +
                                "                        0.5027,\n" +
                                "                        0.5984,\n" +
                                "                        0.9821,\n" +
                                "                        0.8186,\n" +
                                "                        0.4578,\n" +
                                "                        0.8881,\n" +
                                "                        0.6299,\n" +
                                "                        0.5836,\n" +
                                "                        0.8831,\n" +
                                "                        0.9457,\n" +
                                "                        0.6053,\n" +
                                "                        0.488,\n" +
                                "                        0.8132,\n" +
                                "                        0.8164,\n" +
                                "                        0.4448,\n" +
                                "                        0.7456,\n" +
                                "                        0.5918,\n" +
                                "                        0.8366,\n" +
                                "                        0.8156\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A3491\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.5135,\n" +
                                "                        0.9799,\n" +
                                "                        0.1575,\n" +
                                "                        0.4928,\n" +
                                "                        0.0245,\n" +
                                "                        0.7462,\n" +
                                "                        0.2101,\n" +
                                "                        0.6154,\n" +
                                "                        0.2024,\n" +
                                "                        0.7816,\n" +
                                "                        0.8127,\n" +
                                "                        0.6034,\n" +
                                "                        0.7138,\n" +
                                "                        0.9626,\n" +
                                "                        0.9887,\n" +
                                "                        0.8635,\n" +
                                "                        0.8791,\n" +
                                "                        0.6941,\n" +
                                "                        0.4742,\n" +
                                "                        0.5609,\n" +
                                "                        0.2765,\n" +
                                "                        0.6931,\n" +
                                "                        0.6964,\n" +
                                "                        0.1674\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A9863\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.4157,\n" +
                                "                        0.7181,\n" +
                                "                        0.0198,\n" +
                                "                        0.6451,\n" +
                                "                        0.267,\n" +
                                "                        0.8594,\n" +
                                "                        0.0669,\n" +
                                "                        0.0877,\n" +
                                "                        0.8267,\n" +
                                "                        0.2859,\n" +
                                "                        0.575,\n" +
                                "                        0.5775,\n" +
                                "                        0.2924,\n" +
                                "                        0.1541,\n" +
                                "                        0.1895,\n" +
                                "                        0.1234,\n" +
                                "                        0.6774,\n" +
                                "                        0.4151,\n" +
                                "                        0.1198,\n" +
                                "                        0.549,\n" +
                                "                        0.047,\n" +
                                "                        0.6458,\n" +
                                "                        0.9876,\n" +
                                "                        0.2577\n" +
                                "                    ]\n" +
                                "                },\n" +
                                "                {\n" +
                                "                    \"license\": \"粤A6145\",\n" +
                                "                    \"distribution\": [\n" +
                                "                        0.696,\n" +
                                "                        0.8917,\n" +
                                "                        0.1373,\n" +
                                "                        0.5967,\n" +
                                "                        0.711,\n" +
                                "                        0.5943,\n" +
                                "                        0.9711,\n" +
                                "                        0.1199,\n" +
                                "                        0.6634,\n" +
                                "                        0.9912,\n" +
                                "                        0.7737,\n" +
                                "                        0.2331,\n" +
                                "                        0.5295,\n" +
                                "                        0.8979,\n" +
                                "                        0.5411,\n" +
                                "                        0.2985,\n" +
                                "                        0.8744,\n" +
                                "                        0.7294,\n" +
                                "                        0.5499,\n" +
                                "                        0.6854,\n" +
                                "                        0.521,\n" +
                                "                        0.2421,\n" +
                                "                        0.4168,\n" +
                                "                        0.1922\n" +
                                "                    ]\n" +
                                "                }\n" +
                                "            ]\n" +
                                "        }\n" +
                                "    }\n" +
                                "}",AbnormalInfo.class);
//                        Logger.d(GsonUtil.GsonString(info));
                        //假数据对象
                        abnormalInfo = info;
                        //给另外一个碎片发送异常车辆信息
                        BaseEvent baseEvent = EventFactory.getInstance();
                        baseEvent.type = "异常车辆信息";
                        baseEvent.object = abnormalInfo.getData().getCars();
                        EventBusUtils.postSticky(baseEvent);
                        cancelLoadingDialong();
                        //获取到网络数据后，加载下面的柱状图
                        initBarChart(abnormalInfo);
                        //获取到网络数据后，自动加载上面的饼状图
                        selectIndex = 0;
                        float areaAbnormal = abnormalInfo.getData().getPies().getPie().get(selectIndex).getAbnormal() * 100;
                        float areaNormal = abnormalInfo.getData().getPies().getPie().get(selectIndex).getNormal() * 100;
                        initChartSummary(new float[]{  areaNormal ,areaAbnormal});
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
        Logger.d("dataArray[0]"+dataArray[0]+"dataArray[1] :" +dataArray[1]);
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
