package com.example.taxidata.ui.TaxiPath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;

public class TaxiPathActivity extends BaseActivity implements TaxiPathContract.TaxiPathView  {

    private TaxiPathContract.TaxiPathPresent taxiPathPresent;
    private MapView taxiPathMapView;
    private AMap taxiPathAMap;
    private double longitude;    //经度
    private double latitude;     //纬度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_path);
        taxiPathPresent.attachView(this);
        taxiPathMapView = findViewById(R.id.taxi_path_map);
        if(taxiPathAMap == null){
            taxiPathAMap = taxiPathMapView.getMap();
        }
        taxiPathMapView.onCreate(savedInstanceState);
        //设置默认显示位置和比例
        LatLng latLng = new LatLng(23.209000, 113.317390);
        taxiPathAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
        taxiPathAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                longitude = latLng.longitude;
                latitude = latLng.latitude;
                taxiPathPresent.getTaxiInfo(TaxiPathActivity.this, longitude, latitude, "2017-02-01 17:00:00");
            }
        });
    }
}
