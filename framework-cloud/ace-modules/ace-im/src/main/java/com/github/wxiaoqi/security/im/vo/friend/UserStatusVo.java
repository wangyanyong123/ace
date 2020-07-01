package com.github.wxiaoqi.security.im.vo.friend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:57 2019/9/5
 * @Modified By:
 */
@Data
public class UserStatusVo implements Serializable {
	private static final long serialVersionUID = -3458958797159836265L;
	@ApiModelProperty("是否是游客，1、是；0、不是")
	private Integer isTourist;
	@ApiModelProperty("是否已申请，1、是；0、不是")
	private Integer isApply;
	@ApiModelProperty("是否加入黑名单，1、是；0、不是")
	private Integer isBlack;
	@ApiModelProperty("是否是好友，1、是；0、不是")
	private Integer isFriend;
}
