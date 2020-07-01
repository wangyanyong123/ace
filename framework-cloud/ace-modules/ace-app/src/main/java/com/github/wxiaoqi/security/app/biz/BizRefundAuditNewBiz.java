package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.api.vo.order.out.RefundOrderAuditInfoVo;
import com.github.wxiaoqi.security.api.vo.to.ApplyRefundTO;
import com.github.wxiaoqi.security.app.biz.order.OrderRefundHandler;
import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.app.entity.BizActivityApply;
import com.github.wxiaoqi.security.app.entity.BizRefundAudit;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.MsgNoticeConstant;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.netflix.client.ssl.AbstractSslContextFactory;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 退款审核
 *
 * @Author guohao
 * @Date 2020/4/10 11:28
 */
@Service
@Slf4j
public class BizRefundAuditNewBiz extends BusinessBiz<BizRefundAuditMapper, BizRefundAudit> {
    @Resource
    private OrderRefundHandler orderRefundHandler;
    @Autowired
    private SysMsgNoticeBiz sysMsgNoticeBiz;
    @Resource
    private BizActivityMapper bizActivityMapper;
    @Resource
    private BizRefundAuditBiz bizRefundAuditBiz;
    @Resource
    private BizAccountBookMapper bizAccountBookMapper;
    @Resource
    private BizRefundAuditMapper refundAuditMapper;
    @Autowired
    private ToolFegin toolFegin;

    @Resource
    private BaseAppServerUserMapper baseAppServerUserMapper;

    public ObjectRestResponse applyRefund(ApplyRefundParam applyRefundParam) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusByActualId(applyRefundParam.getActualId());
        Assert.notNull(accountBook,"支付id有误");
        Assert.isTrue(accountBook.getBusType().equals(applyRefundParam.getBusType())
                ,"申请业务类型与实际支付业务类型不匹配");
        //获取当前单据的所有有效退款记录
        List<BizRefundAudit> refundAuditList = refundAuditMapper.selectValidRefundAuditListBySubId(applyRefundParam.getSubId());
        long count = refundAuditList.stream()
                .filter(refundAudit -> !AceDictionary.REFUND_AUDIT_STATUS_COM.equals(refundAudit.getAuditStatus()))
                .count();

