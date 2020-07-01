package com.github.wxiaoqi.pay.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付账单
 * @Author guohao
 * @Date 2020/4/20 12:05
 */
@Data
@Table(name = "biz_account_book")
public class BizAccountBook implements Serializable {

	private static final long serialVersionUID = -4290034948845926123L;
	//ID
    @Id
    private String id;

	    //订单id
    @Column(name = "sub_id")
    private String subId;

    //业务类型 0：旧业务 ，1：商品订单，2：服务订单
    @Column(name = "bus_type")
    private Integer busType;

	    //支付凭证
    @Column(name = "pay_Id")
    private String payId;

	    //实收金额ID
    @Column(name = "actual_Id")
    private String actualId;

	//多个公司合并的支付id
	@Column(name = "account_pid")
	private String accountPid;

	    //支付状态(1-未支付,2-已支付)
    @Column(name = "pay_status")
    private String payStatus;

	    //支付类型(1-支付宝,2-微信)
    @Column(name = "pay_type")
    private String payType;

	    //支付时间
    @Column(name = "pay_date")
    private Date payDate;

    //微信支付宝应用ID
    @Column(name = "app_id")
    private String appId;

	    //支付宝/微信支付商家账户
    @Column(name = "settle_account")
    private String settleAccount;

	//实收金额=应收金额-优惠金额
	@Column(name = "actual_cost")
	private BigDecimal actualCost;

	@Column(name = "refund_status")
	private String refundStatus;

	@Column(name = "refund_amount")
	private BigDecimal refundAmount;

	@Column(name = "refund_fail_reason")
	private String refundFailReason;

	@Column(name = "refund_reason")
	private String refundReason;

	//支付异常日志
	@Column(name = "pay_fail_remark")
	private String payFailRemark;

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

}
