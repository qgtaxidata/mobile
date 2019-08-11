package com.example.taxidata.ui.TaxiPath;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.bean.TaxiInfo;

import java.util.List;

public class TaxiInfoAdapter extends RecyclerView.Adapter<TaxiInfoAdapter.ViewHolder> {

    private List<TaxiInfo.DataBean> taxiInfoList;
    protected OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View taxiInfoView;
        TextView licenseplatenoTv;

        public ViewHolder(View view) {
            super(view);
            taxiInfoView = view;
            licenseplatenoTv = view.findViewById(R.id.tv_licenseplateno);
        }
    }

    public TaxiInfoAdapter(List<TaxiInfo.DataBean> taxiInfoList){
        this.taxiInfoList = taxiInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taxi_info_recycleview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.taxiInfoView.setOnClickListener(new View.OnClickListener() {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < taxiInfoList.size()){
            TaxiInfo.DataBean taxiInfo = taxiInfoList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.licenseplatenoTv.setText(taxiInfo.getLicenseplateno());
        }
    }

    @Override
    public int getItemCount() {
        return taxiInfoList.size();
    }

    public void seOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
