package com.example.taxidata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;

/**
 * @author: ODM
 * @date: 2019/8/14
 */
public class EmptyHotSpotHistoryView extends RelativeLayout {

    public EmptyHotSpotHistoryView(Context context) {
        super(context);
    }

    public EmptyHotSpotHistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View inflate = inflate(TaxiApp.getContext(), R.layout.view_empty_hotspot_history, this);
    }

    public EmptyHotSpotHistoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
