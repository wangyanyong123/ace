package com.github.wxiaoqi.security.report.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 用户下载量表
 * 
 * @author zxl
 * @Date 2019-01-29 14:25:41
 */
@Table(name = "report_download_statistics")
public class ReportDownloadStatistics implements Serializable {
	private static final long serialVersionUID = -9159001555362538700L;

	//ID
	@Id
	private String id;

	//日期
	@Column(name = "statistical_date")
	private String statisticalDate;

	//总注册量
	@Column(name = "total_register_num")
	private Integer totalRegisterNum;

	//注册未认证量
	@Column(name = "unauth_num")
	private Integer unauthNum;

	//认证量
	@Column(name = "auth_num")
	private Integer authNum;

	//当天新增注册数量
	@Column(name = "today_register_num")
	private Integer todayRegisterNum;

	//当天注册未认证量
	@Column(name = "today_unauth_num")
	private Integer todayUnauthNum;

	//当天新增认证量
	@Column(name = "today_auth_num")
	private Integer todayAuthNum;

	//状态(0-删除，1-正常)
	@Column(name = "status")
	private String status;

	//创建日期
	@Column(name = "create_time")
	private Date createTime;

	//修改日期
	@Column(name = "modify_time")
	private Date modifyTime;


	/**
	 * 设置：ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：日期
	 */
	public void setStatisticalDate(String statisticalDate) {
		this.statisticalDate = statisticalDate;
	}
	/**
	 * 获取：日期
	 */
	public String getStatisticalDate() {
		return statisticalDate;
	}
	/**
	 * 设置：总注册量
	 */
	public void setTotalRegisterNum(Integer totalRegisterNum) {
		this.totalRegisterNum = totalRegisterNum;
	}
	/**
	 * 获取：总注册量
	 */
	public Integer getTotalRegisterNum() {
		return totalRegisterNum;
	}
	/**
	 * 设置：注册未认证量
	 */
	public void setUnauthNum(Integer unauthNum) {
		this.unauthNum = unauthNum;
	}
	/**
	 * 获取：注册未认证量
	 */
	public Integer getUnauthNum() {
		return unauthNum;
	}
	/**
	 * 设置：认证量
	 */
	public void setAuthNum(Integer authNum) {
		this.authNum = authNum;
	}
	/**
	 * 获取：认证量
	 */
	public Integer getAuthNum() {
		return authNum;
	}
	/**
	 * 设置：当天新增注册数量
	 */
	public void setTodayRegisterNum(Integer todayRegisterNum) {
		this.todayRegisterNum = todayRegisterNum;
	}
	/**
	 * 获取：当天新增注册数量
	 */
	public Integer getTodayRegisterNum() {
		return todayRegisterNum;
	}
	/**
	 * 设置：当天注册未认证量
	 */
	public void setTodayUnauthNum(Integer todayUnauthNum) {
		this.todayUnauthNum = todayUnauthNum;
	}
	/**
	 * 获取：当天注册未认证量
	 */
	public Integer getTodayUnauthNum() {
		return todayUnauthNum;
	}
	/**
	 * 设置：当天新增认证量
	 */
	public void setTodayAuthNum(Integer todayAuthNum) {
		this.todayAuthNum = todayAuthNum;
	}
	/**
	 * 获取：当天新增认证量
	 */
	public Integer getTodayAuthNum() {
		return todayAuthNum;
	}
	/**
	 * 设置：状态(0-删除，1-正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0-删除，1-正常)
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改日期
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改日期
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
}
