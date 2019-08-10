package com.example.taxidata.ui.heatpower;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Gradient;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.taxidata.R;

import java.util.ArrayList;
import java.util.List;

public class HeatPowerActivity extends AppCompatActivity implements HeatPowerContract.HeatPowerView, AMap.OnCameraChangeListener {

    private MapView mMapView;
    private AMap mAMap;
    private HeatmapTileProvider.Builder heatpowerBuilder;
    private HeatmapTileProvider heatpower;
    private HeatPowerContract.HeatPowerPresent present;
    private static final String TAG = "HeatPowerActivity";
    //屏幕中心的纬度
    private LatLng target =  new LatLng(23.209000,113.317390);

    //渐变颜色数组
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.rgb( 0, 0, 255),
            Color.rgb( 117, 211, 248),
            Color.rgb(0, 255, 0),
            Color.rgb(255, 234, 0),
            Color.rgb(255, 0, 0)
    };
    //渐变开始点数组
    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = { 0.5f,
            0.65f, 0.7f, 0.9f, 1.0f };

    //渐变
    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(
            ALT_HEATMAP_GRADIENT_COLORS, ALT_HEATMAP_GRADIENT_START_POINTS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_power);
        mMapView = findViewById(R.id.mv_heat_power);
        present = new HeatPowerPresent();
        present.attachView(this);
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null){
            mAMap = mMapView.getMap();
        }
        LatLng latLng = new LatLng(23.209000,113.317390);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,12,0,0));
        mAMap.moveCamera(cameraUpdate);
        mAMap.setOnCameraChangeListener(this);
        present.updataHeatPoint(getTime());
    }

    @Override
    public LatLng getCentralLongitudeLatitude() {
        return this.target;
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public void showHeatPower(List<WeightedLatLng> heatPointList) {
        if (mAMap != null){
            mAMap.clear();
        }
        heatpowerBuilder = new HeatmapTileProvider.Builder();
        heatpowerBuilder.weightedData(heatPointList)
                .gradient(ALT_HEATMAP_GRADIENT);
        heatpower = heatpowerBuilder.build();
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatpower);
        mAMap.addTileOverlay(tileOverlayOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        present.detachView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        this.target = cameraPosition.target;
        Log.d(TAG,":" + target);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }
}
