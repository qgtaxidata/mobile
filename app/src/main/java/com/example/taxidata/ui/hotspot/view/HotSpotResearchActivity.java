package com.example.taxidata.ui.hotspot.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.base.BaseFragment;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotHint;
import com.example.taxidata.bean.HotSpotHistorySearch;
import com.example.taxidata.common.StatusManager;
import com.example.taxidata.ui.hotspot.adapter.HintHotSpotAdapter;
import com.example.taxidata.ui.hotspot.adapter.HistoryHotspotSearchAdapter;
import com.example.taxidata.ui.hotspot.adapter.RecommandHotSpotAdapter;
import com.example.taxidata.ui.hotspot.contract.HotSpotContract;
import com.example.taxidata.ui.hotspot.presenter.HotSpotPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private HotSpotPresenter mPresenter = new HotSpotPresenter();
    private List<HotSpotHistorySearch> historyList;
    private List<HotSpotHint> hintList;
    private List<HotSpotCallBackInfo.DataBean>  hotSpotList;
    private HistoryHotspotSearchAdapter historyAdapter;
    private HintHotSpotAdapter hintAdapter;
    private RecommandHotSpotAdapter  recommandAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
        hintList = new ArrayList<>();
        historyList = new ArrayList<>();
        hotSpotList = new ArrayList<>();
        initRecyclerView();
        initOnclickEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(StatusManager.hotSpotChosen) {
            //热点已经选定了
            etSearch.setHint("请输入起点");
        } else {

            etSearch.setHint("请输入地点");
        }
    }



    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setReverseLayout(true);
        historyAdapter = new HistoryHotspotSearchAdapter(R.layout.item_hotspot_history ,historyList);
        hintAdapter = new HintHotSpotAdapter(R.layout.item_hotspot_hint ,hintList);
        recommandAdapter.setEmptyView(new EmptyHotSpotView(this ,null));
        recommandAdapter = new RecommandHotSpotAdapter(R.layout.item_hotspot_recommand ,hotSpotList);
        rvSearch.setLayoutManager(layoutManager);
        //搜索页面默认出现搜索历史列表
        rvSearch.setAdapter(historyAdapter);


    }

    public void  initOnclickEvent() {
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
                    mPresenter.convertToLocation(inputAddress);
                }
            }
        });
    }



    @Override
    public void showHotSpot(List<HotSpotCallBackInfo.DataBean> hotSpotCallBackInfoList) {

        if(recommandAdapter != null && rvSearch != null) {
            hotSpotList.clear();
            recommandAdapter.setNewData(hotSpotCallBackInfoList);
            rvSearch.swapAdapter(recommandAdapter,true);
        }
    }

    @Override
    public void showHistorySearchList(List<HotSpotHistorySearch> hotSpotHistorySearchList) {
            if(historyAdapter != null && rvSearch != null) {
                historyAdapter.setNewData(hotSpotHistorySearchList);
                rvSearch.swapAdapter(historyAdapter,true);
            }
    }

    @Override
    public void showHintHotSpotList(List<HotSpotHint> hintList) {
        if(hintAdapter != null && rvSearch != null) {
            hintAdapter.setNewData(hintList);
            rvSearch.swapAdapter(hintAdapter,true);
        }
    }


}
