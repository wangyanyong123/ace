package com.github.wxiaoqi.sms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:40 2018/11/20
 * @Modified By:
 */
@Data
public class MsgVo implements Serializable {

	private static final long serialVersionUID = 9203584945290268925L;
	private String phone;
	private String code;
}
