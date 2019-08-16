package com.example.taxidata.ui.areaincome;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxidata.R;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.constant.Area;
import com.example.taxidata.ui.taxidemand.TaxiDemandActivity;
import com.example.taxidata.util.TimeChangeUtil;
import com.example.taxidata.widget.DropDownSelectView;

import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.taxidata.constant.Area.BAI_YUN;
import static com.example.taxidata.constant.Area.CONG_HUA;
import static com.example.taxidata.constant.Area.HAI_ZHU;
import static com.example.taxidata.constant.Area.HUANG_PU;
import static com.example.taxidata.constant.Area.HUA_DU;
import static com.example.taxidata.constant.Area.LI_WAN;
import static com.example.taxidata.constant.Area.NAN_SHA;
import static com.example.taxidata.constant.Area.PAN_YU;
import static com.example.taxidata.constant.Area.TIAN_HE;
import static com.example.taxidata.constant.Area.YUE_XIU;
import static com.example.taxidata.constant.Area.ZENG_CHENG;

public class AreaIncomeActivity extends BaseActivity implements AreaIncomeContract.AreaIncomeView {


    @BindView(R.id.area_income_area_select_view)
    DropDownSelectView areaIncomeAreaSelectView;
    @BindView(R.id.area_income_time_select_view)
    DropDownSelectView areaIncomeTimeSelectView;
    @BindView(R.id.area_income_refresh_list_btn)
    Button areaIncomeRefreshListBtn;

    private AreaIncomeContract.AreaIncomePresent present;
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    private int areaId = 5;
    private String date = "2017-02-03";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_income);
        ButterKnife.bind(this);
        present = new AreaIncomePresent();
        present.attachView(this);
        initAreaList();
        initTimeList();
        initChart();
        //获取用户选择的区域
        areaIncomeAreaSelectView.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
        //获取用户选择的时间
        areaIncomeTimeSelectView.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String time = timeList.get(position);
                try {
                    date = TimeChangeUtil.transform(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.area_income_refresh_list_btn)
    public void onViewClicked() {
        present.getAreaIncomeInfo(AreaIncomeActivity.this, areaId, date);
    }

    //初始化区域popupWindow
    private void initAreaList() {
        areaList.add(LI_WAN);
        areaList.add(YUE_XIU);
        areaList.add(TIAN_HE);
        areaList.add(HAI_ZHU);
        areaList.add(HUANG_PU);
        areaList.add(HUA_DU);
        areaList.add(BAI_YUN);
        areaList.add(PAN_YU);
        areaList.add(NAN_SHA);
        areaList.add(CONG_HUA);
        areaList.add(ZENG_CHENG);
        areaIncomeAreaSelectView.setItemsData(areaList, 1);
    }

    //初始化时间popupWindow
    private void initTimeList(){
        timeList.add("2017年02月03日");
        timeList.add("2017年02月04日");
        timeList.add("2017年02月05日");
        timeList.add("2017年02月06日");
        timeList.add("2017年02月07日");
        timeList.add("2017年02月08日");
        timeList.add("2017年02月09日");
        timeList.add("2017年02月10日");
        timeList.add("2017年02月11日");
        timeList.add("2017年02月12日");
        timeList.add("2017年02月13日");
        timeList.add("2017年02月14日");
        timeList.add("2017年02月15日");
        timeList.add("2017年02月16日");
        timeList.add("2017年02月17日");
        timeList.add("2017年02月18日");
        timeList.add("2017年02月19日");
        timeList.add("2017年02月20日");
        timeList.add("2017年02月21日");
        areaIncomeTimeSelectView.setItemsData(timeList, 2);
    }

    //初始化图表(默认显示番禺区和2017年02月03号的数据)
    private void initChart(){
        present.getAreaIncomeInfo(AreaIncomeActivity.this, areaId, date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }
}
