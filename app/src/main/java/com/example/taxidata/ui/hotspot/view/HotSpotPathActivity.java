package com.example.taxidata.ui.hotspot.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.taxidata.HomePageActivity;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.HotSpotInfo;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.common.eventbus.EventFactory;
import com.example.taxidata.constant.EventBusType;
import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.PlanInfoCard;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotSpotPathActivity extends BaseActivity {

    private static final String TAG = "HotSpotPathActivity";
    @BindView(R.id.mv_hotspot_path)
    MapView mvHotspotPath;
    @BindView(R.id.layout_originhotspot)
    OriginHotSpotLayout layoutOriginHotSpot;
    @BindView(R.id.layout_plancard)
    PlanInfoCard layoutPlanCard;
    View linearLayoutOne;
    View linearLayoutTwo;
    View linearLayoutThree;
    TextView tvPlanOne;
    TextView tvPlanTwo;
    TextView tvPlanThree;
    TextView tvFarOne;
    TextView tvFarTwo;
    TextView tvFarThree;
    TextView tvTimeOne;
    TextView tvTimeTwo;
    TextView tvTimeThree;
    List<TextView> textViewListOne;
    List<TextView> textViewListTwo;
    List<TextView> textViewListThree;
    AMap pathMap;
    ImageView searchBack;
    TextView searchOrigin;
    TextView searchEndPoint;
    UiSettings uiSettings;
    String hotSpotChosen = "";
    String originChosen = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_path);
        ButterKnife.bind(this);
        mvHotspotPath.onCreate(savedInstanceState);
        initViews();
        initMap();
        initData();
        //初始化注册EventBus
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
        Log.e(TAG,"onCreate!!");
    }
    @Override
    protected void onPause() {
        super.onPause();
        mvHotspotPath.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvHotspotPath.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvHotspotPath.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    public void initViews(){
        searchEndPoint = layoutOriginHotSpot.findViewById(R.id.search_end_point);
        searchOrigin = layoutOriginHotSpot.findViewById(R.id.search_origin);
        searchOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择输入起点，跳转输入界面
                BaseEvent baseEventOrigin = EventFactory.getInstance();
                baseEventOrigin.type = EventBusType.ORIGIN_HOTSPOT_TO_CHOOSE;
                EventBusUtils.postSticky(baseEventOrigin);
                Intent intentHotSearch = new Intent(HotSpotPathActivity.this, OriginHotSpotActivity.class);
                startActivity(intentHotSearch);
            }
        });
        searchBack = layoutOriginHotSpot.findViewById(R.id.search_back);
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseEvent baseEventBack = EventFactory.getInstance();
                baseEventBack.type = EventBusType.HOTSPOT_CHOSE_AGAIN;
                EventBusUtils.postSticky(baseEventBack);
                Intent intentHotChooseAgain = new Intent(HotSpotPathActivity.this, HotSpotResearchActivity.class);
                startActivity(intentHotChooseAgain);
            }
        });
        linearLayoutOne = layoutPlanCard.findViewById(R.id.ll_plan_one);
        linearLayoutTwo = layoutPlanCard.findViewById(R.id.ll_plan_two);
        linearLayoutThree = layoutPlanCard.findViewById(R.id.ll_plan_third);
        tvPlanOne = linearLayoutOne.findViewById(R.id.hotspot_plan_first);
        tvPlanTwo = linearLayoutTwo.findViewById(R.id.hotspot_plan_second);
        tvPlanThree = linearLayoutThree.findViewById(R.id.hotspot_plan_third);
        tvTimeOne = linearLayoutOne.findViewById(R.id.hotspot_time_first);
        tvTimeTwo = linearLayoutTwo.findViewById(R.id.hotspot_time_second);
        tvTimeThree = linearLayoutThree.findViewById(R.id.hotsopt_time_third);
        tvFarOne = linearLayoutOne.findViewById(R.id.hotspot_far_first);
        tvFarTwo = linearLayoutTwo.findViewById(R.id.hotspot_far_second);
        tvFarThree = linearLayoutThree.findViewById(R.id.hotspot_far_third);
        linearLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextColor(textViewListOne ,getResources().getColor(R.color.blue_color));
                changeTextColor(textViewListTwo ,Color.BLACK);
                changeTextColor(textViewListThree ,Color.BLACK);
            }
        });
        linearLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextColor(textViewListOne ,Color.BLACK);
                changeTextColor(textViewListTwo ,getResources().getColor(R.color.blue_color));
                changeTextColor(textViewListThree ,Color.BLACK);
            }
        });
        linearLayoutThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextColor(textViewListOne ,Color.BLACK);
                changeTextColor(textViewListTwo ,Color.BLACK);
                changeTextColor(textViewListThree ,getResources().getColor(R.color.blue_color));
            }
        });

    }



    public void initMap(){
        if (mvHotspotPath != null) {
            pathMap = mvHotspotPath.getMap();
        }
        uiSettings = pathMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
    }

    public void initData() {
        textViewListOne = new ArrayList<>();
        textViewListTwo = new ArrayList<>();
        textViewListThree = new ArrayList<>();
        textViewListOne.add(tvPlanOne);
        textViewListOne.add(tvTimeOne);
        textViewListOne.add(tvFarOne);
        textViewListTwo.add(tvPlanTwo);
        textViewListTwo.add(tvTimeTwo);
        textViewListTwo.add(tvFarTwo);
        textViewListThree.add(tvPlanThree);
        textViewListThree.add(tvFarThree);
        textViewListThree.add(tvTimeThree);
    }


    /**
     * 用 Marker 形式展示热点
     * @param hotSpotInfo 热点信息对象
     */
    private void showHotSpot(HotSpotInfo hotSpotInfo) {
        double longititue = hotSpotInfo.getLongitude();
        double latitute = hotSpotInfo.getLatitude();
        Logger.d("打标记：经度: " + longititue + "： 纬度" + latitute);
        LatLng latLngHot = new LatLng(latitute, longititue);
        MarkerOptions markerOptions = new MarkerOptions().position(latLngHot).title(hotSpotInfo.getAddress()).snippet("热度" + hotSpotInfo.getHeat())
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources() ,R.mipmap.ui_hotspot_endpoint)));
        final Marker markerHot =pathMap .addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLngHot, 15, 0, 0));
        //将相机移动到标记所在位置
        pathMap.moveCamera(cameraUpdate);
    }

    //注册绑定EventBus
    protected boolean isRegisterEventBus() {
        return true;
    }

    /**
     * 处理eventbus发过来的事件
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEvent baseEvent) {
        if (baseEvent.type.equals(EventBusType.HOTSPOT_CHOSEN)) {
            Log.e(TAG,"接收事件 热点已经选择，显示状态框");
            layoutPlanCard.setVisibility(View.GONE);
            layoutOriginHotSpot.setVisibility(View.VISIBLE);
            HotSpotInfo hotSpotInfo = (HotSpotInfo) baseEvent.object;
            showHotSpot(hotSpotInfo);
            hotSpotChosen = hotSpotInfo.getAddress();
            //如果热点文本框Visible则赋值用户选定的热点信息
            if (searchEndPoint.getVisibility() == View.VISIBLE) {
                searchEndPoint.setText(hotSpotInfo.getAddress());
            }
        }

        if(baseEvent.type.equals(EventBusType.ORIGIN_HOTSPOT_BOTH_CHOSEN)) {
            Logger.d("接收事件 ： 热点和地点都已经选择");
        }
    }

    public void  changeTextColor(List<TextView> viewList , int color){
        for(TextView view : viewList) {
            view.setTextColor(color);
        }
    }

    public void  setOriginHotSpotText(String origin ,String hotspot) {
        searchOrigin.setText(origin);
        searchEndPoint.setText(hotspot);
    }

}

