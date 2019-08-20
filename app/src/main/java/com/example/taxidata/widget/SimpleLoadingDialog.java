package com.example.taxidata.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.taxidata.R;

/**
 * @author: ODM
 * @date: 2019/8/13
 */
public class SimpleLoadingDialog extends Dialog {

    private static final String TAG = "LoadingDialog";

    // 加载标题
    private String mMessage;
    // 旋转图片的资源id
    private int mImageId;
    private boolean mCancelable;
    private RotateAnimation mRotateAnimation;
    private ImageView iv_loading;

    public SimpleLoadingDialog(@NonNull Context context, String message, int imageId) {
        this(context, R.style.LoadingDialog,message,imageId,false);
    }

    public SimpleLoadingDialog(@NonNull Context context, int themeResId, String message, int imageId, boolean cancelable) {
        super(context, themeResId);
        mMessage = message;
        mImageId = imageId;
        mCancelable = cancelable;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iv_loading.setAnimation(mRotateAnimation);
    }

    private void initView() {
        setContentView(R.layout.view_dialog_loading_simple);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        // 设置窗口背景透明度
        attributes.alpha = 0.3f;
        // 设置窗口宽为屏幕的二分之一，高为1/3
        attributes.width = screenWidth/2;
        attributes.height = screenWidth/3;
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);
        TextView tv_loading = findViewById(R.id.tv_loading);
        iv_loading = findViewById(R.id.iv_loading);
        tv_loading.setText(mMessage);
        iv_loading.setImageResource(mImageId);
        // 先对imageView进行测量，才能拿到它的宽高（否则getMeasuredWidth为0）
        iv_loading.measure(0,0);
        // 设置选择动画
        mRotateAnimation = new RotateAnimation(0,360,iv_loading.getMeasuredWidth()/2,iv_loading.getMeasuredHeight()/2);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(-1);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            // 屏蔽返回键
            return mCancelable;
        }
        return super.onKeyDown(keyCode, event);
    }
}