package com.github.wxiaoqi.log.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:23 2019/4/11
 * @Modified By:
 */
@Data
public class UserInfoVo implements Serializable {

	private static final long serialVersionUID = -9069129678557056905L;

	private String account;
	private String name;
	private String phone;
	private String photo;
	private String sex;
	private String email;
	private String createTime;
	private String userType;
}
