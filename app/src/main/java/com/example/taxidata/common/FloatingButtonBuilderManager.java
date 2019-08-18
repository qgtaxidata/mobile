package com.example.taxidata.common;

import android.graphics.Rect;
import android.util.Log;

import com.example.taxidata.R;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.Util;

/**
 * @author: ODM
 * @date: 2019/8/18
 */
public class FloatingButtonBuilderManager {

    private static final String TAG = "BuilderManager";
    private static int[] imageResources = new int[]{
            R.mipmap.ui_ad_position,
            R.mipmap.ui_area_analyze,
            R.mipmap.ui_behavior_analyze,
            R.mipmap.ui_exception_analyze,
            R.mipmap.ui_heat_power,
            R.mipmap.ui_hotspot_route,
            R.mipmap.ui_income,
            R.mipmap.ui_request_analyze,
            R.mipmap.ui_road_analyze,
            R.mipmap.ui_route_recommand,
            R.mipmap.ui_route_visiable,
            R.mipmap.ui_settings
    };

    private static String[] titleCollection = new String[] {
            "广告牌位置推荐",
            "车辆利用率分析",
            "区域收入分析" ,
            "出租车异常分析",
            "热力图模式",
            "载客热点推荐",
            "收入排行榜",
            "出租车需求量分析",
            "道路质量分析",
            "路线规划",
            "路径可视化",
            "设置"
    };

    private static int imageResourceIndex = 0;

    private static int titleIndex = 0 ;


    private static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) {
            imageResourceIndex = 0;
        }
        return imageResources[imageResourceIndex++];
    }

    private static String getTitle() {
        if(titleIndex >= titleCollection.length) {
            titleIndex = 0 ;
        }
        return  titleCollection[titleIndex++];
    }

   public static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilderWithDifferentPieceColor() {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalText(getTitle())
                .buttonRadius(Util.dp2px(30))
                //四个属性，分别是向右偏移-大小-偏移--大小
                .imageRect(new Rect(Util.dp2px(15), Util.dp2px(15), Util.dp2px(45), Util.dp2px(45)) )
                .textSize(12);
    }


    private static FloatingButtonBuilderManager ourInstance = new FloatingButtonBuilderManager();

    public static FloatingButtonBuilderManager getInstance() {
        return ourInstance;
    }

    private FloatingButtonBuilderManager() {
    }
}
