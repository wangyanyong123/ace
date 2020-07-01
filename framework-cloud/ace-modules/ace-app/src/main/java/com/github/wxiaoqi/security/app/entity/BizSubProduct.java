package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 订单产品表
 * 
 * @author zxl
 * @Date 2018-12-14 17:44:12
 */
@Table(name = "biz_sub_product")
public class BizSubProduct implements Serializable {
	private static final long serialVersionUID = 273816443670965468L;
	
	    //id
    @Id
    private String id;
	
	    //订单id
    @Column(name = "sub_Id")
    private String subId;
	
	    //产品id
    @Column(name = "product_Id")
    private String productId;

	//规格编码
	@Column(name = "product_name")
	private String productName;
	
	    //规格ID
    @Column(name = "spec_id")
    private String specId;
	
	    //规格
    @Column(name = "spec_name")
    private String specName;
	
	    //数量
    @Column(name = "sub_num")
    private Integer subNum;
	
	    //单价
    @Column(name = "price")
    private BigDecimal price;

	//单位
	@Column(name = "unit")
	private String unit;
	
	    //总价
    @Column(name = "cost")
    private BigDecimal cost;

    //图片
    @Column(name = "img_id")
    private String imgId;
	
	    //状态：0、删除；1、正常
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
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：订单id
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	/**
	 * 获取：订单id
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * 设置：产品id
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品id
	 */
	public String getProductId() {
		return productId;
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
	 * 设置：数量
	 */
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
	/**
	 * 获取：数量
	 */
	public Integer getSubNum() {
		return subNum;
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
	 * 设置：总价
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	/**
	 * 获取：总价
	 */
	public BigDecimal getCost() {
		return cost;
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

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
