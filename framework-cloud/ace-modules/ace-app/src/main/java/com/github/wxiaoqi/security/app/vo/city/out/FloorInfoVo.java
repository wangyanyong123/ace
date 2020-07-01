package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:54 2018/11/26
 * @Modified By:
 */
@Data
public class FloorInfoVo implements Serializable {
	private static final long serialVersionUID = -6627591614931658267L;
	@ApiModelProperty("楼层id")
	private String floorId;
	@ApiModelProperty("楼层名称")
	private String floorName;
	@ApiModelProperty("房屋信息")
	private List<HouseInfoVo> houseInfoVos;
}
