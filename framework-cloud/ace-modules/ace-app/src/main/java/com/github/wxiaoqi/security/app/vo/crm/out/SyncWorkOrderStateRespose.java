package com.github.wxiaoqi.security.app.vo.crm.out;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: huangxl
 * @Description:
 * @Date: Created in   2019/2/12
 * @Modified By:
 */
@Data
public class SyncWorkOrderStateRespose implements Serializable {

	private static final long serialVersionUID = -4504225733285741843L;
	private String code;
	private String describe;
	private String msg;
}
