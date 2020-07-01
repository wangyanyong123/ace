package com.github.wxiaoqi.security.app.vo.useraudit.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:29 2018/11/22
 * @Modified By:
 */
@Data
public class InviteUserVo implements Serializable {
	private static final long serialVersionUID = 6122001958723621990L;
	@ApiModelProperty(value = "手机号" ,required = true ,example = "138****1234")
	private String mobile;
	@ApiModelProperty(value = "房屋id" ,required = true ,example = "10101010100001")
	private String houseId;
	@ApiModelProperty(value = "身份类型：1、家属；2、租客；3、业主" ,required = true ,example = "3")
	private String identityType;
}
