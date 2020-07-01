package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;

/**
 * 同步人脸数据封装
 */
@Data
public class SyncCrmFaceVo implements Serializable {
	private static final long serialVersionUID = -5251030320042256997L;
	// 用户照片url
	private String photoUrl;
	// 房间Guid
	private String houseID;
	// 房间编码
	private String housecode;
	// 姓名/昵称
	private String name;
	// 手机号码
	private String phoneNumber;
	// 与业主关系
	private String relationship;

}
