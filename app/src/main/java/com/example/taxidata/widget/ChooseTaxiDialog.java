package com.example.taxidata.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.adapter.TaxiChooseAdapter;
import com.example.taxidata.adapter.TaxiOnClickListener;
import com.example.taxidata.bean.GetTaxiPathInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.adapter.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseTaxiDialog extends Dialog {
    @BindView(R.id.choose_taxi_recyclerv)
    RecyclerView chooseTaxiRecyclerV;
    @BindView(R.id.choose_taxi_cancel)
    Button chooseTaxiCancel;
    @BindView(R.id.choose_taxi_sure)
    Button chooseTaxiSure;

    private TaxiOnClickListener listener;
    private TaxiChooseAdapter adapter;
    private List<TaxiInfo.DataBean> taxiInfoList = new ArrayList<>();
    GetTaxiPathInfo pathInfo = new GetTaxiPathInfo();

    public ChooseTaxiDialog(@NonNull Context context) {
        super(context);
    }

    public ChooseTaxiDialog(@NonNull Context context, int themeResId, List<TaxiInfo.DataBean> taxiList) {
        super(context, themeResId);
        taxiInfoList.addAll(taxiList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_taxi);
        ButterKnife.bind(this);
        initList();
    }

    @OnClick({R.id.choose_taxi_cancel, R.id.choose_taxi_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_taxi_cancel:
                cancel();
                break;
            case R.id.choose_taxi_sure:
                Log.d("wxD", "wx");
                EventBus.getDefault().post(pathInfo);
                dismiss();
                break;
        }
    }

    public void setListener(TaxiOnClickListener listener) {
        this.listener = listener;
    }

    private void initList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chooseTaxiRecyclerV.setLayoutManager(layoutManager);
        adapter = new TaxiChooseAdapter(taxiInfoList);
        chooseTaxiRecyclerV.setAdapter(adapter);
        Log.d("wxD1", "wx");
        adapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                taxiInfoList.get(position).setSelected(true);
                adapter.notifyDataSetChanged();
                String licenseplateno = taxiInfoList.get(position).getLicenseplateno();
                String time = taxiInfoList.get(position).getTime();
                pathInfo.setTime(time);
                pathInfo.setLicenseplateno(licenseplateno);
                Log.d("wxD2", time);
            }
        });
    }
}
