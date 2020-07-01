package com.github.wxiaoqi.security.external.controller;

import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoTwoVo;
import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.external.feign.AppFegin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:08 2018/12/25
 * @Modified By:
 */
@RestController
@RequestMapping("rest/common")
@CheckExternalService
@Api(tags="人脸")
public class FaceRecognitionController {
	@Autowired
	private AppFegin appFegin;

	@PostMapping("sysProjectInfo")
	@ApiOperation(value = "同步基础信息接口", notes = "同步基础信息接口",httpMethod = "POST")
	public ObjectRestResponse<List<SysProjectInfoVo>> sysProjectInfo(){
		return appFegin.sysProjectInfo();
	}

	@PostMapping("getSysProjectInfo")
	@ApiOperation(value = "同步基础信息接口", notes = "同步基础信息接口",httpMethod = "POST")
	public ObjectRestResponse<List<SysProjectInfoTwoVo>> getSysProjectInfo(){
		return appFegin.getSysProjectInfo();
	}

}
