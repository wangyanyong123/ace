package com.github.wxiaoqi.security.external.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 道闸表
 * 
 * @author zxl
 * @Date 2019-01-03 10:34:15
 */
@Table(name = "biz_facilities")
public class BizFacilities implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //围合ID
    @Column(name = "enclosed_id")
    private String enclosedId;
	
	    //编码
    @Column(name = "facilities_code")
    private String facilitiesCode;
	
	    //设备设施名称
    @Column(name = "facilities_name")
    private String facilitiesName;
	
	    //设备类型(1-进门禁闸机，2-出门禁闸机)
    @Column(name = "type")
    private String type;
	
	    //是否私有设备
    @Column(name = "is_private")
    private String isPrivate;
	
	    //备注
    @Column(name = "remark")
    private String remark;
	
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
	 * 设置：围合ID
	 */
	public void setEnclosedId(String enclosedId) {
		this.enclosedId = enclosedId;
	}
	/**
	 * 获取：围合ID
	 */
	public String getEnclosedId() {
		return enclosedId;
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
	 * 设置：设备设施名称
	 */
	public void setFacilitiesName(String facilitiesName) {
		this.facilitiesName = facilitiesName;
	}
	/**
	 * 获取：设备设施名称
	 */
	public String getFacilitiesName() {
		return facilitiesName;
	}
	/**
	 * 设置：设备类型(1-进门禁闸机，2-出门禁闸机)
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：设备类型(1-进门禁闸机，2-出门禁闸机)
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：是否私有设备
	 */
	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}
	/**
	 * 获取：是否私有设备
	 */
	public String getIsPrivate() {
		return isPrivate;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
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
