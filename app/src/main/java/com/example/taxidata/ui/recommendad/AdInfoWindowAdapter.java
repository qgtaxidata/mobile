package com.example.taxidata.ui.recommendad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.example.taxidata.R;

public class AdInfoWindowAdapter implements AMap.InfoWindowAdapter {

    private View view;
    private Context context;

    public AdInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
            view.findViewById(R.id.snippet).setSelected(true);
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
