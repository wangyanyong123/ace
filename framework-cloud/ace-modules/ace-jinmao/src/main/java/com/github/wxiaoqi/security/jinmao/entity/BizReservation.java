package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 预约服务表
 * 
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
@Data
@Table(name = "biz_reservation")
public class BizReservation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "company_id")
    private String companyId;
	
	    //服务编码
    @Column(name = "reservation_code")
    private String reservationCode;
	
	    //服务名称
    @Column(name = "name")
    private String name;
	
	    //服务封面logo
    @Column(name = "reservation_logo")
    private String reservationLogo;

	//商品精选图片
	@Column(name = "selection_image")
	private String selectionImage;
	
	    //商品图文详情
    @Column(name = "reservation_imagetext_info")
    private String reservationImagetextInfo;
	
	    //预约量
    @Column(name = "sales")
    private Integer sales;
	
	    //服务状态(1-待发布，2-待审核，3-已发布，4已驳回）
    @Column(name = "reserva_Status")
    private String reservaStatus;
	
	    //申请时间
    @Column(name = "apply_time")
    private Date applyTime;
	
	    //审核时间
    @Column(name = "audit_time")
    private Date auditTime;
	
	    //发布时间
    @Column(name = "publish_time")
    private Date publishTime;

	//租户id
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

	//单账号限制购买数量
	@Column(name = "limit_Num")
	private Integer limitNum;
	//商品库存数
	@Column(name = "product_Num")
	private Integer productNum;
	//服务需知
	@Column(name = "reservation_Desc")
	private String reservationDesc;
	//服务热线
	@Column(name = "reservation_Tel")
	private String reservationTel;
	//服务日期范围
	@Column(name = "data_Scope_Val")
	private String dataScopeVal;
	//上午开始时间
	@Column(name = "forenoon_Start_Time")
	private String forenoonStartTime;
	//上午结束时间
	@Column(name = "forenoon_End_Time")
	private String forenoonEndTime;
	//上午商品库存数
	@Column(name = "product_num_forenoon")
	private Integer productNumForenoon;
	//下午开始时间
	@Column(name = "afternoon_Start_Time")
	private String afternoonStartTime;
	//下午结束时间
	@Column(name = "afternoon_End_Time")
	private String afternoonEndTime;
	//下午商品库存数
	@Column(name = "product_num_afternoon")
	private Integer productNumAfternoon;
	//供应商
	@Column(name = "supplier")
	private String supplier;
	//销售方式
	@Column(name = "sales_way")
	private String salesWay;
}
