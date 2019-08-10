package com.example.taxidata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;

import java.util.List;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.constant.ColorGriant;
import com.example.taxidata.constant.MyCharacter;
import com.example.taxidata.ui.heatpower.HeatPowerContract;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomePageActivity extends AppCompatActivity implements AMap.OnCameraChangeListener, HeatPowerContract.HeatPowerView {

    /*--------------------------------常量相关------------------------------------------------------*/

    private static final String TAG = "HomePageActivity";

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

    /*------------------------------------present相关-----------------------------------------------*/

    /**
     * 热力图的present层
     */
    private HeatPowerContract.HeatPowerPresent heatPowerPresent;

    /*------------------------------------状态相关--------------------------------------------------*/

    /**
     * 是否隐藏热力图，默认为true，即隐藏热力图
     */
    private boolean isHeatPowerHide = true;

    /*----------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        homePageMv = findViewById(R.id.mv_home_page);
        homePageMv.onCreate(savedInstanceState);
        if (homepageAMap == null){
            homepageAMap = homePageMv.getMap();
        }
        setContentView(R.layout.activity_home_page);

        //获取地图实例
        homePageMv = findViewById(R.id.mv_home_page);
        homePageMv.onCreate(savedInstanceState);
        //初始化present
        initPresent();
        //初始化悬浮按钮
        initFloatButton();
        //初始化地图
        initMap();
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
    }

    /**
     * 获取屏幕中心位置
     *
     * @param cameraPosition 相机位置
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        this.target = cameraPosition.target;
        Log.d(TAG, "屏幕中心位置 : " + target);
    }

    /**
     * 获取屏幕中心位置
     *
     * @param cameraPosition 相机位置
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @OnClick(R.id.fbtn_heat_power)
    public void onViewClicked() {

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
                .gradient(ColorGriant.ALT_HEATMAP_GRADIENT);
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
        if (isHeatPowerHide) {
            heatPowerPresent.heatPoint();
            //热力图不再隐藏
            isHeatPowerHide = false;
        } else {
            heatPowerPresent.pause();
            isHeatPowerHide = true;
        }

    }

    @OnClick({R.id.fbtn_passenger_hot, R.id.fbtn_heat_power, R.id.fbtn_road_quality, R.id.fbtn_request_analysis, R.id.fbtn_taxi_income, R.id.fbtn_behavior_analysis, R.id.fbtn_abnormal_analysis, R.id.fbtn_visualization, R.id.fbtn_set_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fbtn_passenger_hot:
                //载客热点推荐
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
                break;
            case R.id.fbtn_set_up:
                //设置
                break;
            default:
        }
        //收起菜单栏
        homePageMenuFam.close(true);
    }
}
