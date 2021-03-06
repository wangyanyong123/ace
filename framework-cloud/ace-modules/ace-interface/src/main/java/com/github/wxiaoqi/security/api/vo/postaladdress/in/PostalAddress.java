package com.github.wxiaoqi.security.api.vo.postaladdress.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;


/**
 * 收货地址
 * 
 * @author huangxl
 * @Date 2018-12-18 18:34:14
 */
@Data
public class PostalAddress implements Serializable {

	private static final long serialVersionUID = -2008656362250014323L;

	@ApiModelProperty(value = "ID")
    private String id;
	
	//联系人姓名
	@ApiModelProperty(value = "联系人姓名(新增和更新时必填)")
    private String contactName;
	
	//联系人手机号码
	@ApiModelProperty(value = "联系人手机号码(新增和更新时必填)")
    private String contactTel;
	
	//省编码
	@ApiModelProperty(value = "省编码(新增和更新时必填)")
    private String procCode;
	
	//省名称
	@ApiModelProperty(value = "省名称(新增和更新时必填)")
    private String procName;
	
	//城市编码
	@ApiModelProperty(value = "城市编码(新增和更新时必填)")
    private String cityCode;
	
	//城市名称
	@ApiModelProperty(value = "城市名称(新增和更新时必填)")
    private String cityName;

	//区县编码
	@ApiModelProperty(value = "区县编码(新增和更新时必填)")
	private String districtCode;

	//区县名称
	@ApiModelProperty(value = "区县名称(新增和更新时必填)")
	private String districtName;
	
	//地址
	@ApiModelProperty(value = "地址(新增和更新时必填)")
    private String addr;
	
	//是否常用(0-否，1-是)
	@ApiModelProperty(value = "是否常用(0-否，1-是)")
	private String isUse;

	@ApiModelProperty(value = "项目id")
	private String projectId;
}

