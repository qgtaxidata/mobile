package com.example.taxidata.ui.TaxiDriverIncome;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taxidata.R;
import com.example.taxidata.widget.DropDownSelectView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
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

public class IncomeActivity extends AppCompatActivity {

    @BindView(R.id.income_ranking_area_select_view)
    DropDownSelectView areaSelectView;
    @BindView(R.id.income_ranking_time_select_view)
    DropDownSelectView timeSelectView;
    @BindView(R.id.income_ranking_btn_refresh_list)
    Button btnRefreshList;
    @BindView(R.id.income_ranking_recycle_view)
    RecyclerView taxiIncomeRecycleView;
    ArrayList<String> areaList = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_ranking);
        ButterKnife.bind(this);
        initAreaList();
        initTimeList();
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
        areaSelectView.setItemsData(areaList, 1);
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
            timeSelectView.setItemsData(timeList, 2);
        }


}
