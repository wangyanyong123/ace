package com.github.wxiaoqi.security.jinmao.vo.houseKeeper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:43 2018/12/10
 * @Modified By:
 */
@Data
public class HouseKeeper implements Serializable {
	private static final long serialVersionUID = 4542210250390241673L;
	@ApiModelProperty(value = "id")
	private String id;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "手机号")
	private String mobilePhone;
}
