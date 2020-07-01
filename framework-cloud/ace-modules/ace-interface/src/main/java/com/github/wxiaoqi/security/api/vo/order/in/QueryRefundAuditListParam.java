package com.github.wxiaoqi.security.api.vo.order.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import com.github.wxiaoqi.security.common.util.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 查询退款审核列表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRefundAuditListParam extends BaseQueryIn {
	private static final long serialVersionUID = -3844351387827590153L;

	@ApiModelProperty(value = "商户id")
	private String companyId;
	@ApiModelProperty(value = "项目id")
	private String projectId;
	@ApiModelProperty(value = "退款状态")
	private String auditStat;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "开始时间(格式yyyy-MM-dd)")
	private Date startTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "结束时间(格式yyyy-MM-dd)")
	private Date endTime;
	@ApiModelProperty(value = "搜索内容")
	private String searchVal;


	@Override
	protected void doCheck() {

		if(endTime != null){
			endTime = DateUtils.addDays(endTime,1);
		}

	}

}
