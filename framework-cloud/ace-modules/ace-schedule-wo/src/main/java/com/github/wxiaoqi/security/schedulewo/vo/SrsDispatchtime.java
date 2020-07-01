package com.github.wxiaoqi.security.schedulewo.vo;

import java.util.Date;

/**
 * 
* @author xufeng 
* @Description: 调度时间
* @date 2015-6-10 上午11:45:25 
* @version V1.0  
*
 */
public class SrsDispatchtime implements Comparable<SrsDispatchtime>{
	
	private String serviceId;
	private Date begTime;
	private Date endTime;
	private long workTime;
	
	public long getWorkTime() {
		return workTime;
	}
	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Date getBegTime() {
		return begTime;
	}
	public void setBegTime(Date begTime) {
		this.begTime = begTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public SrsDispatchtime() {
		super();
	}
	/*
	 * (non-Javadoc) 根据工作时长排序
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SrsDispatchtime o) {
		return Integer.parseInt(o.getWorkTime()/1000 + "");
	}
	
}
