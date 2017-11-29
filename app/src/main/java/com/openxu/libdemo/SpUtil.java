package com.openxu.libdemo;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * author : openXu
 * create at : 2017/5/10 16:29
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : OXAndroid
 * version : 1.0
 * class describe：
 */
public class SpUtil {


    private SharedPreferences sp;
    private Context context;


    private static SpUtil instance;


    private String NAME = "openxu";
    public static SpUtil getInstance(Context context){
        if(instance==null){
            instance = new SpUtil(context);

        }
        return instance;
    }

    private SpUtil(Context context){
        this.context = context;
        sp = context.getSharedPreferences(NAME, MODE_PRIVATE);
    }


    private String KEY_TEST = "test";

    public String getTest(){
        return sp.getString(KEY_TEST,"空的");
    }



    public void  setTest(){
        sp.edit().putString(KEY_TEST,"哈哈哈哈哈哈").commit();
    }

}
