package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author huangxl
 * @Date 2019-04-02 15:27:58
 */
@Table(name = "biz_pass")
public class BizPass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //二维码值
    @Column(name = "qr_val")
    private String qrVal;
	
	    //二维码类型:1.临时,2.正式,3.访客
    @Column(name = "qr_type")
    private String qrType;
	
	    //通行类型（0:进，1：出）
    @Column(name = "pass_type")
    private String passType;
	
	    //通行状态（1:成功,0：失败）
    @Column(name = "pass_status")
    private String passStatus;
	
	    //通行地址
    @Column(name = "pass_addr")
    private String passAddr;
	
	    //通行状态描述
    @Column(name = "pass_desc")
    private String passDesc;
	
	    //
    @Column(name = "time_stamp")
    private Date timeStamp;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //编码
    @Column(name = "facilities_code")
    private String facilitiesCode;
	
	    //类型（设备类型）
    @Column(name = "facilities_type")
    private String facilitiesType;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //1:成功，2：失败
    @Column(name = "type")
    private String type;
	
	    //原因
    @Column(name = "desc")
    private String desc;
	
	    //开门状态:1未开门，2已开门
    @Column(name = "open_status")
    private String openStatus;
	

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
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
	 * 设置：二维码值
	 */
	public void setQrVal(String qrVal) {
		this.qrVal = qrVal;
	}
	/**
	 * 获取：二维码值
	 */
	public String getQrVal() {
		return qrVal;
	}
	/**
	 * 设置：二维码类型:1.临时,2.正式,3.访客
	 */
	public void setQrType(String qrType) {
		this.qrType = qrType;
	}
	/**
	 * 获取：二维码类型:1.临时,2.正式,3.访客
	 */
	public String getQrType() {
		return qrType;
	}
	/**
	 * 设置：通行类型（0:进，1：出）
	 */
	public void setPassType(String passType) {
		this.passType = passType;
	}
	/**
	 * 获取：通行类型（0:进，1：出）
	 */
	public String getPassType() {
		return passType;
	}
	/**
	 * 设置：通行状态（1:成功,0：失败）
	 */
	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}
	/**
	 * 获取：通行状态（1:成功,0：失败）
	 */
	public String getPassStatus() {
		return passStatus;
	}
	/**
	 * 设置：通行地址
	 */
	public void setPassAddr(String passAddr) {
		this.passAddr = passAddr;
	}
	/**
	 * 获取：通行地址
	 */
	public String getPassAddr() {
		return passAddr;
	}
	/**
	 * 设置：通行状态描述
	 */
	public void setPassDesc(String passDesc) {
		this.passDesc = passDesc;
	}
	/**
	 * 获取：通行状态描述
	 */
	public String getPassDesc() {
		return passDesc;
	}
	/**
	 * 设置：
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * 设置：
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：编码
	 */
	public void setFacilitiesCode(String facilitiesCode) {
		this.facilitiesCode = facilitiesCode;
	}
	/**
	 * 获取：编码
	 */
	public String getFacilitiesCode() {
		return facilitiesCode;
	}
	/**
	 * 设置：类型（设备类型）
	 */
	public void setFacilitiesType(String facilitiesType) {
		this.facilitiesType = facilitiesType;
	}
	/**
	 * 获取：类型（设备类型）
	 */
	public String getFacilitiesType() {
		return facilitiesType;
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
	 * 设置：1:成功，2：失败
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：1:成功，2：失败
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：原因
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取：原因
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置：开门状态:1未开门，2已开门
	 */
	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	/**
	 * 获取：开门状态:1未开门，2已开门
	 */
	public String getOpenStatus() {
		return openStatus;
	}
}
