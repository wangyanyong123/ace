package com.github.wxiaoqi.security.schedulewo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:45 2019/4/4
 * @Modified By:
 */
@Data
public class SubVo implements Serializable {
	private static final long serialVersionUID = 7098903382593056582L;
	private String subId;
	private String userId;
	private String title;
}
