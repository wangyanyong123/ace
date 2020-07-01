package com.github.wxiaoqi.security.merchant.vo.userhouse.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:54 2018/11/23
 * @Modified By:
 */
@Data
public class HouseInfoVo implements Serializable {
	private static final long serialVersionUID = -5747366340233337498L;
	@ApiModelProperty("id")
	private String id;
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
	@ApiModelProperty("房屋名")
	private String houseName;
}
