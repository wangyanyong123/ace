package com.github.wxiaoqi.security.jinmao.vo.household;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HouseholdVo implements Serializable {
    @ApiModelProperty(value = "楼层")
    private String rfname;
    @ApiModelProperty(value = "房屋")
    private String houseNumber;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "审核id")
    private String auditId;
    @ApiModelProperty(value = "人员类型 1、家属；2、租客；3、业主")
    private String identityType;
    @ApiModelProperty(value = "人员状态 0、正常；1、审核中；2、已拒绝；3、删除")
    private String userStatus;
    @ApiModelProperty(value = "手机号")
    private String mobilePhone;
    @ApiModelProperty(value = "头像")
    private String profilePhoto;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "注册时间")
    private String registerTime;
    @ApiModelProperty(value = "id")
    private String id;
}
