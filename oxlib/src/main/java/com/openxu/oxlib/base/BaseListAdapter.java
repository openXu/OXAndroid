package com.openxu.oxlib.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author : openXu
 * create at : 2017/3/6 15:46
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : OXAndroid
 * class name : BaseListAdapter
 * version : 1.0
 * class describe：
 */
public class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> data = new ArrayList<>();
    protected Context context;

    public BaseListAdapter(Context context) {
        this.context = context;
    }

    public BaseListAdapter(Context context, List<T> data) {
        this.context = context;
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
