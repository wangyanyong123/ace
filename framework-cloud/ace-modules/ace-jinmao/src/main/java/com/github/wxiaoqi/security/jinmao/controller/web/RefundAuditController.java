package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditVo;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditparamVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.feign.RefundFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:33 2019/3/28
 * @Modified By:
 */
@RestController
@RequestMapping("web/refundAudit")
@CheckClientToken
@CheckUserToken
@Api(tags="商品退款")
public class RefundAuditController {
	@Autowired
	private RefundFeign refundFeign;

	@Autowired
	private BaseTenantMapper baseTenantMapper;

	@PostMapping(value = "/getRefundAuditList")
	@ApiOperation(value = "获取退款审核列表", notes = "获取退款审核列表",httpMethod = "POST")
	public ObjectRestResponse getRefundAuditList(@RequestBody @ApiParam RefundAuditparamVo refundAuditparamVo) {
		if(refundAuditparamVo!=null && StringUtils.isEmpty(refundAuditparamVo.getCompanyId())){
			UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
			if(userInfo!=null && "2".equals(userInfo.getTenantType())){
				refundAuditparamVo.setCompanyId(BaseContextHandler.getTenantID());
			}else if(userInfo!=null && "1".equals(userInfo.getTenantType())){
				refundAuditparamVo.setCompanyId(BaseContextHandler.getTenantID());
			}
		}
		return refundFeign.getRefundAuditList(refundAuditparamVo);
	}

	@PostMapping("refundAudit")
	@ApiOperation(value = "审核", notes = "审核",httpMethod = "POST")
	public ObjectRestResponse audit( @RequestBody @ApiParam RefundAuditVo refundAuditVo) {
		return refundFeign.refundAudit(refundAuditVo);
	}

	@GetMapping("restartRefund")
	@ApiOperation(value = "重新发起退款", notes = "重新发起退款",httpMethod = "GET")
	@ApiImplicitParam(name="auditId",value="实际支付ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse restartRefund(String auditId) {
		return refundFeign.restartRefund(auditId);
	}

	@GetMapping("viewReason")
	@ApiOperation(value = "查看原因", notes = "查看原因",httpMethod = "GET")
	@ApiImplicitParam(name="auditId",value="实际支付ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse viewReason(String auditId) {
		return refundFeign.viewReason(auditId);
	}


	/**
	 * 强制审核通过
	 * @param orderId
	 * @return
	 */
	@GetMapping("forceRefundAudit")
	@ApiOperation(value = "强制审核通过", notes = "强制审核通过",httpMethod = "GET")
	ObjectRestResponse refundAudit(@RequestParam("orderId")  String orderId){
		UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
		if(userInfo!=null){
			if("3".equals(userInfo.getTenantType())) {
				return refundFeign.forceRefundAudit(orderId);
			}
		}
		ObjectRestResponse objectRestResponse = new ObjectRestResponse();
		objectRestResponse.setStatus(101);
		objectRestResponse.setMessage("非法用户");
		return objectRestResponse;
	}
}
