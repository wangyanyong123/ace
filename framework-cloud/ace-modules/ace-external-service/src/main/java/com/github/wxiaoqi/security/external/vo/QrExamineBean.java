package com.github.wxiaoqi.security.external.vo;

import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:05 2019/1/3
 * @Modified By:
 */
public class QrExamineBean {
	// 校验结果类型 0:无效 1:有效  2 :无权限
	private String rType;

	// 校验结果 0:成功 1:二维码不存在 2:二维码已删除 3:二维码已过期 4:二维码已超过使用次数 5：非法二维码 6:没有开通此围合权限
	private String rdesc;

	// 进出类型 0:进 1：出
	private String passType;

	// 刷码地址
	private String passAddr;

	private String id;// varchar(36) 二维码ID
	private String qrType;// varchar(36) 二维码类型
	private String qrNum;// varchar(64) 输入值
	private String qrVal;// varchar(64) 值
	private Date effTime;// datetime 生效时间
	private Date loseTime;// datetime 失效时间
	private int validTims;// int(11) 有效次数
	private int useTimes;// int(11) 已使用次数
	private int surTimes;// int(11) 剩余次数
	private Date preUseTime;// datetime 上次使用时间

	private String privateNum;// 私码值
	private String privateVal;// 私码值
	private Date privateIssuedTime;// 私码下发时间
	private String Status;// 状态
	private String createBy;// 创建人

	private String isPrivate;// 道闸私有属性

	public String getrType() {
		return rType;
	}

	public void setrType(String rType) {
		this.rType = rType;
	}

	public String getRdesc() {
		return rdesc;
	}

	public void setRdesc(String rdesc) {
		this.rdesc = rdesc;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public String getPassAddr() {
		return passAddr;
	}

	public void setPassAddr(String passAddr) {
		this.passAddr = passAddr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
	}

	public String getQrNum() {
		return qrNum;
	}

	public void setQrNum(String qrNum) {
		this.qrNum = qrNum;
	}

	public String getQrVal() {
		return qrVal;
	}

	public void setQrVal(String qrVal) {
		this.qrVal = qrVal;
	}

	public Date getEffTime() {
		return effTime;
	}

	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}

	public Date getLoseTime() {
		return loseTime;
	}

	public void setLoseTime(Date loseTime) {
		this.loseTime = loseTime;
	}

	public int getValidTims() {
		return validTims;
	}

	public void setValidTims(int validTims) {
		this.validTims = validTims;
	}

	public int getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}

	public int getSurTimes() {
		return surTimes;
	}

	public void setSurTimes(int surTimes) {
		this.surTimes = surTimes;
	}

	public Date getPreUseTime() {
		return preUseTime;
	}

	public void setPreUseTime(Date preUseTime) {
		this.preUseTime = preUseTime;
	}

	public String getPrivateNum() {
		return privateNum;
	}

	public void setPrivateNum(String privateNum) {
		this.privateNum = privateNum;
	}

	public String getPrivateVal() {
		return privateVal;
	}

	public void setPrivateVal(String privateVal) {
		this.privateVal = privateVal;
	}

	public Date getPrivateIssuedTime() {
		return privateIssuedTime;
	}

	public void setPrivateIssuedTime(Date privateIssuedTime) {
		this.privateIssuedTime = privateIssuedTime;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}
}
