package com.openxu.libdemo;

import android.app.Application;

import com.openxu.oxlib.utils.ToastAlone;

/**
 * author : openXu
 * create at : 2017/5/10 16:48
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : OXAndroid
 * version : 1.0
 * class describe：
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastAlone.init(this);
    }
}
