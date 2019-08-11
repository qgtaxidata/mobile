package com.example.taxidata.ui.hotspot.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.HotSpotOrigin;

import java.util.ArrayList;
import java.util.List;

public class HotSpotOriginActivity extends BaseActivity {

    List<HotSpotOrigin> originList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_origin);
        originList = new ArrayList<>();
    }

    private void initView() {

    }




}
