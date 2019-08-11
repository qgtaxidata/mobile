package com.example.taxidata;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.LatLng;
import java.util.List;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.bean.HotSpotInfo;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.common.eventbus.EventFactory;
import com.example.taxidata.constant.ColorGriant;
import com.example.taxidata.constant.EventBusType;
import com.example.taxidata.constant.MyCharacter;
import com.example.taxidata.ui.TaxiDriverIncome.IncomeActivity;
import com.example.taxidata.ui.TaxiPath.TaxiPathActivity;
import com.example.taxidata.ui.heatpower.HeatPowerContract;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.example.taxidata.ui.hotspot.view.HotSpotResearchActivity;
import com.example.taxidata.ui.hotspot.view.OriginHotSpotLayout;
import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.ToastUtil;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import com.example.taxidata.ui.hotspot.view.HotSpotResearchActivity;
import com.example.taxidata.ui.heatpower.HeatPowerPresent;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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

    ImageView searchBack;

    TextView searchOrigin;
    TextView searchEndPoint;
    @BindView(R.id.layout_originhotspot)
    OriginHotSpotLayout layoutOriginhotspot;

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
        if (homepageAMap == null) {
            homepageAMap = homePageMv.getMap();
        }

        //初始化present
        initPresent();
        //初始化悬浮按钮
        initFloatButton();
        //初始化地图
        initMap();
        //初始化控件
        initViews();
        //初始化注册EventBus
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
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
        if (isHeatPowerHide) {
            heatPowerPresent.heatPoint();
            //热力图不再隐藏
            isHeatPowerHide = false;
        } else {
            heatPowerPresent.pause();
            isHeatPowerHide = true;
        }
    }

    /**
     * 展示热点
     * @param hotSpotInfo 热点信息对象
     */
    private void showHotSpot(HotSpotInfo hotSpotInfo) {
        double longititue = hotSpotInfo.getLongitude();
        double latitute = hotSpotInfo.getLatitude();
        Logger.d("打标记：经度: " + longititue + "： 纬度" + latitute);
        LatLng latLngHot = new LatLng(latitute, longititue);
        final Marker markerHot = homepageAMap.addMarker(new MarkerOptions().position(latLngHot).title(hotSpotInfo.getAddress()).snippet("热度" + hotSpotInfo.getHeat()));

    }

    public void initViews() {
        searchEndPoint = layoutOriginhotspot.findViewById(R.id.search_end_point);
        searchOrigin = layoutOriginhotspot.findViewById(R.id.search_origin);
        searchOrigin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择输入起点，跳转输入界面
                    StatusManager.hotSpotChosen = true;
                    Intent intentHotSearch = new Intent(HomePageActivity.this, HotSpotResearchActivity.class);
                    startActivity(intentHotSearch);
                    BaseEvent baseEventOrigin = EventFactory.getInstance();
                    baseEventOrigin.type = EventBusType.ORIGIN_HOTSPOT_TO_CHOOSE;
                    EventBusUtils.postSticky(baseEventOrigin);

                }
            });
            searchBack = layoutOriginhotspot.findViewById(R.id.search_back);
            searchBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseEvent baseEventBack = EventFactory.getInstance();
                    baseEventBack.type = EventBusType.HOTSPOT_CHOSE_AGAIN;
                    EventBusUtils.postSticky(baseEventBack);
                    Intent intentHotChooseAgain = new Intent(HomePageActivity.this, HotSpotResearchActivity.class);
                    startActivity(intentHotChooseAgain);
                }
            });
        }


    /**
     * 悬浮按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.fbtn_passenger_hot, R.id.fbtn_heat_power, R.id.fbtn_road_quality, R.id.fbtn_request_analysis, R.id.fbtn_taxi_income
            , R.id.fbtn_behavior_analysis, R.id.fbtn_abnormal_analysis, R.id.fbtn_visualization, R.id.fbtn_set_up})
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
                Intent incomeIntent = new Intent(HomePageActivity.this, IncomeActivity.class);
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


    //注册绑定EventBus
    protected boolean isRegisterEventBus() {
        return true;
    }

    /**
     * 处理eventbus发过来的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEvent baseEvent) {
        if (baseEvent.type.equals(EventBusType.HOTSPOT_CHOSEN)) {
            Logger.d("接收事件 热点已经选择，显示状态框");
            HotSpotInfo hotSpotInfo = (HotSpotInfo) baseEvent.object;
            layoutOriginhotspot.setVisibility(View.VISIBLE);
            showHotSpot(hotSpotInfo);
            //如果热点文本框Visible则赋值用户选定的热点信息
            if (searchEndPoint.getVisibility() == View.VISIBLE) {
                searchEndPoint.setText(hotSpotInfo.getAddress());
            }
        }
        if(baseEvent.type.equals(EventBusType.ORIGIN_HOTSPOT_BOTH_CHOSEN)) {
            Logger.d("接收事件 ： 热点和地点都已经选择");
        }
    }


}
