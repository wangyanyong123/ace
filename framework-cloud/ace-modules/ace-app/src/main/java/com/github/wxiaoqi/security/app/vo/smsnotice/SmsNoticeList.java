package com.github.wxiaoqi.security.app.vo.smsnotice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SmsNoticeList implements Serializable {
    private static final long serialVersionUID = 5123837120466389230L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "接收者ID")
    private String receiverId;
    @ApiModelProperty(value = "业务ID")
    private String objectId;
    @ApiModelProperty(value = "业务类型（1 报修 2 住户审核 3 系统通知 4 投诉）")
    private String msgType;
    @ApiModelProperty(value = "业务类型字段")
    private String msgTypeStr;
    @ApiModelProperty(value = "是否跳转(0-不跳转1-跳转)")
    private String isJump;
    @ApiModelProperty(value = "手机通知跳转页")
    private String page;
    @ApiModelProperty(value = "消息内容")
    private String msgContent;
    @ApiModelProperty(value = "消息标题")
    private String msgTitle;
    @ApiModelProperty(value = "是否已读(0-未读1-已读)")
    private String isRead;
    @ApiModelProperty(value = "时间")
    private String noticeTime;


    public String getMsgTypeStr() {
        String msgTypeStr = "";
        if("1".equals(msgType)){
            msgTypeStr = "报修";
        }else if("2".equals(msgType)){
            msgTypeStr = "住户审核";
        } else if ("3".equals(msgType)) {
            msgTypeStr = "系统通知";
        } else if ("4".equals(msgType)) {
            msgTypeStr = "投诉";
        } else if ("5".equals(msgType) || "6".equals(msgType) || "7".equals(msgType)) {
            msgTypeStr = "订单";
        } else if ("8".equals(msgType)) {
            msgTypeStr = "活动";
        }else if ("9".equals(msgType)){
            msgTypeStr = "好友申请";
        }else if ("10".equals(msgType)){
            msgTypeStr = "好友审核";
        }
        return msgTypeStr;
    }


}
