package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.order.in.QueryRefundAuditListParam;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditVo;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditparamVo;
import com.github.wxiaoqi.security.api.vo.order.out.RefundOrderAuditInfoVo;
import com.github.wxiaoqi.security.app.biz.BizRefundAuditBiz;
import com.github.wxiaoqi.security.app.biz.BizRefundAuditNewBiz;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:33 2019/3/28
 * @Modified By:
 */
@RestController
@RequestMapping("refundAudit")
@CheckClientToken
@CheckUserToken
@Api(tags="计划工单退款审核")
public class RefundAuditController {

	@Autowired
	private BizRefundAuditBiz refundAuditBiz;
	@Autowired
	private BizRefundAuditNewBiz bizRefundAuditNewBiz;

	@PostMapping(value = "/getRefundAuditList")
	@ApiOperation(value = "获取退款审核列表", notes = "获取退款审核列表",httpMethod = "POST")
	public ObjectRestResponse getRefundAuditList(@RequestBody @ApiParam QueryRefundAuditListParam param){

			Map<String,Object> result = new HashMap<>();
			int count = bizRefundAuditNewBiz.countRefundAuditList(param);
			List<RefundOrderAuditInfoVo> list;
			if(count > 0){
				list = bizRefundAuditNewBiz.findRefundAuditList(param);
			}else{
				list = Collections.emptyList();
			}
			result.put("total",count);
			result.put("list",list);
			return ObjectRestResponse.ok(result);
	}

	@PostMapping("refundAudit")
	@ApiOperation(value = "审核", notes = "审核",httpMethod = "POST")
	public ObjectRestResponse refundAudit( @RequestBody @ApiParam RefundAuditVo refundAuditVo) {

		return bizRefundAuditNewBiz.refundAudit(refundAuditVo);
	}

	@GetMapping("restartRefund")
	@ApiOperation(value = "重新发起退款", notes = "重新发起退款",httpMethod = "GET")
	@ApiImplicitParam(name="auditId",value="实际支付ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse restartRefund(String auditId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(auditId)){
			response.setStatus(502);
			response.setMessage("审核id不能为空！");
			return response;
		}
		return bizRefundAuditNewBiz.restartRefund(auditId);
	}

	@GetMapping("viewReason")
	@ApiOperation(value = "查看原因", notes = "查看原因",httpMethod = "GET")
	@ApiImplicitParam(name="auditId",value="实际支付ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse viewReason(String auditId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(auditId)){
			response.setStatus(502);
			response.setMessage("审核id不能为空！");
			return response;
		}
		return refundAuditBiz.viewReason(auditId);
	}


	/**
	 * 强制审核通过
	 * @param orderId
	 * @return
	 */
	@GetMapping("forceRefundAudit")
	@ApiOperation(value = "强制审核通过", notes = "强制审核通过",httpMethod = "POST")
	public ObjectRestResponse forceRefundAudit(String orderId) {
		return bizRefundAuditNewBiz.forceRefundAudit(orderId);
	}

}
