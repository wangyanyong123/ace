package com.github.wxiaoqi.security.jinmao.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class WoListVo implements Serializable {
    private String id;

    //标题
    @ApiModelProperty(value = "标题")
    private String title;

    //工单编码
    @ApiModelProperty(value = "工单编码")
    private String woCode;

    //工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
    @ApiModelProperty(value = "工单状态")
    private String woStatus;

    private String woStatusStr;

    @ApiModelProperty(value = "工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)")
    private String woType;

    @ApiModelProperty(value = "客户联系人名称")
    private String contactName;

    @ApiModelProperty(value = "客户联系人电话")
    private String contactTel;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "工单处理人")
    private String handleBy;

    @ApiModelProperty(value = "工单创建时间")
    private String createTime;

    @ApiModelProperty(value = "工单接单时间")
    private String receiveWoTime;

    @ApiModelProperty(value = "工单开始处理时间")
    private String startProcessTime;

    @ApiModelProperty(value = "工单完成时间")
    private String finishWoTime;

    //图片路径
    @ApiModelProperty(value = "图片路径")
    private List<String> imgList;

    @ApiModelProperty(value = "工单来源")
    private String comeFrom;

    private String comeFromStr;

    @ApiModelProperty(value = "是否代客，代客-1 非代客-0")
    private String valet;

    private String valetStr;

    @ApiModelProperty(value = "代客人姓名")
    private String publishName;

    @ApiModelProperty(value = "代客人手机号")
    private String publishTel;
    @ApiModelProperty(value = "同步CRM状态(0-未同步1-已同步3-同步失败)")
    private String crmSyncFlag;
    private String crmSyncFlagStr;

    public String getValetStr() {
        String valetStr = "";
        if("0".equals(valet)){
            valetStr = "非代客";
        }else if("1".equals(valet)){
            valetStr = "代客";
        }
        return valetStr;
    }
}
