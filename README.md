#框架

##1. 支持lambda语法

①. project\build.gradle:
>   repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'me.tatarka:gradle-retrolambda:3.2.5'
    }

②. oxlib\build.gradle:
>   apply plugin: 'me.tatarka.retrolambda'
    android {
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }

##2. butterknife

##3. gson

##4. EventBus事件发布和订阅框架
> EventBus in 3 steps

①. Define events:
```Java
    public static class MessageEvent { /* Additional fields if needed */ }
```
②.Prepare subscribers: Declare and annotate your subscribing method, optionally specify a thread mode:
```Java
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {/* Do something */};
```
    Register and unregister your subscriber. For example on Android, activities and fragments should usually register according to their life cycle:
```Java
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
```
③. Post events:
```Java
    EventBus.getDefault().post(new MessageEvent());
```

④. Add EventBus to your project
    Gradle:

    compile 'org.greenrobot:eventbus:3.0.0'



##5. 通用的TitleBar，扩展性好，自由度高
> 使用方法（需要展示什么就配置相应的属性值）

①. 布局文件中设置属性
```xml
   <com.openxu.oxlib.view.TitleLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        style="@style/TitleDefStyle"
        openxu:textLeft="本月任务"
        openxu:textcenter="center"
        openxu:textRight="right"
        openxu:iconLeft="@mipmap/home_nav_icon_task"
        openxu:iconCenterRow="@android:drawable/arrow_down_float"
        openxu:iconRightPop="@mipmap/home_nav_icon_task"
        openxu:iconRight="@mipmap/home_nav_icon_task"/>
```
②. 代码中设置
```xml
    <com.openxu.oxlib.view.TitleLayout
      android:id="@+id/title_Layout"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      style="@style/TitleDefStyle"/>
```
```Java
 title_Layout.setBackgroundColor1(Color.RED)
                .setTextLeft("我是左侧标题")
                .setIconLeft(R.mipmap.home_nav_icon_task)
                .setIconRight(R.mipmap.home_nav_icon_dot)
                .show();
```

③. 相关资源文件
> drawable/menu_icon_more.png



##6. 6.0运行时权限处理PermissionUtils
> 需要注意的是：

并不是应用所需的所有权限都应该在BaseActivity中申请，
BaseActivity中处理的通常是应用必须使用、且目标机型上都必须有的权限，比如读写存储、照相机等必要权限；
但是NFC权限就不应该在baseActivity中申请，而是在使用的那个activity中判断，
因为有很多手机不支持NFC，那判断授权的结果就是不通过。通常NFC的使用场景比较少，
不能因为手机没有NFC功能就不让用应用了（除非是NFC专用功能应用）。

此处只是举个例子，具体的权限管理应根据自己应用的情况考虑


> 使用：在BaseActivity中添加如下代码即可

```Java

    /**需要申请的权限*/
    static final String[] PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // 写入权限
            Manifest.permission.READ_EXTERNAL_STORAGE,  //读取权限
            Manifest.permission.CAMERA, //摄像头
            Manifest.permission.RECORD_AUDIO //录音
//            Manifest.permission.NFC
    };

    @Override
    public void onStart() {
        super.onStart();
        checkCameraPermission();
    }
    /**所有权限被通过时调用，比如splash界面，当权限被允许后，跳转主界面*/
    protected void allPermissionGranted() {
    }
    /**6.0权限*/
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
```



##7. 网络请求使用Retrofit https://github.com/square/retrofit

> Retrofit requires at minimum Java 7 or Android 2.3.

> Retrofit包含下面三个部分：

>POJO或模型实体类 : 从服务器获取的JSON数据将被填充到这种类的实例中。

> 接口 : 我们需要创建一个接口来管理像GET,POST...等请求的URL，这是一个服务类。

> RestAdapter类 : 这是一个REST客户端(RestClient)类，retrofit中默认用的是Gson来解析JSON数据，你也可以设置自己的JSON解析器，比如jackson，我们将在下面的教程中详细解说明。

①. compile 'com.squareup.retrofit2:retrofit:2.2.0'
 
②. 将Rest API转换为java接口
②. 创建模型类
②. Retrofit会帮我们自动生成接口的实现类的实例
②. 调用接口中定义的业务方法:get post等























