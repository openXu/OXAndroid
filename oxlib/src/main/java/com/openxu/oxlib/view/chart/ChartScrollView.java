package com.openxu.oxlib.view.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.openxu.oxlib.utils.LogUtil;


/**
 * Company: SyberOS BeiJing
 * Project: Device Inspection
 * Created by 陈冬 on 16/2/3.
 */
public class ChartScrollView extends ScrollView {
    private String TAG = "ChartScrollView";

    public ChartScrollView(Context context) {
        super(context);
    }

    public ChartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChartScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
         boolean result = super.dispatchTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        LogUtil.e(TAG, "onInterceptTouchEvent拦截事件 "+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean result = super.onTouchEvent(ev);
        LogUtil.i(TAG, "onTouchEvent处理事件 "+result);
        return result;
    }
}
