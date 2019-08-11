package com.example.taxidata.ui.TaxiDriverIncome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taxidata.R;
import com.example.taxidata.widget.DropDownSelectView;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class IncomeActivity extends AppCompatActivity {

    private DropDownSelectView areaDropDownSelectView;
    private DropDownSelectView timeDropDownSelectView;
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        areaDropDownSelectView = findViewById(R.id.area_select_view);
        timeDropDownSelectView = findViewById(R.id.time_select_view);
        initAreaList();
        initTimeList();
    }

    //初始化区域popupWindow
    private void initAreaList(){
        areaList.add("荔湾区");
        areaList.add("越秀区");
        areaList.add("天河区");
        areaList.add("海珠区");
        areaList.add("黄埔区");
        areaList.add("萝岗区");
        areaList.add("花都区");
        areaList.add("白云区");
        areaList.add("番禺区");
        areaList.add("南沙区");
        areaList.add("从化市");
        areaList.add("增城区");
        areaDropDownSelectView.setItemsData(areaList, 1);
    }

    //初始化时间popupWindow
    private void initTimeList(){
        timeList.add("2007年02月04日");
        timeList.add("2007年02月05日");
        timeList.add("2007年02月06日");
        timeList.add("2007年02月07日");
        timeList.add("2007年02月08日");
        timeList.add("2007年02月09日");
        timeList.add("2007年02月10日");
        timeList.add("2007年02月11日");
        timeList.add("2007年02月12日");
        timeList.add("2007年02月13日");
        timeList.add("2007年02月14日");
        timeList.add("2007年02月15日");
        timeDropDownSelectView.setItemsData(timeList, 2);
    }


}
