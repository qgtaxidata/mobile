package com.example.taxidata.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义TextView，文字跑马灯效果，可设置点击事件，不可点击暂停效果
 * 需要搭配XMl属性使用
 *
 * @author: ODM
 * @date: 2019/8/15
 */
public class MarqueeTextView extends AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused(){
        return true;
    }
}