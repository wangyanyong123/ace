package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:36 2019/3/28
 * @Modified By:
 */
@Data
public class RefundAuditparamVo implements Serializable {
	private static final long serialVersionUID = 4006032171647134220L;
	@ApiModelProperty(value = "商户id")
	private String companyId;
	@ApiModelProperty(value = "项目id")
	private String projectId;
	@ApiModelProperty(value = "退款状态")
	private String auditStat;
	@ApiModelProperty(value = "开始时间(格式yyyy-MM-dd)")
	private String startTime;
	@ApiModelProperty(value = "结束时间(格式yyyy-MM-dd)")
	private String endTime;
	@ApiModelProperty(value = "搜索内容")
	private String searchVal;
	@ApiModelProperty(value = "当前页码")
	private int page;
	@ApiModelProperty(value = "每页条数")
	private int limit;


}
