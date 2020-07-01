package com.github.wxiaoqi.security.app.vo.face;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:26 2018/12/27
 * @Modified By:
 */
@Data
public class UploadfaceVo extends FaceHeadVo implements Serializable {

	private static final long serialVersionUID = 7603997071187176926L;
	private List<String> photoArray;
	private String projectId;
	private String appUserId;
	private List<Map<String,String>> unitList;

}
