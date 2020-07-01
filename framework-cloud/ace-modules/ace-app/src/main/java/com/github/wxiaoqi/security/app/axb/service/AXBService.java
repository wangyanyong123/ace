package com.github.wxiaoqi.security.app.axb.service;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.AXBResponseCodeEnum;
import com.github.wxiaoqi.security.api.vo.pns.out.AXBResultData;
import com.github.wxiaoqi.security.api.vo.pns.out.AXBResult;
import com.github.wxiaoqi.security.app.axb.bean.*;
import com.github.wxiaoqi.security.api.vo.pns.in.AXBBindingDTO;
import com.github.wxiaoqi.security.app.axb.constants.*;
import com.github.wxiaoqi.security.app.axb.util.AXBUtils;
import com.github.wxiaoqi.security.app.biz.BizPnsCallBiz;
import com.github.wxiaoqi.security.app.biz.BizPnsCallLogBiz;
import com.github.wxiaoqi.security.app.biz.BizPnsCallRecordBiz;
import com.github.wxiaoqi.security.app.entity.BizPnsCall;
import com.github.wxiaoqi.security.app.entity.BizPnsCallLog;
import com.github.wxiaoqi.security.app.entity.BizPnsCallRecord;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.xml.bind.util.JAXBResult;
import java.text.ParseException;
import java.util.Date;

/**
 * AXB逻辑处理
 */
@Slf4j
@Service
public class AXBService {

    @Autowired
    private AXBUtils axbUtils;
    @Autowired
    private BizPnsCallBiz bizPnsCallBiz;
    @Autowired
    private BizPnsCallRecordBiz bizPnsCallRecordBiz;
    @Autowired
    private BizPnsCallLogBiz bizPnsCallLogBiz;
    @Autowired
    private AXBBindTimeOutJobBiz axbBindTimeOutJobBiz;

    @Value("${pns.axb.binding}")
    private String bindingURL;
    @Value("${pns.axb.unbinding}")
    private String unbindingURL;
    @Value("${pns.axb.outtransfer.set}")
    private String outtransferSetURL;
    @Value("${pns.axb.outtransfer.delete}")
    private String outtransferDeleteURL;
    @Value("${pns.expiration}")
    private String expiration;
    @Value("${pns.record}")
    private String record;
    @Value("${pns.area.code}")
    private String areaCode;
    @Value("${pns.filter}")
    private String filter;
    @Value("${pns.Interval.time}")
    private Integer IntervalTime;


    /**
     * 绑定并记录日志
     * @param axbBindingDTO
     * @return
     */
    public ObjectRestResponse axbBinding(AXBBindingDTO axbBindingDTO){
        ObjectRestResponse result = new ObjectRestResponse();
        BizPnsCallLog bizPnsCallLog = new BizPnsCallLog();
        bizPnsCallLog.setBindingFlag(BindingFlagConstant.BINGDING);
        bizPnsCallLog.setBindingType(BindingTypeConstant.MANUAL);
        this.axbBinding(axbBindingDTO,bizPnsCallLog,result);
        this.addlogRecord(bizPnsCallLog,result);
        return result;
    }

    /**
     * 绑定并记录日志
     * @param axbBindingDTO
     * @return
     */
    public AXBResult axbBindingInner(AXBBindingDTO axbBindingDTO){
        ObjectRestResponse result = new ObjectRestResponse();
        BizPnsCallLog bizPnsCallLog = new BizPnsCallLog();
        bizPnsCallLog.setBindingFlag(BindingFlagConstant.BINGDING);
        bizPnsCallLog.setBindingType(BindingTypeConstant.MANUAL);
        AXBResult axbResult = this.axbBinding(axbBindingDTO,bizPnsCallLog,result);
        this.addlogRecord(bizPnsCallLog,result);
        return axbResult;
    }

