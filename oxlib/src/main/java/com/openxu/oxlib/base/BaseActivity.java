package com.openxu.oxlib.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.openxu.oxlib.R;
import com.openxu.oxlib.utils.LogUtil;
import com.openxu.oxlib.utils.PermissionUtils;
import com.openxu.oxlib.view.TitleLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : openXu
 * create at : 2017/3/6 15:10
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : OXAndroid
 * version : 1.0
 * class describe：
 * activity基类，包含如下操作：
 * 1. 初始化titleLayout
 * 2. 运行时权限处理
 * 3. 模板方法
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = "BaseActivity";
    protected Context mContext;

    protected Unbinder unbinder;
    protected TitleLayout titleLayout;


    /**
     * 需要申请的权限
     */
    static final String[] PERMISSION = new String[]{
         /*   Manifest.permission.WRITE_EXTERNAL_STORAGE, // 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.CAMERA, //摄像头
            Manifest.permission.RECORD_AUDIO //录音*/
//            Manifest.permission.NFC

    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        mContext = this;
        View titleView = findViewById(R.id.title_layout);

        if (null != titleView) {
            titleLayout = (TitleLayout)titleView;
            titleLayout.setBackgroundColor1(getResources().getColor(R.color.title_color));
            //默认处理返回键
            titleLayout.setOnMenuClickListener((menu, view)-> {
                switch (menu) {
                    case MENU_BACK:
                        onBackPressed();
                        return;
                }
                onMenuClick(menu, view);
            });
        }


        unbinder = ButterKnife.bind(this);
        initView();
        setListener();
        initData();
    }

    protected abstract int getLayoutID();
    protected abstract void initView();
    protected void onMenuClick(TitleLayout.MENU_NAME menu, View view) {

    }
    protected void setListener(){}
    protected abstract void initData();


    @Override
    public void onStart() {
        super.onStart();
        checkCameraPermission();
        EventBus.getDefault().register(this);
    }







    /************************权限相关**************************/
    /**所有权限被通过时调用，比如splash界面，当权限被允许后，跳转主界面*/
    protected void allPermissionGranted() {
    }

    /**
     * 6.0权限
     */
    private void checkCameraPermission() {
        LogUtil.i(TAG, "======================申请系列必要权限========================");
        if (PermissionUtils.checkPermissionArray(this, PERMISSION, PermissionUtils.PERMISSION_ARRAY)) {
            LogUtil.i(TAG, "系列必要权限全部通过");
            allPermissionGranted();
        } else {
            LogUtil.e(TAG, "系列必要权限有部分未通过");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.PERMISSION_ARRAY:
                List<String> list = new ArrayList<>();
                if (PermissionUtils.verifyPermissions(grantResults, permissions, list)) {
                    LogUtil.d(TAG, "权限组被允许");
                    allPermissionGranted();
                } else {
                    String pers = PermissionUtils.getUnRrantName(list);
                    LogUtil.e(TAG, pers + "权限被拒绝了");
                    // Permission Denied
                    String msg = "当前应用缺少"+(list.size() > 1 ? (list.size() + "项"):" ")+"必要权限。\n详情如下：\n"
                            + pers +
                            "请点击\"设置\"-\"权限\"-打开所需权限后继续使用\n" ;
                    new AlertDialog.Builder(this)
                            .setTitle("权限申请")
                            .setMessage(msg)
                            .setPositiveButton("去设置", (DialogInterface dialogInterface, int i)-> {
                                PermissionUtils.showInstalledAppDetails(this, getPackageName());
                            })
                            .setNegativeButton("取消", (DialogInterface dialogInterface, int i)-> {
                                //退出
                                finish();
                            }).setCancelable(false)
                            .show();
                }
                break;
        }
    }


    /************************权限相关**************************/

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
