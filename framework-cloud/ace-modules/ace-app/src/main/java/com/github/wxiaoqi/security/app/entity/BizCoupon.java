package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 优惠券表
 *
 * @Date 2019-04-16 10:49:40
 */
@Table(name = "biz_coupon")
public class BizCoupon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //优惠券ID流水号
    @Column(name = "flow_num")
    private String flowNum;
	
	    //优惠券名称
    @Column(name = "coupon_name")
    private String couponName;
	
	    //优惠券类型(1-代金券2-折扣券)
    @Column(name = "coupon_type")
    private String couponType;
	
	    //优惠券开始使用时间
    @Column(name = "start_use_time")
    private String startUseTime;
	
	    //优惠券结束使用时间
    @Column(name = "end_use_time")
    private String endUseTime;
	
	    //优惠券总数
    @Column(name = "amount")
    private Integer amount;
	
	    //优惠券面值
    @Column(name = "value")
    private BigDecimal value;
	
	    //折扣力度
    @Column(name = "discount_num")
    private BigDecimal discountNum;
	
	    //最高折扣金额
    @Column(name = "max_value")
    private BigDecimal maxValue;
	
	    //最低消费金额
    @Column(name = "min_value")
    private BigDecimal minValue;
	
	    //每人累计领取上限
    @Column(name = "get_limit")
    private Integer getLimit;

	@Column(name = "product_cover")
	private String productCover;
	    //封面图片
    @Column(name = "cover_photo")
    private String coverPhoto;
	    //使用状态(0-待发布1-已发布2-使用中4-已下架)
    @Column(name = "use_status")
    private String useStatus;

    @Column(name = "tenant_id")
	private String tenantId;
	    //状态
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
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：优惠券ID流水号
	 */
	public void setFlowNum(String flowNum) {
		this.flowNum = flowNum;
	}
	/**
	 * 获取：优惠券ID流水号
	 */
	public String getFlowNum() {
		return flowNum;
	}
	/**
	 * 设置：优惠券名称
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	/**
	 * 获取：优惠券名称
	 */
	public String getCouponName() {
		return couponName;
	}
	/**
	 * 设置：优惠券类型(1-代金券2-折扣券)
	 */
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	/**
	 * 获取：优惠券类型(1-代金券2-折扣券)
	 */
	public String getCouponType() {
		return couponType;
	}
	/**
	 * 设置：优惠券开始使用时间
	 */
	public void setStartUseTime(String startUseTime) {
		this.startUseTime = startUseTime;
	}
	/**
	 * 获取：优惠券开始使用时间
	 */
	public String getStartUseTime() {
		return startUseTime;
	}
	/**
	 * 设置：优惠券结束使用时间
	 */
	public void setEndUseTime(String endUseTime) {
		this.endUseTime = endUseTime;
	}
	/**
	 * 获取：优惠券结束使用时间
	 */
	public String getEndUseTime() {
		return endUseTime;
	}
	/**
	 * 设置：优惠券总数
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	/**
	 * 获取：优惠券总数
	 */
	public Integer getAmount() {
		return amount;
	}
	/**
	 * 设置：优惠券面值
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	/**
	 * 获取：优惠券面值
	 */
	public BigDecimal getValue() {
		return value;
	}
	/**
	 * 设置：折扣力度
	 */
	public void setDiscountNum(BigDecimal discountNum) {
		this.discountNum = discountNum;
	}
	/**
	 * 获取：折扣力度
	 */
	public BigDecimal getDiscountNum() {
		return discountNum;
	}
	/**
	 * 设置：最高折扣金额
	 */
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}
	/**
	 * 获取：最高折扣金额
	 */
	public BigDecimal getMaxValue() {
		return maxValue;
	}
	/**
	 * 设置：最低消费金额
	 */
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}
	/**
	 * 获取：最低消费金额
	 */
	public BigDecimal getMinValue() {
		return minValue;
	}

	public Integer getGetLimit() {
		return getLimit;
	}

	public void setGetLimit(Integer getLimit) {
		this.getLimit = getLimit;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}

	/**
	 * 设置：封面图片
	 */
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	/**
	 * 获取：封面图片
	 */
	public String getCoverPhoto() {
		return coverPhoto;
	}
	/**
	 * 设置：使用状态(0-待发布1-已发布2-使用中4-已下架)
	 */
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	/**
	 * 获取：使用状态(0-待发布1-已发布2-使用中4-已下架)
	 */
	public String getUseStatus() {
		return useStatus;
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
