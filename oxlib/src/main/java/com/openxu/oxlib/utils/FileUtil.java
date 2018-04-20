package com.openxu.oxlib.utils;

import android.os.Environment;

import com.openxu.oxlib.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * autour : openXu
 * date : 2017/11/6 11:24
 * className : FileUtil
 * version : 1.0
 * description : 文件相关工具类
 */
public class FileUtil {

    private static String TAG = "FileUtil";

    /*存储根目录*/
    private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+ BuildConfig.APP_FILE_NAME;
    /*版本升级下载路径*/
    private static final String PATH_UPDATE = ROOT_PATH + File.separator+ "appDownload";
    /*崩溃日志*/
    private static final String PATH_ERROR = ROOT_PATH + File.separator+ "errorlog";
    /*附件下载目录*/
    private static final String PATH_CACHE = ROOT_PATH + File.separator+ "cache";
    /*附件上传文件目录*/
    private static final String PATH_STORAGE = ROOT_PATH + File.separator+ "storage";
    /*临时目录*/
    private static final String PATH_TEMP = ROOT_PATH + File.separator+ "temp";
    /**
     * 初始化存储文件夹，此方法需要在Application.onCreate()中调用
     * @return
     */
    public static boolean initFileDir(){
        try{
            File file = new File(ROOT_PATH);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }
            file = new File(PATH_UPDATE);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }
            file = new File(PATH_ERROR);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }
            file = new File(PATH_CACHE);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }
            file = new File(PATH_STORAGE);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }
            file = new File(PATH_TEMP);
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();
            }
            LogUtil.i(TAG, "初始化存储文件夹...");
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**获取版本升级下载目录*/
    public static String getUpdatePath(){
        File file = new File(PATH_UPDATE);
        if(!file.exists() || !file.isDirectory()){
            initFileDir();
        }
        return PATH_UPDATE;
    }
    /**获取版本升级下载目录*/
    public static String getErrorPath(){
        File file = new File(PATH_ERROR);
        if(!file.exists() || !file.isDirectory()){
            initFileDir();
        }
        return PATH_ERROR;
    }
    /**获取版本升级下载目录*/
    public static String getCachePath(){
        File file = new File(PATH_CACHE);
        if(!file.exists() || !file.isDirectory()){
            initFileDir();
        }
        return PATH_CACHE;
    }
    /**获取版本升级下载目录*/
    public static String getStoragePath(){
        File file = new File(PATH_STORAGE);
        if(!file.exists() || !file.isDirectory()){
            initFileDir();
        }
        return PATH_STORAGE;
    }
    /**获取临时目录*/
    public static String getTempPath(){
        File file = new File(PATH_TEMP);
        if(!file.exists() || !file.isDirectory()){
            initFileDir();
        }
        return PATH_TEMP;
    }

    /*************************工具方法************************/
    /**
     * 在目录下创建临时文件
     * @param path 父文件夹
     * @param fileName 文件名
     * @return 创建的文件
     */
    public static File createFile(String path, String fileName, String suffix) {
        try {
            File file = new File(path);
            if (!file.exists()|| !file.isDirectory())
                file.mkdir();
            File tempFile = File.createTempFile(fileName, //文件名
                    suffix, //后缀
                    file);   //目录
            LogUtil.i(TAG, "创建文件="+(tempFile.exists()&&tempFile.isFile()));
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除文件及其子目录
     * @param root
     */
    public static void deleteFiles(File root) {
        try {
            if (root.isFile()) {
                root.delete();
                return;
            }
            File files[] = root.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) { // 判断是否为文件夹
                        deleteFiles(f);
                    } else if (f.exists()){
                        f.delete();
                    }
                }
            }
            root.delete();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 复制文件
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @return 是否复制成功
     */
    public static boolean copyFile(File srcFile, File destFile){
        try {
            if (!srcFile.exists() || srcFile.isDirectory() || destFile.isDirectory())
                return false;
            if (destFile.exists() && destFile.isFile())
                destFile.delete();
            destFile.createNewFile();
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            int readLen;
            byte[] buf = new byte[1024];
            while ((readLen = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLen);
            }
            LogUtil.i(TAG, "复制文件srcFile="+srcFile.getAbsolutePath()+"   destFile="+destFile.getAbsolutePath());
            fos.flush();
            fos.close();
            fis.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }




}
