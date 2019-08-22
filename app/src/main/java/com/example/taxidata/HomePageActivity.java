package com.example.taxidata;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.common.FloatingButtonBuilderManager;
import com.example.taxidata.common.SharedPreferencesManager;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.constant.Algorithm;
import com.example.taxidata.constant.Area;
import com.example.taxidata.constant.ColorGriant;
import com.example.taxidata.constant.MyCharacter;
import com.example.taxidata.net.RetrofitManager;
import com.example.taxidata.ui.IncomeRanking.IncomeRankingActivity;
import com.example.taxidata.ui.TaxiPath.TaxiPathActivity;
import com.example.taxidata.ui.abnormlAnalyze.AbnormalRootActivity;
import com.example.taxidata.ui.areaanalyze.AreaAnalyzeActivity;
import com.example.taxidata.ui.areaincome.AreaIncomeActivity;
import com.example.taxidata.ui.heatpower.HeatPowerContract;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.example.taxidata.ui.hotspot.view.HotSpotResearchActivity;
import com.example.taxidata.ui.passengerpath.view.OriginEndActivity;
import com.example.taxidata.ui.recommendad.RecommendAdActivity;
import com.example.taxidata.ui.roadquality.RoadQualityActivity;
import com.example.taxidata.ui.setup.SetUpActivity;
import com.example.taxidata.ui.taxidemand.TaxiDemandActivity;
import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.LimitClickUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.MyTimerPicker;
import com.example.taxidata.widget.StatusBar;
import com.example.taxidata.widget.StatusToast;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Util;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends BaseActivity implements AMap.OnCameraChangeListener, HeatPowerContract.HeatPowerView {

    private WeakReference<Activity> weakActivity = new WeakReference<Activity>(this);

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
    private static final int ZOOM = 11;

    private static final int CONST_INDEX_HEAT_POWER = 4;

    /*----------------------------------地图相关---------------------------------------------------*/

    /**
     * 主页地图
     */
    private MapView homePageMv;
    private AMap homepageAMap;
    private UiSettings uiSettings;
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
    @BindView(R.id.buttom_menu_homepage)
    BoomMenuButton buttonMenu;

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
        initButtonMenu();
        initButtonClickEvent();
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
        homePageMv.onDestroy();
        destroyPresent();
        //解绑EventBus
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
        Logger.d("onDestory");
        if (chooseTimeTp != null) {
            chooseTimeTp.onDestroy();
        }
        super.onDestroy();
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
            if (heatPowerStb.getStatus() == 2) {
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
        uiSettings = homepageAMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
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
        heatPowerPresent = new HeatPowerPresent(heatPowerStb, showHideHeatPowerBtn);
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
        if (heatPowerPresent.getTaskQueue() == 0) {
            showHideHeatPowerBtn.setText("显示");
        }
    }

    @Override
    public void showHideButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonMenu.getBoomButton(CONST_INDEX_HEAT_POWER).getTextView().setText(MyCharacter.CONST_HIDE_BUTTON);
            }
        });
    }

    @Override
    public void showShowButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonMenu.getBoomButton(CONST_INDEX_HEAT_POWER).getTextView().setText(MyCharacter.CONST_SHOW_BUTTON);
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
            if (heatPowerStb.getStatus() == 2) {
                algorithmDsv.setVisibility(View.GONE);
            }
            //隐藏时间选择器
            chooseTimeTp.hideDetailTimePicker();
            //隐藏状态栏
            heatpowerRl.setVisibility(View.GONE);
            //退出热力图模式
            isHeatPowerMode = false;
            buttonMenu.getBoomButton(CONST_INDEX_HEAT_POWER).getTextView().setText(MyCharacter.CONST_SHOW_BUTTON);
            //将状态切换至最初位置
            if (!(heatPowerStb.getStatus() == 1)) {
                heatPowerStb.switchToRealTime();
            }
        } else {
            //热力图模式
            heatpowerRl.setVisibility(View.VISIBLE);
            isHeatPowerMode = true;
            buttonMenu.getBoomButton(CONST_INDEX_HEAT_POWER).getTextView().setText(MyCharacter.CONST_HIDE_BUTTON);
        }
    }


    public void initViews() {

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
                if (LimitClickUtil.isQuick(1000)) {
                    return;
                }
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
                Log.d(TAG, Area.area.get(heatpowerAreaDsv.getSlectedArea()) + "");
                Log.d(TAG, heatpowerAreaDsv.getSlectedArea());
                if (chooseTimeTp.isNoSelectedTime()) {
                    StatusToast.getMyToast().ToastShow(this,null,R.mipmap.ic_sad,"请选择时间");
                    return;
                }
                if (!chooseTimeTp.isHistory()) {
                    StatusToast.getMyToast().ToastShow(this,null,R.mipmap.ic_sad,"这不是历史时间");
                    return;
                }
                heatPowerPresent.showHistoryHeatPower(Area.area.get(heatpowerAreaDsv.getSlectedArea()), chooseTimeTp.getTime() + ":00");
                break;
            case 1:
                heatPowerPresent.showRealTimeHeatPower(Area.area.get(heatpowerAreaDsv.getSlectedArea()));
                break;
            case 2:
                if (chooseTimeTp.isNoSelectedTime()) {
                    StatusToast.getMyToast().ToastShow(this,null,R.mipmap.ic_sad,"请选择时间");
                    return;
                }
                if (!chooseTimeTp.isFeature()) {
                    StatusToast.getMyToast().ToastShow(this,null,R.mipmap.ic_sad,"这不是未来时间");
                    return;
                }
                heatPowerPresent.showFeatureHeatPower(Area.area.get(heatpowerAreaDsv.getSlectedArea()),
                        chooseTimeTp.getTime() + ":00", Algorithm.algorithm.get(algorithmDsv.getSlectedArea()));
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
    private void initAlgorithm() {
        algorithmList.add(Algorithm.WANG_ALGORITHM);
        algorithmList.add(Algorithm.GU_ALGORITHM);
        algorithmList.add(Algorithm.LI_ALGORITHM);
        algorithmDsv.setItemsData(algorithmList, 3);
    }

    /**
     * 相机移动到地图上的某一地点
     * @param latLng 该地点的经纬度
     */
    private void moveToAnyWhere(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, ZOOM, 0, 0));
        /*homepageAMap.moveCamera(update);*/
        homepageAMap.animateCamera(update);
    }

    /**
     * 初始化下拉选择框的点击事件
     */
    private void initDropDownSelectClick() {
        //设置热力图区域下拉框点击事件
        heatpowerAreaDsv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (LimitClickUtil.isQuick(1000)) {
                    if (heatPowerStb.getStatus() == StatusBar.REALTIME) {
                        heatPowerPresent.pause();
                    }else {
                        hideHeatPower();
                    }
                    return;
                }
                heatPowerPresent.pause();
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
                if (LimitClickUtil.isQuick(1000)) {
                    hideHeatPower();
                    return;
                }
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
                    .setTitle("提示")
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
                                    .save(SharedPreferencesManager.CONST_POLLING, HeatPowerPresent.getPollingTime());
                            finish();
                        }
                    })
                    .show();
        }
        Log.d(TAG, "按下返回键");
        return super.onKeyDown(keyCode, event);
    }


    public void initButtonMenu() {
        for (int i = 0; i < 12; i++) {
            //建造者模式 ，添加按钮，你们自己注意按钮并不会被初始化的
            buttonMenu.addBuilder(FloatingButtonBuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor());
        }
        float width = Util.dp2px(80);
        float height = Util.dp2px(96);
        float height_0_5 = height / 2;
        float height_1_5 = height * 1.5f;
        float heightMargin = buttonMenu.getButtonHorizontalMargin();
        float widthMargin = buttonMenu.getButtonVerticalMargin();
        float vm_0_5 = widthMargin / 2;
        float vm_1_5 = widthMargin * 1.5f;
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(-width - heightMargin, -height_1_5 - vm_1_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(      0, -height_1_5 - vm_1_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(+width + heightMargin, -height_1_5 - vm_1_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(-width - heightMargin, -height_0_5 - vm_0_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(      0, -height_0_5 - vm_0_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(+width + heightMargin, -height_0_5 - vm_0_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(-width - heightMargin, +height_0_5 + vm_0_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(      0, +height_0_5 + vm_0_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(+width + heightMargin, +height_0_5 + vm_0_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(-width - heightMargin, +height_1_5 + vm_1_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(      0, +height_1_5 + vm_1_5-height_0_5));
        buttonMenu.getCustomButtonPlacePositions().add(new PointF(+width + heightMargin, +height_1_5 + vm_1_5-height_0_5));
    }

    /**
     * 设置悬浮按钮(子按钮）的点击事件
     */
    public void initButtonClickEvent() {
        buttonMenu.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                Log.e(TAG, "onClicked: 当前点击了第" + index+" 个" );
                Intent intent = null;
                switch (index ) {
                    case 0:
                        intent = new Intent(HomePageActivity.this, RecommendAdActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //车辆利用率分析
                        intent = new Intent(HomePageActivity.this, AreaAnalyzeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //区域收入分析
                        Intent areaIncomeIntent = new Intent(HomePageActivity.this, AreaIncomeActivity.class);
                        startActivity(areaIncomeIntent);
                        break;
                    case 3:
                        //出租车异常
                        startActivity(new Intent(HomePageActivity.this , AbnormalRootActivity.class));
                        break;
                    case 4:
                        //热力图模式
                        controlHeatPower();
                        break;
                    case 5:
                        //载客热点推荐
                        ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(HomePageActivity.this);
                        startActivity(new Intent(HomePageActivity.this, HotSpotResearchActivity.class), compat.toBundle());
                        overridePendingTransition(R.transition.transition_slide, R.transition.transition_fade);
                        break;
                    case 6:
                        //收入排行榜
                        Intent incomeIntent = new Intent(HomePageActivity.this, IncomeRankingActivity.class);
                        startActivity(incomeIntent);
                        break;
                    case 7:
                        //出租车需求量分析
                        Intent taxiDemandIntent = new Intent(HomePageActivity.this, TaxiDemandActivity.class);
                        startActivity(taxiDemandIntent);
                        break;
                    case 8:
                        //道路质量分析
                        Intent roadQualityIntent = new Intent(HomePageActivity.this, RoadQualityActivity.class);
                        startActivity(roadQualityIntent);
                        break;
                    case 9:
                        //路线
                        intent = new Intent(HomePageActivity.this, OriginEndActivity.class);
                        startActivity(intent);
                        break;
                    case 10:
                        //路径可视化
                        intent = new Intent(HomePageActivity.this, TaxiPathActivity.class);
                        startActivity(intent);
                        break;
                    case 11:
                        //设置
                        intent = new Intent(HomePageActivity.this, SetUpActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onBackgroundClick() {
            }
            @Override
            public void onBoomWillHide() {
            }
            @Override
            public void onBoomDidHide() {
            }
            @Override
            public void onBoomWillShow() {
            }
            @Override
            public void onBoomDidShow() {
            }
        });
    }

    @Override
    public void showError() {
        if (weakActivity.get() != null) {
            weakActivity.get()
                    .runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            StatusToast.getMyToast().ToastShow(HomePageActivity.this,
                                    null,R.mipmap.ic_sad,"网络异常");
                        }
                    });
        }
    }

    @Override
    public void mapClear() {
        homepageAMap.clear();
    }
}