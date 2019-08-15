package com.example.taxidata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;

import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;

import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.adapter.CustomOnclick;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.common.SharedPreferencesManager;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.constant.Algorithm;

import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.constant.Area;
import com.example.taxidata.constant.ColorGriant;
import com.example.taxidata.constant.MyCharacter;
import com.example.taxidata.ui.IncomeRanking.IncomeRankingActivity;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.ui.TaxiPath.TaxiPathActivity;
import com.example.taxidata.ui.areaanalyze.AreaAnalyzeActivity;
import com.example.taxidata.ui.heatpower.HeatPowerContract;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.example.taxidata.ui.hotspot.view.HotSpotResearchActivity;
import com.example.taxidata.ui.setup.SetUpActivity;

import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.MyTimerPicker;
import com.example.taxidata.widget.StatusBar;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
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

    /**
     * 缩放比例
     */
    private static final int ZOOM = 14;

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
    @BindView(R.id.dsv_area_heat_power)
    DropDownSelectView heatpowerAreaDsv;
    @BindView(R.id.dsv_algorithm)
    DropDownSelectView algorithmDsv;

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

    /*------------------------------------容器相关--------------------------------------------------*/

    private ArrayList<String> areaList = new ArrayList<>();
    private ArrayList<String> algorithmList = new ArrayList<>();

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
        //初始化控件
        initViews();
        //初始化注册EventBus
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
        //初始化区域列表
        initAreaList();
        //初始化下拉框的点击事件
        initDropDownSelectClick();
        //初始化下拉框算法列表
        initAlgorithm();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePageMv.onDestroy();
        destroyPresent();
        //解绑EventBus
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
        Logger.d("onDestory");
    }

    @Override
    protected void onPause() {
        super.onPause();
        homePageMv.onPause();
        //activity pause时,若处于热力图模式，则退出热力图
        if (isHeatPowerMode) {
            //此时只会执行退出热力图
            controlHeatPower();
            //处于未来状态
            if (heatPowerStb.getStatus() == 2){
                algorithmDsv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        homePageMv.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * 初始化地图
     */
    private void initMap() {
        if (homepageAMap == null) {
            homepageAMap = homePageMv.getMap();
        }
        //番禺市经纬度
        LatLng latLng = Area.area_latlng.get(5);
        //将相机移动到番禺区
        moveToAnyWhere(latLng);
        //监听相机位置变化,以获得屏幕中心经纬度
        homepageAMap.setOnCameraChangeListener(this);
        //取消掉右下角的放大缩小+ -
        UiSettings uiSettings = homepageAMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
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
     *
     * @param cameraPosition 相机位置
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        this.target = cameraPosition.target;

    }

    /**
     * 获取屏幕中心位置
     *
     * @param cameraPosition 相机位置
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    /**
     * 统一初始化present
     */
    private void initPresent() {
        heatPowerPresent = new HeatPowerPresent(heatPowerStb,showHideHeatPowerBtn);
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
        showHideHeatPowerBtn.setText("显示");
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
            dropOutHeatPower();
            //按钮变回默认状态
            showHideHeatPowerBtn.setText("显示");
            if (heatPowerStb.getStatus() == 2){
                algorithmDsv.setVisibility(View.GONE);
            }
            //隐藏时间选择器
            chooseTimeTp.hideDetailTimePicker();
            //隐藏状态栏
            heatpowerRl.setVisibility(View.GONE);
            //退出热力图模式
            isHeatPowerMode = false;
            heatPowerFbtn.setLabelText(MyCharacter.CONST_SHOW_BUTTON);
            //将状态切换至最初位置
            if (!(heatPowerStb.getStatus() == 1)) {
                heatPowerStb.switchToRealTime();
            }
        } else {
            //热力图模式
            heatpowerRl.setVisibility(View.VISIBLE);
            isHeatPowerMode = true;
            heatPowerFbtn.setLabelText(MyCharacter.CONST_HIDE_BUTTON);
        }
    }


    public void initViews() {

    }


    /**
     * 悬浮按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.fbtn_passenger_hot, R.id.fbtn_heat_power, R.id.fbtn_road_quality, R.id.fbtn_request_analysis, R.id.fbtn_taxi_income
            , R.id.fbtn_behavior_analysis, R.id.fbtn_abnormal_analysis, R.id.fbtn_visualization, R.id.fbtn_set_up})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fbtn_passenger_hot:
                //载客热点推荐
//                Intent hotSpotIntent = new Intent(HomePageActivity.this, HotSpotResearchActivity.class);
//                startActivity(hotSpotIntent);
                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(this);
                startActivity(new Intent(this, HotSpotResearchActivity.class), compat.toBundle());
                overridePendingTransition(R.transition.transition_slide ,R.transition.transition_fade);
                break;
            case R.id.fbtn_heat_power:
                //热力图模式
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
                Intent incomeIntent = new Intent(HomePageActivity.this, IncomeRankingActivity.class);
                startActivity(incomeIntent);

                break;
            case R.id.fbtn_behavior_analysis:
                //出租车行为分析与预测
                break;
            case R.id.fbtn_abnormal_analysis:
                //出租车异常情况分析
                break;
            case R.id.fbtn_visualization:
                //路径可视化
                intent = new Intent(HomePageActivity.this, TaxiPathActivity.class);
                startActivity(intent);
                break;
            case R.id.fbtn_set_up:
                //设置
                intent = new Intent(HomePageActivity.this, SetUpActivity.class);
                startActivity(intent);
                break;
            default:
        }
        //收起菜单栏
        homePageMenuFam.close(true);
    }

    /**
     * 初始化时间选择器,即状态栏
     */
    private void initTimePicker() {
        //绑定状态栏
        chooseTimeTp.setStatusBar(heatPowerStb);
        //绑定状态栏事件
        chooseTimeTp.setHistoryStatusClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                //不管有没有,暂停就是了
                heatPowerPresent.pause();
                clearMap();
                //恢复至初始状态
                showHideHeatPowerBtn.setText("显示");
                //不管有没有,隐藏就是了
                algorithmDsv.setVisibility(View.GONE);
            }
        });
        chooseTimeTp.setRealTimeClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                clearMap();
                //恢复至初始状态
                showHideHeatPowerBtn.setText("显示");
                algorithmDsv.setVisibility(View.GONE);
            }
        });
        chooseTimeTp.setFeatureClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                heatPowerPresent.pause();
                clearMap();
                showHideHeatPowerBtn.setText("显示");
                algorithmDsv.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 初始化隐藏热力图按钮
     */
    private void initHideShowButton() {
        showHideHeatPowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heatPowerPresent != null) {
                    if (showHideHeatPowerBtn.getText().toString().equals("隐藏")) {
                        //退出热力图模式
                        dropOutHeatPower();
                        //将隐藏按钮改变为显示按钮
                        showHideHeatPowerBtn.setText("显示");
                    } else {
                        //显示热力图
                        happenHeatPower();
                    }
                }
            }
        });
    }

    /**
     * 显示热力图
     */
    private void happenHeatPower() {
        int status = heatPowerStb.getStatus();
        switch (status) {
            case 0:
                // FIXME 测试
                Log.d(TAG, Area.area.get(heatpowerAreaDsv.getSlectedArea()) + "");
                Log.d(TAG, heatpowerAreaDsv.getSlectedArea());
                if (chooseTimeTp.isNoSelectedTime()) {
                    // FIXME 修改为MyToast
                    ToastUtil.showShortToastBottom("请选择时间");
                    return;
                }
                if (!chooseTimeTp.isHistory()) {
                    // FIXME 修改为MyToast
                    ToastUtil.showShortToastBottom("这不是历史时间");
                    return;
                }
                heatPowerPresent.showHistoryHeatPower(Area.area.get(heatpowerAreaDsv.getSlectedArea()), chooseTimeTp.getTime() + ":00");
                break;
            case 1:
                //显示选中的区域
                Log.d(TAG, Area.area.get(heatpowerAreaDsv.getSlectedArea()) + "");
                Log.d(TAG, heatpowerAreaDsv.getSlectedArea());
                heatPowerPresent.showRealTimeHeatPower(Area.area.get(heatpowerAreaDsv.getSlectedArea()));
                break;
            case 2:
                //TODO 未来热力图
                                /*
                                1.获取用户选择的时间和地点
                                2.发送网络请求
                                 */
                if (chooseTimeTp.isNoSelectedTime()) {
                    // FIXME 修改为MyToast
                    ToastUtil.showShortToastBottom("请选择时间");
                    return;
                }
                if (!chooseTimeTp.isFeature()) {
                    // FIXME 修改为MyToast
                    ToastUtil.showShortToastBottom("这不是未来时间");
                    return;
                }
                heatPowerPresent.showFeatureHeatPower(Area.area.get(heatpowerAreaDsv.getSlectedArea()),
                        chooseTimeTp.getTime() + ":00",Algorithm.algorithm.get(algorithmDsv.getSlectedArea()));
                break;
            default:
        }
        showHideHeatPowerBtn.setText("隐藏");
    }

    /**
     * 退出热力图模式
     */
    private void dropOutHeatPower() {
        //获取当前的状态(历史，实时，未来)
        int status = heatPowerStb.getStatus();
        //根据不同状态进行不同的操作
        switch (status) {
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
    }

    //注册绑定EventBus
    protected boolean isRegisterEventBus() {
        return true;
    }

    /**
     * 处理eventbus发过来的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEvent baseEvent) {

    }

    /**
     * 初始化列表
     */
    private void initAreaList() {
        areaList.add(Area.GUANG_ZHOU);
        areaList.add(Area.LI_WAN);
        areaList.add(Area.YUE_XIU);
        areaList.add(Area.TIAN_HE);
        areaList.add(Area.HAI_ZHU);
        areaList.add(Area.HUANG_PU);
        areaList.add(Area.HUA_DU);
        areaList.add(Area.BAI_YUN);
        areaList.add(Area.PAN_YU);
        areaList.add(Area.NAN_SHA);
        areaList.add(Area.CONG_HUA);
        areaList.add(Area.ZENG_CHENG);
        heatpowerAreaDsv.setItemsData(areaList, 1);
    }

    /**
     * 初始化算法列表
     */
    private void initAlgorithm(){
        algorithmList.add(Algorithm.WANG_ALGORITHM);
        algorithmList.add(Algorithm.GU_ALGORITHM);
        algorithmList.add(Algorithm.LI_ALGORITHM);
        algorithmDsv.setItemsData(algorithmList,3);
    }

    /**
     * 相机移动到地图上的某一地点
     * @param latLng 该地点的经纬度
     */
    private void moveToAnyWhere(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, ZOOM, 0, 0));
        homepageAMap.moveCamera(update);
    }

    /**
     * 初始化下拉选择框的点击事件
     */
    private void initDropDownSelectClick() {
        // TODO 设置点击事件
        //设置热力图区域下拉框点击事件
        heatpowerAreaDsv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //取出地区地址
                String adress = areaList.get(position);
                //取出地区编号
                int area = Area.area.get(adress);
                //获取地区经纬度
                LatLng latLng = Area.area_latlng.get(area);
                //清除当前屏幕上的热力图
                dropOutHeatPower();
                //移动到该地区
                moveToAnyWhere(latLng);
                //按钮显示为隐藏
                showHideHeatPowerBtn.setText("隐藏");
                //重新发起网络请求显示热力图
                happenHeatPower();
            }
        });
        //设置算法下拉框点击事件
        algorithmDsv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //取出算法名称
                String algorithmName = algorithmList.get(position);
                //取出算法代号
                int algorithmNumber = Algorithm.algorithm.get(algorithmName);
                //清除当前屏幕上的热力图
                dropOutHeatPower();
                //按钮显示为隐藏
                showHideHeatPowerBtn.setText("隐藏");
                //重新发送网络请求
                happenHeatPower();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //退出程序时,显示对话框
        if ((keyCode == KeyEvent.KEYCODE_BACK) || (keyCode == KeyEvent.KEYCODE_HOME)) {
            new QMUIDialog.MessageDialogBuilder(this)
                    .setTitle("标题")
                    .setMessage("确认要退出吗？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction(0, "确认", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            //退出时存储时间
                            SharedPreferencesManager.getManager()
                                    .save(SharedPreferencesManager.CONST_NOW_APP_TIME, TaxiApp.getAppNowTime());
                            //退出时存储超时时间
                            SharedPreferencesManager.getManager()
                                    .save(SharedPreferencesManager.CONST_TIME_OUT, RetrofitManager.getTimeoutTime());
                            //退出时存储轮询间隔时间
                            SharedPreferencesManager.getManager()
                                    .save(SharedPreferencesManager.CONST_POLLING,HeatPowerPresent.getPollingTime());
                            finish();
                        }
                    })
                    .show();
        }
        Log.d(TAG,"按下返回键");
        return super.onKeyDown(keyCode,event);
    }


}