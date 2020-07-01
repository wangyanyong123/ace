package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.ApplyRefundParam;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditVo;
import com.github.wxiaoqi.security.api.vo.order.in.RefundAuditparamVo;
import com.github.wxiaoqi.security.api.vo.order.in.RefundInfoVo;
import com.github.wxiaoqi.security.api.vo.order.out.RefundOrderAuditInfoVo;
import com.github.wxiaoqi.security.api.vo.to.AliRefundTO;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.in.DoOperateBean;
import com.github.wxiaoqi.security.app.vo.product.in.ProductVo;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.MsgNoticeConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 退款审核表
 *
 * @author zxl
 * @Date 2019-03-28 14:08:45
 */
@Service
@Slf4j
public class BizRefundAuditBiz extends BusinessBiz<BizRefundAuditMapper,BizRefundAudit> {
	@Autowired
	private BizSubscribeWoMapper subscribeWoMapper;
	@Autowired
	private SysMsgNoticeBiz sysMsgNoticeBiz;
	@Autowired
	private BizSubscribeWoBiz subscribeWoBiz;
	@Autowired
	private BizActivityMapper bizActivityMapper;
	@Autowired
	private BizRefundAuditNewBiz bizRefundAuditNewBiz;
	@Autowired
	private BizSubBiz bizSubBiz;
	@Autowired
	private BizAccountBookMapper bizAccountBookMapper;

	public ObjectRestResponse applyRefund(RefundInfoVo refundInfoVo) {
		BizSubscribeWo subscribeWo = subscribeWoMapper.selectByPrimaryKey(refundInfoVo.getSubId());
		BizSubscribe bizSubscribe = bizSubBiz.selectById(refundInfoVo.getSubId());
		ApplyRefundParam applyRefundParam = new ApplyRefundParam();
		applyRefundParam.setBusType(AceDictionary.BUS_TYPE_OLD);
		applyRefundParam.setSend(true);
		applyRefundParam.setUserType(refundInfoVo.getUserType());
		applyRefundParam.setSubId(refundInfoVo.getSubId());
		applyRefundParam.setTenantId(subscribeWo.getCompanyId());
		applyRefundParam.setSubCode(subscribeWo.getCode());
		applyRefundParam.setSubTitle(bizSubscribe.getTitle());
		applyRefundParam.setSubCreateTime(bizSubscribe.getCreateTime());
		applyRefundParam.setApplyPrice(bizSubscribe.getActualCost());
		applyRefundParam.setUserId(bizSubscribe.getUserId());
		applyRefundParam.setProjectId(subscribeWo.getProjectId());
		BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusBySubId(refundInfoVo.getSubId());
		Assert.notNull(accountBook,"未找到该单据的支付记录");
		applyRefundParam.setActualId(accountBook.getActualId());
		return bizRefundAuditNewBiz.applyRefund(applyRefundParam);
	}

	@Autowired
	private BizRefundAuditMapper refundAuditMapper;
	@Autowired
	private ToolFegin toolFegin;
	@Autowired
	private BizFlowProcessOperateMapper flowProcessOperateMapper;
	@Autowired
	private BizProductMapper bizProductMapper;
	@Resource
	private BaseAppServerUserMapper baseAppServerUserMapper;

