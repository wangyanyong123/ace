package com.github.wxiaoqi.security.im.vo.userchat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:01 2018/12/18
 * @Modified By:
 */
@Data
public class HouseKeeperVo implements Serializable {
	private static final long serialVersionUID = -5422280448190568951L;
	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("用户名")
	private String userName;
	@ApiModelProperty("头像")
	private String profilePhoto;
	@ApiModelProperty("电话")
	private String tel;
	@ApiModelProperty("公司名称")
	private String companyName;
	@ApiModelProperty("职务")
	private String dutyName;
	@ApiModelProperty("身份")
	private String identity;
}
