package com.github.wxiaoqi.security.common.util;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {

	private static AESUtil instance = null;

	private AESUtil() {

	}

	public static AESUtil getInstance() {
		if (instance == null)
			instance = new AESUtil();
		return instance;
	}

	public static String Encrypt(String encData, String secretKey, String vector)
			throws Exception {

		if (secretKey == null) {
			return null;
		}
		if (secretKey.length() != 16) {
			return null;
		}
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = secretKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}

	// 加密
	public String encrypt(String sSrc, String sKey, String ivParameter) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}

	// 解密
	public String decrypt(String sSrc, String sKey, String ivParameter) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String sKey = createKey();
		String ivParameter = sKey;//createIvParameter();
		System.out.println(sKey);
		// 需要加密的字串
		//String cSrc = "[{\"request_no\":\"1001\",\"service_code\":\"FS0001\",\"contract_id\":\"100002\",\"order_id\":\"0\",\"phone_id\":\"13913996922\",\"plat_offer_id\":\"100094\",\"channel_id\":\"1\",\"activity_id\":\"100045\"}]";
		String cSrc = "支付宝支付信息加密测试";
		// 加密
		long lStart = System.currentTimeMillis();
		String enString = AESUtil.getInstance().encrypt(cSrc, sKey, ivParameter);
		System.out.println("加密后的字串是：" + enString);

		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");
		// 解密
		lStart = System.currentTimeMillis();
		String DeString = AESUtil.getInstance().decrypt(enString, sKey, ivParameter);
		System.out.println("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");
		System.out.println("a0B9o+u0XQbaWiRe7g1f/xQQWse0Owt6KypDHv4odCGQYDjJDsYdfvbveaQy4Rw4IlXBVDXmN8+Bn4kyq4KcyW/ryxxe2TbE7JHCzJ7XLJv9SrtN6usJzhwUFi0M8xAr0yTnXVmpI/npCvqgSUMPiR+VvE/pL7nRhMbL4NVfvQiPBGyppcMwpzIHHUMgHrBib0n1NW7avq+gEDKkXkYM3nJakaCA+SIex8GGtmln2PQ=".equals("a0B9o+u0XQbaWiRe7g1f/xQQWse0Owt6KypDHv4odCGQYDjJDsYdfvbveaQy4Rw4IlXBVDXmN8+Bn4kyq4KcyW/ryxxe2TbE7JHCzJ7XLJv9SrtN6usJzhwUFi0M8xAr0yTnXVmpI/npCvqgSUMPiR+VvE/pL7nRhMbL4NVfvQiPBGyppcMwpzIHHUMgHrBib0n1NW7avq+gEDKkXkYM3nJakaCA+SIex8GGtmln2PQ="));
	}
	
	/**
	 * 生成16的加密密码
	 * @return
	 */
	public static String createKey(){
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < 16; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	}
	
	/**
	 * 生成16位的偏移量
	 * @return
	 */
	public static String createIvParameter(){
		String base = "0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < 16; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	}
}
