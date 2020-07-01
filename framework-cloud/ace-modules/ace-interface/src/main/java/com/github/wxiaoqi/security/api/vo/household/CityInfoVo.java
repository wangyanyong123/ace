package com.github.wxiaoqi.security.api.vo.household;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:06 2018/12/5
 * @Modified By:
 */
@Data
public class CityInfoVo implements Serializable {

	private static final long serialVersionUID = -5962159459637597534L;
	@ApiModelProperty("城市id")
	private String cityId;
	@ApiModelProperty("城市名字")
	private String cityName;
}
