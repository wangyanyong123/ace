package com.github.wxiaoqi.sms;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:58 2018/11/20
 * @Modified By:
 */
public class MsgReceiver implements Serializable {

	private static final long serialVersionUID = -7289611342571148706L;
	private String receiverType = ReceiverType.INDIVIDUAL.toString();//默认个人
	private String objectId;
	private String phoneNum;
	private String email;


	public MsgReceiver() {
		super();
	}

	public MsgReceiver(String receiverType, String objectId) {
		super();
		this.receiverType = receiverType;
		this.objectId = objectId;
	}

	public static MsgReceiver buildByPhoneNum(String phoneNum){
		MsgReceiver mr = new MsgReceiver();
		mr.setPhoneNum(phoneNum);
		return mr;
	}

	public static MsgReceiver buildByObjectId(String objectId){
		MsgReceiver mr = new MsgReceiver();
		mr.setObjectId(objectId);
		return mr;
	}

	public static MsgReceiver buildByEmail(String email){
		MsgReceiver mr = new MsgReceiver();
		mr.setEmail(email);
		return mr;
	}

	public static MsgReceiver buildByAll(String phoneNum, String objectId, String email){
		MsgReceiver mr = new MsgReceiver();
		mr.setPhoneNum(phoneNum);
		mr.setObjectId(objectId);
		mr.setEmail(email);
		return mr;
	}

	public MsgReceiver(String receiverType, String objectId,
					   String phoneNum, String email) {
		super();
		this.receiverType = receiverType;
		this.objectId = objectId;
		this.phoneNum = phoneNum;
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * 接受者类型
	 * @author xufeng
	 * @Description: TODO
	 * @date 2016年1月22日 下午3:03:33
	 * @version V1.0
	 *
	 */
	public enum ReceiverType{

		INDIVIDUAL("1"),PROJECT("2");
		private String val;

		public boolean equals(String value){
			if(value == null){
				return false;
			}
			return value.equals(this.val);
		}

		public String getVal() {
			return val;
		}
		public void setVal(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return val;
		}
		private ReceiverType(String value){
			this.val = value;
		}

	}
}

