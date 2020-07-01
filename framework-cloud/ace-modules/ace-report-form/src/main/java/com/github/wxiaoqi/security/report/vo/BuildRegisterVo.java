package com.github.wxiaoqi.security.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:22 2019/3/11
 * @Modified By:
 */
@Data
public class BuildRegisterVo implements Serializable {
	private static final long serialVersionUID = 8828829306991131633L;
	@ApiModelProperty("日期")
	private String registerDate;

	@ApiModelProperty("项目名")
	private String projectName;

	@ApiModelProperty("楼栋名")
	private String buildingName;

	@ApiModelProperty("认证数")
	private Integer registerNumber;
	@ApiModelProperty("地块名")
	private String blockName;
	@ApiModelProperty("业主数")
	private String OwnerNumber;
}
