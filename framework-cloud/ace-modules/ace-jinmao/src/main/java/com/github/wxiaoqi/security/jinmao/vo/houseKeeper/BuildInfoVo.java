package com.github.wxiaoqi.security.jinmao.vo.houseKeeper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:17 2018/12/10
 * @Modified By:
 */
@Data
public class BuildInfoVo implements Serializable {
	private static final long serialVersionUID = -3910433900646600966L;
	@ApiModelProperty("楼栋id")
	private String buildId;
	@ApiModelProperty("楼栋名称")
	private String buildName;
	@ApiModelProperty("是否已被选择:0、未被选择；1、已被选择")
	private String isChoose;
}
