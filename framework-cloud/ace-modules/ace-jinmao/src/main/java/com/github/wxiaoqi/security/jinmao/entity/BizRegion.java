package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 省市区区域表
 * 
 * @author zxl
 * @Date 2018-12-18 15:19:48
 */
@Table(name = "biz_region")
public class BizRegion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //区域ID
    @Id
    private String id;
	
	    //上级区域ID
    @Column(name = "region_PId")
    private String regionPid;
	
	    //区域编码
    @Column(name = "region_Code")
    private String regionCode;
	
	    //区域名称
    @Column(name = "region_Name")
    private String regionName;
	
	    //
    @Column(name = "region_FullName")
    private String regionFullname;
	
	    //经度
    @Column(name = "longitude")
    private String longitude;
	
	    //纬度
    @Column(name = "latitude")
    private String latitude;
	
	    //简称
    @Column(name = "short_Name")
    private String shortName;
	
	    //拼音
    @Column(name = "pingyin_Name")
    private String pingyinName;
	
	    //拼音首字母
    @Column(name = "ipingyin_Name")
    private String ipingyinName;
	
	    //围合包
    @Column(name = "enclosed_package")
    private String enclosedPackage;
	
	    //是否启用
    @Column(name = "is_enabled")
    private String isEnabled;
	
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
	 * 设置：区域ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：区域ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：上级区域ID
	 */
	public void setRegionPid(String regionPid) {
		this.regionPid = regionPid;
	}
	/**
	 * 获取：上级区域ID
	 */
	public String getRegionPid() {
		return regionPid;
	}
	/**
	 * 设置：区域编码
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * 获取：区域编码
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * 设置：区域名称
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * 获取：区域名称
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * 设置：
	 */
	public void setRegionFullname(String regionFullname) {
		this.regionFullname = regionFullname;
	}
	/**
	 * 获取：
	 */
	public String getRegionFullname() {
		return regionFullname;
	}
	/**
	 * 设置：经度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：经度
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置：纬度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：纬度
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置：简称
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * 获取：简称
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * 设置：拼音
	 */
	public void setPingyinName(String pingyinName) {
		this.pingyinName = pingyinName;
	}
	/**
	 * 获取：拼音
	 */
	public String getPingyinName() {
		return pingyinName;
	}
	/**
	 * 设置：拼音首字母
	 */
	public void setIpingyinName(String ipingyinName) {
		this.ipingyinName = ipingyinName;
	}
	/**
	 * 获取：拼音首字母
	 */
	public String getIpingyinName() {
		return ipingyinName;
	}
	/**
	 * 设置：围合包
	 */
	public void setEnclosedPackage(String enclosedPackage) {
		this.enclosedPackage = enclosedPackage;
	}
	/**
	 * 获取：围合包
	 */
	public String getEnclosedPackage() {
		return enclosedPackage;
	}
	/**
	 * 设置：是否启用
	 */
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	/**
	 * 获取：是否启用
	 */
	public String getIsEnabled() {
		return isEnabled;
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
