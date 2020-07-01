package com.github.wxiaoqi.security.app.vo.serveruser.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:00 2018/11/21
 * @Modified By:
 */
@Data
public class ServerUserVo implements Serializable {

	private static final long serialVersionUID = 6526547632803670438L;
	@ApiModelProperty("用户id")
	private String id;
	@ApiModelProperty("手机号")
	private String mobilePhone;
	@ApiModelProperty("头像")
	private String profilePhoto;
	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("生日")
	private String birthday;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("性别：0、未知；1、男；2、女")
	private String sex;
	@ApiModelProperty("资质图片")
	private String seniorityPhoto;
	@ApiModelProperty("是否激活(0-否，1-是)")
	private String isActive;
	@ApiModelProperty("是否是物业服务人员(0-否，1-是)")
	private String isService;
	@ApiModelProperty("是否是管家(0-否，1-是)")
	private String isHousekeeper;
	@ApiModelProperty("是否是客服人员(0-否，1-是)")
	private String isCustomer;
	@ApiModelProperty("是否是商业人员(0-否，1-是)")
	private String isBusiness;
	@ApiModelProperty("是否有装修监理的权限(0-否，1-是)")
	private String isSupervision;
	@ApiModelProperty("公司名称")
	private String companyName;

}
