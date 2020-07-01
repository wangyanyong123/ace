package com.github.wxiaoqi.security.jinmao.vo.houseKeeper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:22 2018/12/7
 * @Modified By:
 */
@Data
public class HouseKeeperVo implements Serializable {
	private static final long serialVersionUID = 5494555827765673231L;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "性别")
	private String sex;
	@ApiModelProperty(value = "手机号")
	private String mobilePhone;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "个人照片")
	private String  profilePhoto;
}
