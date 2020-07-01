package com.github.wxiaoqi.security.api.vo.order.in;

import com.github.wxiaoqi.security.api.vo.in.BaseIn;
import com.github.wxiaoqi.security.api.vo.in.BaseQueryIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryRefundAuditParam extends BaseQueryIn {

    private static final long serialVersionUID = 229287558636270181L;
    @ApiModelProperty(value = "商户id")
    private String companyId;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "退款状态")
    private String auditStat;
    @ApiModelProperty(value = "开始时间(格式yyyy-MM-dd)")
    private Date startTime;
    @ApiModelProperty(value = "结束时间(格式yyyy-MM-dd)")
    private Date endTime;
    @ApiModelProperty(value = "搜索内容")
    private String searchVal;
    @Override
    protected void doCheck() {

    }
}
