package com.example.taxidata.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class AreaAnalyzeTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.88f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        if(position < -1){
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }else if(position <= 1){
            float scaleFactor = MIN_SCALE +(1 - Math.abs(position))*(MAX_SCALE-MIN_SCALE);
            page.setScaleX(scaleFactor);
            if(position > 0 ){
                page.setTranslationX(-scaleFactor*2);
            }else if(position<0){
                page.setTranslationX(scaleFactor*2);
            }
            page.setScaleY(scaleFactor);
        }else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
