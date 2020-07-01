package com.github.wxiaoqi.security.schedulewo.vo;

import java.util.Date;

public class SrsWoStep {
    private String id;
    
    private String woId;
    
    private String busStep;

    private String busWoStepId;

    private String description;
    
    private String imgId;
    
    private Double cost;
    
    private Date begTime;

    private Date endTime;

    private String status;

    private Date timeStamp;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusStep() {
        return busStep;
    }

    public void setBusStep(String busStep) {
        this.busStep = busStep == null ? null : busStep.trim();
    }

    public String getBusWoStepId() {
        return busWoStepId;
    }

    public void setBusWoStepId(String busWoStepId) {
        this.busWoStepId = busWoStepId == null ? null : busWoStepId.trim();
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc == null ? null : desc.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}