    // 返回绑定结果
    private AXBResult axbBinding(AXBBindingDTO axbBindingDTO, BizPnsCallLog bizPnsCallLog,ObjectRestResponse result){
        AXBResult axbResult = null;
        //获取商户号码
        String companyTel = axbBindingDTO.getTelB();
        if(StringUtils.isEmpty(companyTel)){
            companyTel = bizPnsCallBiz.getCompanyTel(axbBindingDTO.getId());
        }
        // 号码B校验
        if(StringUtils.isEmpty(companyTel)){
            log.error("隐私号邦绑定失败，商户号码为空，订单ID:{},号码A:{},号码B:{}",axbBindingDTO.getId(),axbBindingDTO.getTelA(),companyTel);
            result.setStatus(AXBResponseCodeEnum.COMPANY_MOBILE_BUSY.getKey());
            result.setMessage(AXBResponseCodeEnum.COMPANY_MOBILE_BUSY.getValue());
            return axbResult;
        }

        axbResult = this.executeBind(axbBindingDTO,bizPnsCallLog,companyTel);
        if(ObjectUtils.isEmpty(axbResult)){
            log.error("隐私号邦绑定失败，绑定接口返回空，订单ID:{},号码A:{},号码B:{}",axbBindingDTO.getId(),axbBindingDTO.getTelA(),companyTel);
            result.setStatus(AXBResponseCodeEnum.UNKNOWN_EXCEPTION.getKey());
            result.setMessage(AXBResponseCodeEnum.UNKNOWN_EXCEPTION.getValue());
            return axbResult;
        }

        if(axbResult.isSuccess()){
            // 保存绑定信息
            this.addBizPnsCall(axbBindingDTO,axbResult.getData(),companyTel);
            result.setData(axbResult.getData());
            return axbResult;
        }else if(axbResult.isNumberBountt()){
            //号已经存绑定关系提示改为商户号码占线
            result.setStatus(AXBResponseCodeEnum.COMPANY_MOBILE_BUSY.getKey());
            result.setMessage(AXBResponseCodeEnum.COMPANY_MOBILE_BUSY.getValue());
            return axbResult;
        }
        result.setStatus(axbResult.getCode());
        result.setMessage(axbResult.getMsg());
        result.setData(axbResult.getData());
        return axbResult;

    }

