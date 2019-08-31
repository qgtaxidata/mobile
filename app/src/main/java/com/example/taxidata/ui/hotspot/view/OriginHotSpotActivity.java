package com.example.taxidata.ui.hotspot.view;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.taxidata.HomePageActivity;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.bean.HotSpotRequestInfo;
import com.example.taxidata.bean.HotSpotRouteInfo;
import com.example.taxidata.bean.HotSpotRouteRequest;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.common.eventbus.EventFactory;
import com.example.taxidata.constant.EventBusType;
import com.example.taxidata.ui.hotspot.adapter.HintHotSpotAdapter;
import com.example.taxidata.ui.hotspot.adapter.OriginHotSpotAdapter;
import com.example.taxidata.ui.hotspot.contract.OriginHotSpotContract;
import com.example.taxidata.ui.hotspot.presenter.OriginHotSpotPresenter;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;
import com.example.taxidata.util.EventBusUtils;
import com.example.taxidata.util.ToastUtil;
import com.example.taxidata.widget.EmptyHotSpotHistoryView;
import com.example.taxidata.widget.SimpleLoadingDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OriginHotSpotActivity extends BaseActivity implements OriginHotSpotContract.OriginHotSpotView {

    private static final String TAG = "HotSpotOriginActivity";
    @BindView(R.id.imagebutton_hotspot_origin_back)
    ImageButton imagebuttonBack;
    @BindView(R.id.et_hotspot_origin)
    EditText etHotspotOrigin;
    @BindView(R.id.btn_hotspot_origin)
    Button btnHotspotOrigin;
    @BindView(R.id.rv_hotspot_origin_history)
    RecyclerView rvHotspotOrigin;

    private List<HotSpotOrigin> originList;
    private List<HotSpotHint> hintList;
    private HintHotSpotAdapter hintAdapter;
    private OriginHotSpotAdapter originAdapter;
    private ItemTouchHelper itemTouchHelper;
    private GeocodeSearch geocodeSearch;
    private String intputString;
    private OriginHotSpotPresenter mPresenter = new OriginHotSpotPresenter();
    private HotSpotRouteRequest routeRequest;
    private String originAddressChosen;
    private SimpleLoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_origin);
        ButterKnife.bind(this);
        initData();
        initRecyclerView();
        initOnclickEvent();
        initViews();
        mPresenter.attachView(this);
        //初始化注册EventBus
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
        showHistoryOriginList(mPresenter.getHistoryOriginList());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
    }

    private void initViews() {
        loadingDialog = new SimpleLoadingDialog(this, "正在加载服务器数据", R.drawable.dialog_image_loading);
        geocodeSearch = new GeocodeSearch(this);
        etHotspotOrigin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                intputString = s.toString();
                if( ! "".equals(intputString) && etHotspotOrigin.isFocused()) {
                    //当前输入框有内容,将输入的内容发送请求获取提示列表
                    mPresenter.getHintList(s.toString());
                } else {
                    //当前输入框没有内容,则显示历史消息记录
                    showHistoryOriginList(mPresenter.getHistoryOriginList());
                }
            }
        });

    }

    private void  initData(){
        routeRequest = new HotSpotRouteRequest();
        originList = new ArrayList<>();
        hintList = new ArrayList<>();
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        hintAdapter = new HintHotSpotAdapter(R.layout.item_hotspot_hint, hintList);
        originAdapter = new OriginHotSpotAdapter(R.layout.item_hotspot_origin_history, originList);
        rvHotspotOrigin.setLayoutManager(layoutManager);
        rvHotspotOrigin.setAdapter(originAdapter);
        originAdapter.setEmptyView(new EmptyHotSpotHistoryView(this ,null));
        //初始化列表拖拽和滑动删除
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(originAdapter);
        itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvHotspotOrigin);
        originAdapter.enableSwipeItem();
        originAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        originAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                mPresenter.removeOriginHistory(originAdapter.getItem(pos));
            }
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        });
    }

    private void initOnclickEvent() {
        //返回按钮点击事件
        imagebuttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 搜索页面返回到地图页面？
                finish();
            }
        });
        btnHotspotOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: 将 热点 和 地点 打包 发送给 服务器 请求返回数据
                if("".equals(etHotspotOrigin.getText().toString())) {
                    originAddressChosen  = etHotspotOrigin.getText().toString();
                    if(originAddressChosen.equals(StatusManager.hotSpotChosen)){
                        ToastUtil.showLongToastBottom("所选热点与起点相同，请重新选择");
                    } else {
                        showLoadingDialog();
                        StatusManager.originChosen = originAddressChosen;
                        mPresenter.saveOriginHotSpotHistory(originAddressChosen);
                        mPresenter.convertToLocation(originAddressChosen,routeRequest ,geocodeSearch);
                    }
                }
            }
        });
        originAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Todo：带着 选中的 热点 & 起点信息 ，去 地图页面画出来！
                originAddressChosen = originAdapter.getData().get(position).getHotSpotOriginHistory();
                if (originAddressChosen.equals(StatusManager.hotSpotChosen)) {
                    ToastUtil.showLongToastBottom("所选热点与起点相同，请重新选择");
                } else {
                    showLoadingDialog();
                    StatusManager.originChosen = originAddressChosen;
                    mPresenter.saveOriginHotSpotHistory(originAddressChosen);
                    mPresenter.convertToLocation(originAddressChosen, routeRequest, geocodeSearch);
                }
            }
        });
        hintAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Todo：带着 选中的 热点 & 起点信息 ，去 地图页面画出来！
                originAddressChosen =hintAdapter.getData().get(position).getHotSpotName();
                if (originAddressChosen.equals(StatusManager.hotSpotChosen)) {
                    ToastUtil.showLongToastBottom("所选热点与起点相同，请重新选择");
                } else {
                    showLoadingDialog();
                    StatusManager.originChosen = originAddressChosen;
                    mPresenter.saveOriginHotSpotHistory(originAddressChosen);
                    mPresenter.convertToLocation(originAddressChosen, routeRequest, geocodeSearch);
                }
            }
        });
    }

    @Override
    public void showHistoryOriginList(List<HotSpotOrigin> hotSpotOrigins) {
        if (originAdapter != null && rvHotspotOrigin != null) {
            itemTouchHelper.attachToRecyclerView(rvHotspotOrigin);
            rvHotspotOrigin.setAdapter(originAdapter);
            originAdapter.setNewData(hotSpotOrigins);
        }
    }

    @Override
    public void showHintHotSpotList(List<HotSpotHint> hintList) {
        if (hintAdapter != null && rvHotspotOrigin != null && ! "".equals(intputString)) {
            //输入框不为空，则正常显示提示列表
            itemTouchHelper.attachToRecyclerView(null);
            rvHotspotOrigin.setAdapter(hintAdapter);
            hintAdapter.setNewData(hintList);
            cancelLoadingDialong();
        } else {
            //输入框为空,且适配器非历史适配器则显示历史列表
            if (rvHotspotOrigin != null && rvHotspotOrigin.getAdapter() != originAdapter) {
                showHistoryOriginList(mPresenter.getHistoryOriginList());
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    //注册绑定EventBus
    protected boolean isRegisterEventBus() {
        return true;
    }

    /**
     * 处理Eventbus发过来的事件
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN_ORDERED)
    public void handleEvent(BaseEvent baseEvent) {
        if (baseEvent.type.equals(EventBusType.ORIGIN_HOTSPOT_TO_CHOOSE)) {
            LatLng latLng = (LatLng) baseEvent.object;
            routeRequest.setLonDestination(latLng.longitude);
            routeRequest.setLatDestination(latLng.latitude);
            StatusManager.hotSpotLat = latLng.latitude;
            StatusManager.hotSpotLng = latLng.longitude;
            Log.e(TAG,"接收用户选择的热点（"+latLng.longitude+","+latLng.latitude+") ,准备选择 起点");
        }
    }

    public void showLoadingDialog() {

        loadingDialog.show();
    }

    public void cancelLoadingDialong() {

        loadingDialog.cancel();
    }

    @Override
    public void requestSuccess(PathInfo info) {
        BaseEvent baseEventBothChosen = EventFactory.getInstance();
        baseEventBothChosen.type = EventBusType.ORIGIN_HOTSPOT_BOTH_CHOSEN ;
        baseEventBothChosen.object = info;
        baseEventBothChosen.content = originAddressChosen;
        EventBusUtils.postSticky(baseEventBothChosen);
        //返回热点路径页面
        cancelLoadingDialong();
        finish();
    }

    @Override
    public void requestFailed(int failCode) {
        if(failCode == StatusManager.FAIL_CODE_WRONG_ADDRESS) {
            cancelLoadingDialong();
            ToastUtil.showLongToastBottom("服务器无法正确识别此地址，请稍后重试");
        }
        if(failCode == StatusManager.FAIL_CONNECT_DATA) {
            cancelLoadingDialong();
            ToastUtil.showLongToastBottom("向服务器请求数据出错，请您检查网络");
        }
        if(failCode == StatusManager.FAIL_CODE_NONE_DATA) {
            cancelLoadingDialong();
            ToastUtil.showLongToastBottom("服务器出现异常，暂时无法获取数据");
        }
    }
}
