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

##3. EventBus事件发布和订阅框架
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





































