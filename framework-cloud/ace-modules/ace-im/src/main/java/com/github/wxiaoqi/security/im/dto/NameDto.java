package com.github.wxiaoqi.security.im.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:57 2019/9/5
 * @Modified By:
 */
@Data
public class NameDto implements Serializable {

	private static final long serialVersionUID = -7546547034167947599L;
	private String userId;
	private String userName;
	private String profilePhoto;
	private String sex;
	private Integer isTourist;
	private Integer isApply;
	private Integer isBlack;
	private Integer isFriend;
}
