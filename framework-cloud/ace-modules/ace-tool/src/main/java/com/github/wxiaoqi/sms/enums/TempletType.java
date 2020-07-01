package com.github.wxiaoqi.sms.enums;

/**
 * @Author: Lzx
 * @Description: 模版类型:1.手机通知，2.手机消息(APP消息)，3.短信，4.邮件，5.站点消息
 * @Date: Created in 15:31 2018/11/20
 * @Modified By:
 */
public enum TempletType {
	NOTICE("1"),MESSAGE("2"),SMS("3"),EMAIL("4"), SITEMESSAGE("5");

	private String val;

	public String getType(){
		return val;
	}

	public boolean equals(String value){
		if(value == null){
			return false;
		}
		return value.equals(this.val);
	}

	private TempletType(String value){
		this.val = value;
	}
}
