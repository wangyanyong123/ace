package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 好物探访表
 * 
 * @author zxl
 * @Date 2018-12-13 09:56:57
 */
@Table(name = "biz_product_goodto_visit")
public class BizProductGoodtoVisit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //用户id
    @Id
    private String id;
	
	    //商品ID
    @Column(name = "product_id")
    private String productId;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //副标题
    @Column(name = "sub_heading")
    private String subHeading;
	
	    //图片详情、内容
    @Column(name = "content")
    private String content;
	
	    //推荐图片
    @Column(name = "recommend_images")
    private String recommendImages;
	
	    //签名
    @Column(name = "signer")
    private String signer;
	
	    //签名头像
    @Column(name = "signer_logo")
    private String signerLogo;
	
	    //发布时间
    @Column(name = "publish_time")
    private Date publishTime;
	
	    //租户id
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //启用状态(0表示禁用，1表示启用)
    @Column(name = "enable_status")
    private String enableStatus;
	
	    //状态：0、删除；1、正常
    @Column(name = "status")
    private String status;
	
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
	 * 设置：用户id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：用户id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：商品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：商品ID
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：副标题
	 */
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}
	/**
	 * 获取：副标题
	 */
	public String getSubHeading() {
		return subHeading;
	}
	/**
	 * 设置：图片详情、内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：图片详情、内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：推荐图片
	 */
	public void setRecommendImages(String recommendImages) {
		this.recommendImages = recommendImages;
	}
	/**
	 * 获取：推荐图片
	 */
	public String getRecommendImages() {
		return recommendImages;
	}
	/**
	 * 设置：签名
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}
	/**
	 * 获取：签名
	 */
	public String getSigner() {
		return signer;
	}
	/**
	 * 设置：签名头像
	 */
	public void setSignerLogo(String signerLogo) {
		this.signerLogo = signerLogo;
	}
	/**
	 * 获取：签名头像
	 */
	public String getSignerLogo() {
		return signerLogo;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublishTime() {
		return publishTime;
	}
	/**
	 * 设置：租户id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户id
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：启用状态(0表示禁用，1表示启用)
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	/**
	 * 获取：启用状态(0表示禁用，1表示启用)
	 */
	public String getEnableStatus() {
		return enableStatus;
	}
	/**
	 * 设置：状态：0、删除；1、正常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0、删除；1、正常
	 */
	public String getStatus() {
		return status;
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
