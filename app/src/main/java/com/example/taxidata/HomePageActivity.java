package com.example.taxidata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.example.taxidata.ui.TaxiPath.TaxiPathActivity;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    MapView homePageMv;
    AMap homepageAMap;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homePageMv = findViewById(R.id.mv_home_page);
        button = findViewById(R.id.btn);
        if (homepageAMap == null){
            homepageAMap = homePageMv.getMap();
        }
        homePageMv.onCreate(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, TaxiPathActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePageMv.onDestroy();
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


}