        Assert.isTrue(count == 0,"存在未完成的退款申请");
        BigDecimal validApplyPrice = refundAuditMapper.getValidApplyPrice(accountBook.getActualId());
        BigDecimal totalApplyPrice = validApplyPrice.add(applyRefundParam.getApplyPrice());
        Assert.isTrue(totalApplyPrice.compareTo(accountBook.getActualCost()) <= 0
                ,"申请退款金额超过支付金额");
        BizRefundAudit refundAudit = buildApplyRefundAudit(applyRefundParam);
        int i = this.mapper.insertSelective(refundAudit);
        if (i <= 0) {
            response.setStatus(502);
            response.setMessage("申请退款失败");
            return response;
        }
        if (AceDictionary.USER_TYPE_BUYER.equals(applyRefundParam.getUserType())) {
            SmsNoticeVo applyRefundSmsNotice = getApplyRefundSmsNotice(refundAudit);
            sysMsgNoticeBiz.saveSmsNotice(applyRefundSmsNotice);
        }
        response.setMessage("申请退款成功");
        if(!applyRefundParam.isSend()){
            RefundAuditVo refundAuditVo = new RefundAuditVo();
            refundAuditVo.setAuditId(refundAudit.getId());
            refundAuditVo.setIsPass("1");
            refundAuditVo.setAutoRefund(true);
            return refundAudit(refundAuditVo);
        }
        return response;
    }

    private BizRefundAudit buildApplyRefundAudit(ApplyRefundParam applyRefundParam) {
        String userId = BaseContextHandler.getUserID() == null ? "admin" : BaseContextHandler.getUserID();

        BizRefundAudit refundAudit = new BizRefundAudit();
        refundAudit.setId(UUIDUtils.generateUuid());
        refundAudit.setApplyId(userId);
        refundAudit.setApplyType(applyRefundParam.getUserType());
        refundAudit.setAuditStatus(AceDictionary.REFUND_AUDIT_STATUS_CHECKING);
        refundAudit.setCompanyId(applyRefundParam.getTenantId());
        refundAudit.setSubId(applyRefundParam.getSubId());
        refundAudit.setBusType(applyRefundParam.getBusType());
        refundAudit.setStatus(AceDictionary.DATA_STATUS_VALID);
        refundAudit.setCreateBy(userId);
        refundAudit.setCreateTime(new Date());
        refundAudit.setActualId(applyRefundParam.getActualId());
        refundAudit.setProjectId(applyRefundParam.getProjectId());
        refundAudit.setSubCode(applyRefundParam.getSubCode());
        refundAudit.setSubTitle(applyRefundParam.getSubTitle());
        refundAudit.setUserId(applyRefundParam.getUserId());
        refundAudit.setApplyPrice(applyRefundParam.getApplyPrice());
        refundAudit.setSubCreateTime(applyRefundParam.getSubCreateTime());
        if (AceDictionary.USER_TYPE_TENANT.equals(applyRefundParam.getUserType())) {
            refundAudit.setAuditStatus(AceDictionary.REFUND_AUDIT_STATUS_REFUNDING);
        }
        return refundAudit;
    }

    private SmsNoticeVo getApplyRefundSmsNotice(BizRefundAudit refundAudit) {
        BizActivityApply activityApply = bizActivityMapper.selectIsActivityBySubId(refundAudit.getSubId());
        SmsNoticeVo smsNoticeVo;
        if (activityApply != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", activityApply.getActivityId());
            jsonObject.put("status", "7");
            String[] smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.ACTIVITY_REFUND);
            smsNoticeVo = buildSmsNotice(refundAudit.getApplyId(), jsonObject.toString(), smsNotice);
        } else {
            String[] smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.COMMODITY_REFUND);
            smsNoticeVo = buildSmsNotice(refundAudit.getApplyId(), refundAudit.getSubId(), smsNotice);
        }
        return smsNoticeVo;
    }

    private SmsNoticeVo buildSmsNotice(String userId, String objectId, String[] smsNotice) {
        SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
        smsNoticeVo.setObjectId(objectId);
        smsNoticeVo.setReceiverId(userId);
        smsNoticeVo.setSmsNotice(smsNotice);
        return smsNoticeVo;

    }



    public ObjectRestResponse refund(String actualId,BigDecimal applyPrice,String outRequestNo, String refundReason) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(actualId)) {
            response.setStatus(500);
            response.setMessage("实际支付id不能为空！");
            return response;
        }
        ApplyRefundTO applyRefundTO = new ApplyRefundTO();
        applyRefundTO.setRefundReason(refundReason);
        BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusByActualId(actualId);
        if (accountBook == null) {
            response.setStatus(501);
            response.setMessage("未找到支付记录");
            return response;
        }
        if (!"2".equals(accountBook.getPayStatus())) {
            response.setStatus(501);
            response.setMessage("订单尚未支付，无法退款。");
            return response;
        }
