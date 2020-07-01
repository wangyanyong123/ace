package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrunWoInVo {

	@ApiModelProperty(value = "工单ID")
	private String woId;
	@ApiModelProperty(value = "指派和转单时有，指派/转派用户Id")
	private String handleBy;
	@ApiModelProperty(value = "如果有，则填写，图片ID，多个图片用逗号分隔")
	private String imgId;
	@ApiModelProperty(value = "如果有，则填写，描述内容")
	private String description;

}