    // 调用第三方接口执行号码绑定
    private AXBResult executeBind(AXBBindingDTO axbBindingDTO, BizPnsCallLog bizPnsCallLog,String companyTel) {
        // 转换
        AXBBinding axbBinding = this.convert(axbBindingDTO);
        // 设置B号码
        axbBinding.setTelB(companyTel);
        //补充日志信息
        BeanUtils.copyProperties(axbBinding,bizPnsCallLog);
        bizPnsCallLog.setExpiration(Integer.parseInt(axbBinding.getExpiration()));
        bizPnsCallLog.setAreaCode(axbBinding.getAreaCode());
        bizPnsCallLog.setRecord(Integer.parseInt(axbBinding.getRecord()));
        bizPnsCallLog.setSubId(axbBindingDTO.getId());
        AXBResult axbResult = null;
        String response = axbUtils.execute(bindingURL, axbBinding);
        if (StringUtils.isNotEmpty(response)) {
            log.info("axb binding response={}", response);
            try {
                axbResult = JacksonJsonUtil.jsonToBean(response, AXBResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return axbResult;
    }

    /**
     * 添加日志
     * @param bizPnsCallLog
     * @param result
     */
    private void addlogRecord(BizPnsCallLog bizPnsCallLog,ObjectRestResponse result){
       if(!ObjectUtils.isEmpty(bizPnsCallLog) && StringUtils.isNotEmpty(bizPnsCallLog.getBindId())){
           bizPnsCallLog.setId(UUIDUtils.generateUuid());
           bizPnsCallLog.setBindingCode(result.getStatus()+"");
           bizPnsCallLog.setBindingMsg(result.getMessage());
           if(result.getStatus() == HttpStatus.OK.value() && !ObjectUtils.isEmpty(result.getData())){
               AXBResultData axbResultData = (AXBResultData)result.getData();
               bizPnsCallLog.setBindId(axbResultData.getBindId());
           }
           bizPnsCallLogBiz.insertSelective(bizPnsCallLog);
       }
    }

    // axbBindingDTO 转换 axbBinding
    private AXBBinding convert(AXBBindingDTO axbBindingDTO){
        AXBBinding axbBinding = new AXBBinding();
        BeanUtils.copyProperties(axbBindingDTO,axbBinding);
        axbBinding.setExpiration(expiration);
        axbBinding.setRecord(record);
        if(StringUtils.isEmpty(axbBindingDTO.getAreaCode())){
            axbBinding.setAreaCode(areaCode);
        }
        return axbBinding;
    }

    // 保存绑定信息
    private void addBizPnsCall(AXBBindingDTO axbBindingDTO,AXBResultData axbResultData,String companyTel){
        // 计算超时时间
        Date date = new Date();
        Date overDate = DateUtils.addSeconds(date,Integer.parseInt(expiration));

        BizPnsCall bizPnsCall = new BizPnsCall();
        BeanUtils.copyProperties(axbBindingDTO,bizPnsCall);
        BeanUtils.copyProperties(axbResultData,bizPnsCall);
        bizPnsCall.setOverTime(overDate);
        bizPnsCall.setId(UUIDUtils.generateUuid());
        bizPnsCall.setTelB(companyTel);
        bizPnsCall.setRecord(Integer.parseInt(record));
        bizPnsCall.setExpiration(Integer.parseInt(expiration));
        if(StringUtils.isEmpty(axbBindingDTO.getAreaCode())){
            bizPnsCall.setAreaCode(areaCode);
        }
        bizPnsCallBiz.insertSelective(bizPnsCall);
        // 加入超时任务
        axbBindTimeOutJobBiz.addBindTimeoutJob(axbResultData.getBindId(),null);
    }

    /**
     * 解绑并记录日志
     * @param bindId
     * @return
     */
    public ObjectRestResponse axbUnbinding(String bindId){
        ObjectRestResponse result = new ObjectRestResponse();
        BizPnsCallLog bizPnsCallLog = new BizPnsCallLog();
        bizPnsCallLog.setBindingFlag(BindingFlagConstant.UNBINGDING);
        bizPnsCallLog.setBindingType(BindingTypeConstant.MANUAL);
        this.axbUnbinding(bindId,bizPnsCallLog,result);
        this.addlogRecord(bizPnsCallLog,result);
        return result;
    }

    /**
     * 解绑并记录日志
     * @param bindId
     * @return
     */
    public AXBResult axbUnbindingInner(String bindId){
        ObjectRestResponse result = new ObjectRestResponse();
        BizPnsCallLog bizPnsCallLog = new BizPnsCallLog();
        bizPnsCallLog.setBindingFlag(BindingFlagConstant.UNBINGDING);
        bizPnsCallLog.setBindingType(BindingTypeConstant.MANUAL);
        AXBResult axbResult = this.axbUnbinding(bindId,bizPnsCallLog,result);
        this.addlogRecord(bizPnsCallLog,result);
        return axbResult;
    }

    /**
     * 解绑
     * @return
     */
    public AXBResult axbUnbinding(String bindId, BizPnsCallLog bizPnsCallLog,ObjectRestResponse result ){
        AXBResult axbResult = null;
        if(StringUtils.isEmpty(bindId)){
            result.setStatus(AXBResponseCodeEnum.SUCCESS.getKey());
            result.setMessage(AXBResponseCodeEnum.SUCCESS.getValue());
            return axbResult;
        }

        // 判断是否已经解绑
        BizPnsCall pnsCall = new BizPnsCall();
        pnsCall.setBindId(bindId);
        pnsCall = bizPnsCallBiz.selectOne(pnsCall);
        if(pnsCall != null && BindingFlagConstant.UNBINGDING == pnsCall.getBindingFlag()){
            result.setStatus(AXBResponseCodeEnum.SUCCESS.getKey());
            result.setMessage(AXBResponseCodeEnum.SUCCESS.getValue());
            return axbResult;
        }
        axbResult = this.executeUnbinding(bindId);
        if(ObjectUtils.isEmpty(axbResult)){
            log.error("隐私号解绑失败，绑定接口返回空，绑定ID:{}",bindId);
            result.setStatus(AXBResponseCodeEnum.UNKNOWN_EXCEPTION.getKey());
            result.setMessage(AXBResponseCodeEnum.UNKNOWN_EXCEPTION.getValue());
            return axbResult;
        }

        //补充日志信息
        if(!ObjectUtils.isEmpty(bizPnsCallLog)){
            BizPnsCall bizPnsCall = new BizPnsCall();
            bizPnsCall.setBindId(bindId);
            bizPnsCall = bizPnsCallBiz.selectOne(bizPnsCall);
            // bizPnsCall 为null说明是运营商测试数据，不做记录
            if(!ObjectUtils.isEmpty(bizPnsCall)){
                BeanUtils.copyProperties(bizPnsCall,bizPnsCallLog);
                bizPnsCallLog.setCreateTime(null);
            }

        }

        if(axbResult.isSuccess() || axbResult.isBindExpiredOrNotExist()){
            bizPnsCallBiz.updateByBindId(BindingFlagConstant.UNBINGDING,bindId);
            return axbResult;
        }
        result.setStatus(axbResult.getCode());
        result.setMessage(axbResult.getMsg());
        result.setData(axbResult.getData());
        return axbResult;


    }

    //调取第三方接口解绑隐私号
    private AXBResult executeUnbinding(String bindId) {
        AXBResult axbResult = null;
        String response = axbUtils.execute(unbindingURL + bindId, "");
        if (StringUtils.isNotEmpty(response)) {
            log.info("axb unbinding response={}", response);
            try {
                axbResult = JacksonJsonUtil.jsonToBean(response, AXBResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return axbResult;
    }

    /**
     * 外呼转接设置
     * @param axbOuttransferSet 设置参数
     * @return
     */
    public String axbOuttransferSet(AXBOuttransferSet axbOuttransferSet){
        ObjectRestResponse result = new ObjectRestResponse();
        String reponse =  axbUtils.execute(outtransferSetURL,axbOuttransferSet);
        log.info("axb outtransfer set reponose={}",reponse);
        return reponse;
    }

    /**
     * 外呼转接删除
     * @param axbOuttransferDelete 删除参数
     * @return
     */
    public String axbOuttransferDelete(AXBOuttransferDelete axbOuttransferDelete){
        String reponse =  axbUtils.execute(outtransferDeleteURL,axbOuttransferDelete);
        log.info("axb outtransfer delete reponose={}",reponse);
        return reponse;
    }

    /**
     * 回调地址接收话单
     * @param axbCallbackCall 话单参数
     * @return
     */
    public AXBResult callbackURL(AXBCallbackCall axbCallbackCall){
        ObjectRestResponse result = new ObjectRestResponse();
        if(filter.equalsIgnoreCase(axbCallbackCall.getCustomer())){
            log.info("call back data:{}",axbCallbackCall);
            BizPnsCallRecord bizPnsCallRecord = new BizPnsCallRecord();
            bizPnsCallRecord.setId(UUIDUtils.generateUuid());
            BeanUtils.copyProperties(axbCallbackCall,bizPnsCallRecord);
            bizPnsCallRecord.setCustomer(axbCallbackCall.getCustomer().toLowerCase());
            bizPnsCallRecord.setEndTypeDesc(AXBEndTypeEnum.getValue(axbCallbackCall.getEndType()));
            bizPnsCallRecord.setEndStateDesc(AXBEndStateEnum.getValue(axbCallbackCall.getEndState()));
            try {
                bizPnsCallRecord.setStartTime(DateUtils.parseDateTime(axbCallbackCall.getStartTime()));
                bizPnsCallRecord.setTalkingTime(DateUtils.parseDateTime(axbCallbackCall.getTalkingTime()));
                bizPnsCallRecord.setEndTime(DateUtils.parseDateTime(axbCallbackCall.getEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            bizPnsCallRecordBiz.insertSelective(bizPnsCallRecord);

            BizPnsCallLog bizPnsCallLog = new BizPnsCallLog();
            bizPnsCallLog.setBindingFlag(BindingFlagConstant.UNBINGDING);
            bizPnsCallLog.setBindingType(BindingTypeConstant.AUTOMATIC);
            // 通话结束不进行解绑操作
            // this.axbUnbinding(axbCallbackCall.getBindId(),bizPnsCallLog,result);
            this.addlogRecord(bizPnsCallLog,result);
        }
        AXBResult axbResult = new AXBResult();
        axbResult.setCode(AXBResponseCodeEnum.SUCCESS.getKey());
        axbResult.setMsg(AXBResponseCodeEnum.SUCCESS.getValue());
        return axbResult;
    }

    /**
     * 超时处理
     */
    public ObjectRestResponse overTimeHandle(String bindId){
        ObjectRestResponse result = new ObjectRestResponse();
        BizPnsCallLog bizPnsCallLog = new BizPnsCallLog();
        bizPnsCallLog.setBindingFlag(BindingFlagConstant.UNBINGDING);
        bizPnsCallLog.setBindingType(BindingTypeConstant.AUTOMATIC);
        this.axbUnbinding(bindId,bizPnsCallLog,result);
        this.addlogRecord(bizPnsCallLog,result);
        try {
            log.error("自动解绑结果："+JacksonJsonUtil.beanToJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解绑失败再次添加任务去解绑
     * @param bindId
     */
    public void addBindTimeoutJob(String bindId){
        axbBindTimeOutJobBiz.addBindTimeoutJob(bindId,IntervalTime);
    }


    /**
     * 根据bindId查询绑定关系是否存在
     */
    public BizPnsCall queryBindRecord(String bindId){
        BizPnsCall pnsCall = new BizPnsCall();
        pnsCall.setBindId(bindId);
        return bizPnsCallBiz.selectOne(pnsCall);
    }

}