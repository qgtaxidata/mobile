package com.example.taxidata.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.ui.TaxiPath.TaxiPathActivity;

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
    private Context mContext;
    private TaxiChooseAdapter adapter;
    private List<TaxiInfo.DataBean> taxiInfoList = new ArrayList<>();
    GetTaxiPathInfo pathInfo = new GetTaxiPathInfo();
    private int currentNum = -1;
    private String time ;
    private String licenseplateno;

    public ChooseTaxiDialog(@NonNull Context context) {
        super(context);
    }

    public ChooseTaxiDialog(@NonNull Context context, int themeResId,TaxiInfo taxiInfo) {
        super(context, themeResId);
        mContext = context;
        taxiInfoList.addAll(taxiInfo.getData());
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
                if(pathInfo.getTime()!=null&&pathInfo.getLicenseplateno()!=null){
                    EventBus.getDefault().post(pathInfo);
                    dismiss();
                }else {
                    StatusToast.getMyToast().ToastShow(mContext,null,R.mipmap.ic_sad,"请选择车牌号。");
                }
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
        adapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (TaxiInfo.DataBean dataBean : taxiInfoList){
                    dataBean.setSelected(false);
                }
                if(currentNum == -1){
                    //选中
                    taxiInfoList.get(position).setSelected(true);
                    currentNum = position;
                }else if(currentNum == position){
                    //同一个item选中后取消
                    for (TaxiInfo.DataBean dataBean : taxiInfoList){
                        dataBean.setSelected(false);
                    }
                    currentNum = -1;
                }else {
                    //与上次选中的item不同（即更换选择）
                    for (TaxiInfo.DataBean dataBean : taxiInfoList){
                        dataBean.setSelected(false);
                    }
                    taxiInfoList.get(position).setSelected(true);
                    currentNum = position;
                }
                adapter.notifyDataSetChanged();
                licenseplateno = taxiInfoList.get(position).getLicenseplateno();
                time = taxiInfoList.get(position).getTime();
                pathInfo.setTime(time);
                pathInfo.setLicenseplateno(licenseplateno);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
