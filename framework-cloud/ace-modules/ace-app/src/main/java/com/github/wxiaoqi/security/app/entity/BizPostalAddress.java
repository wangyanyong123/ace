package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 收货地址
 * 
 * @author huangxl
 * @Date 2018-12-18 18:34:14
 */
@Table(name = "biz_postal_address")
public class BizPostalAddress implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;

	//用户ID
	@Column(name = "user_id")
	private String userId;
	
	    //联系人姓名
    @Column(name = "contact_name")
    private String contactName;
	
	    //联系人手机号码
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //省编码
    @Column(name = "proc_code")
    private String procCode;
	
	    //省名称
    @Column(name = "proc_name")
    private String procName;
	
	    //城市编码
    @Column(name = "city_code")
    private String cityCode;
	
	    //城市名称
    @Column(name = "city_name")
    private String cityName;

	//'区县编码
	@Column(name = "district_code")
	private String districtCode;

	//区县名称
	@Column(name = "district_name")
	private String districtName;

	    //房间ID
    @Column(name = "room_id")
    private String roomId;
	
	    //地址
    @Column(name = "addr")
    private String addr;
	
	    //是否常用(0-否，1-是)
    @Column(name = "is_use")
    private String isUse;
	
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

    @Column(name = "project_id")
    private String projectId;


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
	 * 设置：联系人姓名
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * 获取：联系人姓名
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * 设置：联系人手机号码
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：联系人手机号码
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：省编码
	 */
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	/**
	 * 获取：省编码
	 */
	public String getProcCode() {
		return procCode;
	}
	/**
	 * 设置：省名称
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}
	/**
	 * 获取：省名称
	 */
	public String getProcName() {
		return procName;
	}
	/**
	 * 设置：城市编码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 获取：城市编码
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 设置：城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取：城市名称
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置：房间ID
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	/**
	 * 获取：房间ID
	 */
	public String getRoomId() {
		return roomId;
	}
	/**
	 * 设置：地址
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * 获取：地址
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * 设置：是否常用(0-否，1-是)
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	/**
	 * 获取：是否常用(0-否，1-是)
	 */
	public String getIsUse() {
		return isUse;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
