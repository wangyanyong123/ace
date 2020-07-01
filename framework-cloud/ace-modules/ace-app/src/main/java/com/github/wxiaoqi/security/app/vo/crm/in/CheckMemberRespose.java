package com.github.wxiaoqi.security.app.vo.crm.in;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:42 2018/12/17
 * @Modified By:
 */
@Data
public class CheckMemberRespose implements Serializable {
	private static final long serialVersionUID = -4096828118887692519L;
	private String code;
	private String describe;
	private CheckMemberBody data;
}
