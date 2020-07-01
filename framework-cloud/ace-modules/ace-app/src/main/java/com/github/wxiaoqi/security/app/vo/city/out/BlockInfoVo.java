package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:52 2018/12/13
 * @Modified By:
 */
@Data
public class BlockInfoVo implements Serializable {
	private static final long serialVersionUID = -423574815908589500L;
	@ApiModelProperty("地块id")
	private String blockId;
	@ApiModelProperty("地块名称")
	private String blockName;
	@ApiModelProperty("楼栋信息")
	private List<BuildInfoVo> buildInfoVos;
}
