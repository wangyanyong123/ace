package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 运营服务-签到规则表
 * 
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@Table(name = "biz_user_sign_rule")
public class BizUserSignRule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //编码
    @Column(name = "code")
    private String code;
	
	    //普通签到积分值
    @Column(name = "integral")
    private Integer integral;
	
	    //连续签到积分值
    @Column(name = "continue_integral")
    private Integer continueIntegral;
	
	    //签到类型(1-正常签到2-连续签到)
    @Column(name = "sign_type")
    private String signType;
	
	    //连续签到天数
    @Column(name = "sign_day")
    private Integer signDay;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_Stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
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
	 * 设置：编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：普通签到积分值
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	/**
	 * 获取：普通签到积分值
	 */
	public Integer getIntegral() {
		return integral;
	}
	/**
	 * 设置：连续签到积分值
	 */
	public void setContinueIntegral(Integer continueIntegral) {
		this.continueIntegral = continueIntegral;
	}
	/**
	 * 获取：连续签到积分值
	 */
	public Integer getContinueIntegral() {
		return continueIntegral;
	}
	/**
	 * 设置：签到类型(1-正常签到2-连续签到)
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}
	/**
	 * 获取：签到类型(1-正常签到2-连续签到)
	 */
	public String getSignType() {
		return signType;
	}
	/**
	 * 设置：连续签到天数
	 */
	public void setSignDay(Integer signDay) {
		this.signDay = signDay;
	}
	/**
	 * 获取：连续签到天数
	 */
	public Integer getSignDay() {
		return signDay;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：时间戳
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：时间戳
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
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
	 * 设置：修改人
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：修改人
	 */
	public String getModifyBy() {
		return modifyBy;
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
