package com.github.wxiaoqi.security.app.vo.clientuser.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:27 2018/12/5
 * @Modified By:
 */
@Data
public class CurrentUserInfosVo implements Serializable {
	private static final long serialVersionUID = 6914449641646893035L;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("房屋名")
	private String houseName;
	@ApiModelProperty("社区id")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
	@ApiModelProperty(value = "身份类型：1、家属；2、租客；3、业主；4、游客" ,required = true ,example = "3")
	private String identityType;
	@ApiModelProperty("城市id")
	private String cityId;
	@ApiModelProperty("城市名")
	private String cityName;
	@ApiModelProperty("地块名")
	private String blockName;
	@ApiModelProperty("楼栋名")
	private String buildingName;
	@ApiModelProperty("单元名")
	private String unitName;
	@ApiModelProperty("楼层名")
	private String floorName;
	@ApiModelProperty(value = "车牌前缀",hidden = true)
	private String abbreviation;
}
