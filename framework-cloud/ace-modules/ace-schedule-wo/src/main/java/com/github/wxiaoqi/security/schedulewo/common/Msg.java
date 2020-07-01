package com.github.wxiaoqi.security.schedulewo.common;

/**
 * 
 * @author xufeng
 * @Description: 消息
 * @date 2015-6-8 下午5:55:30
 * @version V1.0
 * 
 */
public class Msg {
	
	private String os;
	private String cid;
	private String alias;
	private String title;
	private String context;

	public Msg() {
		super();
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}


	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
