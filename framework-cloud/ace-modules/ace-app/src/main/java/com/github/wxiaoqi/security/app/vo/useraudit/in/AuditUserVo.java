package com.github.wxiaoqi.security.app.vo.useraudit.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:26 2018/11/22
 * @Modified By:
 */
@Data
public class AuditUserVo implements Serializable {
	private static final long serialVersionUID = -4922253697796688280L;
	@ApiModelProperty(value = "审核id" ,required = true ,example = "138dsfsdg1234")
	private String auditId;
	@ApiModelProperty(value = "是否通过：1、通过；2、拒绝" ,required = true ,example = "1")
	private String isPass;
}
