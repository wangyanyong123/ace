package com.github.wxiaoqi.sms;

import lombok.Data;

import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:48 2018/11/20
 * @Modified By:
 */
@Data
public class Msg {
	private String title;
	private String context;
	private String page;
	private String sound;
	private Map<String, String> params;

}
