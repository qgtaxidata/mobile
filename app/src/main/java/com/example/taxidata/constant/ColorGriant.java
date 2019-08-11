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
            Color.argb(0, 0, 255, 255),
            Color.argb(255 / 3 * 2, 0, 255, 0),
            Color.rgb(125, 191, 0),
            Color.rgb(185, 71, 0),
            Color.rgb(255, 0, 0)
    };

    /**
     * 颜色渐变
     */
    private static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = { 0.0f,
            0.10f, 0.20f, 0.60f, 1.0f};

    /**
     * 渐变
     */
    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(
            ALT_HEATMAP_GRADIENT_COLORS, ALT_HEATMAP_GRADIENT_START_POINTS);
}
