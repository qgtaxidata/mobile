package com.example.taxidata;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.adapter.CustomOnclick;
import com.example.taxidata.constant.ColorGriant;
import com.example.taxidata.constant.MyCharacter;
import com.example.taxidata.ui.TaxiPath.TaxiPathActivity;
import com.example.taxidata.ui.heatpower.HeatPowerContract;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.example.taxidata.ui.hotspot.view.HotSpotResearchActivity;
import com.example.taxidata.widget.MyTimerPicker;
import com.example.taxidata.widget.StatusBar;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomePageActivity extends AppCompatActivity implements AMap.OnCameraChangeListener, HeatPowerContract.HeatPowerView {

    /*--------------------------------常量相关------------------------------------------------------*/

    private static final String TAG = "HomePageActivity";

    /**
     * 菜单蓝色字体颜色
     */
    private static final int CONST_MENU_TEXT_COLOR = 0x4C93FD;

    /**
     * 标签黄色背景
     */
    private static final int CONST_LABEL_BACKGROUND = 0xFFBB33;

    /*----------------------------------地图相关---------------------------------------------------*/

    /**
     * 主页地图
     */
    private MapView homePageMv;
    private AMap homepageAMap;
    /**
     * 热力图
     */
    private HeatmapTileProvider.Builder heatpowerBuilder;
    private HeatmapTileProvider heatpower;
    /**
     * 屏幕中心的位置,最初为广州市的经纬度
     */
    private LatLng target = new LatLng(23.209000, 113.317390);

    /*----------------------------------ui组件相关--------------------------------------------------*/

    @BindView(R.id.fbtn_passenger_hot)
    FloatingActionButton passengerHotFbtn;
    @BindView(R.id.fbtn_heat_power)
    FloatingActionButton heatPowerFbtn;
    @BindView(R.id.fbtn_road_quality)
    FloatingActionButton roadQualityFbtn;
    @BindView(R.id.fbtn_request_analysis)
    FloatingActionButton requestAnalysisFbtn;
    @BindView(R.id.fbtn_taxi_income)
    FloatingActionButton taxiIncomeFbtn;
    @BindView(R.id.fbtn_behavior_analysis)
    FloatingActionButton behaviorAnalysisFbtn;
    @BindView(R.id.fbtn_abnormal_analysis)
    FloatingActionButton abnormalAnalysisFbtn;
    @BindView(R.id.fbtn_visualization)
    FloatingActionButton visualizationFbtn;
    @BindView(R.id.fbtn_set_up)
    FloatingActionButton setUpFbtn;
    @BindView(R.id.fam_home_page_menu)
    FloatingActionMenu homePageMenuFam;
    @BindView(R.id.stb_heat_power)
    StatusBar heatPowerStb;
    @BindView(R.id.tp_home_page_time_picker)
    MyTimerPicker chooseTimeTp;
    @BindView(R.id.rl_heat_power_switch)
    RelativeLayout heatpowerRl;
    @BindView(R.id.btn_show_hide_heat_power)
    Button showHideHeatPowerBtn;

    /*------------------------------------present相关-----------------------------------------------*/

    /**
     * 热力图的present层
     */
    private HeatPowerContract.HeatPowerPresent heatPowerPresent;

    /*------------------------------------状态相关--------------------------------------------------*/

    /**
     * 是否为热力图模式，默认为false，即不是热力图模式
     */
    private boolean isHeatPowerMode = false;

    /*----------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        homePageMv = findViewById(R.id.mv_home_page);
        homePageMv.onCreate(savedInstanceState);
        if (homepageAMap == null) {
            homepageAMap = homePageMv.getMap();
        }
        //初始化present
        initPresent();
        //初始化悬浮按钮
        initFloatButton();
        //初始化地图
        initMap();
        //初始化时间选择器
        initTimePicker();
        //初始化显示隐藏按钮
        initHideShowButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePageMv.onDestroy();
        destroyPresent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        homePageMv.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        homePageMv.onResume();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        if (homepageAMap == null) {
            homepageAMap = homePageMv.getMap();
        }
        //广州市经纬度
        LatLng latLng = new LatLng(23.209000, 113.317390);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 12, 0, 0));
        //将相机移动到广州市
        homepageAMap.moveCamera(cameraUpdate);
        //监听相机位置变化,以获得屏幕中心经纬度
        homepageAMap.setOnCameraChangeListener(this);
    }

    /**
     * 初始化悬浮按钮
     */
    private void initFloatButton() {
        //将悬浮按钮的Label背景设置为透明
        heatPowerFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        abnormalAnalysisFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        behaviorAnalysisFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        passengerHotFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        requestAnalysisFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        roadQualityFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        setUpFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        taxiIncomeFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
        visualizationFbtn.setLabelColors(Color.TRANSPARENT, CONST_LABEL_BACKGROUND, Color.TRANSPARENT);
    }


    /**
     * 获取屏幕中心位置
     * @param cameraPosition 相机位置
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        this.target = cameraPosition.target;
        Log.d(TAG, "屏幕中心位置 : " + target);
    }

    /**
     * 获取屏幕中心位置
     * @param cameraPosition 相机位置
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    /**
     * 统一初始化present
     */
    private void initPresent() {
        heatPowerPresent = new HeatPowerPresent();
        heatPowerPresent.attachView(this);
    }

    /**
     * 统一解绑present
     */
    private void destroyPresent() {
        heatPowerPresent.detachView();
    }

    /**
     * 清空地图所有状态
     */
    public void clearMap() {
        if (homepageAMap != null) {
            homepageAMap.clear();
        }
    }

    @Override
    public LatLng getCentralLongitudeLatitude() {
        return this.target;
    }

    @Override
    public void showHeatPower(List<WeightedLatLng> heatPointList) {
        if (homepageAMap != null) {
            //清空原来地图状态
            homepageAMap.clear();
        }
        //构造热力图以及权重，设置渐变
        heatpowerBuilder = new HeatmapTileProvider.Builder();
        heatpowerBuilder.weightedData(heatPointList)
                .gradient(ColorGriant.ALT_HEATMAP_GRADIENT)
                .radius(25);
        heatpower = heatpowerBuilder.build();
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatpower);
        //显示热力图
        homepageAMap.addTileOverlay(tileOverlayOptions);
    }

    @Override
    public void hideHeatPower() {
        clearMap();
    }

    @Override
    public void showHideButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                heatPowerFbtn.setLabelText(MyCharacter.CONST_HIDE_BUTTON);
            }
        });
    }

    @Override
    public void showShowButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                heatPowerFbtn.setLabelText(MyCharacter.CONST_SHOW_BUTTON);
            }
        });
    }

    /**
     * 控制热力图的显示或隐藏
     */
    private void controlHeatPower() {
        if (isHeatPowerMode) {
            //退出热力图模式
            heatPowerPresent.pause();
            //隐藏状态栏
            heatpowerRl.setVisibility(View.GONE);
            //退出热力图模式
            isHeatPowerMode = false;
            heatPowerFbtn.setLabelText(MyCharacter.CONST_SHOW_BUTTON);
        } else {
            //热力图模式
            heatPowerPresent.showRealTimeHeatPower(1);
            heatpowerRl.setVisibility(View.VISIBLE);
            isHeatPowerMode = true;
            heatPowerFbtn.setLabelText(MyCharacter.CONST_HIDE_BUTTON);
        }

    }

    @OnClick({R.id.fbtn_passenger_hot, R.id.fbtn_heat_power, R.id.fbtn_road_quality, R.id.fbtn_request_analysis, R.id.fbtn_taxi_income, R.id.fbtn_behavior_analysis, R.id.fbtn_abnormal_analysis, R.id.fbtn_visualization, R.id.fbtn_set_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fbtn_passenger_hot:
                //载客热点推荐
                Intent hotSpotIntent = new Intent(HomePageActivity.this, HotSpotResearchActivity.class);
                startActivity(hotSpotIntent);
                break;
            case R.id.fbtn_heat_power:
                controlHeatPower();
                break;
            case R.id.fbtn_road_quality:
                //道路质量分析
                break;
            case R.id.fbtn_request_analysis:
                //区域出租车需求分析
                break;
            case R.id.fbtn_taxi_income:
                //出租车司机收入排行榜
                break;
            case R.id.fbtn_behavior_analysis:
                //出租车行为分析与预测
                break;
            case R.id.fbtn_abnormal_analysis:
                //出租车异常情况分析
                break;
            case R.id.fbtn_visualization:
                //路径可视化
                Intent intent = new Intent(HomePageActivity.this, TaxiPathActivity.class);
                startActivity(intent);
                break;
            case R.id.fbtn_set_up:
                //设置
                break;
            default:
        }
        //收起菜单栏
        homePageMenuFam.close(true);
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        chooseTimeTp.setTimeStatusBarClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                //点击显示时间选择器
                chooseTimeTp.showDetailTimePicker();
            }
        });
    }

    /**
     * 初始化隐藏热力图按钮
     */
    private void initHideShowButton(){
        showHideHeatPowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heatPowerPresent != null){
                    if (showHideHeatPowerBtn.getText().toString().equals("隐藏")){
                        //获取当前的状态(历史，实时，未来)
                        int status = heatPowerStb.getStatus();
                        //根据不同状态进行不同的操作
                        switch (status){
                            case 0:
                                //切换至历史状态时，无轮询状态，直接清空地图
                                clearMap();
                                break;
                            case 1:
                                //切换至实时状态时，有轮询状态，先停止轮询，再清空地图
                                heatPowerPresent.pause();
                                break;
                            case 2:
                                //未来状态，无轮询，直接清空地图
                                clearMap();
                                break;
                            default:
                        }
                        //将隐藏按钮改变为显示按钮
                        showHideHeatPowerBtn.setText("显示");
                    }else {
                        int status = heatPowerStb.getStatus();
                        switch (status){
                            case 0:
                                // TODO 历史热力图
                                /*
                                1. 获取用户选择的时间和地点
                                2. 发送网络请求
                                    P.showHeatPoint
                                 */
                                break;
                            case 1:
                                // TODO 实时热力图
                                /*
                                1. 获取用户选择的时间和地点
                                2.发送网络请求
                                 */
                                break;
                            case 2:
                                //TODO 未来热力图
                                /*
                                1.获取用户选择的时间和地点
                                2.发送网络请求
                                 */
                                break;
                            default:
                        }
                    }
                }
            }
        });
    }
}
