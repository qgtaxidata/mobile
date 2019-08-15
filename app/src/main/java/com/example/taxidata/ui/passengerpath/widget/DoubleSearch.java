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

    @BindView(R.id.search_back)
    ImageView backIv;
    @BindView(R.id.search_origin)
    TextView originSearchTv;
    @BindView(R.id.search_end_point)
    TextView endSearchTv;

    private SearchOnClickListener originListener;

    private SearchOnClickListener endListener;

    private SearchOnClickListener backListener;

    public DoubleSearch(Context context) {
        this(context, null);
    }

    public DoubleSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LinearLayout.inflate(context, R.layout.view_search_widget, this);
        ButterKnife.bind(view);
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

    @OnClick({R.id.search_back, R.id.search_origin, R.id.search_end_point})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                backListener.onClick();
                break;
            case R.id.search_origin:
                originListener.onClick();
                break;
            case R.id.search_end_point:
                endListener.onClick();
                break;
            default:
        }
    }

    public void setOriginText(String originName){
        originSearchTv.setText(originName);
    }

    public void setEndText(String endName){
        endSearchTv.setText(endName);
    }

    public String getOriginText(){
        return originSearchTv.getText().toString();
    }

    public String getEndText(){
        return endSearchTv.getText().toString();
    }
}
