package com.example.taxidata.ui.areaanalyze;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.taxidata.R;
import com.example.taxidata.adapter.AreaAnalyzeTransformer;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.adapter.ViewPagerAdapter;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.example.taxidata.constant.Area;
import com.example.taxidata.util.TimeChangeUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.SimpleLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

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
import static com.example.taxidata.constant.Area.area;

public class AreaAnalyzeActivity extends BaseActivity implements AreaAnalyzeContract.AreaAnalyzeView {

    @BindView(R.id.area_analyze_vp)
    ViewPager areaAnalyzeVp;
    @BindView(R.id.area_analyze_area_select_view)
    DropDownSelectView areaAnalyzeAreaSelectView;
    @BindView(R.id.area_analyze_time_select_view)
    DropDownSelectView areaAnalyzeTimeSelectView;
    @BindView(R.id.area_analyze_btn_refresh_list)
    Button areaAnalyzeBtnRefreshList;

    private AreaAnalyzeContract.AreaAnalyzePresent present;
    private SimpleLoadingDialog loading;
    private List<Fragment> areaAnalyzeList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    private int areaId = 5;
    private String date = "2017-02-05";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_analyze);
        ButterKnife.bind(this);
        present = new AreaAnalyzePresent();
        present.attachView(this);
        //设置viewPager
        setViewPager(areaAnalyzeVp);
        //初始化区域popupWindow
        initAreaList();
        //初始化时间popupWindow
        initTimeList();
        //默认显示番禺区2017年02月05日的数据
        present.getAreaAnalyzeInfo(this, areaId, date);
        //获取用户选择的区域
        areaAnalyzeAreaSelectView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
        //获取用户选择的时间
        areaAnalyzeTimeSelectView.setOnItemClickListener(new OnItemClickListener() {
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


    private void setViewPager(ViewPager viewPager) {
        areaAnalyzeList.add(new DepartureRateFragment());      //添加出车率碎片
        areaAnalyzeList.add(new PassagerFrequencyFragment());    //添加载客频率碎片
        areaAnalyzeList.add(new MileageUtilizationRateFragment());     //添加里程利用率碎片
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), areaAnalyzeList);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new AreaAnalyzeTransformer());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(10);
    }

    @Override
    public void sendData(AreaAnalyzeInfo.DataBean dataBean) {
        Log.d("AreaAnalyzeActivity", "data");
        EventBus.getDefault().post(dataBean.getMileage_utilization());   //里程利用率
        EventBus.getDefault().post(dataBean.getPick_up_freq());          //载客频率
        EventBus.getDefault().post(dataBean.getTime_utilization());     //出车率
    }

    @OnClick(R.id.area_analyze_btn_refresh_list)
    public void onViewClicked() {
        present.getAreaAnalyzeInfo(AreaAnalyzeActivity.this, areaId, date);
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
        areaAnalyzeTimeSelectView.setItemsData(timeList, 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }

    //加载loading界面
    @Override
    public void showLoadingView() {
        //初始化loading界面
        loading = new SimpleLoadingDialog(this,"数据正在加载中！",R.drawable.dialog_image_loading);
        loading.show();
    }
    //取消loading界面
    @Override
    public void hideLoadingView() {
        if(loading!=null){
            loading.dismiss();
        }
    }
}
