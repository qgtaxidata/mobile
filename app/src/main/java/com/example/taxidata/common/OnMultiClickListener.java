package com.example.taxidata.common;

import android.view.View;

public abstract class OnMultiClickListener implements View.OnClickListener {

    //两次按钮点击间隔不小于10000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 10000;
    private static long lastClickTime;

    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v){
        long curClickTime = System.currentTimeMillis();  //当前点击时间
        if((curClickTime - lastClickTime)>= MIN_CLICK_DELAY_TIME){
            //超过间隔时间后再将lastClickTime设置为当前点击时间
            lastClickTime = curClickTime;
            onMultiClick(v);
        }
    }
}
