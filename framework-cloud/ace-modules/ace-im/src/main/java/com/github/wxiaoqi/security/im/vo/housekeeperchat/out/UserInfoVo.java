package com.github.wxiaoqi.security.im.vo.housekeeperchat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:01 2018/12/18
 * @Modified By:
 */
@Data
public class UserInfoVo implements Serializable {
	private static final long serialVersionUID = 1399402019868470519L;
	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("用户名")
	private String userName;
	@ApiModelProperty("头像")
	private String profilePhoto;

	@ApiModelProperty(value = "最近一条消息的房屋id")
	private String houseId;
	@ApiModelProperty(value = "房屋信息")
	private String houseInfos;
	@ApiModelProperty("未读消息数")
	private String unReadNum;
	@ApiModelProperty("最近一条消息")
	private String lastMsg;
	@ApiModelProperty("创建时间")
	private Date createTime;
	@ApiModelProperty("消息类型")
	private String msgType;
}
