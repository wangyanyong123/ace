package com.github.wxiaoqi.security.api.vo.order.out;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
public class OperateButton implements Serializable {


    private static final long serialVersionUID = -3313833732369702035L;
    @ApiModelProperty(value = "操作ID")
    private String operateId;
    @ApiModelProperty(value = "操作名称")
    private String operateName;
    @ApiModelProperty(value = "按钮颜色(0-默认，1-红色)")
    private String buttonColour;
    @ApiModelProperty(value = "按钮类型(01-调接口、02-app支付页面、03-评价页面、04-app退货/售后页面，05-取消、06-工单关闭，07-工单完成)")
    private String buttonType;
    @ApiModelProperty(value = "操作类型")
    private String operateType;
    @ApiModelProperty(value = "操作服务")
    private List<BizFlowServiceBean> beforeServiceList;
}