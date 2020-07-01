package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单表
 * 
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
@Table(name = "biz_subscribe")
public class BizSubscribe implements Serializable {
	private static final long serialVersionUID = 2340666745976353856L;
	
	    //ID
    @Id
    private String id;
	
	    //订阅编码
    @Column(name = "sub_code")
    private String subCode;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //描述
    @Column(name = "description")
    private String description;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //收货联系人
    @Column(name = "contact_name")
    private String contactName;
	
	    //收货联系电话
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //收货地址
    @Column(name = "delivery_addr")
    private String deliveryAddr;
	
	    //来源(1-android,2-ios)
    @Column(name = "source")
    private String source;

	@Column(name = "notice_status")
	private String noticeStatus;

	//下单时所属项目ID
    @Column(name = "project_id")
    private String projectId;

    @Column(name = "crm_project_code")
	private String crmProjectCode;

    @Column(name = "room_id")
	private String roomId;

	@Column(name = "crm_room_code")
	private String crmRoomCode;

	@Column(name = "crm_user_id")
	private String crmUserId;

	    //应收金额=商品总金额+运费
    @Column(name = "receivable_cost")
    private BigDecimal receivableCost;
	
	    //商品总金额
    @Column(name = "product_cost")
    private BigDecimal productCost;
	
	    //运费
    @Column(name = "express_cost")
    private BigDecimal expressCost;
	
	    //实收金额=应收金额-优惠金额
    @Column(name = "actual_cost")
    private BigDecimal actualCost;
	
	    //优惠金额
    @Column(name = "discount_cost")
    private BigDecimal discountCost;
	
	    //备注
    @Column(name = "remark")
    private String remark;
	
	    //商品总件数
    @Column(name = "total_num")
    private Integer totalNum;

	//单价(当订单只有一个时才有效)
	@Column(name = "price")
	private BigDecimal price;

	//单位(当订单只有一个时才有效)
	@Column(name = "unit")
	private String unit;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_Stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期(下单日期)
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;

	//抵扣优惠券Id
	@Column(name = "coupon_Id")
	private String couponId;

	//发票类型(0-不开发票,1-个人,2-公司)
	@Column(name = "invoice_Type")
	private String invoiceType;

	//发票名称
	@Column(name = "invoice_Name")
	private String invoiceName;

	//税号
	@Column(name = "duty_Num")
	private String dutyNum;

	@Column(name = "telphone")
	private String telphone;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "bank_num")
	private String bankNum;
	@Column(name = "unit_addr")
	private String unitAddr;

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
	 * 设置：订阅编码
	 */
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	/**
	 * 获取：订阅编码
	 */
	public String getSubCode() {
		return subCode;
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
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
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
	 * 设置：收货联系人
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * 获取：收货联系人
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * 设置：收货联系电话
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：收货联系电话
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：收货地址
	 */
	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	/**
	 * 获取：收货地址
	 */
	public String getDeliveryAddr() {
		return deliveryAddr;
	}
	/**
	 * 设置：来源(1-android,2-ios)
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：来源(1-android,2-ios)
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置：下单时所属项目ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：下单时所属项目ID
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：应收金额=商品总金额+运费
	 */
	public void setReceivableCost(BigDecimal receivableCost) {
		this.receivableCost = receivableCost;
	}
	/**
	 * 获取：应收金额=商品总金额+运费
	 */
	public BigDecimal getReceivableCost() {
		return receivableCost;
	}
	/**
	 * 设置：商品总金额
	 */
	public void setProductCost(BigDecimal productCost) {
		this.productCost = productCost;
	}
	/**
	 * 获取：商品总金额
	 */
	public BigDecimal getProductCost() {
		return productCost;
	}
	/**
	 * 设置：运费
	 */
	public void setExpressCost(BigDecimal expressCost) {
		this.expressCost = expressCost;
	}
	/**
	 * 获取：运费
	 */
	public BigDecimal getExpressCost() {
		return expressCost;
	}
	/**
	 * 设置：实收金额=应收金额-优惠金额
	 */
	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}
	/**
	 * 获取：实收金额=应收金额-优惠金额
	 */
	public BigDecimal getActualCost() {
		return actualCost;
	}
	/**
	 * 设置：优惠金额
	 */
	public void setDiscountCost(BigDecimal discountCost) {
		this.discountCost = discountCost;
	}
	/**
	 * 获取：优惠金额
	 */
	public BigDecimal getDiscountCost() {
		return discountCost;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：商品总件数
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	/**
	 * 获取：商品总件数
	 */
	public Integer getTotalNum() {
		return totalNum;
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
	 * 设置：创建日期(下单日期)
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期(下单日期)
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCrmProjectCode() {
		return crmProjectCode;
	}

	public void setCrmProjectCode(String crmProjectCode) {
		this.crmProjectCode = crmProjectCode;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCrmRoomCode() {
		return crmRoomCode;
	}

	public void setCrmRoomCode(String crmRoomCode) {
		this.crmRoomCode = crmRoomCode;
	}

	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(String crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getDutyNum() {
		return dutyNum;
	}

	public void setDutyNum(String dutyNum) {
		this.dutyNum = dutyNum;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getUnitAddr() {
		return unitAddr;
	}

	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}
}
