package com.example.taxidata.ui.TaxiPath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.TaxiInfo;

import java.util.ArrayList;
import java.util.List;

public class TaxiInfoListActivity extends BaseActivity implements TaxiPathContract.TaxiPathView {

    private TaxiPathContract.TaxiPathPresent taxiPathPresent;
    private RecyclerView taxiInfoRecyclerV;
    private TaxiInfoAdapter adapter;
    private List<TaxiInfo.DataBean> taxiInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_info_list);
        taxiInfoRecyclerV = findViewById(R.id.taxi_info_recycle_view);
        taxiPathPresent = new TaxiPathPresent();
        taxiPathPresent.attachView(this);
        Intent intent =getIntent();
        List<TaxiInfo.DataBean> info = (List<TaxiInfo.DataBean>) intent.getSerializableExtra("taxiInfo");
        taxiInfoList.addAll(info);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        taxiInfoRecyclerV.setLayoutManager(layoutManager);
        adapter = new TaxiInfoAdapter(taxiInfoList);
        taxiInfoRecyclerV.setAdapter(adapter);
        adapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String licenseplateno = taxiInfoList.get(position).getLicenseplateno();
                String time = taxiInfoList.get(position).getTime();
                taxiPathPresent.getTaxiPathInfo(time, licenseplateno);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        taxiPathPresent.detachView();
    }

    @Override
    public void showPath(List<TaxiInfo.DataBean> listInfo) {

    }

}

