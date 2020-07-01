package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:56 2018/11/26
 * @Modified By:
 */
@Data
public class HouseInfoVo implements Serializable {
	private static final long serialVersionUID = -4550308250185579825L;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("房屋名称")
	private String houseName;
}
