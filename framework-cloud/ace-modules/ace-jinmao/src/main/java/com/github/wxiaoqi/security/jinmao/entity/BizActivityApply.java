package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 小组活动报名表
 * 
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@Table(name = "biz_activity_apply")
public class BizActivityApply implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //用户名称
    @Column(name = "user_name")
    private String userName;
	
	    //用户电话号码
    @Column(name = "user_mobile")
    private String userMobile;
	
	    //活动ID
    @Column(name = "activity_Id")
    private String activityId;
	
	    //订单ID：收费活动报名此字段才有值
    @Column(name = "sub_id")
    private String subId;
	
	    //签到类型：1=未签到，2=已签到
    @Column(name = "sign_type")
    private String signType;
	
	    //来源渠道(1-客户端APP,2-微信)
    @Column(name = "come_from")
    private String comeFrom;
	
	    //报名状态(0=未报名，1=报名成功，2-取消报名)
    @Column(name = "apply_status")
    private String applyStatus;
	
	    //支付状态(1=待支付，2=已支付，3=退款中，4-退款完成)
    @Column(name = "pay_status")
    private String payStatus;
	
	    //状态(0=删除，1=正常)
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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名称
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：用户电话号码
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	/**
	 * 获取：用户电话号码
	 */
	public String getUserMobile() {
		return userMobile;
	}
	/**
	 * 设置：活动ID
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	/**
	 * 获取：活动ID
	 */
	public String getActivityId() {
		return activityId;
	}
	/**
	 * 设置：订单ID：收费活动报名此字段才有值
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	/**
	 * 获取：订单ID：收费活动报名此字段才有值
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * 设置：签到类型：1=未签到，2=已签到
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}
	/**
	 * 获取：签到类型：1=未签到，2=已签到
	 */
	public String getSignType() {
		return signType;
	}
	/**
	 * 设置：来源渠道(1-客户端APP,2-微信)
	 */
	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}
	/**
	 * 获取：来源渠道(1-客户端APP,2-微信)
	 */
	public String getComeFrom() {
		return comeFrom;
	}
	/**
	 * 设置：报名状态(0=未报名，1=报名成功，2-取消报名)
	 */
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	/**
	 * 获取：报名状态(0=未报名，1=报名成功，2-取消报名)
	 */
	public String getApplyStatus() {
		return applyStatus;
	}
	/**
	 * 设置：支付状态(1=待支付，2=已支付，3=退款中，4-退款完成)
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * 获取：支付状态(1=待支付，2=已支付，3=退款中，4-退款完成)
	 */
	public String getPayStatus() {
		return payStatus;
	}
	/**
	 * 设置：状态(0=删除，1=正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0=删除，1=正常)
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
