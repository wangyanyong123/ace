package com.github.wxiaoqi.security.external.vo;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:08 2019/1/3
 * @Modified By:
 */
public class QrHexBean {
	private String userId;
	private String qrCode;
	private String currversion;
	private String qrType;
//	private String externalUserType;
	private String appType;

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCurrversion() {
		return currversion;
	}

	public void setCurrversion(String currversion) {
		this.currversion = currversion;
	}

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
	}
}
