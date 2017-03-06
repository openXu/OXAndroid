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