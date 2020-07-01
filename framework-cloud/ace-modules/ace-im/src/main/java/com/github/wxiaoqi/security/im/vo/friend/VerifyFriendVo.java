package com.github.wxiaoqi.security.im.vo.friend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:35 2019/9/5
 * @Modified By:
 */
@Data
public class VerifyFriendVo implements Serializable {
	private static final long serialVersionUID = 2682221666225715211L;

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("1、通过；2、拒绝")
	private String isPass;
}
