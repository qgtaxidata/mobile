package com.example.taxidata.ui.hotspot.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
public class HotSpotResearchActivity extends BaseActivity implements HotSpotContract.HotSpotView {

    @BindView(R.id.imagebutton_hotspot_search_back)
    ImageButton btnSearchBack;
    @BindView(R.id.et_hotspot_search)
    EditText etSearch;
    @BindView(R.id.btn_hotspot_search)
    Button btnSearch;
    @BindView(R.id.rv_hotspot_search_history)
    RecyclerView rvSearch;
    private static final String TAG = "HotSpotResearchFragment";
//    @BindView(R.id.imagebutton_hotspot_search_back_test)
//    ImageButton imagebuttonHotspotSearchBackTest;
//    @BindView(R.id.et_hotspot_search_test)
//    EditText etHotspotSearchTest;
//    @BindView(R.id.btn_hotspot_search_test)
//    Button btnHotspotSearchTest;
//    @BindView(R.id.rv_hotspot_search_history_test)
//    RecyclerView rvHotspotSearchHistoryTest;
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
    private String intputString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotspot_search);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        initData();
        initRecyclerView();
        initOnclickEvent();
        initViews();
        geocodeSearch = new GeocodeSearch(this);

        rvSearch.setAdapter(historyAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (StatusManager.hotSpotChosen) {
            //热点已经选定了
            etSearch.setHint("请输入起点");
            showHistoryOriginList(mPresenter.getHistoryOriginList());
        } else {
            //热点尚未选定
            showHistorySearchList(mPresenter.getHistorySearchList());
            etSearch.setHint("请输入地点");
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

    public void initViews() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                intputString = s.toString();
                mPresenter.getHintList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged: " );
            }
        });
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        historyAdapter = new HistoryHotspotSearchAdapter(R.layout.item_hotspot_history, historyList);
        hintAdapter = new HintHotSpotAdapter(R.layout.item_hotspot_hint, hintList);
        recommandAdapter = new RecommandHotSpotAdapter(R.layout.item_hotspot_recommand, hotSpotList);
        recommandAdapter.setEmptyView(new EmptyHotSpotView(this, null));
        originAdapter = new OriginHotSpotAdapter(R.layout.item_hotspot_origin_history, originList);
        rvSearch.setLayoutManager(layoutManager);
        //搜索页面默认出现搜索历史列表

    }

    public void initOnclickEvent() {
        btnSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 搜索页面返回到地图页面？
                finish();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Todo 将输入框的内容发送到P -》M ，请求获取热点数据
                String inputAddress = etSearch.getText().toString();
                //将用户输入的地址转换为坐标
                if (mPresenter != null && !"".equals(inputAddress)) {
                    mPresenter.convertToLocation(inputAddress ,geocodeSearch );
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
                mPresenter.convertToLocation(address ,geocodeSearch );
            }
        });
        hintAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                String address = hintAdapter.getData().get(position).getHotSpotLocation();
//                mPresenter.convertToLocation(address ,geocodeSearch );
                double longitute = hintAdapter.getData().get(position).getLongitude();
                double latitute = hintAdapter.getData().get(position).getLatitute();
                mPresenter.getHotSpotData(longitute ,latitute ,"");
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
//        提示列表上拉加载没有意义，因为API一个关键词只给10个
//        hintAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                mPresenter.getHintList(intputString);
//            }
//        });

    }


    @Override
    public void showHotSpot(List<HotSpotCallBackInfo.DataBean> hotSpotCallBackInfoList) {

        if (recommandAdapter != null && rvSearch != null) {
            hotSpotList.clear();
            rvSearch.setAdapter(recommandAdapter);
            recommandAdapter.setNewData(hotSpotCallBackInfoList);

        }
    }

    @Override
    public void showHistorySearchList(List<HotSpotHistorySearch> hotSpotHistorySearchList) {
        if (historyAdapter != null && rvSearch != null) {
            rvSearch.setAdapter(historyAdapter);
            historyAdapter.setNewData(hotSpotHistorySearchList);

        }
    }

    @Override
    public void showHintHotSpotList(List<HotSpotHint> hintList) {
        if (hintAdapter != null && rvSearch != null) {
            rvSearch.setAdapter(hintAdapter);
            hintAdapter.setNewData(hintList);
        }
    }


    @Override
    public void showHistoryOriginList(List<HotSpotOrigin> hotSpotOrigins) {
        if (originAdapter != null && rvSearch != null) {
            rvSearch.setAdapter(originAdapter);
            originAdapter.setNewData(hotSpotOrigins);
        }
    }

}
