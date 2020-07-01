package com.github.wxiaoqi.security.im.vo.friend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:49 2019/9/4
 * @Modified By:
 */
@Data
public class NameApplyVo extends NameVo {

	private static final long serialVersionUID = -3253316131468349331L;

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("0、待审核；1、通过；2、拒绝")
	private String isPass;

}
