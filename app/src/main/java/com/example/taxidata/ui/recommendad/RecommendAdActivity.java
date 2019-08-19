package com.example.taxidata.ui.recommendad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxidata.R;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.SimpleLoadingDialog;
import com.example.taxidata.widget.StatusToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecommendAdActivity extends AppCompatActivity implements RecommendAdContract.RecommendAdView {

    @BindView(R.id.dsv_ad_day_style)
    DropDownSelectView adDayStyleDsv;
    @BindView(R.id.dsv_ad_time_style)
    DropDownSelectView adTimeStyleDsv;
    @BindView(R.id.dsv_ad_area)
    DropDownSelectView adAreaDsv;
    @BindView(R.id.btn_ad_config_refresh)
    Button adConfigCancelBtn;
    @BindView(R.id.tv_ad_one)
    TextView oneAdTv;
    @BindView(R.id.ll_ad_one)
    LinearLayout oneAdLl;
    @BindView(R.id.tv_ad_two)
    TextView twoAdTv;
    @BindView(R.id.ll_ad_two)
    LinearLayout twoAdLl;
    @BindView(R.id.tv_ad_three)
    TextView threeAdTv;
    @BindView(R.id.ll_ad_three)
    LinearLayout threeAdLl;
    @BindView(R.id.tv_ad_four)
    TextView fourAdTv;
    @BindView(R.id.ll_ad_four)
    LinearLayout fourAdLl;
    @BindView(R.id.tv_ad_five)
    TextView fiveAdTv;
    @BindView(R.id.ll_ad_five)
    LinearLayout fiveAdLl;
    @BindView(R.id.btn_ad_create_chart)
    Button createChartBtn;
    @BindView(R.id.rl_ad)
    RelativeLayout adRl;
    private RecommendAdContract.RecommendAdPresent present;
    private SimpleLoadingDialog dialog;
    private Map<String,Integer> dayStyle = new HashMap();
    private Map<String,Integer> timeStyle = new HashMap<>();
    private Map<String,Integer> areaStyle = new HashMap<>();
    private boolean isFirst = true;
    private static final String TAG = "RecommendAdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_ad);
        ButterKnife.bind(this);

        present = new RecommendAdPresent(this);
        present.attachView(this);

        dialog = new SimpleLoadingDialog(this, "加载中..", R.drawable.dialog_image_loading);
        initTv();
        initMap();
        initDsv();
    }

    private void initTv() {
        oneAdTv.setSelected(true);
        twoAdTv.setSelected(true);
        threeAdTv.setSelected(true);
        fourAdTv.setSelected(true);
        fiveAdTv.setSelected(true);
    }

    private void initMap() {
        dayStyle.put("不限",0);
        dayStyle.put("工作日",1);
        dayStyle.put("休息日",2);
        timeStyle.put("不限",0);
        timeStyle.put("工作日",1);
        timeStyle.put("休息日",2);
        areaStyle.put("花都区",1);
        areaStyle.put("南沙区",2);
        areaStyle.put("增城区",3);
        areaStyle.put("从化区",4);
        areaStyle.put("番禺区",5);
        areaStyle.put("白云区",6);
        areaStyle.put("黄埔区",7);
        areaStyle.put("荔湾区",8);
        areaStyle.put("海珠区",9);
        areaStyle.put("天河区",10);
        areaStyle.put("越秀区",11);
    }

    private void initDsv() {
        adDayStyleDsv.setItemsData(new ArrayList<>(dayStyle.keySet()),6);
        adTimeStyleDsv.setItemsData(new ArrayList<>(timeStyle.keySet()),5);
        adAreaDsv.setItemsData(new ArrayList<>(areaStyle.keySet()),4);
    }

    @Override
    public void showAdPosition(String adName, int position) {
        switch (position) {
            case 0:
                oneAdTv.setText(adName);
                break;
            case 1:
                twoAdTv.setText(adName);
                break;
            case 2:
                threeAdTv.setText(adName);
                break;
            case 3:
                fourAdTv.setText(adName);
                break;
            case 4:
                fiveAdTv.setText(adName);
                break;
            default:
        }
    }

    @Override
    public void showLoading() {
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }

    @OnClick({R.id.btn_ad_config_refresh, R.id.ll_ad_one, R.id.ll_ad_two, R.id.ll_ad_three, R.id.ll_ad_four, R.id.ll_ad_five, R.id.btn_ad_create_chart})
    public void onViewClicked(View view) {
        String pre = "广东省广州市" + adAreaDsv.getSlectedArea();
        switch (view.getId()) {
            case R.id.btn_ad_config_refresh:
                if (isFirst) {
                    //第一次为确认操作
                    config();
                }else {
                    //刷新操作
                    refresh();
                }
                break;
            case R.id.ll_ad_one:
                if (present.positingPosition(0,pre + oneAdTv.getText().toString())) {
                    jumpToNextActivity();
                }
                break;
            case R.id.ll_ad_two:
                if (present.positingPosition(1,pre + twoAdTv.getText().toString())) {
                    jumpToNextActivity();
                }
                break;
            case R.id.ll_ad_three:
                if (present.positingPosition(2,pre + threeAdTv.getText().toString())) {
                    jumpToNextActivity();
                }
                break;
            case R.id.ll_ad_four:
                if (present.positingPosition(3,pre + fourAdTv.getText().toString())) {
                    jumpToNextActivity();
                }
                break;
            case R.id.ll_ad_five:
                if (present.positingPosition(4,pre + fiveAdTv.getText().toString())) {
                    jumpToNextActivity();
                }
                break;
            case R.id.btn_ad_create_chart:
                if (present.createChart()) {
                    //生成图表成功,跳往该界面
                    Intent intent = new Intent(RecommendAdActivity.this,ContrastChartActivity.class);
                    startActivity(intent);
                }else {
                    StatusToast.getMyToast().ToastShow(RecommendAdActivity.this,null,R.mipmap.ic_sad,"生成图表失败");
                }
                break;
            default:
        }
    }

    private void jumpToNextActivity() {
        Intent intent = new Intent(this,MapDetailActivity.class);
        startActivity(intent);
    }

    /**
     * 确认时操作
     */
    private void config() {
        //显示广告牌位置
        adRl.setVisibility(View.VISIBLE);
        //请求网络访问数据
        request();
        adConfigCancelBtn.setText("刷新");
    }

    /**
     * 刷新时操作
     */
    private void refresh() {
        request();
    }

    private void request() {
        int area;
        int day;
        int time;
        try {
            area = areaStyle.get(adAreaDsv.getSlectedArea());
            day = dayStyle.get(adDayStyleDsv.getSlectedArea());
            time = timeStyle.get(adTimeStyleDsv.getSlectedArea());
            present.handlePosition(area,time,day);
        }catch (NullPointerException e) {
            StatusToast.getMyToast().ToastShow(this,null,R.mipmap.ic_sad,"请填入完整信息");
        }catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }
    }
}
