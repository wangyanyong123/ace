package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品表
 * 
 * @author zxl
 * @Date 2018-12-10 16:38:03
 */
@Table(name = "biz_product")
public class BizProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //商品ID
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "company_id")
    private String companyId;
	
	    //商品编码
    @Column(name = "product_code")
    private String productCode;
	
	    //商品名称
    @Column(name = "product_name")
    private String productName;
	
	    //商品logo
    @Column(name = "product_image")
    private String productImage;
	
	    //商品简介
    @Column(name = "product_summary")
    private String productSummary;
	
	    //商品售后
    @Column(name = "product_after_sale")
    private String productAfterSale;
	
	    //商品图文详情
    @Column(name = "product_imagetext_info")
    private String productImagetextInfo;
	
	    //原价
    @Column(name = "original_price")
    private BigDecimal originalPrice;
	
	    //单价
    @Column(name = "price")
    private BigDecimal price;
	
	    //单位
    @Column(name = "unit")
    private String unit;
	
	    //销量
    @Column(name = "sales")
    private Integer sales;
	
	    //商品总份数
    @Column(name = "product_Num")
    private Integer productNum;
	
	    //成团份数
    @Column(name = "groupbuy_Num")
    private Integer groupbuyNum;
	
	    //团购销售修正数
    @Column(name = "correct_Number")
    private Integer correctNumber;
	
	    //开始时间
    @Column(name = "beg_Time")
    private Date begTime;
	
	    //结束时间
    @Column(name = "end_Time")
    private Date endTime;

	//团购是否成团标识(0-待成团，1-已成团，2-团购结束未成团)
	@Column(name = "is_group_flag")
	private String isGroupFlag;
	
	    //喜欢数量
    @Column(name = "like_Num")
    private Integer likeNum;
	
	    //业务状态(1-待发布，2-待审核，3-已发布，4已驳回，5-已下架）
    @Column(name = "bus_Status")
    private String busStatus;
	
	    //价格描述
    @Column(name = "price_desc")
    private String priceDesc;
	
	    //商品是否推荐，0表示不推荐，1表示推荐
    @Column(name = "is_recommend")
    private String isRecommend;
	
	    //申请时间
    @Column(name = "apply_time")
    private Date applyTime;
	
	    //审核时间
    @Column(name = "audit_time")
    private Date auditTime;
	
	    //发布时间
    @Column(name = "publish_time")
    private Date publishTime;
	
	    //租户ID
    @Column(name = "tenant_id")
    private String tenantId;
	
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
	 * 设置：商品ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：商品ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：商户ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：商户ID
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：商品编码
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 获取：商品编码
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * 设置：商品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：商品logo
	 */
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	/**
	 * 获取：商品logo
	 */
	public String getProductImage() {
		return productImage;
	}
	/**
	 * 设置：商品简介
	 */
	public void setProductSummary(String productSummary) {
		this.productSummary = productSummary;
	}
	/**
	 * 获取：商品简介
	 */
	public String getProductSummary() {
		return productSummary;
	}
	/**
	 * 设置：商品售后
	 */
	public void setProductAfterSale(String productAfterSale) {
		this.productAfterSale = productAfterSale;
	}
	/**
	 * 获取：商品售后
	 */
	public String getProductAfterSale() {
		return productAfterSale;
	}
	/**
	 * 设置：商品图文详情
	 */
	public void setProductImagetextInfo(String productImagetextInfo) {
		this.productImagetextInfo = productImagetextInfo;
	}
	/**
	 * 获取：商品图文详情
	 */
	public String getProductImagetextInfo() {
		return productImagetextInfo;
	}
	/**
	 * 设置：原价
	 */
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	/**
	 * 获取：原价
	 */
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	/**
	 * 设置：单价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：单价
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取：单位
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * 设置：销量
	 */
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	/**
	 * 获取：销量
	 */
	public Integer getSales() {
		return sales;
	}
	/**
	 * 设置：商品总份数
	 */
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	/**
	 * 获取：商品总份数
	 */
	public Integer getProductNum() {
		return productNum;
	}
	/**
	 * 设置：成团份数
	 */
	public void setGroupbuyNum(Integer groupbuyNum) {
		this.groupbuyNum = groupbuyNum;
	}
	/**
	 * 获取：成团份数
	 */
	public Integer getGroupbuyNum() {
		return groupbuyNum;
	}
	/**
	 * 设置：团购销售修正数
	 */
	public void setCorrectNumber(Integer correctNumber) {
		this.correctNumber = correctNumber;
	}
	/**
	 * 获取：团购销售修正数
	 */
	public Integer getCorrectNumber() {
		return correctNumber;
	}
	/**
	 * 设置：开始时间
	 */
	public void setBegTime(Date begTime) {
		this.begTime = begTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getBegTime() {
		return begTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：喜欢数量
	 */
	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
	/**
	 * 获取：喜欢数量
	 */
	public Integer getLikeNum() {
		return likeNum;
	}
	/**
	 * 设置：业务状态(1-待发布，2-待审核，3-已发布，4已驳回，5-已下架）
	 */
	public void setBusStatus(String busStatus) {
		this.busStatus = busStatus;
	}
	/**
	 * 获取：业务状态(1-待发布，2-待审核，3-已发布，4已驳回，5-已下架）
	 */
	public String getBusStatus() {
		return busStatus;
	}
	/**
	 * 设置：价格描述
	 */
	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}
	/**
	 * 获取：价格描述
	 */
	public String getPriceDesc() {
		return priceDesc;
	}
	/**
	 * 设置：商品是否推荐，0表示不推荐，1表示推荐
	 */
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	/**
	 * 获取：商品是否推荐，0表示不推荐，1表示推荐
	 */
	public String getIsRecommend() {
		return isRecommend;
	}
	/**
	 * 设置：申请时间
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * 获取：申请时间
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getAuditTime() {
		return auditTime;
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
	 * 设置：租户ID
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户ID
	 */
	public String getTenantId() {
		return tenantId;
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

	public String getIsGroupFlag() {
		return isGroupFlag;
	}

	public void setIsGroupFlag(String isGroupFlag) {
		this.isGroupFlag = isGroupFlag;
	}
}
