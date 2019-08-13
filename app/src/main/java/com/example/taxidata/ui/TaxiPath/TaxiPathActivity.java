package com.example.taxidata.ui.TaxiPath;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.GetTaxiPathInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TaxiPathActivity extends BaseActivity implements TaxiPathContract.TaxiPathView  {

    private TaxiPathContract.TaxiPathPresent taxiPathPresent;
    private MapView taxiPathMapView;
    private AMap taxiPathAMap;
    private double longitude;    //经度
    private double latitude;     //纬度
    private List<TaxiInfo.DataBean> taxiList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_path);
        taxiPathPresent = new TaxiPathPresent();
        taxiPathPresent.attachView(this);
        EventBus.getDefault().register(this);
        taxiPathMapView = findViewById(R.id.taxi_path_map);
        if(taxiPathAMap == null){
            taxiPathAMap = taxiPathMapView.getMap();
        }
        taxiPathMapView.onCreate(savedInstanceState);
        //设置默认显示位置和比例
        LatLng latLng = new LatLng(23.209000, 113.317390);
        taxiPathAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        taxiPathAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                longitude = latLng.longitude;
                latitude = latLng.latitude;
                taxiPathPresent.getTaxiInfo(TaxiPathActivity.this, longitude, latitude, "2017-02-01 17:00:00");
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        taxiPathPresent.detachView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void initList(List<TaxiInfo.DataBean> taxiInfoList) {
        taxiList.addAll(taxiInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    }

    @Override
    public void showPath(List<TaxiPathInfo.DataBean> listInfo) {
        Log.d("wxV1", "show");
        List<LatLng> latLngs = new ArrayList<LatLng>();
        Log.d("wxV2", "show");
        for(TaxiPathInfo.DataBean info:listInfo){
            latLngs.add(new LatLng(info.getLatitude(), info.getLongitude()));
        }
        Log.d("wxV3", "show");
        taxiPathAMap.addPolyline(new PolylineOptions()
                .addAll(latLngs)
                .width(10)
                .color(Color.BLACK));
        Log.d("wxV4", "show");
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getPathInfo(GetTaxiPathInfo getTaxiPathInfo){
        Log.d("wxV1", getTaxiPathInfo.getLicenseplateno());
        taxiPathPresent.getTaxiPathInfo(this,getTaxiPathInfo.getTime(), getTaxiPathInfo.getLicenseplateno());
        Log.d("wxV2", getTaxiPathInfo.getLicenseplateno());
    }
}