//        if ("2".equals(accountBook.getRefundStatus())) {
//            //该条支付退款成功，（一条支付只允许退款一次。），直接返回退款成功
//            response.setMessage("退款成功。");
//            return response;
//        }
        applyRefundTO.setOutTradeNo(accountBook.getActualId());
        applyRefundTO.setAppId(accountBook.getAppId());
        applyRefundTO.setRefundMoney(applyPrice);
        applyRefundTO.setOutRequestNo(outRequestNo);
        try {
            response = toolFegin.refund(applyRefundTO);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("退款失败");
            response.setStatus(500);
        }
        return response;
    }

    /**
     * 退款审核，审核通过操作退款
     * <p>
     * 步骤：
     * 1: 审核通过直接操作支付平台退款，退款失败直接返回
     * 2：更新本地单据状态
     * 3：发消息通知
     *
     * @param refundAuditVo : 入参
     * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
     * @Author guohao
     * @Date 2020/4/11 20:06
     */
    public ObjectRestResponse refundAudit(RefundAuditVo refundAuditVo) {

        BizRefundAudit refundAudit = this.mapper.selectByPrimaryKey(refundAuditVo.getAuditId());
        if(AceDictionary.BUS_TYPE_OLD.equals(refundAudit.getBusType())){
            return bizRefundAuditBiz.refundAudit(refundAuditVo);
        }
        ObjectRestResponse response = beforeRefundAudit(refundAudit,refundAuditVo.isAutoRefund());
        if (200 != response.getStatus()) {
            return response;
        }
        boolean isPass = "1".equals(refundAuditVo.getIsPass());

        if(!isPass){
            return  doRejectRefund(refundAudit);
        }else {
            return doPassRefund(refundAudit);
        }
    }


    /**
     * 退款审核 业务校验
     *
     * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
     * @Author guohao
     * @Date 2020/4/11 20:08
     */
    private ObjectRestResponse beforeRefundAudit(BizRefundAudit refundAudit,boolean autoRefund) {
        ObjectRestResponse response = ObjectRestResponse.ok();
        if (null == refundAudit) {
            response.setStatus(504);
            response.setMessage("审核id错误！");
            return response;
        }

        String tenantType = baseAppServerUserMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(StringUtils.isEmpty(tenantType) ||(!"3".equals(tenantType))){
            if (!autoRefund && !refundAudit.getCompanyId().equals(BaseContextHandler.getTenantID())) {
                response.setStatus(505);
                response.setMessage("该用户没有权限！");
                return response;
            }
        }

        if ((!"0".equals(refundAudit.getAuditStatus())
            && !"3".equals(refundAudit.getAuditStatus()))
                || "0".equals(refundAudit.getStatus())) {
            response.setStatus(506);
            response.setMessage("该条信息已经被审核过了！");
            return response;
        }
        return response;
    }

    //处理驳回的逻辑
    private ObjectRestResponse doRejectRefund(BizRefundAudit refundAudit){
        refundAudit.setAuditStatus(AceDictionary.REFUND_AUDIT_STATUS_CAN);
        refundAudit.setModifyBy(BaseContextHandler.getUserID());
        refundAudit.setModifyTime(new Date());
        bizRefundAuditBiz.updateSelectiveById(refundAudit);

        orderRefundHandler.refundReject(refundAudit.getSubId(),refundAudit.getBusType(),BaseContextHandler.getUserID());

        SmsNoticeVo auditSmsNotice = getAuditRefundAuditSmsNotice(false, refundAudit);
        sysMsgNoticeBiz.saveSmsNotice(auditSmsNotice);
        return ObjectRestResponse.ok();
    }
    private ObjectRestResponse doPassRefund(BizRefundAudit refundAudit){
        ObjectRestResponse response = new ObjectRestResponse();
        //执行退款
        Boolean refundStatus = doRefundMoney(refundAudit);

        String auditStatus = refundStatus ? AceDictionary.REFUND_AUDIT_STATUS_COM:AceDictionary.REFUND_AUDIT_STATUS_FAIL;
        refundAudit.setAuditStatus(auditStatus);
        refundAudit.setModifyBy(BaseContextHandler.getUserID());
        refundAudit.setModifyTime(new Date());
        refundAudit.setRefundSuccessTime(new Date());
        bizRefundAuditBiz.updateSelectiveById(refundAudit);

        if (!refundStatus) {
            response.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            response.setMessage("退款失败");
            return response;
        }

        orderRefundHandler.refundSuccess(refundAudit.getSubId(),refundAudit.getBusType(),BaseContextHandler.getUserID());

        SmsNoticeVo auditSmsNotice = getAuditRefundAuditSmsNotice(true, refundAudit);
        sysMsgNoticeBiz.saveSmsNotice(auditSmsNotice);
        return ObjectRestResponse.ok();
    }


    private SmsNoticeVo getAuditActivityRefundSmsNotice(boolean isPass, BizRefundAudit refundAudit, BizActivityApply activityApply) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", activityApply.getActivityId());
        String[] smsNotice;
        if (isPass) {
            jsonObject.put("status", "8");
            smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.ACTIVITY_REFUND_P);
        } else {
            jsonObject.put("status", "9");
            smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.ACTIVITY_REFUND_F);
        }
        return buildSmsNotice(refundAudit.getApplyId(), jsonObject.toString(), smsNotice);
    }

    private SmsNoticeVo getAuditRefundAuditSmsNotice(boolean isPass, BizRefundAudit refundAudit) {
        //商品订单退款审核
        String[] smsNotice;
        if (isPass) {
            smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.COMMODITY_REFUND_P);
        } else {
            smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.COMMODITY_REFUND_F);
        }
        SmsNoticeVo smsNoticeVo = buildSmsNotice(refundAudit.getApplyId(), refundAudit.getSubId(), smsNotice);
        return smsNoticeVo;
    }

    private boolean doRefundMoney(BizRefundAudit refundAudit) {
        try {
            ObjectRestResponse restResponse = refund(refundAudit.getActualId(),refundAudit.getApplyPrice(),refundAudit.getId(), "正常退款");
            return restResponse != null && 200 == restResponse.getStatus();

        }catch (Exception e){
            log.error("退款失败：message:{},auditId:{},subId:{},busType:{}"
                    ,e.getMessage(),refundAudit.getId(),refundAudit.getSubId(),refundAudit.getBusType());
            return  false;
        }
    }

    public ObjectRestResponse restartRefund(String auditId) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizRefundAudit refundAudit = this.mapper.selectByPrimaryKey(auditId);
        if (null == refundAudit) {
            response.setStatus(504);
            response.setMessage("审核id错误！");
            return response;
        }
