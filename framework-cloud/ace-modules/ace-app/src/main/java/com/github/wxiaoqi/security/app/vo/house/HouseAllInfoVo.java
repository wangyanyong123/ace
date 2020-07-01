package com.github.wxiaoqi.security.app.vo.house;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: huangxl
 * @Description:
 * @Date: 2018/12/09
 * @Modified By:
 */
@Data
public class HouseAllInfoVo implements Serializable {
	private static final long serialVersionUID = 8035609454806637972L;

	@ApiModelProperty("城市id")
	private String cityId;
	@ApiModelProperty("城市名称")
	private String cityName;

	@ApiModelProperty("项目id")
	private String projectId;
	@ApiModelProperty("项目名称")
	private String projectName;
	@ApiModelProperty("CRM项目编码")
	private String crmProjectCode;


	@ApiModelProperty("地块id")
	private String blockId;
	@ApiModelProperty("地块名称")
	private String blockName;

	@ApiModelProperty("楼栋id")
	private String buildId;
	@ApiModelProperty("楼栋名称")
	private String buildName;

	@ApiModelProperty("单元id")
	private String unitId;
	@ApiModelProperty("单元名称")
	private String unitName;

	@ApiModelProperty("楼层id")
	private String floorId;
	@ApiModelProperty("楼层名称")
	private String floorName;

	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("房屋名称")
	private String houseName;
	@ApiModelProperty("CRM房屋编码")
	private String crmHouseCode;

}
