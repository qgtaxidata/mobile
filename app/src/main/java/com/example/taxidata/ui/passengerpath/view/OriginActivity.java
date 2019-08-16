package com.example.taxidata.ui.passengerpath.view;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.taxidata.ui.passengerpath.contract.OriginContract;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;
import com.example.taxidata.ui.passengerpath.present.OriginPresent;
import com.example.taxidata.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OriginActivity extends AppCompatActivity implements OriginContract.OriginView,Inputtips.InputtipsListener, OnItemSwipeListener {

    @BindView(R.id.ibtn_origin_path_search_back)
    ImageButton backIbtn;
    @BindView(R.id.et_origin_path_search)
    EditText originPointEt;
    @BindView(R.id.btn_origin_search)
    Button originSearchBtn;
    @BindView(R.id.rv_history_origin)
    RecyclerView originHistoryRv;

    OriginContract.OriginPresent present;

    private boolean isStop;

    private HistoryAdapter adapter;
    private LinearLayoutManager manager;
    private List<OriginInfo> originList = new ArrayList<>();
    private List<String> originNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        ButterKnife.bind(this);
        present = new OriginPresent();
        present.attachView(this);
        originNameList = new ArrayList<>();
        initRecyclerView();
        initEt();
    }

    @Override
    protected void onStart() {
        super.onStart();
        present.showHistory();
    }

    /**
     * 初始化滑动组件
     */
    private void initRecyclerView(){
        adapter = new HistoryAdapter(R.layout.item_passenger_path, originNameList);
        manager = new LinearLayoutManager(this);
        originHistoryRv.setLayoutManager(manager);
        originHistoryRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_passenger_path:
                        //发送信息给消息展示页面
                        EventBus.getDefault().post(originList.get(position));
                        //保存历史纪录
                        present.saveHistory(originList.get(position));
                        //结束该活动
                        finish();
                        break;
                    default:
                }
            }
        });
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(originHistoryRv);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(this);
    }

    private void initEt(){
        originPointEt.addTextChangedListener(new TextWatcher() {
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
                    Inputtips tips = new Inputtips(OriginActivity.this,query);
                    tips.setInputtipsListener(OriginActivity.this);
                    tips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void showList(List<OriginInfo> history) {
        if (originList != null){
            //同步清空
            originList.clear();
            originNameList.clear();
            originList.addAll(history);
            //转化为字符串数组
            for (OriginInfo temp : originList){
                originNameList.add(temp.getOrigin());
            }
            //通知适配器更新
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
                ToastUtil.showShortToastBottom("请在下面列表中选择一个地址");
                break;
            default:
        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == 1000){
            List<OriginInfo> infoList = new ArrayList<>();
            for (Tip tip : list){
                if (tip.getPoiID() != null) {
                    if (tip.getPoint() != null) {
                        OriginInfo info = new OriginInfo();
                        info.setLat(tip.getPoint().getLatitude());
                        info.setLng(tip.getPoint().getLongitude());
                        info.setOrigin(tip.getName());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
        Log.d("OriginActivity","被移除前:" + pos);
    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        Log.d("OriginActivity","滑动时:" + pos);
        present.delete(originList.get(pos));
        originList.remove(pos);
    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

    }
}
