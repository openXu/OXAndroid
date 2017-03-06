package com.openxu.oxlib.encryption;

import java.security.SecureRandom;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : AES
 * version : 1.0
 * class describe：AES对称加密
 */
public class AES {

	public static String ALGORITHM = "AES";

	public static byte[] getKey(int keylen) {
		byte[] pasw = new byte[keylen];
		Random rand = new Random();
		for(int i=0;i<keylen;i++){
			pasw[i] = (byte) (rand.nextInt()&0xFF);
		}
		return pasw;
	}

	public static byte[] getIv(int ivlen) {
		byte[] pasw = new byte[ivlen];
		Random rand = new Random();
		for(int i=0;i<ivlen;i++){
			pasw[i] = (byte) (rand.nextInt()&0xFF);
		}
		return pasw;
	}
	
	/**
	 * 获得Key
	 * @param pasw 密钥值
	 * @return
	 */
	private static SecretKeySpec setSecretKey(byte[] pasw) throws Exception {
		SecretKeySpec key = null;
		KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		sr.setSeed(pasw);
		kgen.init(128, sr);// 192 and 256 bits may not be available
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		key = new SecretKeySpec(enCodeFormat, ALGORITHM);
		return key;
	}

	/**
	 * 加密
	 */
	public static byte[] encrypt(String MODE, byte[] data, byte[] pasw,
			IvParameterSpec iv) throws Exception {
		/**
		 * 用AES去加解密，发现在4.x以上的版本的android系统里都会报这个错误
		 * **javax.crypto.BadPaddingException: pad block corrupted** 异常原因：
		 * 异常消息是误导性的。如果你的密码编码的密码库的早期版本，更高版本的密码库， 然后尝试解码的密码，将引发异常（如“填充”异常你发布）。时，
		 * 我遇到这样的转换到新版本的Android。你的情况， 我建议更换新的密码库的版本加密的值的加密值。
		 * 总之，这个版本库的密码可能不匹配，因此，“填充”的算法不匹配。
		 */
		/*
		 * KeyGenerator kgen = KeyGenerator.getInstance("AES"); kgen.init(128,
		 * new SecureRandom(password.getBytes())); SecretKey secretKey =
		 * kgen.generateKey(); byte[] enCodeFormat = secretKey.getEncoded();
		 * SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES")
		 */
		SecretKeySpec key = setSecretKey(pasw);
		Cipher cipher = Cipher.getInstance(MODE);// 创建密码器
		if (iv != null)
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		else
			cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(data);
		return result; // 加密
	}

	/**
	 * 解密
	 */
	public static byte[] decrypt(String MODE, byte[] data, byte[] pasw,
			IvParameterSpec iv) throws Exception {
		SecretKeySpec key = setSecretKey(pasw);
		Cipher cipher = Cipher.getInstance(MODE);// 创建密码器
		if (iv != null)
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
		else
			cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(data);
		return result;
	}

	/**
	 * 加密 这种加密方式有两种限制 密钥必须是16位的 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出异常：
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	/*
	 * public static byte[] encrypt2(String content, String password) { try {
	 * SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES"); Cipher
	 * cipher = Cipher.getInstance("AES/ECB/NoPadding"); byte[] byteContent =
	 * content.getBytes("utf-8"); cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
	 * byte[] result = cipher.doFinal(byteContent); return result; // 加密 } catch
	 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
	 * (NoSuchPaddingException e) { e.printStackTrace(); } catch
	 * (InvalidKeyException e) { e.printStackTrace(); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } catch
	 * (IllegalBlockSizeException e) { e.printStackTrace(); } catch
	 * (BadPaddingException e) { e.printStackTrace(); } return null; }
	 */
	

	/**
	 * 蓝牙key应用层加密使用的AES_OFB_128
	 * 加密
	 */
	public static byte[] encrypt_Ofb128(byte[] data, byte[] keyDat,
			byte[] ivData){
		try {
//			SecretKeySpec key = setSecretKey(keyDat);   //不需要编码
			SecretKeySpec key = new SecretKeySpec(keyDat, "AES");
//			Cipher cipher = Cipher.getInstance("AES/OFB/ISO10126Padding");// 创建密码器
			Cipher cipher = Cipher.getInstance("AES/OFB/Nopadding");// 创建密码器
//			Cipher cipher = Cipher.getInstance("AES/OFB/PKCS5Padding");// 创建密码器
			IvParameterSpec iv = new IvParameterSpec(ivData);
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			byte[] result = cipher.doFinal(data);
			return result; // 加密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 */
	public static byte[] decrypt_Ofb128(byte[] data, byte[] keyDat,
			byte[] ivData){
		try {
			SecretKeySpec key = new SecretKeySpec(keyDat, "AES");
			Cipher cipher = Cipher.getInstance("AES/OFB/Nopadding");// 创建密码器
			IvParameterSpec iv = new IvParameterSpec(ivData);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] result = cipher.doFinal(data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
}
