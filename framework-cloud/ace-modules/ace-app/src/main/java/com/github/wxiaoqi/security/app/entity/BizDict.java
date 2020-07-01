package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 业务数据字典
 * 
 * @author huangxl
 * @Date 2019-02-14 15:53:33
 */
@Table(name = "biz_dict")
public class BizDict implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //数据字典编码
    @Id
    private String id;
	
	    //父ID编码
    @Column(name = "pid")
    private String pid;
	
	    //值
    @Column(name = "val")
    private String val;
	
	    //名称
    @Column(name = "name")
    private String name;
	
	    //英文名称
    @Column(name = "en_name")
    private String enName;
	
	    //排序
    @Column(name = "view_sort")
    private Integer viewSort;
	
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
	 * 设置：数据字典编码
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：数据字典编码
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：父ID编码
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父ID编码
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置：值
	 */
	public void setVal(String val) {
		this.val = val;
	}
	/**
	 * 获取：值
	 */
	public String getVal() {
		return val;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：英文名称
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}
	/**
	 * 获取：英文名称
	 */
	public String getEnName() {
		return enName;
	}
	/**
	 * 设置：排序
	 */
	public void setViewSort(Integer viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getViewSort() {
		return viewSort;
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
