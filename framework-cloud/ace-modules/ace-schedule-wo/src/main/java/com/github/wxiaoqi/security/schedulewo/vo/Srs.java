package com.github.wxiaoqi.security.schedulewo.vo;

/**
 * 
* @author xufeng 
* @Description: 服务资源
* @date 2015-6-4 上午11:39:20 
* @version V1.0  
*
 */
public class Srs {
	
	private String id;
	private String objectType;//服务资源类型  
	private String status;
	private String objectId;
	private String userId; //服务资源对应的用户ID

	public String getId() {
		return id;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Srs() {
		super();
	}
	
}
