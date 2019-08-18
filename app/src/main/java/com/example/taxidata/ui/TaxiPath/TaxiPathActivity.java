package com.example.taxidata.ui.TaxiPath;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.example.taxidata.R;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.GetTaxiPathInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.constant.Area;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.StrongStengthTimerPicker;
import com.example.taxidata.widget.TimePickClickedListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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

public class TaxiPathActivity extends BaseActivity implements TaxiPathContract.TaxiPathView {

    @BindView(R.id.taxi_path_clear_btn)
    Button taxiPathClearBtn;
    @BindView(R.id.taxi_path_map)
    MapView taxiPathMap;
    @BindView(R.id.taxi_path_licenseplateno_btn)
    Button taxiPathLicenseplatenoBtn;
    @BindView(R.id.taxi_path_area_select_view)
    DropDownSelectView taxiPathAreaSelectView;
    @BindView(R.id.taxi_path_time_picker)
    StrongStengthTimerPicker taxiPathTimePicker;
    @BindView(R.id.taxi_path_history_tv)
    TextView taxiPathHistoryTv;
    @BindView(R.id.taxi_path_now_tv)
    TextView taxiPathNowTv;

    private TaxiPathContract.TaxiPathPresent taxiPathPresent;
    private AMap taxiPathAMap;
    private int areaId = 5;
    private int flag = 1;   //实时为1，历史为2
    private List<TaxiInfo.DataBean> taxiList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_path);
        ButterKnife.bind(this);
        taxiPathPresent = new TaxiPathPresent();
        taxiPathPresent.attachView(this);
        //绑定显示出租车信息的dialog
        EventBus.getDefault().register(this);
        //隐藏清除按钮
        taxiPathClearBtn.setVisibility(View.GONE);
        //将时间选择器设置为不可点击且开始计时（实时状态）
        taxiPathTimePicker.startTimer();
        taxiPathTimePicker.setTimeStatusBarClick(null);
        //初始化区域选择框
        initAreaList();
        //得到地图实例
        if (taxiPathAMap == null) {
            taxiPathAMap = taxiPathMap.getMap();
        }
        taxiPathMap.onCreate(savedInstanceState);
        //设置默认显示番禺区
        LatLng latLng = Area.area_latlng.get(5);
        taxiPathAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        //获取用户选择的区域
        taxiPathAreaSelectView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taxiPathPresent.detachView();
        EventBus.getDefault().unregister(this);
    }


    //显示出租车历史路径
    @Override
    public void showHistoryPath(List<TaxiPathInfo.DataBean> listInfo) {
        LatLng latLng = new LatLng(listInfo.get(0).getLatitude(),listInfo.get(0).getLongitude());
        taxiPathAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        Log.d("show1",listInfo.get(0).getLicenseplateno());
        List<LatLng> latLngs = new ArrayList<LatLng>();
        Log.d("show2",listInfo.get(0).getLicenseplateno());
        for (TaxiPathInfo.DataBean info : listInfo) {
            latLngs.add(new LatLng(info.getLatitude(), info.getLongitude()));
            Log.d("TaxiPathActivity", info.getLatitude()+"和"+info.getLongitude());
        }
        Log.d("show3",listInfo.get(0).getLicenseplateno());
        taxiPathAMap.addPolyline(new PolylineOptions()
                .addAll(latLngs)
                .width(10)
                .color(Color.parseColor("#51b46d")));
        Log.d("show4",listInfo.get(0).getLicenseplateno());
        //显示清除路径按钮
        taxiPathClearBtn.setVisibility(View.VISIBLE);
    }

    //显示出租车实时路径
    @Override
    public void showCurrentPath(List<TaxiPathInfo.DataBean> listInfo) {

    }

    //处理dialog发送的事件
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getPathInfo(GetTaxiPathInfo getPathInfo) {
        Log.d("flag", ""+flag);
        if(flag == 1){
            taxiPathPresent.getCurrentTaxiPathInfo(this, getPathInfo.getTime(), getPathInfo.getLicenseplateno());
        }else {
            taxiPathPresent.getHistoryTaxiPathInfo(this, getPathInfo.getTime(), getPathInfo.getLicenseplateno());
        }
    }

    //点击事件
    @OnClick({R.id.taxi_path_clear_btn, R.id.taxi_path_licenseplateno_btn,R.id.taxi_path_history_tv, R.id.taxi_path_now_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.taxi_path_clear_btn:
                //清除地图显示的路径
                taxiPathAMap.clear();
                //将清除按钮隐藏
                taxiPathClearBtn.setVisibility(View.GONE);
                //停止轮询
                taxiPathPresent.setFlag(true);
                break;
            case R.id.taxi_path_licenseplateno_btn:
                //显示车牌号列表
                if(flag == 1){
                    //实时
                    taxiPathPresent.getTaxiInfo(this, areaId, taxiPathTimePicker.getTime());
                }else {
                    //历史
                    taxiPathPresent.getTaxiInfo(this, areaId, taxiPathTimePicker.getHistoryTime());
                }
                break;
            case R.id.taxi_path_history_tv:
                flag = 2;
                //清除地图的路径
                taxiPathAMap.clear();
                //停止轮询
                taxiPathPresent.setFlag(true);
                //改变布局
                taxiPathHistoryTv.setBackgroundResource(R.drawable.shape_small_button);
                taxiPathHistoryTv.setTextColor(Color.parseColor("#ffffff"));
                taxiPathNowTv.setBackgroundResource(R.drawable.shape_status_bar);
                taxiPathNowTv.setTextColor(Color.parseColor("#4c93fd"));
                taxiPathTimePicker.stopTimer();
                taxiPathTimePicker.setTimeStatusBarClick(new TimePickClickedListener() {
                    @Override
                    public void onClick(StrongStengthTimerPicker v) {
                        v.showDetailTimerPicker();
                    }
                });
                break;
            case R.id.taxi_path_now_tv:
                flag = 1;
                //清除地图的路径
                taxiPathAMap.clear();
                //停止轮询
                taxiPathPresent.setFlag(true);
                //改变布局
                taxiPathNowTv.setBackgroundResource(R.drawable.shape_small_button);
                taxiPathNowTv.setTextColor(Color.parseColor("#ffffff"));
                taxiPathHistoryTv.setBackgroundResource(R.drawable.shape_status_bar);
                taxiPathHistoryTv.setTextColor(Color.parseColor("#4c93fd"));
                taxiPathTimePicker.startTimer();
                taxiPathTimePicker.setTimeStatusBarClick(null);
                break;
        }
    }

    //初始化区域popupWindow
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
        taxiPathAreaSelectView.setItemsData(areaList, 1);
    }

    @Override
    public void clearMap() {
        taxiPathAMap.clear();
    }



}
