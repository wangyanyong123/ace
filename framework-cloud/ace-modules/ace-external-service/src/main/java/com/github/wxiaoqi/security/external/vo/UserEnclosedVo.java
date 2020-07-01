package com.github.wxiaoqi.security.external.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:58 2019/1/4
 * @Modified By:
 */
@Data
public class UserEnclosedVo implements Serializable {
	private static final long serialVersionUID = -5449020763985570144L;
	private String unitId;
	private String enclosedId;
	private String enclosedPid;
}
