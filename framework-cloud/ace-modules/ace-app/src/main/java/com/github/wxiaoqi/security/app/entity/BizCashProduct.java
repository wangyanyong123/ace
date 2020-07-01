package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 商品兑换记录表
 * 
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@Table(name = "biz_cash_product")
public class BizCashProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "contact_tel")
	private String contactTel;

	@Column(name = "description")
    private String description;
	
	    //兑换编码
    @Column(name = "cash_code")
    private String cashCode;
        //项目id
	@Column(name = "project_id")
    private String projectId;
	
	    //商品ID
    @Column(name = "product_id")
    private String productId;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //兑换商品数量
    @Column(name = "cash_Num")
    private Integer cashNum;
	
	    //兑换商品所需积分
    @Column(name = "cash_integral")
    private Integer cashIntegral;

    //地址
	@Column(name = "addr")
    private String addr;

	//地址
	@Column(name = "sign_date")
	private Date signDate;

	//地址
	@Column(name = "use_time")
	private Date useTime;

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

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

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
	 * 设置：兑换编码
	 */
	public void setCashCode(String cashCode) {
		this.cashCode = cashCode;
	}
	/**
	 * 获取：兑换编码
	 */
	public String getCashCode() {
		return cashCode;
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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：兑换商品数量
	 */
	public void setCashNum(Integer cashNum) {
		this.cashNum = cashNum;
	}
	/**
	 * 获取：兑换商品数量
	 */
	public Integer getCashNum() {
		return cashNum;
	}
	/**
	 * 设置：兑换商品所需积分
	 */
	public void setCashIntegral(Integer cashIntegral) {
		this.cashIntegral = cashIntegral;
	}
	/**
	 * 获取：兑换商品所需积分
	 */
	public Integer getCashIntegral() {
		return cashIntegral;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
}
