package com.github.wxiaoqi.security.external.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:21 2018/12/29
 * @Modified By:
 */
public class PassportInfo implements Serializable {

	private static final long serialVersionUID = -7432265064706365665L;

	private String newQrVal;//最新的二维码值（共码）
	private String newQrNum;//最新的二维码编码（共码）
//	private String newPrivateQrVal;//最新的二维码值（私码）
//	private String newPrivateQrNum;//最新的二维码编码（私码）
	private String token;//用户登录token
	private String userId;//用户id
//	private String qrType;//认证状态
	private String effTime;//生效时间
	private String loseTime;//失效时间
	private int useTimes;
//	private Date privateIssuedTime;//私码下发时间
	private String passType;//通行类型
	private String qrId;//QR ID值

	public void setPassType(String p){
		this.passType = p ;
	}
	public String getPassType(){
		return passType;
	}
//	public Date getPrivateIssuedTime() {
//		return privateIssuedTime;
//	}
//	public void setPrivateIssuedTime(Date privateIssuedTime) {
//		this.privateIssuedTime = privateIssuedTime;
//	}
	public int getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}
//	public String getQrType() {
//		return qrType;
//	}
//	public void setQrType(String qrType) {
//		this.qrType = qrType;
//	}
	public String getLoseTime() {
		return loseTime;
	}
	public void setLoseTime(String loseTime) {
		this.loseTime = loseTime;
	}
	public String getNewQrNum() {
		return newQrNum;
	}
	public void setNewQrNum(String newQrNum) {
		this.newQrNum = newQrNum;
	}
	public String getNewQrVal() {
		return newQrVal;
	}
	public void setNewQrVal(String newQrVal) {
		this.newQrVal = newQrVal;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
//	public String getNewPrivateQrVal() {
//		return newPrivateQrVal;
//	}
//	public void setNewPrivateQrVal(String newPrivateQrVal) {
//		this.newPrivateQrVal = newPrivateQrVal;
//	}
//	public String getNewPrivateQrNum() {
//		return newPrivateQrNum;
//	}
//	public void setNewPrivateQrNum(String newPrivateQrNum) {
//		this.newPrivateQrNum = newPrivateQrNum;
//	}
	public String getQrId() {
		return qrId;
	}
	public void setQrId(String qrId) {
		this.qrId = qrId;
	}
	public String getEffTime() {
		return effTime;
	}
	public void setEffTime(String effTime) {
		this.effTime = effTime;
	}

}
