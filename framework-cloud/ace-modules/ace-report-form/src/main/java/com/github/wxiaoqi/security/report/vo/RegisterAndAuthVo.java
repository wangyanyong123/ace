package com.github.wxiaoqi.security.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:42 2019/1/30
 * @Modified By:
 */
@Data
public class RegisterAndAuthVo implements Serializable {
	@ApiModelProperty("日期")
	private String statisticalDate;

	@ApiModelProperty("总注册量")
	private Integer totalRegisterNum;

	@ApiModelProperty("注册未认证量")
	private Integer unauthNum;

	@ApiModelProperty("认证量")
	private Integer authNum;

	@ApiModelProperty("当天新增注册数量")
	private Integer todayRegisterNum;

	@ApiModelProperty("当天注册未认证量")
	private Integer todayUnauthNum;

	@ApiModelProperty("当天新增认证量")
	private Integer todayAuthNum;
}
