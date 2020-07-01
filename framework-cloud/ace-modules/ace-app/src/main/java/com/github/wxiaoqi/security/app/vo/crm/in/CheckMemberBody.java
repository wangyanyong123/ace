package com.github.wxiaoqi.security.app.vo.crm.in;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:43 2018/12/17
 * @Modified By:
 */
@Data
public class CheckMemberBody implements Serializable {
	private static final long serialVersionUID = -2339317023843154730L;
	private String isOwner;
	private String crmUserId;
	private String isSignService;
	private String memberId;
	private String tips;
	private String isSigned;


}
