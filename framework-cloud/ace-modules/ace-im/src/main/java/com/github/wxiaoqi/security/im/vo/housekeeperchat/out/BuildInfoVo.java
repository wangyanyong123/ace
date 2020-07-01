package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:49 2018/11/26
 * @Modified By:
 */
@Data
public class BuildInfoVo implements Serializable {
	private static final long serialVersionUID = 6138648533357058209L;
	@ApiModelProperty("楼栋id")
	private String buildId;
	@ApiModelProperty("楼栋名称")
	private String buildName;
}