	public ObjectRestResponse refund(String subId,String refundReason) {
		ObjectRestResponse response= new ObjectRestResponse();
		if(StringUtils.isEmpty(subId)){
			response.setStatus(500);
			response.setMessage("subId不能为空！");
			return response;
		}
		AliRefundTO aliRefundTO = new AliRefundTO();
		aliRefundTO.setRefundReason(refundReason);
		String outTradeNo = "";
		try {
			outTradeNo = refundAuditMapper.getOutTradeNoBySubId(subId);
		}catch (Exception e){
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(outTradeNo)){
			response.setStatus(501);
			response.setMessage("未查询商品支付编码！");
			return response;
		}else {
			aliRefundTO.setOutTradeNo(outTradeNo);
		}
		try {
			response = toolFegin.refund(aliRefundTO);
//			if(response !=null && 200 == response.getStatus()){
//				Map<String, Object> map = flowProcessOperateMapper.getOperateIdByIdAndOperateType(subId,OperateConstants.OperateType.REFUNDCALLBACK.toString());
//				if(map!=null && map.size()>0){
//					//操作Id
//					String operateId = (String) map.get("operateId");
//					DoOperateBean doOperateBean = new DoOperateBean();
//					doOperateBean.setId(subId);
//					doOperateBean.setOperateId(operateId);
//					//调用支付回调成功操作
//					try {
//						ObjectRestResponse restResponse = subscribeWoBiz.doOperate(doOperateBean);
//						if(restResponse==null || restResponse.getStatus()!=200) {
//							log.info(">>>>>>>>>>>调用subscribeWoBiz.doOperate()失败，返回的结果:"+JSON.toJSONString(restResponse));
//						}
//					}catch (Exception e){
//						log.error(">>>>>>>>>>>调用subscribeWoBiz.doOperate()出错");
//						e.printStackTrace();
//					}
//				}
//			}
		}catch (Exception e){
			e.printStackTrace();
			response.setMessage("退款失败");
			response.setStatus(500);
		}
		return response;
	}

	public ObjectRestResponse getRefundAuditList(RefundAuditparamVo refundAuditparamVo) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		int page = refundAuditparamVo.getPage();
		int limit = refundAuditparamVo.getLimit();
		if (page < 0 ) {
			page = 1;
		}
		if (limit < 0) {
			limit = 10;
		}
		//分页
		int startIndex = (page - 1) * limit;
		Map<String,Object> paramMap = new HashMap<>();
		if (page != 0 && limit != 0) {
			paramMap.put("page",startIndex);
			paramMap.put("limit",limit);
		}
		paramMap.put("startDate",refundAuditparamVo.getStartTime());
		paramMap.put("endDate",refundAuditparamVo.getEndTime());
		paramMap.put("projectId",refundAuditparamVo.getProjectId());
		paramMap.put("companyId",refundAuditparamVo.getCompanyId());
		paramMap.put("auditStat",refundAuditparamVo.getAuditStat());
		paramMap.put("searchVal",refundAuditparamVo.getSearchVal());
		List<RefundOrderAuditInfoVo> refundOrderAuditInfoVos = refundAuditMapper.getRefundAuditList(paramMap);
		int total = 0;
		if(refundOrderAuditInfoVos==null || refundOrderAuditInfoVos.size()==0){
			refundOrderAuditInfoVos = new ArrayList<>();
		}else {
			total = refundAuditMapper.getRefundAuditListTotal(paramMap);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("total",total);
		map.put("list",refundOrderAuditInfoVos);
		restResponse.setData(map);
		return restResponse;
	}

