package com.openxu.libdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.openxu.libdemo.R;
import com.openxu.oxlib.adapter.CommandRecyclerAdapter;
import com.openxu.oxlib.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ViewListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<String> datas;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datas = new ArrayList<>();
        datas.add("南丁格尔玫瑰图");
        datas.add("饼状图");
        datas.add("进度环形图");
        datas.add("纵向柱状图");
        datas.add("横向柱状图");
        datas.add("折线图");
        datas.add("股票信息");

        recyclerView.setAdapter(new CommandRecyclerAdapter<String>(this, R.layout.list_item, datas) {
            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.tv_name, str);
            }
            @Override
            public void onItemClick(String str, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(ViewListActivity.this, RoseActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(ViewListActivity.this, PieActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(ViewListActivity.this, ProgressPieActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(ViewListActivity.this, VerticalBarActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(ViewListActivity.this, HorizontalBarActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(ViewListActivity.this, XmStockChartActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(ViewListActivity.this, XmStockChartActivity1.class));
                        break;
                }
            }

        });

    }
}
