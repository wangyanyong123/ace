package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商户邮费关联表
 * 
 * @author huangxl
 * @Date 2019-04-28 16:27:46
 */
@Table(name = "base_tenant_postage")
public class BaseTenantPostage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //起算金额
    @Column(name = "start_amount")
    private BigDecimal startAmount;
	
	    //截止金额(-1表示只算起算金额)
    @Column(name = "end_amount")
    private BigDecimal endAmount;
	
	    //邮费
    @Column(name = "postage")
    private BigDecimal postage;
	
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
	 * 设置：商户ID
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：商户ID
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：起算金额
	 */
	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}
	/**
	 * 获取：起算金额
	 */
	public BigDecimal getStartAmount() {
		return startAmount;
	}
	/**
	 * 设置：截止金额(-1表示只算起算金额)
	 */
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	/**
	 * 获取：截止金额(-1表示只算起算金额)
	 */
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	/**
	 * 设置：邮费
	 */
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	/**
	 * 获取：邮费
	 */
	public BigDecimal getPostage() {
		return postage;
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
