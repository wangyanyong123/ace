package com.github.wxiaoqi.security.jinmao.vo.houseKeeper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:41 2018/12/10
 * @Modified By:
 */
@Data
public class HouseKeeperInfoVo extends HouseKeeperVo implements Serializable {
	private static final long serialVersionUID = 4193495546592478475L;
	@ApiModelProperty(value = "id")
	private String id;
}
