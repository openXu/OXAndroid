package com.openxu.oxlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : openXu
 * create at : 2017/3/6 15:10
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : OXAndroid
 * class name : BaseActivity
 * version : 1.0
 * class describe：
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        unbinder = ButterKnife.bind(this);
        initView();
        setListener();
        initData();
    }

    protected abstract int getLayoutID();
    protected abstract void initView();
    protected void setListener(){}
    protected abstract void initData();

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
