package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 积分商品表
 * 
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@Table(name = "biz_integral_product")
public class BizIntegralProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //商品ID
    @Id
    private String id;
	
	    //商品编码
    @Column(name = "product_code")
    private String productCode;
	
	    //商品名称
    @Column(name = "product_name")
    private String productName;

	@Column(name = "product_classify")
	private String productClassify;
	
	    //商品封面
    @Column(name = "product_image")
    private String productImage;
	
	    //精选图片
    @Column(name = "selection_image")
    private String selectionImage;
	
	    //商品图文详情
    @Column(name = "product_imagetext_info")
    private String productImagetextInfo;
	
	    //规格名称
    @Column(name = "spec_name")
    private String specName;
	
	    //规格积分
    @Column(name = "spec_integral")
    private Integer specIntegral;
	
	    //规格单位
    @Column(name = "spec_unit")
    private String specUnit;
	
	    //商品总数量
    @Column(name = "product_Num")
    private Integer productNum;
	
	    //兑换数量
    @Column(name = "cash_Num")
    private Integer cashNum;
	
	    //业务状态(1-待发布,2-已发布3-已下架）
    @Column(name = "bus_Status")
    private String busStatus;
	
	    //商品是否推荐，0表示不推荐，1表示推荐
    @Column(name = "is_recommend")
    private String isRecommend;
	
	    //发布时间
    @Column(name = "publish_time")
    private Date publishTime;
	
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


	public String getProductClassify() {
		return productClassify;
	}

	public void setProductClassify(String productClassify) {
		this.productClassify = productClassify;
	}

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
	 * 设置：商品封面
	 */
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	/**
	 * 获取：商品封面
	 */
	public String getProductImage() {
		return productImage;
	}
	/**
	 * 设置：精选图片
	 */
	public void setSelectionImage(String selectionImage) {
		this.selectionImage = selectionImage;
	}
	/**
	 * 获取：精选图片
	 */
	public String getSelectionImage() {
		return selectionImage;
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
	 * 设置：规格名称
	 */
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	/**
	 * 获取：规格名称
	 */
	public String getSpecName() {
		return specName;
	}
	/**
	 * 设置：规格积分
	 */
	public void setSpecIntegral(Integer specIntegral) {
		this.specIntegral = specIntegral;
	}
	/**
	 * 获取：规格积分
	 */
	public Integer getSpecIntegral() {
		return specIntegral;
	}
	/**
	 * 设置：规格单位
	 */
	public void setSpecUnit(String specUnit) {
		this.specUnit = specUnit;
	}
	/**
	 * 获取：规格单位
	 */
	public String getSpecUnit() {
		return specUnit;
	}
	/**
	 * 设置：商品总数量
	 */
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	/**
	 * 获取：商品总数量
	 */
	public Integer getProductNum() {
		return productNum;
	}
	/**
	 * 设置：兑换数量
	 */
	public void setCashNum(Integer cashNum) {
		this.cashNum = cashNum;
	}
	/**
	 * 获取：兑换数量
	 */
	public Integer getCashNum() {
		return cashNum;
	}
	/**
	 * 设置：业务状态(1-待发布,2-已发布3-已下架）
	 */
	public void setBusStatus(String busStatus) {
		this.busStatus = busStatus;
	}
	/**
	 * 获取：业务状态(1-待发布,2-已发布3-已下架）
	 */
	public String getBusStatus() {
		return busStatus;
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
