package com.example.taxidata.ui.heatpower;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.example.taxidata.R;

import java.util.List;

public class HeatPowerActivity extends AppCompatActivity implements HeatPowerContract.HeatPowerView{

    private MapView mMapView;
    private AMap mAMap;
    private HeatmapTileProvider.Builder heatpowerBuilder;
    private HeatmapTileProvider heatpower;
    private HeatPowerContract.HeatPowerPresent present;

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
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,10,0,0));
        mAMap.moveCamera(cameraUpdate);

        present.updataHeatPoint(getCentralLongitudeLatitude(),getTime());
    }

    @Override
    public LatLng getCentralLongitudeLatitude() {
        //获取地图长宽高
        int left = mMapView.getLeft();
        int top = mMapView.getTop();
        int right = mMapView.getRight();
        int bottom = mMapView.getBottom();
        //获取屏幕中心位置
        int x = (right - left) / 2 + left;
        int y = (bottom - top) / 2 + top;
        Projection pro = mAMap.getProjection();
        LatLng centralLongitudeLatitude = pro.fromScreenLocation(new Point(x,y));
        return centralLongitudeLatitude;
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public void showHeatPower(List<LatLng> heatPointList) {
        heatpowerBuilder = new HeatmapTileProvider.Builder();
        heatpowerBuilder.data(heatPointList);
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
}
