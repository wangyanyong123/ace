package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultManageVo implements Serializable {


    private static final long serialVersionUID = -8744448624627974111L;
    @ApiModelProperty(value = "租户(公司、商户)ID")
    private String id;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "公司名称")
    private String name;
    @ApiModelProperty(value = "营业执照号")
    private String licenceNo;
    @ApiModelProperty(value = "负责人名字")
    private String contactorName;
    @ApiModelProperty(value = "负责人电话")
    private String contactTel;
    @ApiModelProperty(value = "负责人邮箱")
    private String contactEmail;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    private String projectCode;
    @ApiModelProperty(value = "启用状态(0表示禁用，1表示启用)")
    private String enableStatus;

    private String tenantType;

    public String getTenantTypeDesc() {
        return AceDictionary.TENANT_TYPE.get(tenantType);
    }
}
