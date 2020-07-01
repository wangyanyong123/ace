package com.github.wxiaoqi.security.external.constant;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:49 2019/1/8
 * @Modified By:
 */
public class QRCodeConstant {

	/*
	 * 默认3年
	 */
	public static final int QRCODE_LOSE_TIME_FOREVER = 365*3;
	/**
	 * 二维码状态: 0  未启用
	 * 
	 */	
	public static final String PUB_QR_STATUS_UNUSE = "0";
	
	/**
	 * 二维码状态: 1 启用
	 * 
	 */	
	public static final String PUB_QR_STATUS_USED= "1";
	/*
	 * 
	 * 二维码类型 1：临时 2：正式  Formal
	 */
	public static final String PUB_QR_TYPE_TEMP= "1";
	/**
	 * 二维码类型 1：临时 2：正式  Formal
	 */
	public static final String PUB_QR_TYPE_FORMAL= "2";

	

	
}
