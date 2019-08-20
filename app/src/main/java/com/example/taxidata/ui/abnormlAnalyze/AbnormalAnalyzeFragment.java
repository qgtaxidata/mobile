package com.example.taxidata.ui.abnormlAnalyze;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taxidata.R;
import com.example.taxidata.adapter.OnItemClickListener;
import com.example.taxidata.base.BaseFragment;
import com.example.taxidata.constant.Area;
import com.example.taxidata.widget.DropDownSelectView;
import com.example.taxidata.widget.SimpleLoadingDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.taxidata.constant.Area.BAI_YUN;
import static com.example.taxidata.constant.Area.CONG_HUA;
import static com.example.taxidata.constant.Area.GUANG_ZHOU;
import static com.example.taxidata.constant.Area.HAI_ZHU;
import static com.example.taxidata.constant.Area.HUANG_PU;
import static com.example.taxidata.constant.Area.HUA_DU;
import static com.example.taxidata.constant.Area.LI_WAN;
import static com.example.taxidata.constant.Area.NAN_SHA;
import static com.example.taxidata.constant.Area.PAN_YU;
import static com.example.taxidata.constant.Area.TIAN_HE;
import static com.example.taxidata.constant.Area.YUE_XIU;
import static com.example.taxidata.constant.Area.ZENG_CHENG;

/**
 * @author: ODM
 * @date: 2019/8/20
 */
public class AbnormalAnalyzeFragment extends BaseFragment {


    @BindView(R.id.btn_refresh_abnormal_analyze)
    Button btnRefresh;
    @BindView(R.id.select_area_abnormal_analyze)
    DropDownSelectView selectViewArea;
    @BindView(R.id.tv_abnormal_analyze_summary)
    TextView tvSummary;
    @BindView(R.id.tv_abnormal_detail)
    TextView tvDetail;
    @BindView(R.id.chart_abnormal_detail)
    LineChart chartDetail;
    @BindView(R.id.chart_abnormal_analyze_summary)
    PieChart chartSummary;

    ArrayList<String> areaList = new ArrayList<>();
    SimpleLoadingDialog loading;
    private XAxis xAxis;
    private YAxis yAxis;
    private int areaId = 0 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abnormal_analyze, container, false);
        ButterKnife.bind(this, view);
        initAreaList();
        initViews();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    public void initViews() {
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        selectViewArea.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Logger.d("用户选择了"+areaList.get(position));
                areaId = Area.area.get(areaList.get(position));
            }
        });

    }

    //初始化区域popupWindow
    private void initAreaList() {
        areaList.add(GUANG_ZHOU);
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
        selectViewArea.setItemsData(areaList, 7);
    }


}
