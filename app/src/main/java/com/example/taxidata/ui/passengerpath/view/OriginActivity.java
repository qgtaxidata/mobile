package com.example.taxidata.ui.passengerpath.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxidata.R;
import com.example.taxidata.adapter.HistoryAdapter;
import com.example.taxidata.ui.passengerpath.contract.OriginContract;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OriginActivity extends AppCompatActivity implements OriginContract.OriginView {

    @BindView(R.id.ibtn_origin_path_search_back)
    ImageButton backIbtn;
    @BindView(R.id.et_origin_path_search)
    EditText originPointEt;
    @BindView(R.id.btn_origin_search)
    Button originSearchBtn;
    @BindView(R.id.rv_history_origin)
    RecyclerView originHistoryRv;

    private HistoryAdapter adapter;
    private LinearLayoutManager manager;
    private List<String> histroyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        ButterKnife.bind(this);
        histroyList = new ArrayList<>();
        initRecyclerView();
    }

    /**
     * 初始化滑动组件
     */
    private void initRecyclerView(){
        adapter = new HistoryAdapter(R.layout.item_hotspot_history,histroyList);
        manager = new LinearLayoutManager(this);
        originHistoryRv.setLayoutManager(manager);
        originHistoryRv.setAdapter(adapter);
    }

    @Override
    public void showList(List<String> history) {
        if (histroyList != null){
            histroyList.clear();
            histroyList.addAll(history);
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.ibtn_origin_path_search_back, R.id.btn_origin_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_origin_path_search_back:
                finish();
                break;
            case R.id.btn_origin_search:
                break;
            default:
        }
    }
}
