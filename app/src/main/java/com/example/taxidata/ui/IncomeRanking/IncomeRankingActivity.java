package com.example.taxidata.ui.IncomeRanking;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.example.taxidata.R;
import com.example.taxidata.adapter.IncomeAdapter;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.constant.Area;
import com.example.taxidata.util.TimeChangeUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.SimpleLoadingDialog;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class IncomeRankingActivity extends BaseActivity implements IncomeRankingContract.IncomeRankingView {

    @BindView(R.id.income_ranking_area_select_view)
    DropDownSelectView incomeRankingAreaSelectView;
    @BindView(R.id.income_ranking_time_select_view)
    DropDownSelectView incomeRankingTimeSelectView;
    @BindView(R.id.income_ranking_btn_refresh_list)
    Button incomeRankingBtnRefreshList;
    @BindView(R.id.income_ranking_recycle_view)
    RecyclerView incomeRankingRecycleView;

    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    ArrayList<IncomeRankingInfo.DataBean> incomeList= new ArrayList<IncomeRankingInfo.DataBean>();
    private SimpleLoadingDialog loading;
    private IncomeAdapter adapter;
    private int areaId = 5;
    private String date = "2017-02-05";

    private IncomeRankingContract.IncomeRankingPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_ranking);
        ButterKnife.bind(this);
        present = new IncomeRankingPresent();
        present.attachView(this);
        initAreaList();
        initTimeList();
        initIncomeList();
        //获取用户选择的区域
        incomeRankingAreaSelectView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String areaName = areaList.get(position);
                areaId = Area.area.get(areaName);
            }
        });
        //获取用户选择的时间
        incomeRankingTimeSelectView.setOnItemClickListener(new OnItemClickListener() {
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
        //用户点击司机编号展示司机运营情况
        adapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String driverID = incomeList.get(position).getDriverID();
                int rank = incomeList.get(position).getRank();
                double income = incomeList.get(position).getIncome();
                present.getDriverConditionInfo(IncomeRankingActivity.this, areaId, date, driverID, rank, income);
            }
        });
    }

    @OnClick(R.id.income_ranking_btn_refresh_list)
    public void onViewClicked() {
        //发送请求司机收入排行榜数据
        present.getIncomeRankingInfo(IncomeRankingActivity.this, areaId, date);
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
        incomeRankingAreaSelectView.setItemsData(areaList, 1);
    }

    //初始化时间popupWindow
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
        incomeRankingTimeSelectView.setItemsData(timeList, 2);
    }

    //展示司机收入排行榜列表
    @Override
    public void showIncomeList(List<IncomeRankingInfo.DataBean> list) {
        incomeList.clear();
        incomeList.addAll(list);
        Log.d("init",incomeList.get(10).getDriverID()+"");
        incomeRankingRecycleView.setAdapter(adapter);
    }

    //初始化列表(默认显示番禺区和2017年02月05号的数据)
    private void initIncomeList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        incomeRankingRecycleView.setLayoutManager(layoutManager);
        adapter = new IncomeAdapter(incomeList);
        incomeRankingRecycleView.setAdapter(adapter);
        present.getIncomeRankingInfo(IncomeRankingActivity.this, areaId, date);
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
