package com.openxu.oxlib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : BitmapUtil
 * version : 1.0
 * class describe：图片处理工具
 */
public class BitmapUtil {

	private static BitmapFactory.Options opt;

	/**
	 * 将图片id转换为Bitmap列表
	 * 
	 * @param cxt
	 * @param width
	 * @param imgId
	 * @return ArrayList<Bitmap>
	 * @function 防止内存泄露
	 */
	public static ArrayList<Bitmap> id2Bitmap(Context cxt, int width,
											  int[] imgId) {
		ArrayList<Bitmap> lists = new ArrayList<Bitmap>();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeResource(cxt.getResources(), imgId[0],
				options);
		int radio = (int) Math.ceil(options.outWidth / (width * 3 - 30));
		options.inSampleSize = radio;
		if (bmp != null) {
			bmp.recycle();
		}
		options.inJustDecodeBounds = false;
		for (int i = 0; i < imgId.length; i++) {
			bmp = BitmapFactory.decodeResource(cxt.getResources(), imgId[i],
					options);
			lists.add(bmp);
		}
		return lists;
	}

	/**
	 * 以较省内存的方式读取drawable下的图片资源
	 * 
	 * @param a
	 *            activity
	 * @param resId
	 *            图片id
	 * @return bitmap
	 */
	public static Bitmap picResId2Bitmap(Activity a, int resId) {
		if (null == opt) {
			opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Config.RGB_565;// 表示16位位图
															// 565代表对应三原色占的位数
			opt.inInputShareable = true;
			opt.inPurgeable = true;// 设置图片可以被回收
			//
		}
		InputStream is = a.getResources().openRawResource(resId);
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 以较省内存的方式读取drawable下的图片资源
	 * 
	 * @param a
	 *            activity
	 * @param resId
	 *            图片id
	 * @return drawable
	 */
	public static Drawable picResId2Drawable(Activity a, int resId) {
		return new BitmapDrawable(a.getResources(), picResId2Bitmap(a, resId));
	}

	/**
	 * 获取圆角图片
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 
	 * 图片的缩放方法 *
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * 
	 * @param newWidth
	 *            ：缩放后宽度
	 * 
	 * @param newHeight
	 *            ：缩放后高度
	 * 
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bgimage, int newWidth, int newHeight) {

		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		return Bitmap.createBitmap(bgimage, 0, 0, width, height, matrix, true);

	}
}
