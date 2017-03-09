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































