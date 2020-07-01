package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

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
	private static final long serialVersionUID = 76203243215508596L;
	@ApiModelProperty("楼层id")
	private String floorId;
	@ApiModelProperty("楼层名称")
	private String floorName;
}
