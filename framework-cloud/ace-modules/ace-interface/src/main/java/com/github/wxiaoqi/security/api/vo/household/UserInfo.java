package com.github.wxiaoqi.security.api.vo.household;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:15 2018/12/5
 * @Modified By:
 */
@Data
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1100283256032963727L;
	@ApiModelProperty(value = "用户id")
	private String userId;
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
}
