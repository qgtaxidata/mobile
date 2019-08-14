package com.example.taxidata.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.taxidata.R;
import com.example.taxidata.bean.DriverConditionInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverConditionDialog extends Dialog {

    @BindView(R.id.close_driver_condition_dialog_imv)
    ImageView closeDriverConditionDialogImv;
    @BindView(R.id.driver_condition_number_tv)
    TextView driverConditionNumberTv;
    @BindView(R.id.driver_condition_company_tv)
    TextView driverConditionCompanyTv;
    @BindView(R.id.driver_condition_income_tv)
    TextView driverConditionIncomeTv;
    @BindView(R.id.driver_condition_rank_tv)
    TextView driverConditionRankTv;
    @BindView(R.id.driver_condition_load_mile_tv)
    TextView driverConditionLoadMileTv;
    @BindView(R.id.driver_condition_load_time_tv)
    TextView driverConditionLoadTimeTv;
    @BindView(R.id.driver_condition_no_load_mile_tv)
    TextView driverConditionNoLoadMileTv;
    @BindView(R.id.driver_condition_no_load_time_tv)
    TextView driverConditionNoLoadTimeTv;



    public DriverConditionDialog(@NonNull Context context, int themeResId,DriverConditionInfo.DataBean dataBean,int rank, String driverID, double income) {
        super(context, themeResId);
        driverConditionNumberTv.setText(driverID);
        driverConditionCompanyTv.setText(dataBean.getCompanyID());
        driverConditionIncomeTv.setText((int)income);
        driverConditionRankTv.setText(rank);
        driverConditionLoadMileTv.setText((int) dataBean.getLoad_mile());
        driverConditionLoadTimeTv.setText((int) dataBean.getLoad_time());
        driverConditionNoLoadMileTv.setText((int) dataBean.getNo_load_mile());
        driverConditionNoLoadTimeTv.setText((int) dataBean.getNo_load_time());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_driver_condition);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close_driver_condition_dialog_imv)
    public void onViewClicked() {
        cancel();
    }
}
