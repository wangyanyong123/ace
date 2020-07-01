package com.github.wxiaoqi.security.im.tio.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:29 2018/12/14
 * @Modified By:
 */
@Data
public class Message implements Serializable {
	private static final long serialVersionUID = -1493106353341843240L;
	@ApiModelProperty(value = "id，发送消息的时候不必传",required = false)
	private String id;
	@ApiModelProperty(value = "发送者id",required = true)
	private String fromUserId;
	@ApiModelProperty(value = "接收者id",required = true)
	private String toUserId;
	@ApiModelProperty(value = "消息",required = true)
	private String message;
	@ApiModelProperty(value = "人工智能输入json串",required = true)
	private String messageJson;
	@ApiModelProperty(value = "消息类型 1、文字，2、图片,3、语音，4、视频，5、智能问答库值，6、无法解答的",required = true)
	private Integer msgType;
	@ApiModelProperty(value = "缩略图",required = false)
	private String smallImg;
	@ApiModelProperty(value = "用户类型 1、客户，2、管家",required = true)
	private String userType;
	@ApiModelProperty(value = "项目id",required = true)
	private String projectId;
	@ApiModelProperty(value = "房间id，客户端发消息必传，管家从通讯录发消息必传，管家从已聊过天的列表发送消息的时候可以不必传",required = false)
	private String houseId;
	@ApiModelProperty(value = "创建时间，发送消息的时候不必传",required = false)
	private Date createTime;
	@ApiModelProperty(value = "是的已读 0、未读；1、已读，发送消息的时候不必传",required = false)
	private String readFlag;
	@ApiModelProperty(value = "来源消息类型 1、智能问答库；2、人工",required = false)
	private String isInteligence;

	@ApiModelProperty(value = "系统 1、Android，2、Ios",required = false)
	private String os;
	@ApiModelProperty(value = "版本 1.0(不发送个推消息)、2.0(发送个推消息)",required = false)
	private String edition;

	@ApiModelProperty(value = "是否是好友之间聊天，1、是，不传或者传其他值均表示不是",required = false)
	private String isFriend;
}
