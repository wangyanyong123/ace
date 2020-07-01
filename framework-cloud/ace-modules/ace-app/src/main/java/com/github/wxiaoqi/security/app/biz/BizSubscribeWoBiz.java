package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.buffer.OperateConstants;
import com.github.wxiaoqi.security.api.vo.order.in.*;
import com.github.wxiaoqi.security.api.vo.order.out.SubProductInfo;
import com.github.wxiaoqi.security.api.vo.user.ServerUserInfo;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.config.CrmConfig;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.SmsUtilsFegin;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.rpc.WorkOrderSyncBiz;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.crm.in.SyncWorkOrderStateIn;
import com.github.wxiaoqi.security.app.vo.in.DoOperateBean;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.app.vo.product.in.ProductVo;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.constant.MsgNoticeConstant;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.RedisKeyUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 订单工单表
 *
 * @author huangxl
 * @Date 2018-12-19 17:49:19
 */
@Service("BizSubscribeWoBiz")
@Slf4j
public class BizSubscribeWoBiz extends BusinessBiz<BizSubscribeWoMapper,BizSubscribeWo> {

    @Autowired
    private BizWoMapper bizWoMapper;
    @Autowired
    private BizSubscribeMapper bizSubscribeMapper;
    @Autowired
    private BizSubscribeWoMapper bizSubscribeWoMapper;
    @Autowired
    private BizTransactionLogBiz bizTransactionLogBiz;
    @Autowired
    private BizFlowProcessOperateMapper bizFlowProcessOperateMapper;
    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;
    @Autowired
    private BizSubProductMapper bizSubProductMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BizAccountBookMapper bizAccountBookMapper;
    @Autowired
    private ToolFegin toolFegin;
    @Autowired
    private BaseAppServerUserMapper appServerUserMapper;
    @Autowired
    private SmsUtilsFegin smsUtilsFegin;
    @Autowired
    private CrmServiceBiz crmServiceBiz;
    @Autowired
    private SysMsgNoticeBiz sysMsgNoticeBiz;
    @Autowired
    private BizPropertyPayLogMapper bizPropertyPayLogMapper;
    @Autowired
    private BizUserHouseBiz bizUserHouseBiz;
    @Autowired
    private BizCrmHouseMapper bizCrmHouseMapper;
    @Autowired
	private BizRefundAuditBiz refundAuditBiz;
    @Autowired
    private BizActivityBiz bizActivityBiz;
    @Autowired
    private BizDecoreteApplyBiz bizDecoreteApplyBiz;
    @Autowired
    private BizCouponUseMapper bizCouponUseMapper;
    @Autowired
    private CrmConfig crmConfig;
    @Autowired
    private WorkOrderSyncBiz workOrderSyncBiz;
    @Autowired
    private BizProductOrderMapper bizProductOrderMapper;
    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;


    public ObjectRestResponse doOperate(DoOperateBean doOperateBean) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        String id = doOperateBean.getId();
        String operateId = doOperateBean.getOperateId();
        TransactionLogBean transactionLogBean = doOperateBean.getTransactionLogBean();
        BizFlowProcessOperate operate = ConfigureBuffer.getInstance().getOperateById(operateId);

        //根据工单id查询工单类型
        //String wotype = bizWoMapper.selectWoTypeById(doOperateBean.getId());

        if(operate==null){
            restResponse.setStatus(101);
            restResponse.setMessage("不存在该订单工单操作ID!");
            log.error("不存在该订单工单操作ID:{}",operateId);
            return restResponse;
        }

        //1.校验当前工单订单状态
        BizSubscribeWo bizSubscribeWo = bizSubscribeWoMapper.selectByPrimaryKey(id);
        if(bizSubscribeWo==null){
            restResponse.setStatus(101);
            restResponse.setMessage("该订单工单数据不存在!");
            log.error("该订单工单数据不存在,ID:{}",id);
            return restResponse;
        }

        String  nowWoStatus = operate.getNowWoStatus();
        if(StringUtils.isNotEmpty(nowWoStatus) && !"-1".equals(nowWoStatus)) {
            //当前操作时工单状态,可以有多个
            String nowStatus = operate.getNowWoStatus() + ",";
            //当前工单状态
            String wStatus = bizSubscribeWo.getWoStatus();
            if (StringUtils.isEmpty(nowStatus) || StringUtils.isEmpty(wStatus) || nowStatus.indexOf(wStatus + ",") == -1) {
                if(!"09".equals(nowWoStatus) && !"10".equals(nowWoStatus) && !"11".equals(nowWoStatus)){
                    restResponse.setStatus(101);
                    restResponse.setMessage("工单已在处理中!");
                    log.error("当前操作已失效，请刷新页面!");
                    return restResponse;
                }
            }
        }

        String  nowSubStatus = operate.getNowSubStatus();
        if(StringUtils.isNotEmpty(nowSubStatus) && !"-1".equals(nowSubStatus)) {
            //当前操作时订单状态,可以有多个
            String nowStatus = nowSubStatus + ",";
            //当前订单状态
            String subStatus = bizSubscribeWo.getSubscribeStatus();
            if (StringUtils.isEmpty(nowStatus) || StringUtils.isEmpty(subStatus) || nowStatus.indexOf(subStatus + ",") == -1) {
                if(!"09".equals(nowWoStatus) && !"10".equals(nowWoStatus) && !"11".equals(nowWoStatus)){
                    restResponse.setStatus(101);
                    restResponse.setMessage("工单已在处理中!");
                    log.error("当前操作已失效，请刷新页面!");
                    return restResponse;
                }
            }
        }

