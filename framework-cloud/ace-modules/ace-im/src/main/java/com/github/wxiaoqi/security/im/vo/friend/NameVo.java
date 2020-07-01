package com.github.wxiaoqi.security.im.vo.friend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:50 2019/9/4
 * @Modified By:
 */
@Data
public class NameVo implements Serializable {

	private static final long serialVersionUID = -6964413450796431994L;

	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("用户名")
	private String userName;
	@ApiModelProperty("头像")
	private String profilePhoto;
	@ApiModelProperty("性别：0、未知；1、男；2、女")
	private String sex;
}
