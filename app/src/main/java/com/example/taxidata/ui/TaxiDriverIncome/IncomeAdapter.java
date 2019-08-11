package com.example.taxidata.ui.TaxiDriverIncome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.ui.TaxiPath.OnItemClickListener;
import com.example.taxidata.ui.TaxiPath.TaxiInfoAdapter;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    private List<TaxiInfo.DataBean> incomeList;
    protected OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View incomeView;
        TextView rankingTv;
        TextView licenseplatenoTv;
        TextView incomeTv;

        public ViewHolder(View view) {
            super(view);
            incomeView = view;
            rankingTv = view.findViewById(R.id.tv_item_ranking);
            licenseplatenoTv = view.findViewById(R.id.tv_item_licenseplateno);
            incomeTv = view.findViewById(R.id.tv_item_income);
        }
    }

    public IncomeAdapter(List<TaxiInfo.DataBean> incomeList){
        this.incomeList = incomeList;
    }

    @NonNull
    @Override
    public IncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taxi_info_recycleview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.ViewHolder holder, int position) {
        if (position < incomeList.size()){
            //为各项进行赋值
        }
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

}
