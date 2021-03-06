package com.openxu.oxlib.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.openxu.oxlib.BuildConfig;

import java.util.ArrayList;
import java.util.List;



/**
 * autour: openXu
 * date: 2017/3/10 13:32
 * className: PermissionUtils
 * version:
 * description: android6.0运行时权限申请工具类
 */
public class PermissionUtils {

    private static final String TAG = "PermissionUtils";
    public static final int ACTIVITY_RESULT = 1;
    //validateRequestPermissionsRequestCode in FragmentActivity requires
    //requestCode to be of 8 bits, meaning the range is from 0 to 255.
    public static final int PERMISSION_SMS_REQUEST_CODE = 0x00a1;
    public static final int PERMISSION_CALLLOG_REQUEST_CODE = 0x00a2;
    public static final int PERMISSION_LOC_REQUEST_CODE = 0x00a3;
    public static final int PERMISSION_RECORD_AUDIO = 0x00a4;
    public static final int PERMISSION_CAMERA_CODE = 0x00a5;

    public static final int PERMISSION_ARRAY = 0x00a6;      //申请一组权限

    public static final int PERMISSION_SETTING_REQ_CODE = 0x00c8;


    /**
     * 需要申请的权限
     */
    public static final String[] PERMISSION = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.CAMERA, //摄像头
            Manifest.permission.ACCESS_COARSE_LOCATION, //定位
            Manifest.permission.ACCESS_FINE_LOCATION, //定位
            Manifest.permission.RECORD_AUDIO //录音
    };

    /*权限名称，当权限被拒绝时，用于提醒用户开启权限（此数组应与上面权限数组一一对应）*/
    public static final String[] PERMISSION_NAME = new String[]{
            "读取手机状态",
            "写入手机存储权限",
            "读取手机存储权限",
            "摄像头权限",
            "获取手机粗略位置",
            "获取精确位置权限",
            "录音权限",
    };

    /**检测某项权限*/
    @TargetApi(23)
    public static boolean checkPermission(Object cxt, String permission, int requestCode) {
        if (!checkPermission(cxt, permission)) {
            Log.w(TAG, "检测权限"+permission+"-未授权");
            if (shouldShowRequestPermissionRationaleWrapper(cxt, permission)) {
                //This method returns true if the user has denied your permission
                // request at least once yet have not selected the ‘Don’t ask again’ option
                // (which appears the second or later time the permission dialog appears).
                requestPermissions(cxt, new String[]{permission}, requestCode);
            } else {
//                Logger.w("提示用户，此权限的作用，引导去设置里打开权限");
                requestPermissions(cxt, new String[]{permission}, requestCode);
            }
            return false;
        }else{
            Log.d(TAG, "检测权限"+permission+"-已授权");
            return true;
        }

    }

    /***
     * 申请单个权限
     * @param cxt activity or fragment
     * @param permission 权限
     * @param requestCode 请求码
     * @param permissionName 权限名称
     * @param use 权限作用
     * @return
     */
    @TargetApi(23)
    public static boolean checkPermission(Object cxt, String permission,
                                          int requestCode, String permissionName, String use) {
        if (!checkPermission(cxt, permission)) {
            Log.w(TAG, "检测权限"+permission+"-未授权");
            if (shouldShowRequestPermissionRationaleWrapper(cxt, permission, permissionName, use)) {
                //This method returns true if the user has denied your permission
                // request at least once yet have not selected the ‘Don’t ask again’ option
                // (which appears the second or later time the permission dialog appears).
                requestPermissions(cxt, new String[]{permission}, requestCode);
            } else {
//                Logger.w("提示用户，此权限的作用，引导去设置里打开权限");
                requestPermissions(cxt, new String[]{permission}, requestCode);
            }
            return false;
        }else{
            Log.d(TAG, "检测权限"+permission+"-已授权");
            return true;
        }
    }

    /**
     * 申请一组权限
     * @param cxt
     * @param permission
     * @param requestCode
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermissionArray(Object cxt, String[] permission, int requestCode) {
        String[] permissionNo = checkSelfPermissionArray(cxt, permission);
        if (permissionNo.length > 0) {
            requestPermissions(cxt, permissionNo, requestCode);
            LogUtil.e(TAG, "申请权限");
            return false;
        } else {
//            LogUtil.i(TAG, "没有未通过的权限");
            return true;
        }
    }

    /**
     * 申请权限
     * @param cxt
     * @param permission
     * @param requestCode
     */
    private static void requestPermissions(Object cxt, String[] permission, int requestCode) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            fragment.requestPermissions(permission, requestCode);
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }


    /**根据授权结果，判断权限是否被允许*/
    public static boolean verifyPermissions(int[] grantResults, String[] permissions, List<String> list) {
        boolean isOk = true;
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for(int i = 0;i<grantResults.length;i++){
            int result = grantResults[i];
//            Logger.v("权限名"+permissions[i] +"   结果："+result);
            if (result != PackageManager.PERMISSION_GRANTED) {
                isOk = false;
                if(list!=null){
                    list.add(permissions[i]);
                }
            }
        }
        return isOk;
    }

    /**获取未得到授权的全名名称*/
    public static String getUnRrantName(List<String> list){
        String pers = "";
        LogUtil.e(TAG, "未通过的权限为："+list);
        for(int i = 0; i<PERMISSION.length; i++){
            String permission = PERMISSION[i];
            if(list.contains(permission)){
                pers += PERMISSION_NAME[i]+"\n";
            }
        }
        return pers;
    }


    /**************************************************/

    /**
     * 检查是否拥有某权限
     * @param cxt
     * @param permission
     * @return
     */
    @TargetApi(23)
    private static boolean checkPermission(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            int hasPermission = ActivityCompat.checkSelfPermission(activity, permission);
            return hasPermission == PackageManager.PERMISSION_GRANTED;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    /**检查一组权限，并将未被允许的权限返回*/
    private static String[] checkSelfPermissionArray(Object cxt, String[] permission) {
        ArrayList<String> permiList = new ArrayList<>();
        for (String p : permission) {
            if (!checkPermission(cxt, p)) {
                LogUtil.e(TAG, p+ "未通过");
                permiList.add(p);
            }else{
//                LogUtil.i(TAG, p+ "已通过");
            }
        }
        return permiList.toArray(new String[permiList.size()]);
    }

    /**
     * @param cxt
     * @param permission
     * @return
     */
    private static boolean shouldShowRequestPermissionRationaleWrapper(Object cxt,
                                                                       String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            boolean should = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if(!should){
                //gotoSetting(activity, permissionName, use);
            }
            return should;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            boolean should = fragment.shouldShowRequestPermissionRationale(permission);
            if(!should){
                // gotoSetting(fragment.getActivity(), permissionName, use);
            }
            return should;
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    private static final String SCHEME = "package";
    /**
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本)
     */
    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
    /**
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2)
     */
    private static final String APP_PKG_NAME_22 = "pkg";
    /**
     * InstalledAppDetails所在包名
     */
    private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
    /**
     * InstalledAppDetails类名
     */
    private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";
    /**
     * 调用系统InstalledAppDetails界面显示已安装应用程序的详细信息。 对于Android 2.3（Api Level
     * 9）以上，使用SDK提供的接口； 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）。
     * @param packageName
     * 应用程序的包名
     */
    public static void showInstalledAppDetails(Activity activity, String packageName) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);
        } else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
            final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
                    : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName(APP_DETAILS_PACKAGE_NAME,
                    APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, packageName);
        }
        activity.startActivityForResult(intent, ACTIVITY_RESULT);
    }

    /**
     * 检测系统弹出权限
     * @param cxt
     * @param req
     * @return
     */
    @TargetApi(23)
    public static boolean checkSettingAlertPermission(Object cxt, int req) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            if (!Settings.canDrawOverlays(activity.getBaseContext())) {
                Log.i(TAG, "Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, req);
                return false;
            }
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            if (!Settings.canDrawOverlays(fragment.getActivity())) {
                Log.i(TAG, "Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + fragment.getActivity().getPackageName()));
                fragment.startActivityForResult(intent, req);
                return false;
            }
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }

        return true;
    }



    /**
     *
     * @param cxt
     * @param permission
     * @param permissionName
     * @param use
     * @return
     */
    private static boolean shouldShowRequestPermissionRationaleWrapper(Object cxt,
                                                                       String permission,
                                                                       String permissionName,
                                                                       String use) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            boolean should = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if(!should){
                gotoSetting(activity, permissionName, use);
            }
            return should;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            boolean should = fragment.shouldShowRequestPermissionRationale(permission);
            if(!should){
                gotoSetting(fragment.getActivity(), permissionName, use);
            }
            return should;
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    /**弹出设置权限对话框*/
    private static void gotoSetting(final Activity activity, String permissionName, String use){
        String msg = "在设置-应用-"+ BuildConfig.appName+
                "-权限中开启"+permissionName+"权限，以正常使用"+use+"功能";
        new AlertDialog.Builder(activity)
                .setTitle("权限申请")
                .setMessage(msg)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showInstalledAppDetails(activity, activity.getPackageName());
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }


}
