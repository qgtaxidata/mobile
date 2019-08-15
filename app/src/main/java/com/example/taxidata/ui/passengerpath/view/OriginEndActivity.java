package com.example.taxidata.ui.passengerpath.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.adapter.HistoryAdapter;
import com.example.taxidata.ui.passengerpath.contract.OriginEndShowContract;
import com.example.taxidata.ui.passengerpath.present.OriginEndShowPresent;
import com.example.taxidata.ui.passengerpath.widget.DoubleSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OriginEndActivity extends AppCompatActivity implements OriginEndShowContract.OriginEndShowView {

    OriginEndShowContract.OriginEndShowPresent present;
    List<String> historyList = new ArrayList<>();
    @BindView(R.id.ds_origin_end)
    DoubleSearch originEndSearchDs;
    @BindView(R.id.rv_history_origin_end)
    RecyclerView historyRv;
    LinearLayoutManager manager;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin_end);
        ButterKnife.bind(this);

        //创建present
        present = new OriginEndShowPresent();
        //绑定view
        present.attachView(this);
        //初始化rv
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化历史列表
        present.getHistory();
    }

    /**
     * 初始化rv
     */
    private void initRecyclerView(){
        adapter = new HistoryAdapter(R.layout.item_hotspot_history,historyList);
        manager = new LinearLayoutManager(this);
        historyRv.setLayoutManager(manager);
        historyRv.setAdapter(adapter);
    }

    @Override
    public void showHistory(List<String> history) {
        //清理数据源
        historyList.clear();
        //添加加载数据
        historyList.addAll(history);
        //刷新
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPath() {
        //TODO 未设计
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }
}
