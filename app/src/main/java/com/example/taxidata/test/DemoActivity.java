package com.example.taxidata.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.example.taxidata.R;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.StrongStengthTimerPicker;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    StrongStengthTimerPicker picker;
    Button start;
    Button end;
    Button getTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        picker = findViewById(R.id.stp_demo);
        start = findViewById(R.id.start_time);
        end = findViewById(R.id.end_time);
        getTime = findViewById(R.id.get_time);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶部时间状态栏时间开始走动
                picker.startTimer();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶部时间状态栏停止走动
                picker.stopTimer();
            }
        });

        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToastBottom(picker.getTime());
            }
        });
    }
}
