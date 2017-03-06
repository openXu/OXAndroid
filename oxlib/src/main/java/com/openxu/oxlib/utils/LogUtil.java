package com.openxu.oxlib.utils;

import android.util.Log;

import com.openxu.oxlib.OXConfig;

/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : LogUtil
 * version : 1.0
 * class describe：
 */
public class LogUtil {
    private static String TAG = "openXu";

    public static void v(String msg) {
        v(TAG, msg);
    }
    public static void v(String TAG, String msg) {
        if (OXConfig.DEBUG){
        	msg=formNull(msg);
            Log.v(TAG, msg);
        }
    }
    public static void v(Object TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.v(TAG.getClass().getName(), msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }
    public static void d(String TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.d(TAG, msg);
        }
    }
    public static void d(Object TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.d(TAG.getClass().getName(), msg);
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }
    public static void i(String TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.i(TAG, msg);
        }
    }
    public static void i(Object TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.i(TAG.getClass().getName(), msg);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }
    public static void w(String TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.w(TAG, msg);
        }
    }
    public static void w(Object TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.w(TAG.getClass().getName(), msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }
    public static void e(String TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.e(TAG, msg);
        }
    }
    public static void e(Object TAG, String msg) {
        if (OXConfig.DEBUG){
            msg=formNull(msg);
            Log.e(TAG.getClass().getName(), msg);
        }
    }


    //对null值进行替换
    private static String formNull(String value){
    	return value==null ?"null":value;
    }
}
