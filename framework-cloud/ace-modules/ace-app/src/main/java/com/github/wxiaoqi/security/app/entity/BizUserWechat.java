package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * @author huangxl
 * @Date 2020-04-12 11:18:57
 */
@Table(name = "biz_user_wechat")
public class BizUserWechat implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private String id;

    //应用id
    @Column(name = "app_id")
    private String appId;

    //应用类型
    @Column(name = "app_type")
    private Integer appType;

    //用户ID
    @Column(name = "user_id")
    private String userId;

    //微信openid
    @Column(name = "open_id")
    private String openId;

    //微信开发平台唯一ID
    @Column(name = "union_id")
    private String unionId;

    //数据状态
    @Column(name = "status")
    private String status;

    //
    @Column(name = "create_time")
    private Date createTime;

    //
    @Column(name = "create_by")
    private String createBy;

    //
    @Column(name = "modify_time")
    private Date modifyTime;

    //
    @Column(name = "modify_by")
    private String modifyBy;


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
     * 设置：用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置：微信openid
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取：微信openid
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置：微信开发平台唯一ID
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    /**
     * 获取：微信开发平台唯一ID
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * 设置：数据状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取：数据状态
     */
    public String getStatus() {
        return status;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}
}
