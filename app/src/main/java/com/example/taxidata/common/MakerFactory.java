package com.example.taxidata.common;

import android.graphics.BitmapFactory;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;

public class MakerFactory {

    public static final int CONST_ORIGIN = 0;

    public static final int CONST_END = 1;

    public static MarkerOptions create(int originEnd, LatLng latLng) {
        switch (originEnd) {
            case CONST_ORIGIN :
                return new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(TaxiApp.getContext().getResources() , R.mipmap.ui_hotspot_origin)));
            case CONST_END :
                return new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(TaxiApp.getContext().getResources() , R.mipmap.ui_hotspot_endpoint)));
            default:
        }

        return null;
    }
}
