package com.github.wxiaoqi.security.app.vo.clientuser.out;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:00 2018/11/21
 * @Modified By:
 */
@Data
public class UserVo implements Serializable {

	private static final long serialVersionUID = -1711599056925833799L;
	@ApiModelProperty("用户id")
	private String id;
	@ApiModelProperty("手机号")
	private String mobilePhone;
	@ApiModelProperty("头像")
	private String profilePhoto;
	@ApiModelProperty("昵称")
	private String nickname;
	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("生日")
	private String birthday;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("性别：0、未知；1、男；2、女")
	private String sex;
	@ApiModelProperty("类型：1、业主；2、家属；3、租客")
	private String type;
	@ApiModelProperty("是否认证房屋：0、未认证；1、已认证")
	private String isAuth;
	@ApiModelProperty("是否添加当前社区：0、未添加；1、已添加")
	private String isAddCurrentProject;
	@ApiModelProperty("是否是运营人员：0、否；1、是（启用）2、(禁用)")
	private String isOperation;
	@ApiModelProperty("车牌前缀")
	private String abbreviation;
	@ApiModelProperty(value = "等级图片")
	private String gradeImg;
	@ApiModelProperty(value = "等级头衔")
	private String gradeTitle;
}