	public ObjectRestResponse refundAudit(RefundAuditVo refundAuditVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		BizRefundAudit refundAudit = this.mapper.selectByPrimaryKey(refundAuditVo.getAuditId());
		if(null == refundAudit){
			response.setStatus(504);
			response.setMessage("审核id错误！");
			return response;
		}

		String tenantType = baseAppServerUserMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
		if(StringUtils.isEmpty(tenantType) ||(!"3".equals(tenantType))){
			if(!refundAudit.getCompanyId().equals(BaseContextHandler.getTenantID())){
				response.setStatus(505);
				response.setMessage("只有该商家可以审核！");
				return response;
			}
		}

		if(!"0".equals(refundAudit.getAuditStatus()) || "0".equals(refundAudit.getStatus())){
			response.setStatus(506);
			response.setMessage("该条信息已经被审核过了！");
			return response;
		}
		String receiverId = refundAudit.getApplyId();
		SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
		smsNoticeVo.setReceiverId(receiverId);
		smsNoticeVo.setObjectId(refundAudit.getSubId());
		//活动退款审核
		BizActivityApply activityApply = bizActivityMapper.selectIsActivityBySubId(refundAudit.getSubId());
		if(activityApply != null){
			Map<String,String> paramMap = new HashMap<>();
			paramMap.put("id",activityApply.getActivityId());
			if (bizActivityMapper.updateRefundAuditStatus(refundAuditVo.getIsPass(),BaseContextHandler.getUserID(),refundAudit.getSubId()) > 0){
				if("1".equals(refundAuditVo.getIsPass())){
					paramMap.put("status", "8");
					JSONObject json = JSONObject.fromObject(paramMap);
					smsNoticeVo.setObjectId(json.toString());
					refundAudit.setAuditStatus("1");
					smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.ACTIVITY_REFUND_P));
					//sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
				}else {
					paramMap.put("status", "9");
					JSONObject json = JSONObject.fromObject(paramMap);
					smsNoticeVo.setObjectId(json.toString());
					smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.ACTIVITY_REFUND_F));
					//sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
					refundAudit.setAuditStatus("4");
				}
			}
		}else{
			//商品订单退款审核
			if("1".equals(refundAuditVo.getIsPass())){
				refundAudit.setAuditStatus("1");
				smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.COMMODITY_REFUND_P));
			}else {
				smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.COMMODITY_REFUND_F));
				refundAudit.setAuditStatus("4");
			}
		}
		refundAudit.setModifyBy(BaseContextHandler.getUserID());
		refundAudit.setModifyTime(new Date());
		if(this.mapper.updateByPrimaryKeySelective(refundAudit) > 0){
			String opType = "";
			boolean falg = false;
			if("1".equals(refundAuditVo.getIsPass())){
				opType = OperateConstants.OperateType.REFUNDCALLBACK.toString();
				ObjectRestResponse restResponse = refund(refundAudit.getSubId(),"正常退款");
				if(restResponse != null && 200 == restResponse.getStatus()){
					falg = true;
					refundAudit.setAuditStatus("2");
					refundAudit.setModifyBy(BaseContextHandler.getUserID());
					refundAudit.setModifyTime(new Date());
					this.mapper.updateByPrimaryKeySelective(refundAudit);
					log.info(">>>>>>>>>>>>>>>>>>>>审核消息通知:" + JSON.toJSONString(smsNoticeVo));
					sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
					//审核用户退款成功.销量减1
					//查询产品id
					List<ProductVo> productVos = bizProductMapper.selectProductVoByWoId(refundAudit.getSubId());
					if(productVos.size()>0){
						for (ProductVo productVo:productVos) {
							bizProductMapper.updateSalesNum(productVo.getProductId(),productVo.getSubNum());
							//家政超市
							bizProductMapper.updateResSalesNum(productVo.getProductId(),productVo.getSubNum());
						}
					}
				}else {
					refundAudit.setAuditStatus("3");
					refundAudit.setModifyBy(BaseContextHandler.getUserID());
					refundAudit.setModifyTime(new Date());
					this.mapper.updateByPrimaryKeySelective(refundAudit);
				}
			}else {
				falg = true;
				opType = OperateConstants.OperateType.REFUSEREFUND.toString();
				log.info(">>>>>>>>>>>>>>>>>>>>审核消息通知:" + JSON.toJSONString(smsNoticeVo));
				sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
			}
			if(falg){
				Map<String, Object> map = flowProcessOperateMapper.getOperateIdByIdAndOperateType(refundAudit.getSubId(),opType);
				if(map!=null && map.size()>0 ){
					//操作Id
					String operateId = (String) map.get("operateId");
					DoOperateBean doOperateBean = new DoOperateBean();
					doOperateBean.setId(refundAudit.getSubId());
					doOperateBean.setOperateId(operateId);
					//调用支付回调成功操作
					try {
						ObjectRestResponse restResponse = subscribeWoBiz.doOperate(doOperateBean);
						if(restResponse==null || restResponse.getStatus()!=200) {
							log.info(">>>>>>>>>>>调用subscribeWoBiz.doOperate()失败，返回的结果:"+JSON.toJSONString(restResponse));
						}
					}catch (Exception e){
						log.error(">>>>>>>>>>>调用subscribeWoBiz.doOperate()出错");
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}

	public ObjectRestResponse restartRefund(String auditId) {
		ObjectRestResponse response = new ObjectRestResponse();
		BizRefundAudit refundAudit = this.mapper.selectByPrimaryKey(auditId);
		if(null == refundAudit){
			response.setStatus(504);
			response.setMessage("审核id错误！");
			return response;
		}
		if(!refundAudit.getCompanyId().equals(BaseContextHandler.getTenantID())){
			response.setStatus(505);
			response.setMessage("只有该商家可以重新发起退款！");
			return response;
		}
		if(!"3".equals(refundAudit.getAuditStatus()) || "0".equals(refundAudit.getStatus())){
			response.setStatus(506);
			response.setMessage("该条订单不能发起重新退款！");
			return response;
		}
		String receiverId = refundAudit.getApplyId();
		SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
		smsNoticeVo.setReceiverId(receiverId);
		smsNoticeVo.setObjectId(refundAudit.getSubId());
		refundAudit.setAuditStatus("1");
		refundAudit.setModifyBy(BaseContextHandler.getUserID());
		refundAudit.setModifyTime(new Date());
		if(this.mapper.updateByPrimaryKeySelective(refundAudit) > 0){
			ObjectRestResponse restResponse = refund(refundAudit.getSubId(),"正常退款");
			if(restResponse == null && 200 == restResponse.getStatus()){
				refundAudit.setAuditStatus("2");
				refundAudit.setModifyBy(BaseContextHandler.getUserID());
				refundAudit.setModifyTime(new Date());
				this.mapper.updateByPrimaryKeySelective(refundAudit);
				Map<String, Object> map = flowProcessOperateMapper.getOperateIdByIdAndOperateType(refundAudit.getSubId(),OperateConstants.OperateType.REFUNDCALLBACK.toString());
				if(map!=null && map.size()>0 ){
					String operateId = (String) map.get("operateId");
					DoOperateBean doOperateBean = new DoOperateBean();
					doOperateBean.setOperateId(operateId);
					doOperateBean.setId(refundAudit.getSubId());
					try {
						ObjectRestResponse resp = subscribeWoBiz.doOperate(doOperateBean);
						if(resp==null || resp.getStatus()!=200) {
							log.info(">>>>>>>>>>>调用subscribeWoBiz.doOperate()失败，返回的结果:"+JSON.toJSONString(restResponse));
						}
					}catch (Exception e){
						log.error(">>>>>>>>>>>调用subscribeWoBiz.doOperate()出错");
						e.printStackTrace();
					}
				}
				log.info(">>>>>>>>>>>>>>>>>>>>审核消息通知:" + JSON.toJSONString(smsNoticeVo));
				sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
				//活动重新发起退款审核（通过）
				bizActivityMapper.updateRefundAuditStatus("1",BaseContextHandler.getUserID(),refundAudit.getSubId());
			}else {
				refundAudit.setAuditStatus("3");
				refundAudit.setModifyBy(BaseContextHandler.getUserID());
				refundAudit.setModifyTime(new Date());
				this.mapper.updateByPrimaryKeySelective(refundAudit);
				//活动重新发起退款审核（拒绝）
				bizActivityMapper.updateRefundAuditStatus("2",BaseContextHandler.getUserID(),refundAudit.getSubId());
			}
		}
		return response;
	}

	public ObjectRestResponse viewReason(String auditId) {
		ObjectRestResponse response = new ObjectRestResponse();
		response.setData(this.mapper.viewReason(auditId));
		return response;
	}
}