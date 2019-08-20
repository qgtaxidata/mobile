package com.example.taxidata.ui.abnormlAnalyze;

import android.graphics.Color;
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
import com.example.taxidata.widget.DropDownSelectView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: ODM
 * @date: 2019/8/20
 */
public class AbnormalDistributionFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.btn_refresh_abnormal_analyze)
    Button btnRefresh;
    @BindView(R.id.select_area_abnormal_analyze)
    DropDownSelectView selectViewArea;
    @BindView(R.id.tv_abnormal_distribution_summary)
    TextView tvSummary;
    @BindView(R.id.chart_abnormal_distribution_summary)
    LineChart chartSummary;

    ArrayList<String> carNumberList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abnormal_distribution, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        initCarNumberList();
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
                 Logger.d("用户点击了这辆车" + carNumberList.get(position) );
             }
         });


    }

    //初始化时间popupWindow
    private void initCarNumberList() {
        carNumberList.add("粤A666666");
        carNumberList.add("粤B777777");
        selectViewArea.setItemsData(carNumberList, 8);
    }

    //初始化折线
    private void initLineDataSet(LineDataSet lineDataSet, String color) {
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(Color.parseColor(color));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleColor(Color.parseColor(color));
        lineDataSet.setCircleRadius(1f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawCircleHole(false);    //设置曲线值的圆点是实心还是空心
        lineDataSet.setValueTextSize(20f);
        lineDataSet.setValueTextColor(Color.parseColor(color));
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
    }
}
