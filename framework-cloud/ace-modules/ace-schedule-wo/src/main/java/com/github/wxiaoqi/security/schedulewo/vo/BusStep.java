package com.github.wxiaoqi.security.schedulewo.vo;

/**
 * 
* @author xufeng 
* @Description: 业务工序
* @date 2018-12-05 10:24:31
* @version V1.0  
*
 */
public class BusStep {

	private String id;//
	private String busId;//业务id
	private String busStage;//阶段
	private int standardTime;//标准工时
	private String viewSort;//序号
	private String stepName;//工序名称
	private String status;//状态
	
	/*扩展业务字段*/
	private String skillId;//技能
	private String crontab;//cron表达式  调度
	private int dispatchTime;//匹配次数 调度
	private int minutes;//执行间隔时间（单位：分钟）
	/*扩展业务字段*/
	
	public String getCrontab() {
		return crontab;
	}
	public int getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(int dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public void setCrontab(String crontab) {
		this.crontab = crontab;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getBusStage() {
		return busStage;
	}
	public void setBusStage(String busStage) {
		this.busStage = busStage;
	}
	public int getStandardTime() {
		return standardTime;
	}
	public void setStandardTime(int standardTime) {
		this.standardTime = standardTime;
	}
	public String getViewSort() {
		return viewSort;
	}
	public void setViewSort(String viewSort) {
		this.viewSort = viewSort;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSkillId() {
		return skillId;
	}
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public BusStep() {
		super();
	}
	
}
