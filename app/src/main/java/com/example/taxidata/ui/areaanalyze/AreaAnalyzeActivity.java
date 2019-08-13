package com.example.taxidata.ui.areaanalyze;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.taxidata.R;
import com.example.taxidata.adapter.AreaAnalyzeTransformer;
import com.example.taxidata.adapter.AreaAnalyzeViewPagerAdapter;
import com.example.taxidata.widget.DropDownSelectView;

import java.util.ArrayList;
import java.util.List;

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

public class AreaAnalyzeActivity extends AppCompatActivity {

    @BindView(R.id.area_analyze_vp)
    ViewPager areaAnalyzeVp;
    @BindView(R.id.area_analyze_area_select_view)
    DropDownSelectView areaAnalyzeAreaSelectView;
    @BindView(R.id.area_analyze_time_select_view)
    DropDownSelectView areaAnalyzeTimeSelectView;
    @BindView(R.id.area_analyze_btn_refresh_list)
    Button areaAnalyzeBtnRefreshList;

    private List<Fragment> areaAnalyzeList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_analyze);
        ButterKnife.bind(this);
        //设置viewPager
        setViewPager(areaAnalyzeVp);
        //初始化区域popupWindow
        initAreaList();
        //初始化时间popupWindow
        initTimeList();
    }


    private void setViewPager(ViewPager viewPager) {
        areaAnalyzeList.add(new DepartureRateFragment());      //添加出车率碎片
        areaAnalyzeList.add(new PassagerFrequencyFragment());    //添加载客频率碎片
        areaAnalyzeList.add(new MileageUtilizationRateFragment());     //添加里程利用率碎片
        AreaAnalyzeViewPagerAdapter adapter = new AreaAnalyzeViewPagerAdapter(getSupportFragmentManager(), areaAnalyzeList);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new AreaAnalyzeTransformer());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(10);
    }


    @OnClick(R.id.area_analyze_btn_refresh_list)
    public void onViewClicked() {
    }


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
        areaAnalyzeAreaSelectView.setItemsData(areaList, 1);
    }



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
        areaAnalyzeTimeSelectView.setItemsData(timeList, 2);
    }
}
