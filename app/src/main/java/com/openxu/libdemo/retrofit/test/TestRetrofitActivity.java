package com.openxu.libdemo.retrofit.test;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.openxu.libdemo.R;
import com.openxu.libdemo.evenbus.EventBusActivity1;
import com.openxu.libdemo.evenbus.MessageEvent;
import com.openxu.libdemo.retrofit.RestApiStores;
import com.openxu.libdemo.retrofit.WeatherBean;
import com.openxu.oxlib.base.BaseActivity;
import com.openxu.oxlib.utils.LogUtil;
import com.openxu.oxlib.utils.ToastAlone;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/** 
 * autour: openXu
 * date: 2017/4/27 16:42 
 * className: TestRetrofitActivity
 * version: 
 * description: 
 */
public class TestRetrofitActivity extends BaseActivity {


    @BindView(R.id.btn_eventbus)
    Button btn_eventbus;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        //lambda表达式
        btn_eventbus.setOnClickListener(v->{
            startActivity(new Intent(this, EventBusActivity1.class));
        });
    }

    @Override
    protected void initData() {
        //1.将Rest API转换为java接口 & 定义java bean
        //2.Retrofit会帮我们自动生成接口的实现类的实例
        Retrofit retrofit = new Retrofit.Builder()  //01:获取Retrofit对象
                .baseUrl(RestApiStores.WEATHER)     //02采用链式结构绑定Base url
                .build();//03执行操作
        RestApiStores service = retrofit.create(RestApiStores.class);//04获取API接口的实现类的实例对象

        //3. 调用接口中定义的业务方法:get post
        Call<WeatherBean> repos = service.getWeather("北京");
        repos.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                LogUtil.i(TAG, "返回数据："+response.body());
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                LogUtil.e(TAG, "请求数据错误："+t.getMessage());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        LogUtil.e(this, "收到事件通知了");
        LogUtil.i(this, event.toString());
        ToastAlone.show(event.toString());
    }


}
