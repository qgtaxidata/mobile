package com.example.taxidata.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.bean.TaxiInfo;

import java.util.List;

public class TaxiChooseAdapter extends RecyclerView.Adapter<TaxiChooseAdapter.ViewHolder> {

    private List<TaxiInfo.DataBean> taxiInfoList;
    protected OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View taxiInfoView;
        CheckBox licenseplatenoCkb;
        TextView licenseplatenoTv;

        public ViewHolder(View view) {
            super(view);
            taxiInfoView = view;
            licenseplatenoCkb = view.findViewById(R.id.ckb_item_choose_taxi_licenseplateno);
            licenseplatenoTv = view.findViewById(R.id.tv_item_choose_taxi_licenseplateno);
        }
    }

    public TaxiChooseAdapter(List<TaxiInfo.DataBean> taxiInfoList){
        this.taxiInfoList = taxiInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_taxi, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.licenseplatenoCkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaxiChooseAdapter.ViewHolder holder, int position) {
        if (position < taxiInfoList.size()){
            TaxiInfo.DataBean taxiInfo = taxiInfoList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.licenseplatenoTv.setText(taxiInfo.getLicenseplateno());
            viewHolder.licenseplatenoCkb.setChecked(taxiInfoList.get(position).isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return taxiInfoList.size();
    }

    public void seOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
