package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:25 2018/12/27
 * @Modified By:
 */
@Data
public class LogVo extends FaceHeadVo implements Serializable {
	private static final long serialVersionUID = 7003033698962466395L;
	private String projectId;
	private String startDate;
	private String endDate;
}
