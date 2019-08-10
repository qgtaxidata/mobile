package com.example.taxidata.constant;

import android.graphics.Color;

import com.amap.api.maps.model.Gradient;

/**
 * 颜色渐变常量
 */
public class ColorGriant {
    /**
     * 隐藏构造器
     */
    private ColorGriant(){}

    /**
     * 颜色渐变
     */
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.rgb( 0, 0, 255),
            Color.rgb( 117, 211, 248),
            Color.rgb(0, 255, 0),
            Color.rgb(255, 234, 0),
            Color.rgb(255, 0, 0)
    };

    /**
     * 颜色渐变
     */
    private static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = { 0.5f,
            0.65f, 0.7f, 0.9f, 1.0f };

    /**
     * 渐变
     */
    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(
            ALT_HEATMAP_GRADIENT_COLORS, ALT_HEATMAP_GRADIENT_START_POINTS);
}
