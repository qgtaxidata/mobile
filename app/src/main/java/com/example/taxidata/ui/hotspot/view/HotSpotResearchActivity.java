package com.example.taxidata.ui.hotspot.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;
import com.example.taxidata.bean.HotSpotOrigin;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.ui.hotspot.adapter.HintHotSpotAdapter;
import com.example.taxidata.ui.hotspot.adapter.HistoryHotspotSearchAdapter;
import com.example.taxidata.ui.hotspot.adapter.OriginHotSpotAdapter;
import com.example.taxidata.ui.hotspot.adapter.RecommandHotSpotAdapter;
import com.example.taxidata.ui.hotspot.contract.HotSpotContract;
import com.example.taxidata.ui.hotspot.presenter.HotSpotPresenter;
import com.example.taxidata.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class HotSpotResearchActivity extends BaseActivity implements HotSpotContract.HotSpotView ,GeocodeSearch.OnGeocodeSearchListener {

    //    @BindView(R.id.imagebutton_hotspot_search_back)
//    ImageButton btnSearchBack;
//    @BindView(R.id.et_hotspot_search)
//    EditText etSearch;
//    @BindView(R.id.btn_hotspot_search)
//    Button btnSearch;
//    @BindView(R.id.rv_hotspot_search_history)
//    RecyclerView rvSearch;
    private static final String TAG = "HotSpotResearchFragment";
    @BindView(R.id.imagebutton_hotspot_search_back_test)
    ImageButton imagebuttonHotspotSearchBackTest;
    @BindView(R.id.et_hotspot_search_test)
    EditText etHotspotSearchTest;
    @BindView(R.id.btn_hotspot_search_test)
    Button btnHotspotSearchTest;
    @BindView(R.id.rv_hotspot_search_history_test)
    RecyclerView rvHotspotSearchHistoryTest;
    private HotSpotPresenter mPresenter = new HotSpotPresenter();
    private List<HotSpotHistorySearch> historyList;
    private List<HotSpotHint> hintList;
    private List<HotSpotCallBackInfo.DataBean> hotSpotList;
    private List<HotSpotOrigin> originList;
    private HistoryHotspotSearchAdapter historyAdapter;
    private HintHotSpotAdapter hintAdapter;
    private RecommandHotSpotAdapter recommandAdapter;
    private OriginHotSpotAdapter originAdapter;
    private GeocodeSearch geocodeSearch ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_search_test);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initData();
        initRecyclerView();
        initOnclickEvent();
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        rvHotspotSearchHistoryTest.setAdapter(historyAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (StatusManager.hotSpotChosen) {
            //热点已经选定了
            etHotspotSearchTest.setHint("请输入起点");
            showHistoryOriginList(mPresenter.getHistoryOriginList());
        } else {
            //热点尚未选定
            showHistorySearchList(mPresenter.getHistorySearchList());
            etHotspotSearchTest.setHint("请输入地点");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StatusManager.hotSpotChosen = false;
    }

    public void initData() {
        hintList = new ArrayList<>();
        historyList = new ArrayList<>();
        hotSpotList = new ArrayList<>();
        originList = new ArrayList<>();
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        historyAdapter = new HistoryHotspotSearchAdapter(R.layout.item_hotspot_history, historyList);
        hintAdapter = new HintHotSpotAdapter(R.layout.item_hotspot_hint, hintList);
        recommandAdapter = new RecommandHotSpotAdapter(R.layout.item_hotspot_recommand, hotSpotList);
        recommandAdapter.setEmptyView(new EmptyHotSpotView(this, null));
        originAdapter = new OriginHotSpotAdapter(R.layout.item_hotspot_origin_history, originList);
        rvHotspotSearchHistoryTest.setLayoutManager(layoutManager);
        //搜索页面默认出现搜索历史列表
    }

    public void initOnclickEvent() {
        imagebuttonHotspotSearchBackTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 搜索页面返回到地图页面？
                finish();
            }
        });
        btnHotspotSearchTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Todo 将输入框的内容发送到P -》M ，请求获取热点数据
                String inputAddress = etHotspotSearchTest.getText().toString();
                //将用户输入的地址转换为坐标
                if (mPresenter != null && !"".equals(inputAddress)) {
                    convertToLocation(inputAddress );
                }
                if(mPresenter != null ) {
                    if ( StatusManager.hotSpotChosen) {
                        mPresenter.saveOriginHotSpotHistory(inputAddress);
                    } else {
                        //保存热点搜索地点，热点选定状态->true
                        mPresenter.saveHotSpotSearchHistory(inputAddress);
                        StatusManager.hotSpotChosen = true;
                    }
                }
            }
        });
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String address = historyAdapter.getData().get(position).getHotSpotHistory();
                convertToLocation(address);
            }
        });
        hintAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String address = hintAdapter.getData().get(position).getHotSpotLocation();
                convertToLocation(address );
            }
        });
        recommandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Todo：带着 选中的 热点的信息，去到 中间页面
            }
        });
        originAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Todo：带着 选中的 热点 & 起点信息 ，去 地图页面画出来！
            }
        });


    }


    @Override
    public void showHotSpot(List<HotSpotCallBackInfo.DataBean> hotSpotCallBackInfoList) {

        if (recommandAdapter != null && rvHotspotSearchHistoryTest != null) {
            hotSpotList.clear();
            recommandAdapter.setNewData(hotSpotCallBackInfoList);
            rvHotspotSearchHistoryTest.swapAdapter(recommandAdapter, true);
        }
    }

    @Override
    public void showHistorySearchList(List<HotSpotHistorySearch> hotSpotHistorySearchList) {
        if (historyAdapter != null && rvHotspotSearchHistoryTest != null) {
            historyAdapter.setNewData(hotSpotHistorySearchList);
            rvHotspotSearchHistoryTest.swapAdapter(historyAdapter, true);
        }
    }

    @Override
    public void showHintHotSpotList(List<HotSpotHint> hintList) {
        if (hintAdapter != null && rvHotspotSearchHistoryTest != null) {
            hintAdapter.setNewData(hintList);
            rvHotspotSearchHistoryTest.swapAdapter(hintAdapter, true);
        }
    }


    @Override
    public void showHistoryOriginList(List<HotSpotOrigin> hotSpotOrigins) {
        if (originAdapter != null && rvHotspotSearchHistoryTest != null) {
            originAdapter.setNewData(hotSpotOrigins);
            rvHotspotSearchHistoryTest.swapAdapter(originAdapter, true);
        }
    }

    @Override
    public void convertToLocation(String address) {
        GeocodeQuery query = new GeocodeQuery(address, "广州");
        geocodeSearch.getFromLocationNameAsyn(query);
        Log.e(TAG, "convertToLocation: 准备中文转换坐标" );
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
    }
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
        LatLonPoint point = geocodeAddress.getLatLonPoint();
        double inputLatitude = point.getLatitude();
        double inputLongitude = point.getLongitude();
        Log.e(TAG, "onGeocodeSearched: "+ "longtitude : " + inputLongitude + "   latitude:  " +inputLatitude );
        mPresenter.getHotSpotData(inputLongitude,inputLatitude ,"");
    }
}