//        if (!refundAudit.getCompanyId().equals(BaseContextHandler.getTenantID())) {
//            response.setStatus(505);
//            response.setMessage("只有该商家可以重新发起退款！");
//            return response;
//        }
        if (!"3".equals(refundAudit.getAuditStatus()) || "0".equals(refundAudit.getStatus())) {
            response.setStatus(506);
            response.setMessage("该条订单不能发起重新退款！");
            return response;
        }
        RefundAuditVo refundAuditVo = new RefundAuditVo();
        refundAuditVo.setAuditId(auditId);
        refundAuditVo.setIsPass("1");
        return refundAudit(refundAuditVo);
    }

    public List<RefundOrderAuditInfoVo> findRefundAuditList(QueryRefundAuditListParam param) {
        param.check();
        return refundAuditMapper.selectRefundAuditList(param);
    }
    public int countRefundAuditList(QueryRefundAuditListParam param) {
        return refundAuditMapper.countRefundAuditList(param);
    }


    /**
     * 强制审核通过
     *
     * @param orderId : 入参
     * @return com.github.wxiaoqi.security.common.msg.ObjectRestResponse
     * @Author guohao
     * @Date 2020/4/11 20:06
     */
    public ObjectRestResponse forceRefundAudit(String orderId) {

        ObjectRestResponse response = new ObjectRestResponse();
        BizRefundAudit refundAudit = new BizRefundAudit();
        refundAudit.setSubId(orderId);
        refundAudit.setStatus(AceDictionary.DATA_STATUS_VALID);
        refundAudit = this.mapper.selectOne(refundAudit);
        if(ObjectUtils.isEmpty(refundAudit)){
            response.setStatus(506);
            response.setMessage("该条订单未找到退款申请记录！");
            return response;
        }

        String tenantType = baseAppServerUserMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(StringUtils.isEmpty(tenantType) ||(!"3".equals(tenantType))){
            response.setStatus(505);
            response.setMessage("该用户没有权限！");
            return response;
        }

        if(!AceDictionary.REFUND_AUDIT_STATUS_CHECKING.equals(refundAudit.getAuditStatus())
            && !AceDictionary.REFUND_AUDIT_STATUS_FAIL.equals(refundAudit.getAuditStatus())){
            response.setStatus(507);
            response.setMessage("该条订单的退款申请记录已审核！");
            return response;
        }

        RefundAuditVo refundAuditVo = new RefundAuditVo();
        refundAuditVo.setAuditId(refundAudit.getId());
        refundAuditVo.setIsPass("1");

        if(AceDictionary.BUS_TYPE_OLD.equals(refundAudit.getBusType())){
            return bizRefundAuditBiz.refundAudit(refundAuditVo);
        }

        boolean isPass = "1".equals(refundAuditVo.getIsPass());

        if(!isPass){
            return  doRejectRefund(refundAudit);
        }else {
            return doPassRefund(refundAudit);
        }
    }
}