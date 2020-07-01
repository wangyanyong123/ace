package com.github.wxiaoqi.security.external.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 二维码
 * 
 * @author zxl
 *  @Date 2019-01-02 18:36:27
 */
@Table(name = "biz_qr")
@Data
public class BizQr implements Serializable {
	private static final long serialVersionUID = 4117279921553407529L;
	
	    //二维码id
    @Id
    private String id;
	
	    //二维码类型（1：临时 2：正式）
    @Column(name = "qr_type")
    private String qrType;
	
	    //输入值
    @Column(name = "qr_num")
    private String qrNum;
	
	    //值
    @Column(name = "qr_val")
    private String qrVal;
	
	    //生效时间
    @Column(name = "eff_time")
    private Date effTime;
	
	    //失效时间
    @Column(name = "lose_time")
    private Date loseTime;
	
	    //有效次数
    @Column(name = "valid_tims")
    private Integer validTims;
	
	    //已使用次数
    @Column(name = "use_times")
    private Integer useTimes;
	
	    //剩余次数
    @Column(name = "sur_times")
    private Integer surTimes;
	
	    //上次使用时间
    @Column(name = "pre_use_time")
    private Date preUseTime;
	
	    //跳转地址
    @Column(name = "go_url")
    private String goUrl;
	
	    //跳转类型
    @Column(name = "go_type")
    private String goType;
	
	    //跳转参数
    @Column(name = "go_para")
    private String goPara;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //推送状态（0：未发送  1：已发送）
    @Column(name = "qr_status")
    private String qrStatus;
	
	    //私码值
    @Column(name = "private_num")
    private String privateNum;
	
	    //私码
    @Column(name = "private_val")
    private String privateVal;
	
	    //私码下发时间
    @Column(name = "private_issued_time")
    private Date privateIssuedTime;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //用户名
    @Column(name = "user_name")
    private String userName;
	
	    //手机号码
    @Column(name = "tel")
    private String tel;

}
