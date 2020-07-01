package com.github.wxiaoqi.security.app.vo.visitor.out;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Qr implements Serializable {

	private static final long serialVersionUID = 5491892430696181531L;
	//二维码id
    private String id;
	
	    //二维码类型（1：临时 2：正式）
    private String qrType;
	
	    //输入值
    private String qrNum;
	
	    //值
    private String qrVal;
	
	    //生效时间
    private Date effTime;
	
	    //失效时间
    private Date loseTime;
	
	    //有效次数
    private Integer validTims;
	
	    //已使用次数
    private Integer useTimes;
	
	    //剩余次数
    private Integer surTimes;
	
	    //上次使用时间
    private Date preUseTime;
	
	    //跳转地址
    private String goUrl;
	
	    //跳转类型
    private String goType;
	
	    //跳转参数
    private String goPara;
	
	    //状态
    private String status;
	
	    //时间戳
    private Date timeStamp;
	
	    //创建人
    private String createBy;
	
	    //创建日期
    private Date createTime;
	
	    //修改人
    private String modifyBy;
	
	    //修改日期
    private Date modifyTime;
	
	    //推送状态（0：未发送  1：已发送）
    private String qrStatus;
	
	    //私码值
    private String privateNum;
	
	    //私码
    private String privateVal;
	
	    //私码下发时间
    private Date privateIssuedTime;
	
	    //用户id
    private String userId;
	
	    //用户名
    private String userName;
	
	    //手机号码
    private String tel;

}
