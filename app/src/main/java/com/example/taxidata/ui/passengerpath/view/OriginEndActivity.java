package com.example.taxidata.ui.passengerpath.view;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.taxidata.R;
import com.example.taxidata.adapter.HistoryAdapter;
import com.example.taxidata.common.MakerFactory;
import com.example.taxidata.ui.passengerpath.contract.OriginEndShowContract;
import com.example.taxidata.ui.passengerpath.enity.EndInfo;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.enity.OriginInfo;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;
import com.example.taxidata.ui.passengerpath.enity.TimeDistance;
import com.example.taxidata.ui.passengerpath.present.OriginEndShowPresent;
import com.example.taxidata.ui.passengerpath.widget.DoubleSearch;
import com.example.taxidata.ui.passengerpath.widget.SearchOnClickListener;
import com.example.taxidata.ui.passengerpath.widget.SelectPlanCard;
import com.example.taxidata.ui.passengerpath.widget.SelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OriginEndActivity extends AppCompatActivity implements OriginEndShowContract.OriginEndShowView, OnItemSwipeListener {

    private OriginEndShowContract.OriginEndShowPresent present;
    private List<String> historyList = new ArrayList<>();
    private List<OriginEndInfo> detailHistoryList = new ArrayList<>();
    @BindView(R.id.ds_origin_end)
    DoubleSearch originEndSearchDs;
    @BindView(R.id.rv_history_origin_end)
    RecyclerView historyRv;
    @BindView(R.id.mv_passenger_path)
    MapView pathMp;
    @BindView(R.id.ll_history_origin_end)
    LinearLayout originEndLl;
    @BindView(R.id.cv_search)
    CardView searchCv;
    @BindView(R.id.cv_history_origin_end)
    CardView historyCv;
    @BindView(R.id.spc_plane)
    SelectPlanCard planSpc;
    private LinearLayoutManager manager;
    private HistoryAdapter adapter;
    /**
     * 当前已输入的信息
     */
    private OriginEndInfo nowInputInfo = new OriginEndInfo();
    /**
     * 起始输入框是否已输入内容
     */
    private boolean isOriginHaveContent = false;

    /**
     * 终点输入框是否已有内容
     */
    private boolean isEndHaveContent = false;

    private AMap pathMap;

    private UiSettings settings;

    private List<PathInfo.DataBean> plan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin_end);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        pathMp.onCreate(savedInstanceState);
        initMapView();

        //创建present
        present = new OriginEndShowPresent();
        //绑定view
        present.attachView(this);
        //初始化rv
        initRecyclerView();
        //搜索框点击事件
        originEndSearchDs.setBackListener(new SearchOnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        originEndSearchDs.setOriginListener(new SearchOnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(OriginEndActivity.this, OriginActivity.class);
                startActivity(intent);
            }
        });
        originEndSearchDs.setEndListener(new SearchOnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(OriginEndActivity.this, EndActivity.class);
                startActivity(intent);
            }
        });
        planSpc.setListener(new SelectedListener() {
            @Override
            public void select(int position) {
                //处于当前位置,则不设置选择事件
                if (planSpc.getStatus() == position) {
                    return;
                } else {
                    List<LatLng> latLngs = new ArrayList<>();
                    PathInfo.DataBean onePlan = plan.get(position);
                    List<PathInfo.DataBean.RouteBean> route = onePlan.getRoute();
                    for (int i = 0; i < route.size(); i++) {
                        //获取经度
                        double lng = route.get(i).getLng();
                        //获取纬度
                        double lat = route.get(i).getLat();
                        latLngs.add(new LatLng(lat,lng));
                    }
                    showRoad(latLngs);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化历史列表
        present.getHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pathMp.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pathMp.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        pathMp.onSaveInstanceState(outState);
    }

    private void initMapView() {
        if (pathMap == null) {
            pathMap = pathMp.getMap();
        }
        settings = pathMap.getUiSettings();
        settings.setZoomControlsEnabled(false);
    }

    /**
     * 初始化rv
     */
    private void initRecyclerView() {
        adapter = new HistoryAdapter(R.layout.item_passenger_path, historyList);
        manager = new LinearLayoutManager(this);
        historyRv.setLayoutManager(manager);
        historyRv.setAdapter(adapter);
        //开启滑动删除
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(historyRv);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //输入框填充信息
                OriginEndInfo info = detailHistoryList.get(position);
                originEndSearchDs.setOriginText(info.getOrigin());
                originEndSearchDs.setEndText(info.getEnd());
                //当前信息指向改历史记录
                nowInputInfo = info;
                //发送网络请求
                drawByNowInputInfo();
            }
        });
        //设置空布局
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty_passenger,null));
    }

    @Override
    public void showHistory(List<OriginEndInfo> history) {
        //清理数据源
        historyList.clear();
        detailHistoryList.clear();
        detailHistoryList.addAll(history);
        for (OriginEndInfo info : detailHistoryList) {
            historyList.add(info.getOrigin() + " —— " + info.getEnd());
        }
        //刷新
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPath(List<PathInfo.DataBean> pathInfos) {
        plan.clear();
        plan.addAll(pathInfos);
        List<TimeDistance> timeDistances = new ArrayList<>();
        for (int i = 0; i < plan.size(); i++) {
            TimeDistance timeDistance = new TimeDistance();
            timeDistance.setTime(pathInfos.get(i).getTime());
            timeDistance.setDistance(pathInfos.get(i).getDistance());
            timeDistances.add(timeDistance);
        }
        planSpc.init(timeDistances);
        List<LatLng> latlngs = new ArrayList<>();
        //默认选择第一条路线
        List<PathInfo.DataBean.RouteBean> one = pathInfos.get(0).getRoute();
        for (int i = 0; i < one.size(); i++) {
            //获取经度
            double lng = one.get(i).getLng();
            //获取纬度
            double lat = one.get(i).getLat();
            latlngs.add(new LatLng(lat,lng));
        }
        showRoad(latlngs);
    }

    private void showRoad(List<LatLng> paths) {
        //清空地图状态
        pathMap.clear();
        //打起点标记
        LatLng origin = new LatLng(nowInputInfo.getOriginLat(),nowInputInfo.getOriginLng());
        pathMap.addMarker(MakerFactory.create(MakerFactory.CONST_ORIGIN,origin));
        //打终点标记
        LatLng end = new LatLng(nowInputInfo.getEndLat(),nowInputInfo.getEndLng());
        pathMap.addMarker(MakerFactory.create(MakerFactory.CONST_END,end));
        moveTo(origin);
        for (LatLng temp : paths) {
            Log.d("showRoad","latitude:" + temp.latitude + "lng:" + temp.longitude);
        }
        pathMap.addPolyline(new PolylineOptions().addAll(paths).width(10).color(Color.parseColor("#51B46D")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detachView();
        EventBus.getDefault().unregister(this);
        pathMp.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleOrigin(OriginInfo info) {
        isOriginHaveContent = true;
        //输入框显示信息
        originEndSearchDs.setOriginText(info.getOrigin());
        //拼凑信息
        nowInputInfo.setOrigin(info.getOrigin());
        nowInputInfo.setOriginLat(info.getLat());
        nowInputInfo.setOriginLng(info.getLng());
        if (isEndHaveContent) {
            handleOriginEnd();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEnd(EndInfo info) {
        isEndHaveContent = true;
        //输入框显示信息
        originEndSearchDs.setEndText(info.getEnd());
        //拼凑信息
        nowInputInfo.setEnd(info.getEnd());
        nowInputInfo.setEndLat(info.getLat());
        nowInputInfo.setEndLng(info.getLng());
        if (isOriginHaveContent) {
            //TODO 数据库存储历史记录
            //TODO 画图
            handleOriginEnd();
        }
    }

    private void handleOriginEnd() {
        //进行数据库存储
        present.saveHistory(nowInputInfo);
        drawByNowInputInfo();
    }

    /**
     * 根据当前输入的信息画图,并定位
     */
    private void drawByNowInputInfo() {
        originEndLl.setBackgroundColor(Color.TRANSPARENT);
        historyCv.setVisibility(View.GONE);
        planSpc.setVisibility(View.VISIBLE);
        //把当前输入框内的起始点进行网络请求
        present.managePath(nowInputInfo);
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        //删除历史记录
        present.deleteHistory(detailHistoryList.get(pos));
        //从容器中移除
        detailHistoryList.remove(pos);
    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

    }

    private void moveTo(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,14,0,0));
        pathMap.moveCamera(update);
    }
}
