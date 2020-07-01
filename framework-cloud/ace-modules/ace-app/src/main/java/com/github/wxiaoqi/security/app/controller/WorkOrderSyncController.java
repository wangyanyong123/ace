package com.github.wxiaoqi.security.app.controller;


import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateByTypeVo;
import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.biz.BizTransactionLogBiz;
import com.github.wxiaoqi.security.app.entity.BizSubscribeWo;
import com.github.wxiaoqi.security.app.mapper.BizSubscribeWoMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.in.WoInfo;
import com.github.wxiaoqi.security.app.vo.order.ResponseEntity;
import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * @Author: huangxl
 * @Description: 同步工单状态接口
 * @Date: Created in  2019/8/8
 */
@RestController
@RequestMapping("wo")
@CheckExternalService
@Api(tags="同步工单状态接口模块")
@Slf4j
public class WorkOrderSyncController {

	@Autowired
	private BizSubscribeWoBiz bizSubscribeWoBiz;
	@Autowired
	private BizTransactionLogBiz bizTransactionLogBiz;
	@Autowired
	private BizSubscribeWoMapper bizSubscribeWoMapper;

	@RequestMapping(value = "syncWorkOrderState", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "接收同步工单状态接口", notes = "接收同步工单状态接口",httpMethod = "POST")
	public ResponseEntity syncWorkOrderState(@RequestBody @ApiParam WoInfo woInfo){
		log.info("接收同步工单状态接口参数："+woInfo.toString());
		ResponseEntity responseEntity = new ResponseEntity();
		String woId = woInfo.getUuid();
		if(StringUtils.isEmpty(woId)){
			responseEntity.setCode("101");
			responseEntity.setDescribe("参数不能为空");
			return responseEntity;
		}
		DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
		doOperateByTypeVo.setId(woId);
		doOperateByTypeVo.setHandleBy(woInfo.getDealName());
		//(01-已创建、03-处理中、05-已完成、07-已关闭)
		if("03".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.WOGRAB.toString());
		}else if("05".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.WOFINISH.toString());
		}else if("07".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.REFUND.toString());
		}else if("08".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.ARRIVE_ON_TIME.toString());
			//1.修改工单表
			BizSubscribeWo bizSubscribeWo = new BizSubscribeWo();
			bizSubscribeWo.setId(woId);
			bizSubscribeWo.setArriveWoTime(new Date());
			bizSubscribeWo.setModifyTime(new Date());
			bizSubscribeWoMapper.updateByPrimaryKeySelective(bizSubscribeWo);

			//3.记录工单操作日志
			TransactionLogBean transactionLogBean = new TransactionLogBean();
			transactionLogBean.setCurrStep("到岗");
			transactionLogBean.setDesc("服务人员已上门");
			bizTransactionLogBiz.insertTransactionLog(woId,transactionLogBean);
			return responseEntity;
		}else if("09".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.DEAL_EXPRESS.toString());
		}else if("10".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.REFUSEREFUND.toString());
		}else if("11".equals(woInfo.getWoStatus())){
			doOperateByTypeVo.setOperateType(OperateConstants.OperateType.SIGN_REFUNDBOND.toString());
		}
		doOperateByTypeVo.setDescription(woInfo.getDescription());
		doOperateByTypeVo.setImgId(woInfo.getFileUrlStr());
		doOperateByTypeVo.setDealTel(woInfo.getDealTel());
		doOperateByTypeVo.setHandleBy(woInfo.getDealName());
		ObjectRestResponse restResponse = bizSubscribeWoBiz.doOperateByType(doOperateByTypeVo);
		if(restResponse!=null){
			responseEntity.setCode(restResponse.getStatus()+"");
			responseEntity.setDescribe(restResponse.getMessage());
		}else{
			responseEntity.setCode("101");
			responseEntity.setDescribe("同步异常");
			return responseEntity;
		}
		return responseEntity;
	}

}
