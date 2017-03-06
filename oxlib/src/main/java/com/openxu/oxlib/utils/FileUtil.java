package com.openxu.oxlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : FileUtil
 * version : 1.0
 * class describe：文件操作工具类
 */
public class FileUtil {

	public static final String APP_NAME = "Update";
	public static String SD_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath();

	public static String DATA_PATH = Environment.getDownloadCacheDirectory()
			.getAbsolutePath();

	public static final String DOWNLOAD_SD_PATH = SD_PATH + "/" + APP_NAME
			+ "/download/";

	public static final String DOWNLOAD_DATA_PATH = DATA_PATH + "/" + APP_NAME
			+ "/download/";

	public static File updateDir = null;
	public static File updateFile = null;

	/***
	 * 创建文件
	 */
	public static void createFile(String name) {
		if (isHaveSDCard()) {
			updateDir = new File(DOWNLOAD_SD_PATH);
			updateFile = new File(DOWNLOAD_SD_PATH + name + ".apk");
		} else {
			updateDir = new File(DOWNLOAD_DATA_PATH);
			updateFile = new File(DOWNLOAD_DATA_PATH + name + ".apk");
		}

		if (!updateDir.exists()) {
			updateDir.mkdirs();
		}
		if (!updateFile.exists()) {
			try {
				updateFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param is
	 * @param os
	 */
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 写文件
	 * 
	 * @param pathName
	 *            文件路径 + JSON内容
	 * @param content
	 */
	public static void writeFile(String pathName, String content) {
		File file = new File(pathName);
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param pathName
	 *            文件路径+名字
	 * @return
	 */
	public static InputStream readFile(String pathName) {
		File file = new File(pathName);
		FileInputStream fis = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return fis;
	}

	/**
	 * 读取文本数据
	 *
	 * @param context
	 *            程序上下文
	 * @param fileName
	 *            文件名
	 * @return String, 读取到的文本内容，失败返回null
	 */
	public static String readAssets(Context context, String fileName)
	{
		InputStream is = null;
		String content = null;
		try
		{
			is = context.getAssets().open(fileName);
			if (is != null)
			{

				byte[] buffer = new byte[1024];
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				while (true)
				{
					int readLength = is.read(buffer);
					if (readLength == -1) break;
					arrayOutputStream.write(buffer, 0, readLength);
				}
				is.close();
				arrayOutputStream.close();
				content = new String(arrayOutputStream.toByteArray());

			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			content = null;
		}
		finally
		{
			try
			{
				if (is != null) is.close();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param pathName
	 *            文件路径
	 * @return
	 */
	public static String readFileString(String pathName) {
		File file = new File(pathName);
		FileInputStream fis = null;
		BufferedReader reader = null;
		String string = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					builder.append(s);
				}
				string = builder.toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return string;
	}

	/**
	 * 保存图片
	 * 
	 * @param path
	 * @param name
	 * @param bitmap
	 */
	public static void writeBitmap(String path, String name, Bitmap bitmap) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		File _file = new File(path + name);
		if (_file.exists()) {
			_file.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
			if (name != null && !"".equals(name)) {
				int index = name.lastIndexOf(".");
				if (index != -1 && (index + 1) < name.length()) {
					String extension = name.substring(index + 1).toLowerCase();
					if ("png".equals(extension)) {
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					} else if ("jpg".equals(extension)
							|| "jpeg".equals(extension)) {
						bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取图片
	 * 
	 * @param pathName
	 * @return
	 */
	public static Bitmap readBitmap(String pathName) {
		InputStream in = readFile(pathName);
		if (in != null) {
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}
		return null;
	}

	/**
	 * 获取文件名
	 * 
	 * @param filePathName
	 * @return
	 */
	public static String getFileName(String filePathName) {
		if (filePathName != null && !"".equals(filePathName)) {
			int index = filePathName.lastIndexOf('/');
			if (index != -1 && (index + 1) < filePathName.length()) {
				return filePathName.substring(index + 1);
			}
		}
		return "";
	}

	/**
	 * 修改文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String modifyFileName(String fileName) {
		if (fileName == null)
			return null;
		String s = "\\/:*?\"<>|";
		StringBuffer sb = new StringBuffer();
		char[] chars = fileName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (s.indexOf(chars[i]) == -1) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 计算文件夹大小
	 * 
	 * @param f
	 * @return
	 */
	public static long getFileLength(File f) {
		long length = 0;
		if (f != null && f.isDirectory()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].isFile()) {
					length += list[i].length();
				}
			}
		}
		return length;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file != null && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 递归删除文件夹
	 * 
	 * @param f
	 */
	public static void delFile(File f) {
		if (f.isDirectory()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					delFile(list[i]);
				} else {
					if (list[i].isFile()) {
						list[i].delete();
					}
				}
			}
			f.delete();
		} else {
			if (f.isFile())
				f.delete();
		}
	}

	/**
	 * 判断是否存在SD卡
	 * 
	 * @return true，有SD卡。false，无SD卡
	 */
	public static boolean isHaveSDCard() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断SD卡空间是否满
	 * 
	 * @return
	 */
	public static boolean storageIsFull() {
		StatFs fs = new StatFs(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		return !(fs.getAvailableBlocks() > 1);
	}
}
