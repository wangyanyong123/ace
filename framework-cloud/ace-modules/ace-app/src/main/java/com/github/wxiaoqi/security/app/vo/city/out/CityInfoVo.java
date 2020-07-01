package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:39 2018/11/26
 * @Modified By:
 */
@Data
public class CityInfoVo implements Serializable {

	private static final long serialVersionUID = -3494911188498344364L;
	@ApiModelProperty("城市id")
	private String cityId;
	@ApiModelProperty("城市名字")
	private String cityName;
	@ApiModelProperty("城市统一编码")
	private String cCode;
}
