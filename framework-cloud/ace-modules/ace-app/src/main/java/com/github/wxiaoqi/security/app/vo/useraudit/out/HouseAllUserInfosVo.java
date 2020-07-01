package com.github.wxiaoqi.security.app.vo.useraudit.out;

import com.github.wxiaoqi.security.app.vo.userhouse.out.UserInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:38 2018/11/23
 * @Modified By:
 */
@Data
public class HouseAllUserInfosVo implements Serializable {
	private static final long serialVersionUID = -4630832459267845025L;
	@ApiModelProperty("当前用户信息")
	private UserInfoVo ownInfo;
	@ApiModelProperty("其他用户信息")
	private List<UserInfoVo> otherInfos;
}
