package com.github.wxiaoqi.security.app.vo.clientuser.in;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:06 2018/11/21
 * @Modified By:
 */
@Data
public class ChooseHouseVo implements Serializable {
	private static final long serialVersionUID = 8920565021463618168L;
	@ApiModelProperty(value = "业主姓名，当身份类型为3时，该字段必填" ,required = false ,example = "张三")
	private String houseOwner;
	@ApiModelProperty(value = "房屋id" ,required = true ,example = "10101010100001")
	private String houseId;
	@ApiModelProperty(value = "身份类型：1、家属；2、租客；3、业主" ,required = true ,example = "3")
	private String identityType;
}
