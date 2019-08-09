package com.example.taxidata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;
import com.example.taxidata.ui.heatpower.HeatPowerContract;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mMapView = null;
        LatLng[] latlngs;

        LatLng latLngBeijing1= new LatLng(34.341568, 108.940174);
        LatLng latLngBeijing2 = new LatLng(40.906901,116.397972);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        AMap aMap = null;
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.showBuildings(true);
        aMap.showMapText(true);

        Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLngBeijing1).title("西安").snippet("DefaultMarker"));
        Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLngBeijing2).title("北京2").snippet("DefaultMarker"));
    }
}
