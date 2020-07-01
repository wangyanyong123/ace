package com.github.wxiaoqi.security.app.vo.userhouse.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:56 2018/11/23
 * @Modified By:
 */
@Data
public class OtherHouseDetailInfosVo implements Serializable {
	private static final long serialVersionUID = -2288468959841049022L;
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("社区名")
	private String projectName;
	@ApiModelProperty("楼栋名")
	private String buildName;
	@ApiModelProperty("单元名称")
	private String unitName;
	@ApiModelProperty("楼层名")
	private String floorName;
	@ApiModelProperty("项目id")
	private String projectId;
	@ApiModelProperty("房屋名称")
	private String houseName;
    @ApiModelProperty("城市名称")
	private String cityName;

	@ApiModelProperty("房屋内所有用户信息")
	private List<UserInfoVo> otherInfos;

}

