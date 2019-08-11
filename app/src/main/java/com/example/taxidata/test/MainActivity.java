package com.example.taxidata.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxidata.R;
import com.example.taxidata.adapter.CustomOnclick;
import com.example.taxidata.widget.MyTimerPicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyTimerPicker picker = findViewById(R.id.test);
        picker.setTimeStatusBarClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                v.showDetailTimePicker();
            }
        });
        picker.setCancelClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                v.hideDetailTimePicker();
            }
        });
        picker.setConfigClick(new CustomOnclick() {
            @Override
            public void onClick(MyTimerPicker v) {
                Toast.makeText(MainActivity.this,v.getTime(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
