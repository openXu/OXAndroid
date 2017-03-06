package com.openxu.oxlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : SPUtil
 * version : 1.0
 * class describe：SharedPreferences 读取的工具类
 * 此类使用需在自定义的Application中调用{@link SPUtil#init(Context, String)}方法，在程序中使用的时候，使用{@link SPUtil#getInstance()}方法进行实例化<br></br>
 * 使用此类建议建立一个常量类，用来保存key值。
 */
public final class SPUtil {

    private static SPUtil spUtil;
    private static Context mContext;
    private static String fileName;
    private static SharedPreferences saveInfo;
    private static SharedPreferences.Editor saveEditor;

    private SPUtil() {
        if(null == mContext){
            throw new NullPointerException("此类没有进行初始化");
        }
        if (TextUtils.isEmpty(fileName)){
            fileName = "itotem";
        }
        if (saveInfo == null && mContext != null) {
            saveInfo = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            saveEditor = saveInfo.edit();
        }
    }

    public static SPUtil getInstance() {
        if (null == spUtil) {
            synchronized (SPUtil.class) {
                if (null == spUtil)
                    spUtil = new SPUtil();
            }
        }
        return spUtil;
    }

    /**
     * 初始化此类，使用此类前必须前执行此方法。<br></br>
     * 此方法执行可放在自定义的Application中
     * @param ctx {@link Context}实例
     * @param name SharedPreferences 文件名
     */
    public static void init(Context ctx, String name) {
        mContext = ctx;
        fileName = name;
    }

    // --------- string ----------
    public boolean putString(String key, String value) {
        saveEditor.putString(key, value);
        return saveEditor.commit();
    }

    public String getString(String key, String defaultValue) {
        return saveInfo.getString(key, defaultValue);
    }

    // --------- float ----------
    public boolean putFloat(String key, Float value) {
        saveEditor.putFloat(key, value);
        return saveEditor.commit();
    }

    public Float getFloat(String key, Float defaultValue) {
        return saveInfo.getFloat(key, defaultValue);
    }

    // --------- boolean ----------
    public boolean putBoolean(String key, boolean value) {
        saveEditor.putBoolean(key, value);
        return saveEditor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return saveInfo.getBoolean(key, defaultValue);
    }

    // ---------- int ---------
    public boolean putInt(String key, int value) {
        saveEditor.putInt(key, value);
        return saveEditor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return saveInfo.getInt(key, defaultValue);
    }

    // ---------- long ----------
    public boolean putLong(String key, long value) {
        saveEditor.putLong(key, value);
        return saveEditor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return saveInfo.getLong(key, defaultValue);
    }

}