        BizSubscribeWo bizSubscribeWoTemp = new BizSubscribeWo();
        bizSubscribeWoTemp.setId(bizSubscribeWo.getId());
        if(StringUtils.isNotEmpty(operate.getNextWoStatus()) && !"-1".equals(operate.getNextWoStatus())) {
            bizSubscribeWoTemp.setWoStatus(operate.getNextWoStatus());
        }
        if(StringUtils.isNotEmpty(operate.getNextSubStatus()) && !"-1".equals(operate.getNextSubStatus())) {
            bizSubscribeWoTemp.setSubscribeStatus(operate.getNextSubStatus());
        }
        bizSubscribeWoTemp.setProcessId(operate.getSuccNextStep());
        bizSubscribeWoTemp.setModifyBy(BaseContextHandler.getUserID());
        bizSubscribeWoTemp.setModifyTime(new Date());
        //工单抢单和指派
        if(OperateConstants.OperateType.WOGRAB.toString().equals(operate.getOperateType())){
            if(StringUtils.isNotEmpty(doOperateBean.getUserId())){
                //指派人员
                bizSubscribeWoTemp.setAcceptBy(doOperateBean.getUserId());
            }
            if(StringUtils.isNotEmpty(doOperateBean.getHandleBy())){
                //处理人员
                bizSubscribeWoTemp.setHandleBy(doOperateBean.getHandleBy());
            }
            bizSubscribeWoTemp.setStartProcessTime(new Date());
            bizSubscribeWoTemp.setReceiveWoTime(new Date());
            if(transactionLogBean==null){
                transactionLogBean = new TransactionLogBean();
            }
            UserVo userVo = baseAppClientUserBiz.getUserNameById(doOperateBean.getHandleBy(),"");
            if(userVo!=null && StringUtils.isNotEmpty(userVo.getName())){
                if(StringUtils.isEmpty(transactionLogBean.getDesc())){
                    transactionLogBean.setDesc("已接单，处理中\r\n处理人："+doOperateBean.getHandleBy());
                }
                transactionLogBean.setConName(userVo.getName());
                transactionLogBean.setConTel(userVo.getMobilePhone());
            }else{
                if(StringUtils.isEmpty(transactionLogBean.getDesc())){
                    transactionLogBean.setDesc("已接单，处理中\r\n处理人："+doOperateBean.getHandleBy());
                }
            }
        }
        //工单完成
        if(OperateConstants.OperateType.WOFINISH.toString().equals(operate.getOperateType())){
            bizSubscribeWoTemp.setFinishWoTime(new Date());
            //评论状态(0-不可评论、1-待评论、2-已评论、3-关闭)'
            bizSubscribeWoTemp.setCommentStatus("1");


//            String userId = bizSubscribeWoTemp.getCreateBy();
//            UserVo userVo = baseAppClientUserBiz.getUserNameById(userId,"c");
//            String mobilePhone = userVo.getMobilePhone();
//            String msgTheme = "FINISH_WO";
//            String msgParam = "";
//            restResponse = smsUtilsFegin.sendMsg(mobilePhone, null, null, null, null, userId, msgTheme, msgParam);
//            log.info("发送消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());

        }
        if(OperateConstants.OperateType.DEAL_EXPRESS.toString().equals(operate.getOperateType())){
            bizSubscribeWoTemp.setProcessId(operate.getProcessId());
        }
        //填写快递单
        if(OperateConstants.OperateType.OUT_EXPRESS.toString().equals(operate.getOperateType())){
            transactionLogBean = new TransactionLogBean();
            if(StringUtils.isEmpty(doOperateBean.getExpressCompany()) || StringUtils.isEmpty(doOperateBean.getExpressNum()) ||
                    doOperateBean.getExpressCompany() == null || doOperateBean.getExpressNum() == null){
                transactionLogBean.setDesc(operate.getTranslogStepDesc());
            }else{
                transactionLogBean.setDesc(operate.getTranslogStepDesc()+"\r\n"+"快递公司："+doOperateBean.getExpressCompany()
                        +"\r\n"+"快递单号："+doOperateBean.getExpressNum());
            }
        }
        //评价
        if(OperateConstants.OperateType.EVALUATE.toString().equals(operate.getOperateType())){
            if("2".equals(bizSubscribeWo.getCommentStatus())){
                restResponse.setStatus(101);
                restResponse.setMessage("已评价，谢谢参与!");
                log.error("已评价，谢谢参与!");
                return restResponse;
            }
            //评论状态(0-不可评论、1-待评论、2-已评论、3-关闭)'
            bizSubscribeWoTemp.setCommentStatus("2");
            if(transactionLogBean!=null && transactionLogBean.getIsArriveOntime()!=null){
                bizSubscribeWoTemp.setIsArriveOntime(transactionLogBean.getIsArriveOntime());
            }
        }
        //工单到岗
        if (OperateConstants.OperateType.ARRIVE_ON_TIME.toString().equals(operate.getOperateType())) {
            bizSubscribeWoTemp.setArriveWoTime(new Date());
        }

