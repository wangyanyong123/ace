package com.github.wxiaoqi.security.app.vo.house;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:26 2018/11/30
 * @Modified By:
 */
@Data
public class HouseInfoVO implements Serializable {
	private static final long serialVersionUID = -3546175118691745173L;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("房屋名")
    private String houseName;
	@ApiModelProperty("社区名")
    private String projectName;
	@ApiModelProperty("楼栋名")
	private String buildName;
	@ApiModelProperty("楼层名")
	private String floorName;
	@ApiModelProperty("城市名称")
    private String cityName;
	@ApiModelProperty(value = "单元名称")
	private String unitName;
}
