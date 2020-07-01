package com.github.wxiaoqi.security.schedulewo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:29 2019/3/6
 * @Modified By:
 */
@Data
public class UserInfoVo implements Serializable {
	private static final long serialVersionUID = -6784868002164069659L;
	private String id;
	private String mobilePhone;
}