        //支付回调
        if(OperateConstants.OperateType.PAYCALLBACK.toString().equals(operate.getOperateType())){
            if( BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId())){
                bizSubscribeWoTemp.setGroupStatus("1");
            }else if(BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())){
                //优先商城需要生成工单
                createWo(bizSubscribeWo);
                workOrderSyncBiz.createProductWorkOrderThread(bizSubscribeWo.getId());
            } else if(BusinessConstant.getPropertyBusId().equals(bizSubscribeWo.getBusId())){
                //物业缴费通知CRM
                propertyNoticeCrm(bizSubscribeWo.getId());
            }else if(BusinessConstant.getActivityBusId().equals(bizSubscribeWo.getBusId())){
                //活动缴费
                bizActivityBiz.updateActivityApplyStatus(id);
            }else if(BusinessConstant.getDecoreteBusId().equals(bizSubscribeWo.getBusId())){
                //装修监理缴费
                bizDecoreteApplyBiz.updateDecoreteApplyStatus(id);
            }else if(BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                //预约服务
                createWo(bizSubscribeWo);
                workOrderSyncBiz.createReservationWorkOrderThread(bizSubscribeWo.getId());
            }
        }

        //疯抢商品取消,销量减1
        if(OperateConstants.OperateType.CANCEL.toString().equals(operate.getOperateType())){
            //查询产品id
            if( BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId()) ||
                    BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())){
                List<ProductVo> productVos = bizProductMapper.selectProductVoByWoId(doOperateBean.getId());
                if(productVos.size()>0){
                    for (ProductVo productVo:productVos) {
                        bizProductMapper.updateSalesNum(productVo.getProductId(),productVo.getSubNum());
                    }
                }
            }
            if(BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())){
                List<String> productId = bizProductMapper.selectProductIdByWoId(doOperateBean.getId());
                bizProductMapper.updateSalesNumById(productId);
            }
            if(BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                List<ProductVo> productVos = bizProductMapper.selectProductVoByWoId(doOperateBean.getId());
                if(productVos.size()>0){
                    for (ProductVo productVo:productVos) {
                        bizProductMapper.updateResSalesNum(productVo.getProductId(),productVo.getSubNum());
                    }
                }
            }
            if(BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId()) ||
                    BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId()) ||
                    BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())){
                //取消订单，回撤优惠券状态
                BizCouponUse bizCouponUse = new BizCouponUse();
                BizSubscribe subscribe = bizSubscribeMapper.selectByPrimaryKey(bizSubscribeWo.getId());
                bizCouponUse.setId(subscribe.getCouponId());
                bizCouponUse.setUseStatus("1");
                bizCouponUse.setOrderId("");
                bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse);
            }
        }
        if(OperateConstants.OperateType.REFUND.toString().equals(operate.getOperateType())||
                OperateConstants.OperateType.SIGN_REFUNDBOND.toString().equals(operate.getOperateType())){
			if(BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
					|| BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
				RefundInfoVo refundInfoVo = new RefundInfoVo();
				refundInfoVo.setSubId(bizSubscribeWo.getId());
				refundInfoVo.setUserType("1");
				refundAuditBiz.applyRefund(refundInfoVo);
			}
        }
        boolean falg = false;
		if(OperateConstants.OperateType.WOCLOSE.toString().equals(operate.getOperateType())){
			if(BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
					|| BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    ||BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
				RefundInfoVo refundInfoVo = new RefundInfoVo();
				refundInfoVo.setSubId(bizSubscribeWo.getId());
				refundInfoVo.setUserType("2");
				ObjectRestResponse response = refundAuditBiz.refund(bizSubscribeWo.getId(),"正常退款");
				if(response != null && 200 == response.getStatus()){
					refundInfoVo.setAuditStatus("2");
					refundAuditBiz.applyRefund(refundInfoVo);
					falg = true;
				}else {
					restResponse.setStatus(511);
					restResponse.setMessage("退款失败！");
					refundInfoVo.setAuditStatus("3");
					refundAuditBiz.applyRefund(refundInfoVo);
					return restResponse;
				}
			}
		}
        //工单暂停
        if (OperateConstants.OperateType.STOP.toString().equals(operate.getOperateType())) {
            bizSubscribeWoTemp.setStopWoTime(new Date());
        }
        //工单恢复
        if (OperateConstants.OperateType.RESTART.toString().equals(operate.getOperateType())) {
            bizSubscribeWoTemp.setRestartWoTime(new Date());
        }
        //订单确认
        if (OperateConstants.OperateType.REVIEW.toString().equals(operate.getOperateType())) {
            bizSubscribeWoTemp.setReviewWoTime(new Date());
        }

        //团购成团
        if(OperateConstants.OperateType.FINISHGROUP.toString().equals(operate.getOperateType())){
            //团购成团需要生成工单
            createWo(bizSubscribeWo);
            bizSubscribeWoTemp.setGroupStatus("2");
            workOrderSyncBiz.createProductWorkOrderThread(bizSubscribeWo.getId());
        }
        bizSubscribeWoMapper.updateByPrimaryKeySelective(bizSubscribeWoTemp);

        //3.记录工单操作日志
        if(OperateConstants.RecordTranslog.YES.toString().equals(operate.getIsRecordTranslog())){
            if(transactionLogBean==null){
                transactionLogBean = new TransactionLogBean();
                transactionLogBean.setCurrStep(operate.getTranslogStepName());
                transactionLogBean.setDesc(operate.getTranslogStepDesc());
            }else{
                transactionLogBean.setCurrStep(operate.getTranslogStepName());
            }
            bizTransactionLogBiz.insertTransactionLog(id,transactionLogBean);
        }
        //4.操作成功后返回提示
        restResponse.setData(operate.getSuccTips());

        //5.投诉报修工单状态变化发送app消息提醒
        if( (BusinessConstant.getCmplainBusId().equals(bizSubscribeWo.getBusId()) || BusinessConstant.getRepairBusId().equals(bizSubscribeWo.getBusId()))
            && (OperateConstants.OperateType.WOFINISH.toString().equals(operate.getOperateType()) || OperateConstants.OperateType.WOGRAB.toString().equals(operate.getOperateType())
                || OperateConstants.OperateType.WOCLOSE.toString().equals(operate.getOperateType()))){
            this.sendMsgForWo(bizSubscribeWo ,operate );
        }
        //6.工单接单、关闭、用户取消、完成，投诉报修的工单同步给CRM系统
        //优化点：加开关标识、加同步的项目列表
        if(OperateConstants.OperateType.WOFINISH.toString().equals(operate.getOperateType())
                || OperateConstants.OperateType.WOCLOSE.toString().equals(operate.getOperateType())
                || OperateConstants.OperateType.WOGRAB.toString().equals(operate.getOperateType())
                || OperateConstants.OperateType.CANCEL.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getCmplainBusId().equals(bizSubscribeWo.getBusId())|| BusinessConstant.getRepairBusId().equals(bizSubscribeWo.getBusId()))){
                final String woIdTemp = bizSubscribeWo.getId();
                //syncWoToCRMThread(woIdTemp);
            }
        }
        //6.工单评价，投诉报修的工单同步给调度引擎系统
        if(OperateConstants.OperateType.EVALUATE.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getCmplainBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getRepairBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setImgIds(transactionLogBean.getImgIds());
                doOperateVo.setAppraisalVal(transactionLogBean.getAppraisalVal());
                doOperateVo.setDescription(transactionLogBean.getDesc());
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setIsArriveOntime(transactionLogBean.getIsArriveOntime());
                workOrderSyncBiz.syncWoEvaluateThread(doOperateVo);
            }
        }
        //6.工单取消，投诉报修的工单同步给调度引擎系统
        if(OperateConstants.OperateType.CANCEL.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getCmplainBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getRepairBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setAppraisalVal(-1);
                workOrderSyncBiz.syncWoCancelThread(doOperateVo);
            }
        }

        //7.订单申请售后，同步给调度引擎系统审核
        if(OperateConstants.OperateType.APPLY_AFTER.toString().equals(operate.getOperateType())||
                OperateConstants.OperateType.UN_APPLY_AFTER.toString().equals(operate.getOperateType()) ){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
             && (BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setDescription(transactionLogBean.getDesc());
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setAppraisalVal(-1);
                workOrderSyncBiz.syncOrderAuditThread(doOperateVo);
            }
        }


        //8.订单填写快递单号，同步给调度引擎系统签收
        if(OperateConstants.OperateType.OUT_EXPRESS.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setDescription(transactionLogBean.getDesc());
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setAppraisalVal(-1);
                workOrderSyncBiz.syncOrderWoSignThread(doOperateVo);
            }
        }
        //9.订单支付后取消，同步给调度引擎系统
        if(OperateConstants.OperateType.REFUND.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setDescription(transactionLogBean.getDesc());
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setAppraisalVal(-1);
                workOrderSyncBiz.syncOrderWoCloseThread(doOperateVo);
            }
        }
        //10.退款成功，同步给调度引擎系统
        if(OperateConstants.OperateType.REFUNDCALLBACK.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setDescription(transactionLogBean.getDesc());
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setAppraisalVal(-1);
                workOrderSyncBiz.syncOrderWoRefundSucceeedThread(doOperateVo);
            }
        }

        //11.退款拒绝，同步给调度引擎系统
        if(OperateConstants.OperateType.REFUSEREFUND.toString().equals(operate.getOperateType())){
            if(bizSubscribeWo!=null && bizSubscribeWo.getBusId()!=null
                    && (BusinessConstant.getTravelBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getShoppingBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getSeckillBusId().equals(bizSubscribeWo.getBusId())
                    || BusinessConstant.getGroupBuyingBusId().equals(bizSubscribeWo.getBusId()))
                    || BusinessConstant.getReservationBusId().equals(bizSubscribeWo.getBusId())){
                final DoOperateVo doOperateVo = new DoOperateVo();
                doOperateVo.setDescription(transactionLogBean.getDesc());
                doOperateVo.setId(bizSubscribeWo.getVrobotWoId());
                doOperateVo.setAppraisalVal(-1);
                workOrderSyncBiz.syncOrderWoRefuseThread(doOperateVo);
            }
        }



        if(falg){
			Map<String, Object> map = bizFlowProcessOperateMapper.getOperateIdByIdAndOperateType(bizSubscribeWo.getId(),OperateConstants.OperateType.REFUNDCALLBACK.toString());
			if(map!=null && map.size()>0 ){
				//操作Id
				String opId = (String) map.get("operateId");
				DoOperateBean param = new DoOperateBean();
				param.setId(bizSubscribeWo.getId());
				param.setOperateId(opId);
				//调用支付回调成功操作
				try {
					Thread.currentThread().sleep(1000);
					ObjectRestResponse restResp = doOperate(param);
					if(restResp==null || restResp.getStatus()!=200) {
						log.info(">>>>>>>>>>>调用subscribeWoBiz.doOperate()失败，返回的结果:"+JSON.toJSONString(restResponse));
					}
                    SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
                    smsNoticeVo.setReceiverId(bizSubscribeWo.getUserId());
                    smsNoticeVo.setObjectId(bizSubscribeWo.getId());
                    smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.COMMODITY_REFUND_C));
                    log.info(">>>>>>>>>>>>>>>>>>>>订单关闭消息通知:" + JSON.toJSONString(smsNoticeVo));
                    sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
				}catch (Exception e){
					log.error(">>>>>>>>>>>调用subscribeWoBiz.doOperate()出错");
					e.printStackTrace();
				}
			}
		}
        return restResponse;
    }

    /**
     * 开线程同步CRM工单状态
     * @param woIdTemp
     */
    public void syncWoToCRMThread(final String woIdTemp){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求失败连续重试三次
                syncWoToCRM(woIdTemp);
            }
        }).start();
    }

    public ObjectRestResponse syncWoToCRM(String woIdTemp) {
        ObjectRestResponse msg = new ObjectRestResponse();
        int count = 1;

        if(StringUtils.isEmpty(woIdTemp)){
            return msg;
        }


        for (int i=0;i<count;i++){
            SyncWorkOrderStateIn syncWorkOrderStateIn = bizSubscribeWoMapper.getSyncWorkOrderState(woIdTemp);
            ObjectRestResponse rusultResponse = crmServiceBiz.syncWorkOrderState(syncWorkOrderStateIn);
            if(200==rusultResponse.getStatus()){
                //1.更新同步订单状态表
                BizWo bizWo = new BizWo();
                bizWo.setId(woIdTemp);
                bizWo.setCrmSyncFlag("1");
                bizWoMapper.updateByPrimaryKeySelective(bizWo);
                break;
            }else if(201==rusultResponse.getStatus()){
                //不处理
                break;
            }else{
                try{
                    //失败后请求间隔1秒
                    Thread.sleep(1000L);
                }catch (Exception e){
                    log.error("投诉报修的工单同步给CRM系统异常");
                }
                //1.新建请求日志表，2.更新同步订单状态表
                BizWo bizWo = new BizWo();
                bizWo.setId(woIdTemp);
                bizWo.setCrmSyncFlag("2");
                bizWoMapper.updateByPrimaryKeySelective(bizWo);
                msg.setMessage(rusultResponse.getMessage());
                msg.setStatus(101);
            }
        }
        return msg;
    }

    /**
     * 物业缴费通知crm系统
     * @param subId
     */
    private void propertyNoticeCrm(String subId) {
        BizSubscribe subscribe = bizSubscribeMapper.selectByPrimaryKey(subId);
        BaseAppClientUser user = baseAppClientUserBiz.getUserById(subscribe.getUserId());
        ObjectRestResponse response = crmServiceBiz.syncPropertyStatus(subscribe);
        if (response.getStatus() != 200) {
            log.info("请求CRM失败");
            //每隔五秒重试三次
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int count = 3;
                    int total = 0;
                    for (int i = 0; i < count; i++) {
                        total++;
                        log.info("5秒后重新请求CRM:" + "请求第" + i + 1 + "次");
                        ObjectRestResponse response = crmServiceBiz.syncPropertyStatus(subscribe);
                        if (200 == response.getStatus()) {
                            break;
                        }else {
                            try {
                                //间隔5秒
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                log.info("缴费通知请求CRM异常");
                            }
                        }
                    }
                    if (total == 3) {
                        //三次失败，发送短信
                        Map<String,String> map = new HashMap();
                        map.put("userName", user.getNickname());
                        ObjectRestResponse response = smsUtilsFegin.sendMsg(user.getMobilePhone(), null, null, "2", "",
                                user.getId(), MsgThemeConstants.PROPERTY_BILL, JSON.toJSONString(map));
                        log.info("发送消息结果："+response.getStatus()+"-"+response.getMessage());
                    }

                }
            }).start();
        }
    }



    private void createWo(BizSubscribeWo bizSubscribeWo){
        //其他支付业务
        BizSubscribe bizSubscribe = bizSubscribeMapper.selectByPrimaryKey(bizSubscribeWo.getId());
        UserVo userVo = baseAppClientUserBiz.getUserNameById(bizSubscribe.getUserId(),"c");
        BizWo bizWo = new BizWo();
        BeanUtils.copyProperties(bizSubscribeWo,bizWo);
        bizWo.setStatus("1");
        bizWo.setWoCode(bizSubscribeWo.getCode());
        bizWo.setTitle(bizSubscribe.getTitle());
        bizWo.setDescription(bizSubscribe.getDescription());
        bizWo.setAddr(bizSubscribe.getDeliveryAddr());
        bizWo.setContactUserId(bizSubscribe.getUserId());
        bizWo.setComeFrom("1");
        bizWo.setProjectId(bizSubscribeWo.getProjectId());
        bizWo.setContactUserId(bizSubscribeWo.getUserId());
        bizWo.setContactName(userVo.getName());
        bizWo.setContactTel(userVo.getMobilePhone());
        bizWo.setTimeStamp(DateTimeUtil.getLocalTime());
        bizWo.setCreateTime(DateTimeUtil.getLocalTime());
        bizWoMapper.insertSelective(bizWo);
    }

    public ObjectRestResponse doOperateByType(DoOperateByTypeVo doOperateByTypeVo){
        ObjectRestResponse objectRestResponse= new ObjectRestResponse();
        String operateType = doOperateByTypeVo.getOperateType()==null?"":(String)doOperateByTypeVo.getOperateType();
        String id = doOperateByTypeVo.getId()==null?"":(String)doOperateByTypeVo.getId();

        if(StringUtils.isNotEmpty(operateType)){
            Map<String, Object> map = bizFlowProcessOperateMapper.getOperateIdByIdAndOperateType(id,operateType);
            if(map!=null && map.size()>0){
                //操作Id
                String operateId = (String) map.get("operateId");
                Map<String, Object> paramMapTemp = new HashMap<String, Object>();
                paramMapTemp.put("operateId", operateId);
                paramMapTemp.put("id", id);

                TransactionLogBean transactionLogBean = null;
                if(StringUtils.isNotEmpty(doOperateByTypeVo.getDescription())){
                    transactionLogBean = new TransactionLogBean();
                    transactionLogBean.setDesc(doOperateByTypeVo.getDescription());
                    transactionLogBean.setImgIds(doOperateByTypeVo.getImgId());
                    transactionLogBean.setConName(doOperateByTypeVo.getHandleBy());
                    transactionLogBean.setConTel(doOperateByTypeVo.getDealTel());
                }
                DoOperateBean doOperateBean = new DoOperateBean();
                doOperateBean.setId(id);
                doOperateBean.setOperateId(operateId);
                doOperateBean.setTransactionLogBean(transactionLogBean);
                doOperateBean.setHandleBy(doOperateByTypeVo.getHandleBy());
                doOperateBean.setUserId(doOperateByTypeVo.getUserId());
                String result = doOperate(doOperateBean).toString();
                log.info("操作("+operateId+")处理结果:"+result);
                objectRestResponse.setData(result);
            }else{
                objectRestResponse.setStatus(103);
                objectRestResponse.setMessage("系统没有配置该业务的这种操作类型:"+operateType);
            }
        }else{
            objectRestResponse.setStatus(102);
            objectRestResponse.setMessage("参数操作ID为空");
        }
        return objectRestResponse;
    }

    public ObjectRestResponse doPayOrderFinish(PayOrderFinishIn payOrderFinishIn){
        ObjectRestResponse objectRestResponse= new ObjectRestResponse();
        String actualId = payOrderFinishIn.getActualId();
        String payId = payOrderFinishIn.getPayId();
        List<Map<String,Object>> list = bizSubscribeWoMapper.getPayNotifyOperateId(actualId,payId);
        if(list!=null && list.size()>0){
            //更新订单账本数据
            BizAccountBook bizAccountBook = new BizAccountBook();
            bizAccountBook.setActualId(actualId);
            bizAccountBook.setPayStatus("2");
            bizAccountBook.setPayId(payId);
            bizAccountBook.setPayType(payOrderFinishIn.getPayType());
            bizAccountBook.setSettleAccount(payOrderFinishIn.getMchId());
            bizAccountBookMapper.updatePayStatusById(bizAccountBook);

            //校验支付实际金额和订单实际需要支付的金额是否匹配
            String totalFee = payOrderFinishIn.getTotalFee();
            BigDecimal totalPrice = new BigDecimal(0);
            for(Map<String,Object> obj : list) {
                BigDecimal actualCost = obj.get("actualCost") == null ? new BigDecimal(0) : (BigDecimal) obj.get("actualCost");
                totalPrice = totalPrice.add(actualCost);
            }

            if("2".equals(payOrderFinishIn.getPayType())) {
                //微信，单位：分
                totalPrice = totalPrice.multiply(new BigDecimal(100));
            }
            BigDecimal totalPriceCallback = new BigDecimal(totalFee);
            if(totalPrice.compareTo(totalPriceCallback) != 0) {
                //根据实际支付ID更新账本字段
                String remark = "支付实际金额和订单实际需要支付的金额不匹配，ActualId="+payOrderFinishIn.getActualId()+",payId="+payOrderFinishIn.getPayId();
                log.error(remark);
                bizAccountBook = new BizAccountBook();
                bizAccountBook.setActualId(actualId);
                bizAccountBook.setPayId(payOrderFinishIn.getPayId());
                bizAccountBook.setPayType(payOrderFinishIn.getPayType());
                bizAccountBook.setPayFailRemark(remark);
                bizAccountBookMapper.updatePayStatusById(bizAccountBook);
                return objectRestResponse;
            }
            for(Map<String,Object> obj : list){
                String subId = obj.get("subId")==null ? "" : (String)obj.get("subId");
                String busId = obj.get("busId")==null ? "" : (String)obj.get("busId");
                String operateId = obj.get("operateId")==null ? "" : (String)obj.get("operateId");
                DoOperateBean doOperateBean = new DoOperateBean();
                doOperateBean.setId(subId);
                doOperateBean.setOperateId(operateId);
                //调用支付回调成功操作
                ObjectRestResponse restResponse = doOperate(doOperateBean);
                if(restResponse==null || restResponse.getStatus()!=200) {
                    continue;
                }
                // 更新产品销量
                List<SubProductInfo> subProductInfoList = bizSubProductMapper.getSubProductInfo(subId);
                if (subProductInfoList == null || subProductInfoList.size() == 0) {
                    continue;
                }

                //如果是团购订单，为了打印流水日志不在同一个时间点，进程休眠1秒钟
                if (BusinessConstant.getGroupBuyingBusId().equals(busId)) {
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e) {
                        log.error("团购订单处理异常"+e.getMessage());
                    }
                }
                for (SubProductInfo subProductInfo : subProductInfoList) {
                    int subNum = subProductInfo.getSubNum();
                    String productId = subProductInfo.getProductId();

                    //判断是否成团,团购订单只有一个产品
                    if (BusinessConstant.getGroupBuyingBusId().equals(busId)) {
                        groupBuyHandle(subId,subProductInfo);
                    }

//                    if(!BusinessConstant.getSeckillBusId().equals(busId)){
//                        //更新产品销量
//                        if (subNum > 0)
//                            //家政超市
//                            if(BusinessConstant.getReservationBusId().equals(busId)){
//                                int i = bizProductMapper.udpateReservationSalesById(productId, subNum);
//                                if (i != 1) {
//                                    log.error("update product sales error");
//                                }
//                            }else{
//                                int i = bizProductMapper.udpateProductSalesById(productId, subNum);
//                                if (i != 1) {
//                                    log.error("update product sales error");
//                                }
//                            }
//                    }
                }
            }
        }else{
            log.info("已处理过业务数据了，ActualId="+payOrderFinishIn.getActualId()+",payId="+payOrderFinishIn.getPayId());
        }
        return objectRestResponse;
    }

    /**
     * 团购情况处理
     * @param subId
     * @param subProductInfo
     */
    private void groupBuyHandle (String subId,SubProductInfo subProductInfo){
        int subNum = subProductInfo.getSubNum();
        String productId = subProductInfo.getProductId();
        BizProduct bizProduct = bizProductMapper.selectByPrimaryKey(productId);
        String isGroupFlag = bizProduct.getIsGroupFlag();
        //判断是否成团,团购订单只有一个产品
        if("1".equals(isGroupFlag)){
            //如果已经成团，则让当前订单成团
            DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
            doOperateByTypeVo.setId(subId);
            doOperateByTypeVo.setOperateType(OperateConstants.OperateType.FINISHGROUP.toString());
            this.doOperateByType(doOperateByTypeVo);
        }else{
            //成团份数
            int groupbuyNum = bizProduct.getGroupbuyNum();
            int nowSales = subNum + bizProduct.getSales();
            if(nowSales>=groupbuyNum){
                //查找未成团的订单并让其成团
                finishGroupProduct(productId);
            }
        }
    }

    public ObjectRestResponse finishGroupProduct(String productId) {
        BizProduct bizProduct = bizProductMapper.selectByPrimaryKey(productId);
        List<Map<String,Object>> waitGroupBuySubList = bizSubscribeWoMapper.getWaitGroupBuySubList(productId);
        if(waitGroupBuySubList!=null && waitGroupBuySubList.size()>0){
            for (Map<String,Object> groupSub : waitGroupBuySubList){
                String groupSubId = groupSub.get("subId")==null ? "" : (String)groupSub.get("subId");
                //让订单成团
                DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
                doOperateByTypeVo.setId(groupSubId);
                doOperateByTypeVo.setOperateType(OperateConstants.OperateType.FINISHGROUP.toString());
                this.doOperateByType(doOperateByTypeVo);
            }
        }
        // 小程序订单成团
        List<OrderIdResult> waitingCompleteList = bizProductOrderMapper.selectOrderIdListGroupWaitingComplete(bizProduct.getId());
        waitingCompleteList.parallelStream().forEach(this::finishGroupProductByOrderId);
        //更新成团标识为已成团
        bizProduct.setIsGroupFlag("1");
        bizProduct.setModifyTime(new Date());
        bizProductMapper.updateByPrimaryKeySelective(bizProduct);
        return ObjectRestResponse.ok();
    }

    public void finishGroupProductByOrderId(OrderIdResult orderIdResult){
        int groupFinish = bizProductOrderMapper.updateOrderStatusByGroupComplete(orderIdResult.getOrderId(), "groupFinish");
        if(groupFinish > 0){
            bizOrderOperationRecordBiz.addRecordByOrderIdResult(orderIdResult, OrderOperationType.GroupComplete,"");
        }
    }

    /**
     * 判断当前订单是否可以支付
     * @param id
     * @return
     */
    public ObjectRestResponse isCanPaySub(String id){
        ObjectRestResponse result = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            result.setStatus(101);
            result.setMessage("参数ID为空");
            return result;
        }
        List<Map<String,Object>> productList = bizProductMapper.selectProductInfoBySubId(id);
        if(productList!=null && productList.size()>0){
            for(Map<String,Object> product : productList){
                String busId = product.get("busId")==null ? "" : (String)product.get("busId");
                String busStatus = product.get("busStatus")==null ? "" : (String)product.get("busStatus");
                Date begTime = product.get("begTime")==null ? null : (Date)product.get("begTime");
                Date endTime = product.get("endTime")==null ? null : (Date)product.get("endTime");

                if(!"3".equals(busStatus)){
                    //非发布状态
                    result.setStatus(103);
                    result.setMessage("当前产品为非发布状态");
                    return result;
                }

                if(BusinessConstant.getGroupBuyingBusId().equals(busId)){
                    if(begTime==null || endTime==null){
                        result.setStatus(104);
                        result.setMessage("团购起始时间设置错误");
                        return result;
                    }
                    if (new Date().before(begTime)) {
                        result.setStatus(104);
                        result.setMessage("团购还未开始");
                        return result;
                    }
                    if (new Date().after(endTime)) {
                        result.setStatus(104);
                        result.setMessage("团购已结束");
                        return result;
                    }
                }
            }
        }else{
            result.setStatus(102);
            result.setMessage("该订单关联产品信息有误");
            return result;
        }
        return result;
    }

    public ObjectRestResponse getAccpetWoUserList(String woId) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<String> userIdList = new ArrayList<>();
        //获取工单信息
        BizSubscribeWo bizSubscribeWo = bizSubscribeWoMapper.selectByPrimaryKey(woId);
        //工单类型(技能匹配，指派)
        String dealType = bizSubscribeWo.getDealType();
        String companyId = bizSubscribeWo.getCompanyId();
        if("1".equals(dealType)){
            //指派：商业人员 所属公司
            List<String> businessUserList = bizWoMapper.getBusinessUserList(companyId);
            if(businessUserList!=null && businessUserList.size()>0){
                userIdList.addAll(businessUserList);
            }
        }else if("2".equals(dealType)){
            //技能匹配：客服人员 所属公司
            List<String> customerUserList = bizWoMapper.getCustomerUserList(companyId);
            if(customerUserList!=null && customerUserList.size()>0){
                userIdList.addAll(customerUserList);
            }

            //技能匹配：物业人员 所属公司 项目、楼栋、技能
            List<Map<String, Object>> woSkillInfoList = bizWoMapper.getWoSkillInfo(woId);
            if(woSkillInfoList!=null && woSkillInfoList.size()>0){
                Map<String, Object> woInfo = woSkillInfoList.get(0);
                String projectId = (String)woInfo.get("projectId");
                String buildId = (String)woInfo.get("buildId");
                String skillId = (String)woInfo.get("skillId");

                Map<String, String> map = new HashMap<>();
                map.put("companyId",companyId);
                map.put("projectId",projectId);
                map.put("buildId",buildId);
                if(woSkillInfoList.size()==1){
                    map.put("skillId",skillId);
                }
                List<Map<String, Object>> propertyUserList = bizWoMapper.getPropertyUserList(map);
                if(woSkillInfoList.size()>1){
                    //如果工单需要多个技能，需要再进行用户技能匹配
                    List<String> woSkilllist = new ArrayList<>();
                    for(Map<String,Object> woSkillInfo : woSkillInfoList){
                        skillId = (String)woSkillInfo.get("skillId");
                        woSkilllist.add(skillId);
                    }
                    Map<String,List<String>> userSkillListMap = new HashMap<>();
                    for(Map<String,Object> propertyUser : propertyUserList){
                        skillId = (String)propertyUser.get("skillId");
                        String userId = (String)propertyUser.get("userId");
                        List<String> skills = userSkillListMap.get(userId);
                        if(skills==null){
                            skills = new ArrayList<>();
                            userSkillListMap.put(userId,skills);
                        }
                        skills.add(skillId);
                    }
                    List<String> containUserList = StringUtils.geInitContainList(woSkilllist, userSkillListMap);
                    if(containUserList!=null && containUserList.size()>0){
                        userIdList.addAll(containUserList);
                    }
                }else{
                    for(Map<String,Object> propertyUser : propertyUserList){
                        String userId = (String)propertyUser.get("userId");
                        userIdList.add(userId);
                    }
                }
            }
            //用户ID去重
            userIdList = StringUtils.removeDuplicate(userIdList);
        }

        //查询服务用户信息
        List<ServerUserInfo> serverUserList = null;
        if(userIdList!=null && userIdList.size()>0){
            //去掉自己
            String userId = BaseContextHandler.getUserID();
            if(StringUtils.isNotEmpty(userId)){
                userIdList.remove(userId);
            }
            if(userIdList!=null && userIdList.size()>0) {
                serverUserList = appServerUserMapper.selectAppUserList(userIdList);
            }
        }
        if(serverUserList==null || serverUserList.size()==0){
            serverUserList = new ArrayList<>();
        }
        objectRestResponse.setData(serverUserList);
        return objectRestResponse;
    }

    /**
     * 投诉报修工单状态变化发送app消息提醒
     * @param bizSubscribeWo
     * @param operate
     */
    private void sendMsgForWo(BizSubscribeWo bizSubscribeWo ,BizFlowProcessOperate operate ){
        SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
        Map<String,String> paramMap = new HashMap<>();
        if( BusinessConstant.getCmplainBusId().equals(bizSubscribeWo.getBusId())){
            smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.WO_CMPLAIN));
        }else if(BusinessConstant.getRepairBusId().equals(bizSubscribeWo.getBusId())){
            smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.WO_REPAIR));
        }
        BizWo bizWo = bizWoMapper.selectByPrimaryKey(bizSubscribeWo.getId());
        //查询工单实际联系人
        if(bizWo!=null && StringUtils.isNotEmpty(bizWo.getContactUserId())){
            smsNoticeVo.setReceiverId(bizWo.getContactUserId());
            smsNoticeVo.setObjectId(bizSubscribeWo.getId());
            if(OperateConstants.OperateType.WOFINISH.toString().equals(operate.getOperateType())){
                //工单完成
                paramMap.put("status","已完成");
                smsNoticeVo.setParamMap(paramMap);
                sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
            }else if(OperateConstants.OperateType.WOGRAB.toString().equals(operate.getOperateType())){
                //工单抢单和指派
                paramMap.put("status","正在处理中");
                smsNoticeVo.setParamMap(paramMap);
                sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
            }else if(OperateConstants.OperateType.WOCLOSE.toString().equals(operate.getOperateType())){
                //工单抢单和指派
                paramMap.put("status","已关闭");
                smsNoticeVo.setParamMap(paramMap);
                sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
            }
            String userId = smsNoticeVo.getReceiverId();
            String msgParam = "";
            ObjectRestResponse sendMsgResponse = toolFegin.sendMsg(null, null, null, null, null, userId, MsgThemeConstants.MSG_LIST_NOTICE, msgParam);
            log.info("发送App消息结果："+sendMsgResponse.getStatus()+"-"+sendMsgResponse.getMessage());
        }
    }

}