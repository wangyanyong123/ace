package com.github.wxiaoqi.pay.wechat.config;


/**
 * @Author: Lzx
 * @Description: 主要与APP前端进行交互
 * @Date: Created in 11:01 2018/12/7
 * @Modified By:
 */
public class ResponseJson {

	// 结果码
	private String code;
	// 结果说明
	private String message;
	// 内容
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
