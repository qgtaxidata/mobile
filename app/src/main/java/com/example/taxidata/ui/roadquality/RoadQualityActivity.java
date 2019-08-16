package com.example.taxidata.ui.roadquality;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.taxidata.R;
import com.example.taxidata.adapter.AreaAnalyzeTransformer;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.adapter.ViewPagerAdapter;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.constant.Area;
import com.example.taxidata.util.TimeChangeUtil;
import com.example.taxidata.widget.DropDownSelectView;

import java.text.ParseException;
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

public class RoadQualityActivity extends BaseActivity implements RoadQualityContract.RoadQualityView {

    @BindView(R.id.road_quality_area_select_view)
    DropDownSelectView roadQualityAreaSelectView;
    @BindView(R.id.road_quality_time_select_view)
    DropDownSelectView roadQualityTimeSelectView;
    @BindView(R.id.road_quality_refresh_list_btn)
    Button roadQualityRefreshListBtn;
    @BindView(R.id.road_quality_vp)
    ViewPager roadQualityVp;

    private RoadQualityContract.RoadQualityPresent present;
    private List<Fragment> roadQualityList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    private int areaId = 5;
    private String date = "2017-02-03";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_quality);
        ButterKnife.bind(this);
        present = new RoadQualityPresent();
        present.attachView(this);
        initAreaList();
        initTimeList();
        //设置viewPager
        setViewPager(roadQualityVp);
        //获取用户选择的区域
        roadQualityAreaSelectView.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
        //获取用户选择的时间
        roadQualityTimeSelectView.seOnItemClickListener(new OnItemClickListener() {
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

    @OnClick(R.id.road_quality_refresh_list_btn)
    public void onViewClicked() {
        present.getRoadQualityInfo(RoadQualityActivity.this, areaId,date);
    }

    private void setViewPager(ViewPager viewPager) {
        roadQualityList.add(new CongestionDegreeFragment());      //添加拥挤度碎片
        roadQualityList.add(new TrafficFlowFragment());    //添加车流量碎片
        roadQualityList.add(new SpeedFragment());     //添加车速碎片
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), roadQualityList);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new AreaAnalyzeTransformer());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(10);
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
        roadQualityAreaSelectView.setItemsData(areaList, 1);
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
        roadQualityTimeSelectView.setItemsData(timeList, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }
}
