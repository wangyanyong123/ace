package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

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
	private static final long serialVersionUID = 6208202694427674423L;
	@ApiModelProperty("地块id")
	private String blockId;
	@ApiModelProperty("地块名称")
	private String blockName;
}
