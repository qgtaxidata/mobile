package com.example.taxidata.ui.passengerpath.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.taxidata.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoubleSearch extends LinearLayout {


    @BindView(R.id.iv_search_back)
    ImageView backIv;
    @BindView(R.id.tv_search_origin)
    TextView originSearchTv;
    @BindView(R.id.tv_search_end_point)
    TextView endSearchTv;
    private SearchOnClickListener originListener;

    private SearchOnClickListener endListener;

    private SearchOnClickListener backListener;

    public DoubleSearch(Context context) {
        this(context, null);
    }

    public DoubleSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LinearLayout.inflate(context, R.layout.view_double_search, this);
        ButterKnife.bind(view);
        originSearchTv.setSelected(true);
        endSearchTv.setSelected(true);
    }

    public void setOriginListener(SearchOnClickListener originListener) {
        this.originListener = originListener;
    }

    public void setEndListener(SearchOnClickListener endListener) {
        this.endListener = endListener;
    }

    public void setBackListener(SearchOnClickListener backListener) {
        this.backListener = backListener;
    }

    public void setOriginText(String originName) {
        originSearchTv.setText(originName);
    }

    public void setEndText(String endName) {
        endSearchTv.setText(endName);
    }

    public String getOriginText() {
        return originSearchTv.getText().toString();
    }

    public String getEndText() {
        return endSearchTv.getText().toString();
    }

    @OnClick({R.id.iv_search_back, R.id.tv_search_origin, R.id.tv_search_end_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                if (backListener != null){
                    backListener.onClick();
                }
                break;
            case R.id.tv_search_origin:
                if (originListener != null){
                    originListener.onClick();
                }
                break;
            case R.id.tv_search_end_point:
                if (endListener != null){
                    endListener.onClick();
                }
                break;
            default:
        }
    }
}
