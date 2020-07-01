package com.github.wxiaoqi.security.app.vo.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 手机信息
 * 
 * @author zxl
 * @Date 2018-11-20 16:04:25
 */
@Data
public class SysMobileInfoVo implements Serializable {

    private static final long serialVersionUID = -5231970743611752220L;
    //ID
        @ApiModelProperty(value = "id")
    private String id;
	
	    //CID
        @ApiModelProperty(value = "CID")
    private String cid;
	
	    //用户ID
        @ApiModelProperty(value = "用户ID")
    private String userId;
	
	    //客户端类型：1、ios客户端APP；2、android客户端APP；3、ios服务端APP；4、android服务端APP
        @ApiModelProperty(value = "客户端类型：1、ios客户端APP；2、android客户端APP；3、ios服务端APP；4、android服务端APP")
    private String clientType;
	
	    //手机类型1.Android  2. IOS
        @ApiModelProperty(value = "手机类型1.Android  2. IOS")
    private String os;
	
	    //手机操作系统版本
        @ApiModelProperty(value = "手机操作系统版本")
    private String osVersion;
	
	    //应用当前版本
        @ApiModelProperty(value = "应用当前版本")
    private String version;
	
	    //客户端唯一标识
        @ApiModelProperty(value = "客户端唯一标识")
    private String macId;
	
}
