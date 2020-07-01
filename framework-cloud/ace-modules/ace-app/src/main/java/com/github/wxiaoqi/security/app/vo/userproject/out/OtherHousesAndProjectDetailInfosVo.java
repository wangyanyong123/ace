package com.github.wxiaoqi.security.app.vo.userproject.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:22 2018/12/6
 * @Modified By:
 */
@Data
public class OtherHousesAndProjectDetailInfosVo implements Serializable {
	private static final long serialVersionUID = 4462669710665549767L;

	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("社区id")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
	@ApiModelProperty("楼栋名")
	private String buildName;
	@ApiModelProperty("楼层名")
	private String floorName;
	@ApiModelProperty("房屋名")
	private String houseName;
	@ApiModelProperty("城市名")
	private String cityName;
	@ApiModelProperty(value = "人员类型 1、家属；2、租客；3、业主；4、审核中；5、游客")
	private String identityType;
	@ApiModelProperty(value = "单元名称")
	private String unitName;
}
