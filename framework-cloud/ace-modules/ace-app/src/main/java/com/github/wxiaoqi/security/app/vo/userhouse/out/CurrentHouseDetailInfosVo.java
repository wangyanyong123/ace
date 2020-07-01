package com.github.wxiaoqi.security.app.vo.userhouse.out;

import com.github.wxiaoqi.security.app.vo.useraudit.out.HouseAllUserInfosVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:56 2018/11/23
 * @Modified By:
 */
@Data
public class CurrentHouseDetailInfosVo extends HouseAllUserInfosVo implements Serializable {
	private static final long serialVersionUID = 5784000711139467832L;
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("房屋id")
	private String houseId;
	@ApiModelProperty("社区名")
	private String projectName;
	@ApiModelProperty("楼栋名")
	private String buildName;
	@ApiModelProperty("楼层名")
	private String floorName;

}

