package com.openxu.oxlib.encryption;

/**
 * author : openXu
 * create at : 2017/3/6 13:41
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : oxlib
 * class name : Base64Util
 * version : 1.0
 * class describe：base64编解码
 */
public class Base64Util {

    /**
     * BASE64解码
     * @param base64
     */
    public static byte[] decode(String base64){
        return android.util.Base64.decode(base64.getBytes(), android.util.Base64.DEFAULT);
    }
    
    /**
     * Base64编码
     * @param bytes
     */
    public static String encode(byte[] bytes){
    	if(bytes==null)
    		return "";
        return new String(android.util.Base64.encode(bytes, android.util.Base64.DEFAULT));
    }
    
 /*   public static String byte2Hax(byte[] in){
		String out = "";
		for(int i = 0;i<in.length;i++){
			out += byteToHexString(in[i]);
		}
		return out;
	}
	
	// 将字节转换为十六进制字符串
    public static  String byteToHexString(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}*/
    
}