package com.github.wxiaoqi.security.schedulewo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:20 2019/3/6
 * @Modified By:
 */
@Data
public class NoticeWoInfoVo implements Serializable {
	private static final long serialVersionUID = -7353187101562890815L;
	private String id;
	private String title;
	private String projectId;
}
