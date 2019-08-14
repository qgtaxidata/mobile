package com.example.taxidata.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.bean.TaxiInfo;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    private List<IncomeRankingInfo.DataBean> incomeList;
    protected OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View incomeView;
        TextView rankingTv;
        TextView numberTv;
        TextView incomeTv;

        public ViewHolder(View view) {
            super(view);
            incomeView = view;
            rankingTv = view.findViewById(R.id.tv_item_ranking);
            numberTv = view.findViewById(R.id.tv_item_driver_number);
            incomeTv = view.findViewById(R.id.tv_item_income);
        }
    }

    public IncomeAdapter(List<IncomeRankingInfo.DataBean> incomeList){
        this.incomeList = incomeList;
    }

    @NonNull
    @Override
    public IncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taxi_income, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.numberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.ViewHolder holder, int position) {
        if (position < incomeList.size()){
            //为各项进行赋值
            IncomeRankingInfo.DataBean incomeRankingInfo = incomeList.get(position);
            ViewHolder viewHolder = holder;
            Log.d("adapter", incomeRankingInfo.getDriverID());
            viewHolder.rankingTv.setText(incomeRankingInfo.getRank());
            viewHolder.numberTv.setText(incomeRankingInfo.getDriverID());
            viewHolder.incomeTv.setText((int)incomeRankingInfo.getIncome());
        }
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public void seOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
