package com.github.wxiaoqi.security.app.vo.useraudit.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:51 2018/11/22
 * @Modified By:
 */
@Data
public class DeleteUserVo implements Serializable {
	@ApiModelProperty(value = "用户id" ,required = true ,example = "138****1234")
	private String userId;
	@ApiModelProperty(value = "房屋id" ,required = true ,example = "10101010100001")
	private String houseId;
}
