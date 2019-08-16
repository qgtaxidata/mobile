package com.example.taxidata.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.example.taxidata.R;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    MapView mapView;
    AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mapView = findViewById(R.id.map_demo);

        if (aMap == null) {
            aMap = mapView.getMap();
        }

        mapView.onCreate(savedInstanceState);

        LatLng a = new LatLng(23.1793090781462,113.25147617125815);
        LatLng b = new LatLng(23.11212215288405,113.2912442012871);
        LatLng c = new LatLng(23.151750116289975,113.35072287540632);
        LatLng d = new LatLng(23.591819371822364,113.57680634160592);
        LatLng e = new LatLng(22.80783659108823,113.54724554599153);
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(a);
        latLngs.add(b);
        latLngs.add(c);
        latLngs.add(d);
        latLngs.add(e);
        aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));

    }


}
