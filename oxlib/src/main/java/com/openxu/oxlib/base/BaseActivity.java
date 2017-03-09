package com.openxu.oxlib.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.openxu.oxlib.R;
import com.openxu.oxlib.view.TitleLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : openXu
 * create at : 2017/3/6 15:10
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : OXAndroid
 * version : 1.0
 * class describe：
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;
    protected TitleLayout titleLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setCxs();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        View titleView = findViewById(R.id.title_layout);
        titleLayout = titleView==null?null:(TitleLayout)titleView;

        unbinder = ButterKnife.bind(this);
        initView();
        setListener();
        initData();
    }

    protected abstract int getLayoutID();
    protected abstract void initView();
    protected void setListener(){}
    protected abstract void initData();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setCxs(){
        getWindow().setStatusBarColor(getResources().getColor(R.color.title_color));
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
//        window.setStatusBarColor(getResources().getColor(R.color.main_style));

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

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
