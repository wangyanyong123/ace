package com.github.wxiaoqi.security.jinmao.vo.houseKeeper;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:22 2018/12/7
 * @Modified By:
 */
@Data
public class HouseKeeperInVo implements Serializable {
	private static final long serialVersionUID = 6295595726072697151L;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "性别")
	private String sex;
	@ApiModelProperty(value = "手机号")
	private String mobilePhone;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "个人照片")
	private List<ImgInfo>  profilePhoto;
}
