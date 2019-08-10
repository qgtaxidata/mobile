package com.example.taxidata.ui.TaxiPath;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.bean.TaxiInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TaxiInfoFragment extends Fragment implements TaxiPathContract.TaxiPathView {

    private TaxiPathContract.TaxiPathPresent taxiPathPresent;
    private RecyclerView taxiInfoRecyclerV;
    private TaxiInfoAdapter adapter;
    private List<TaxiInfo.DataBean> taxiInfoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.taxi_info_fragment, container, false);
        taxiInfoRecyclerV = view.findViewById(R.id.taxi_info_recycle_view);
        taxiPathPresent.attachView(this);
        Intent intent = getActivity().getIntent();
        List<TaxiInfo.DataBean> info = (List<TaxiInfo.DataBean>) intent.getSerializableExtra("taxiInfo");
        taxiInfoList.addAll(info);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        taxiInfoRecyclerV.setLayoutManager(layoutManager);
        adapter = new TaxiInfoAdapter(taxiInfoList);
        taxiInfoRecyclerV.setAdapter(adapter);
        adapter.seOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String licenseplateno = taxiInfoList.get(position).getLicenseplateno();
            }
        });
        return view;
    }
}
