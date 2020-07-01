package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 覆盖率统计表
 * 
 * @author huangxl
 * @Date 2019-10-28 09:57:52
 */
@Table(name = "sys_coverage_stat")
public class SysCoverageStat implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //统计日期
    @Column(name = "day")
    private Date day;
	
	    //项目名称
    @Column(name = "project_Name")
    private String projectName;
	
	    //累计认证用户数
    @Column(name = "sum_user_Num")
    private Integer sumUserNum;
	
	    //累计认证户数
    @Column(name = "sum_house_Num")
    private Integer sumHouseNum;
	
	    //新增认证用户数
    @Column(name = "add_user_Num")
    private Integer addUserNum;
	
	    //新增认证户数
    @Column(name = "add_house_Num")
    private Integer addHouseNum;
	
	    //状态
    @Column(name = "STATUS")
    private String status;
	
	    //时间戳
    @Column(name = "TIME_STAMP")
    private Long timeStamp;
	
	    //创建人
    @Column(name = "CREATE_BY")
    private String createBy;
	
	    //创建日期
    @Column(name = "CREATE_TIME")
    private Date createTime;
	
	    //修改人
    @Column(name = "MODIFY_BY")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;
	

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：统计日期
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	/**
	 * 获取：统计日期
	 */
	public Date getDay() {
		return day;
	}
	/**
	 * 设置：项目名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：项目名称
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 设置：累计认证用户数
	 */
	public void setSumUserNum(Integer sumUserNum) {
		this.sumUserNum = sumUserNum;
	}
	/**
	 * 获取：累计认证用户数
	 */
	public Integer getSumUserNum() {
		return sumUserNum;
	}
	/**
	 * 设置：累计认证户数
	 */
	public void setSumHouseNum(Integer sumHouseNum) {
		this.sumHouseNum = sumHouseNum;
	}
	/**
	 * 获取：累计认证户数
	 */
	public Integer getSumHouseNum() {
		return sumHouseNum;
	}
	/**
	 * 设置：新增认证用户数
	 */
	public void setAddUserNum(Integer addUserNum) {
		this.addUserNum = addUserNum;
	}
	/**
	 * 获取：新增认证用户数
	 */
	public Integer getAddUserNum() {
		return addUserNum;
	}
	/**
	 * 设置：新增认证户数
	 */
	public void setAddHouseNum(Integer addHouseNum) {
		this.addHouseNum = addHouseNum;
	}
	/**
	 * 获取：新增认证户数
	 */
	public Integer getAddHouseNum() {
		return addHouseNum;
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
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：时间戳
	 */
	public Long getTimeStamp() {
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
