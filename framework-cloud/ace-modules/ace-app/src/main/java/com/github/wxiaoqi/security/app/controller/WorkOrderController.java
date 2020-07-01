package com.github.wxiaoqi.security.app.controller;


import com.github.wxiaoqi.security.api.vo.order.in.FileListVo;
import com.github.wxiaoqi.security.app.biz.BizWoBiz;

import com.github.wxiaoqi.security.app.fegin.ToolFegin;

import com.github.wxiaoqi.security.app.vo.in.WoInVo;
import com.github.wxiaoqi.security.app.vo.order.CreateWorkOrderIn;

import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;

import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: huangxl
 * @Description: 投诉报修工单对接模块
 * @Date: Created in  2019/2/12
 * @Modified By:
 */
@RestController
@RequestMapping("rest/externalwo")
@CheckExternalService
@Api(tags="投诉报修工单对接模块")
@Slf4j
public class WorkOrderController {

	@Autowired
	private BizWoBiz bizWoBiz;
	@Autowired
	private ToolFegin toolFegin;

	@RequestMapping(value = "createWorkOrder", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "生成报修投诉工单接口", notes = "生成报修投诉工单接口",httpMethod = "POST")
	public ObjectRestResponse<Map<String,String>> createWorkOrder(@RequestBody @ApiParam CreateWorkOrderIn createWorkOrderIn)
			throws Exception {
		ObjectRestResponse result = new ObjectRestResponse();
        log.info("生成报修投诉工单接口参数："+createWorkOrderIn.toString());
		if(StringUtils.isAnyoneEmpty(createWorkOrderIn.getCrmWoCode(),createWorkOrderIn.getProjectId(),createWorkOrderIn.getContacts(),createWorkOrderIn.getContactTel(),
				createWorkOrderIn.getType(),createWorkOrderIn.getClassifyCode(),createWorkOrderIn.getClassifyName(),createWorkOrderIn.getDescription())) {
			result.setStatus(101);
			result.setMessage("生成工单参数不能为空");
			return result;
		}

		WoInVo woInVo = new WoInVo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if(StringUtils.isNotEmpty(createWorkOrderIn.getPlanTime())){
				Date date = sdf.parse(createWorkOrderIn.getPlanTime());
				woInVo.setExpectedServiceTimeStr(createWorkOrderIn.getPlanTime());
			}
		}catch (Exception e){
			result.setStatus(101);
			result.setMessage("预约时间格式不对");
			return result;
		}

		woInVo.setCrmWoCode(createWorkOrderIn.getCrmWoCode());
		//工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
		woInVo.setComeFrom("3");
		woInVo.setDescription(createWorkOrderIn.getDescription());
		woInVo.setRoomId(createWorkOrderIn.getRoomId());
		woInVo.setProjectId(createWorkOrderIn.getProjectId());
		woInVo.setThreeCategoryCode(createWorkOrderIn.getClassifyCode());
		woInVo.setThreeCategoryName(createWorkOrderIn.getClassifyName());
		woInVo.setContactTel(createWorkOrderIn.getContactTel());
		woInVo.setContactName(createWorkOrderIn.getContacts());
		if(createWorkOrderIn.getFileList()!=null && createWorkOrderIn.getFileList().size()>0){
			FileListVo fileListVo = new FileListVo();
			fileListVo.setFileList(createWorkOrderIn.getFileList());
			ObjectRestResponse resultResponse = toolFegin.uploadBase64Images(fileListVo);
			if(resultResponse!=null && 200==resultResponse.getStatus()){
				//图片id,多张图片逗号分隔
				woInVo.setImgId(resultResponse.getData()==null ? "" : (String)resultResponse.getData());
			}
		}
		if (createWorkOrderIn.getType().equals("1")) {
			woInVo.setBusId(BusinessConstant.getCmplainBusId());
			woInVo.setIncidentType("cmplain");
		}else if (createWorkOrderIn.getType().equals("2")){
			woInVo.setBusId(BusinessConstant.getRepairBusId());
			woInVo.setIncidentType("repair");
		}else{
			result.setStatus(101);
			result.setMessage("工单类型参数不正确");
			return result;
		}
		return bizWoBiz.createWoOrder(woInVo);
	}
}
