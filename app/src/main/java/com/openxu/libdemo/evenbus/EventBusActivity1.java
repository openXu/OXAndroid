package com.openxu.libdemo.evenbus;

import android.widget.Button;

import com.openxu.libdemo.R;
import com.openxu.oxlib.base.BaseActivity;
import com.openxu.oxlib.utils.LogUtil;
import com.openxu.oxlib.utils.ToastAlone;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by openXu on 2017/3/7.
 */

public class EventBusActivity1 extends BaseActivity {


    @BindView(R.id.btn_eventbus1)
    Button btnEventbus1;
    @BindView(R.id.btn_eventbus2)
    Button btnEventbus2;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_eventbus1;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        //lambda表达式
        btnEventbus1.setOnClickListener(v -> {
            EventBus.getDefault().post(new MessageEvent(1, "上个页面"));
        });
        btnEventbus2.setOnClickListener(v -> {
            EventBus.getDefault().post(new MessageEvent(2, "当前页面"));
        });
    }

    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        LogUtil.e(TAG, "收到事件通知了");
        LogUtil.i(TAG, event.toString());
        ToastAlone.show(event.toString());
    }



}
