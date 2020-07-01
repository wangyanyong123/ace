package com.github.wxiaoqi.sms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:57 2018/11/20
 * @Modified By:
 */
public class MsgInfo implements Serializable {

	private static final long serialVersionUID = -6717485037003245875L;

	private String theme;//消息主题
	private MsgReceiver receiver;//消息接收者
	private Map<String, String> para;//消息参数
	private Map<String,String> morePara;//更多消息，因为手机推送需要把数据带到客户端，而推送消息是有长度限制，故不需要带到客户端的信息放到此变量中

	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public MsgReceiver getReceiver() {
		return receiver;
	}
	public void setReceiver(MsgReceiver receiver) {
		this.receiver = receiver;
	}
	public Map<String, String> getPara() {
		if(para==null){
			para = new HashMap<>();
		}
		return para;
	}
	public void setPara(Map<String, String> para) {
		this.para = para;
	}
	public MsgInfo() {
		super();
	}
	public MsgInfo(String theme, MsgReceiver receiver,
				   Map<String, String> para) {
		super();
		this.theme = theme;
		this.receiver = receiver;
		this.para = para;
	}
	public MsgInfo morePara(Map<String,String> morePara) {
		this.morePara = morePara;
		return this;
	}

	public Map<String,String> getMorePara() {
		if(morePara==null){
			morePara = new HashMap<>();
		}
		return morePara;
	}
	public void setMorePara(Map<String,String> morePara) {
		this.morePara = morePara;
	}
	@Override
	public String toString() {
		return "MsgInfo [theme=" + theme + ", receiver=" + receiver + ", para=" + para + ", morePara=" + morePara + "]";
	}
}

