package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品购物车
 * 
 * @author zxl
 * @Date 2018-12-12 17:43:04
 */
@Table(name = "biz_shopping_cart")
public class BizShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_Id")
    private String userId;

	//公司ID
	@Column(name = "project_id")
	private String projectId;
	
	    //公司ID
    @Column(name = "company_Id")
    private String companyId;
	
	    //公司名称
    @Column(name = "company_Name")
    private String companyName;
	
	    //产品ID
    @Column(name = "product_Id")
    private String productId;
	
	    //产品名称
    @Column(name = "product_Name")
    private String productName;
	
	    //金额
    @Column(name = "product_price")
    private BigDecimal productPrice;
	
	    //数量
    @Column(name = "product_Num")
    private BigDecimal productNum;
	
	    //规格ID
    @Column(name = "spec_Id")
    private String specId;
	
	    //规格编码
    @Column(name = "spec_Code")
    private String specCode;
	
	    //规格
    @Column(name = "spec_Name")
    private String specName;
	
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

    @Column(name = "app_type")
    private Integer appType;

    @Column(name = "open_id")
    private String openId;
	

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
	 * 设置：公司ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：公司ID
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：公司名称
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 获取：公司名称
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * 设置：产品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品ID
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置：产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：金额
	 */
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	/**
	 * 获取：金额
	 */
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	/**
	 * 设置：数量
	 */
	public void setProductNum(BigDecimal productNum) {
		this.productNum = productNum;
	}
	/**
	 * 获取：数量
	 */
	public BigDecimal getProductNum() {
		return productNum;
	}
	/**
	 * 设置：规格ID
	 */
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	/**
	 * 获取：规格ID
	 */
	public String getSpecId() {
		return specId;
	}
	/**
	 * 设置：规格编码
	 */
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	/**
	 * 获取：规格编码
	 */
	public String getSpecCode() {
		return specCode;
	}
	/**
	 * 设置：规格
	 */
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	/**
	 * 获取：规格
	 */
	public String getSpecName() {
		return specName;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
