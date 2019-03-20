package com.openxu.libdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.openxu.libdemo.evenbus.EventBusActivity1;
import com.openxu.libdemo.evenbus.MessageEvent;
import com.openxu.libdemo.view.CoordinatorActivity;
import com.openxu.libdemo.view.ViewListActivity;
import com.openxu.libdemo.wchat.WchatActivity;
import com.openxu.oxlib.adapter.CommandRecyclerAdapter;
import com.openxu.oxlib.adapter.ViewHolder;
import com.openxu.oxlib.base.BaseActivity;
import com.openxu.oxlib.utils.LogUtil;
import com.openxu.oxlib.utils.ToastAlone;
import com.openxu.oxlib.view.chart.dashboard.DashBoardItem;
import com.openxu.oxlib.view.chart.dashboard.DashBoardView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> itemList;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        itemList = new ArrayList<>();
        itemList.add("自定义控件");
        itemList.add("CoordinatorLayout");
        itemList.add("retrofit");
        itemList.add("微信小程序测试");

        CommandRecyclerAdapter adapter = new CommandRecyclerAdapter<String>(this,
                R.layout.item_recycler, itemList){
            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.tv_name, str);
            }

            @Override
            public void onItemClick(String data, int position) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(mContext, ViewListActivity.class);
                        break;
                    case 1:
                        intent = new Intent(mContext, CoordinatorActivity.class);
                        break;
                    case 3:
                        intent = new Intent(mContext, WchatActivity.class);
                        break;
                }
                if(null!=intent)
                    startActivity(intent);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(itemList);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("OrganiseUnitID", "20e1516b-2032-11e7-98bc-000c29624c55");
       /* OkHttpUtils.post()
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
*/

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        LogUtil.e(TAG, "收到事件通知了");
        LogUtil.i(TAG, event.toString());
        ToastAlone.show(event.toString());
    }


}
