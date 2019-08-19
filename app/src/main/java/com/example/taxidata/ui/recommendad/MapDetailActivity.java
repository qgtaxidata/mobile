package com.example.taxidata.ui.recommendad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.taxidata.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MapDetailActivity extends AppCompatActivity {

    private static final String TAG = "MapDetailActivity";
    private MapView detailAdMv;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
        detailAdMv = findViewById(R.id.mv_detail_poisting);
        detailAdMv.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = detailAdMv.getMap();
        }

        initAmap();

        EventBus.getDefault().register(this);
    }

    private void initAmap() {
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                }else {
                    marker.showInfoWindow();
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailAdMv.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailAdMv.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailAdMv.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        detailAdMv.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void positingPosition(DetailAdInfo info) {
        LatLng latLng = new LatLng(info.getInfo().getBoardLat(),info.getInfo().getBoardLon());
        Log.d(TAG,latLng.toString());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("地址").snippet(info.getDetailInfo());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_positing)));
        aMap.addMarker(markerOptions);
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,14,0,0));
        aMap.moveCamera(update);
    }
}
