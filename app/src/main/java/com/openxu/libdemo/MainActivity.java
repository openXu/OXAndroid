package com.openxu.libdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.openxu.libdemo.evenbus.EventBusActivity1;
import com.openxu.libdemo.evenbus.MessageEvent;
import com.openxu.oxlib.base.BaseActivity;
import com.openxu.oxlib.utils.LogUtil;
import com.openxu.oxlib.utils.ToastAlone;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends BaseActivity {


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
        Map<String, String> params = new HashMap<>();
        params.put("OrganiseUnitID", "20e1516b-2032-11e7-98bc-000c29624c55");
        OkHttpUtils.post()
                .url("http://172.16.160.34:8002/WebApi/DataExchange/SendData/SHOP_Register_Cancel?datakey=00-00-00-00")
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtil.e(TAG, "==response==" + response);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
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
