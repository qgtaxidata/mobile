package com.example.taxidata.ui.passengerpath.view;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.taxidata.R;
import com.example.taxidata.adapter.HistoryAdapter;
import com.example.taxidata.ui.passengerpath.contract.EndContract;
import com.example.taxidata.ui.passengerpath.enity.EndInfo;
import com.example.taxidata.ui.passengerpath.present.EndPresent;
import com.example.taxidata.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EndActivity extends AppCompatActivity implements EndContract.EndView,Inputtips.InputtipsListener, OnItemSwipeListener {

    @BindView(R.id.ibtn_end_path_search_back)
    ImageButton backIBtn;
    @BindView(R.id.et_end_path_search)
    EditText endSearchEt;
    @BindView(R.id.btn_end_search)
    Button endSearchBtn;
    @BindView(R.id.rv_history_end)
    RecyclerView endRv;

    private EndContract.EndPresent present;

    private List<String> endNameList;
    private List<EndInfo> endList;

    private HistoryAdapter adapter;
    private LinearLayoutManager manager;

    private boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        ButterKnife.bind(this);
        present = new EndPresent();
        present.attachView(this);

        endNameList = new ArrayList<>();
        endList = new ArrayList<>();
        initEt();
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        present.showHistory();
    }

    @Override
    public void showList(List<EndInfo> history) {
        //清空数据
        endList.clear();
        endNameList.clear();
        endList.addAll(history);
        for (EndInfo info : endList) {
            endNameList.add(info.getEnd());
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.ibtn_end_path_search_back, R.id.btn_end_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_end_path_search_back:
                finish();
                break;
            case R.id.btn_end_search:
                ToastUtil.showShortToastBottom("请从下面列表中选择一个地址");
                break;
            default:
        }
    }

    private void initEt(){
        endSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    isStop = true;
                    //没有输入时,展示历史记录
                    present.showHistory();
                }else {
                    isStop = false;
                    InputtipsQuery query = new InputtipsQuery(s.toString(),"广州");
                    query.setCityLimit(true);
                    Inputtips tips = new Inputtips(EndActivity.this,query);
                    tips.setInputtipsListener(EndActivity.this);
                    tips.requestInputtipsAsyn();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000){
            List<EndInfo> infoList = new ArrayList<>();
            for (Tip tip : list){
                if (tip.getPoiID() != null) {
                    if (tip.getPoint() != null) {
                        EndInfo info = new EndInfo();
                        info.setLat(tip.getPoint().getLatitude());
                        info.setLng(tip.getPoint().getLongitude());
                        info.setEnd(tip.getName());
                        infoList.add(info);
                    }
                }
            }
            if (isStop) {
                return;
            }
            showList(infoList);
        }
    }

    private void initRecyclerView(){
        adapter = new HistoryAdapter(R.layout.item_passenger_path, endNameList);
        manager = new LinearLayoutManager(this);
        endRv.setLayoutManager(manager);
        endRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_passenger_path:
                        //发送信息给消息展示页面
                        EventBus.getDefault().post(endList.get(position));
                        //保存历史纪录
                        present.saveHistory(endList.get(position));
                        //结束该活动
                        finish();
                        break;
                    default:
                }
            }
        });
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(endRv);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(this);
        //设置空布局
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty_passenger,null));
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        present.delete(endList.get(pos));
        endList.remove(pos);
    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

    }
}
