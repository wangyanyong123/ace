package com.github.wxiaoqi.security.app.vo.userhouse.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:04 2018/11/23
 * @Modified By:
 */
@Data
public class UserInfoVo implements Serializable {
	private static final long serialVersionUID = 5150949588131541781L;

	@ApiModelProperty("身份类型：1、家属；2、租客；3、业主")
	private String identityType;
	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("用户名")
	private String userName;
	@ApiModelProperty("手机号")
	private String mobilePhone;
	@ApiModelProperty("头像")
	private String profilePhoto;
}
