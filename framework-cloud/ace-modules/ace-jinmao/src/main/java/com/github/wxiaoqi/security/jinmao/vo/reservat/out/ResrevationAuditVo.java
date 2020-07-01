package com.github.wxiaoqi.security.jinmao.vo.reservat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResrevationAuditVo implements Serializable {

    @ApiModelProperty(value = "服务id")
    private String id;
    @ApiModelProperty(value = "服务编码")
    private String reservationCode;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "申请时间")
    private String applyTime;
    @ApiModelProperty(value = "发布时间")
    private String publishTime;
    @ApiModelProperty(value = "审核时间")
    private String auditTime;
    @ApiModelProperty(value = "服务状态(1-待发布，2-待审核，3-已发布，4已驳回,5-已撤回）")
    private String reservaStatus;
    @ApiModelProperty(value = "服务范围")
    private String projectName;
    @ApiModelProperty(value = "所属分类")
    private String classifyName;
    @ApiModelProperty(value = "预约量")
    private int sales;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;


}
