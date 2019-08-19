package com.example.taxidata.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.common.eventbus.BaseEvent;
import com.example.taxidata.util.EventBusUtils;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class BaseFragment extends Fragment implements  BaseView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解注册eventbus
        if (isRegisterEventBus()) {
            EventBusUtils.unregister(this);
        }
        RefWatcher refWatcher = TaxiApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    /**
     * 处理eventbus发过来的事件,子View 需要重写处理事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEvent baseEvent) {
    }

    /**
     * 是否注册事件分发，子View若要注册，必须Override 返回true
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }
}
