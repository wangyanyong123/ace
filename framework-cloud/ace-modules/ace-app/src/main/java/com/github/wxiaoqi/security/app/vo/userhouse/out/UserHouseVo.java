package com.github.wxiaoqi.security.app.vo.userhouse.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:48 2018/11/23
 * @Modified By:
 */
@Data
public class UserHouseVo implements Serializable {
	private static final long serialVersionUID = -4379986841825041236L;
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("是否是当前房屋：0、不是；1、是")
	private String isNow;
	@ApiModelProperty("身份类型：1、家属；2、租客；3、业主")
	private String identityType;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("社区ID")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
	@ApiModelProperty("楼栋名")
	private String buildName;
	@ApiModelProperty("楼层名")
	private String floorName;
	@ApiModelProperty("房屋人数")
	private String number;
	@ApiModelProperty(value = "房屋名")
	private String houseName;
	@ApiModelProperty(value = "城市名")
	private String cityName;
	@ApiModelProperty(value = "单元名称")
	private String unitName;
}
