package com.openxu.oxlib.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : SHA1
 * version : 1.0
 * class describe：SHA1散列值算法
 */
public class SHA1 {
	
/*	public static String encode(String text) throws NoSuchAlgorithmException {
		byte[] result = eccrypt(text);
		return byteArrayToHexString(result);
	}*/

	public static byte[] eccrypt(byte[] info) throws NoSuchAlgorithmException {
		MessageDigest SHA1 = MessageDigest.getInstance("SHA-1");
//		byte[] srcBytes = info.getBytes();
		SHA1.update(info);
		byte[] resultBytes = SHA1.digest();
		return resultBytes;
	}

	@SuppressWarnings("unused")
	private static String byteArrayToHexString(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexString(bytearray[i]);
		}
		return strDigest;
	}

	private static String byteToHexString(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

}