package com.openxu.libdemo.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.openxu.libdemo.R;
import com.openxu.libdemo.evenbus.EventBusActivity1;
import com.openxu.oxlib.adapter.CommandRecyclerAdapter;
import com.openxu.oxlib.adapter.ViewHolder;
import com.openxu.oxlib.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * autour : openXu
 * date : 2018/4/20 14:51
 * className : ViewActivityTest
 * version : 1.0
 * description : 请添加类说明
 */
public class ViewActivityTest extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CommandRecyclerAdapter adapter;
    private List<String> itemList;
    @Override
    protected int getLayoutID() {
        return R.layout.activity_command_recyclerlist;
    }

    @Override
    protected void initView() {

        titleLayout.setTextcenter("自定义控件")
                .setIconBack(R.mipmap.title_icon_back);

        adapter = new CommandRecyclerAdapter<String>(this,
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
                        intent = new Intent(mContext, EventBusActivity1.class);
                        break;
                    case 1:
                        intent = new Intent(mContext, EventBusActivity1.class);
                        break;
                }
                if(null!=intent)
                    startActivity(intent);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {

        itemList = new ArrayList<>();
        itemList.add("油门表盘");
        itemList.add("图表");

        adapter.setData(itemList);
    }




}
