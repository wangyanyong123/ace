package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:27 2018/12/27
 * @Modified By:
 */
@Data
public class FaceHeadVo implements Serializable {
	private static final long serialVersionUID = -5251030320042256997L;
	private String appId;
	private String timestamp;
	private String sign;
}
