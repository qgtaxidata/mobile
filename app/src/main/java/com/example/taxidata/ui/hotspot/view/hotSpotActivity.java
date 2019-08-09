package com.example.taxidata.ui.hotspot.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.hotSpotCallBackInfo;
import com.example.taxidata.ui.hotspot.contract.hotspotContract;
import com.example.taxidata.ui.hotspot.presenter.hotspotPresenter;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ODM
 * @date: 2019/8/9
 */
public class hotSpotActivity extends BaseActivity implements hotspotContract.View , GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.map_hotspot)
    MapView mapHotspot;
    @BindView(R.id.btn_hotspot_showdialog)
    Button btn_showDialog;
    MyAlertInputDialog myAlertInputDialog;
    GeocodeSearch geocodeSearch ;
    double inputLatitude;
    double inputLongtitude;
    AMap aMap = null;
    hotspotPresenter mPresenter = new hotspotPresenter();
    private static final String TAG = "hotSpotActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot);
        ButterKnife.bind(this);
        mapHotspot.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapHotspot.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapHotspot.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapHotspot.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapHotspot.onSaveInstanceState(outState);
    }

    public void initViews() {

        if (aMap == null) {
            aMap = mapHotspot.getMap();
        }
         LatLng latLngGuangZhou = new LatLng(23.129112,113.264385);
        // 位移到广州
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngGuangZhou,16));
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        btn_showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        myAlertInputDialog = new MyAlertInputDialog(this).builder()
                .setTitle("请输入")
                .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = myAlertInputDialog.getResult();
                //将用户输入的地址转换为坐标
                GeocodeQuery query = new GeocodeQuery(address, "广州");
                geocodeSearch.getFromLocationNameAsyn(query);
                myAlertInputDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
            }
        });
    }

    @Override
    public void showHotSpot(List<hotSpotCallBackInfo>  hotSpotCallBackInfoList) {
        if (hotSpotCallBackInfoList.size() > 0) {
            for (hotSpotCallBackInfo info : hotSpotCallBackInfoList) {
                double longtitude = info.getLongitude();
                double latitude = info.getLatitude();
                LatLng latLng = new LatLng(longtitude  ,latitude);
                Marker marker = aMap.addMarker(new MarkerOptions().position(latLng));
            }

        } else {
            Logger.d("热点推荐列表大小为0 !! ");
        }
    }

    public void showInputDialog() {
        if(myAlertInputDialog != null) {
            myAlertInputDialog.show();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    /**
     * 回调获取输入的地址对应的坐标
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
        LatLonPoint point = geocodeAddress.getLatLonPoint();
        inputLatitude = point.getLatitude();
        inputLongtitude = point.getLongitude();
        Log.e(TAG,"转换坐标: 经度:  " + inputLongtitude + "   纬度:  " + inputLatitude);
        mPresenter.getHotSpot(inputLongtitude,inputLatitude ,"");
    }

//    public void addressTransferPoint(String  address) {
//        GeocodeQuery query = new GeocodeQuery(address, "广州");
//        geocodeSearch.getFromLocationNameAsyn(query);
//    }
}
