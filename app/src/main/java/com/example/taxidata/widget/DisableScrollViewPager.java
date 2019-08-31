package com.example.taxidata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @author: ODM
 * @date: 2019/8/21
 */
public class DisableScrollViewPager extends ViewPager {

        private boolean noScroll = true;

       public DisableScrollViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

       public DisableScrollViewPager(Context context) {
            super(context);
       }

       public void setNoScroll(boolean noScroll) {
            this.noScroll = noScroll;
       }

       @Override
       public void scrollTo(int x, int y) {
            super.scrollTo(x, y);
       }

       @Override
       public boolean onTouchEvent(MotionEvent arg0) {
       if (noScroll) {
           return false;
       } else {
           return super.onTouchEvent(arg0);
         }
       }

       @Override
       public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            return false;
        } else {
           return super.onInterceptTouchEvent(arg0);}
       }

       @Override
       public void setCurrentItem(int item, boolean smoothScroll) {
                super.setCurrentItem(item, smoothScroll);
        }

       @Override
       public void setCurrentItem(int item) {
           //表示切换的时候，不需要切换时间。
           super.setCurrentItem(item, false);
        }
}
