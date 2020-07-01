package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:52 2018/11/26
 * @Modified By:
 */
@Data
public class UnitInfoVo implements Serializable {
	private static final long serialVersionUID = 3630972219174444839L;
	@ApiModelProperty("单元id")
	private String unitId;
	@ApiModelProperty("单元名称")
	private String unitName;
}
