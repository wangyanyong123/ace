package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.merchant.entity.BizOrderOperationRecord;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderOperationRecord;
import com.github.wxiaoqi.security.merchant.mapper.BizReservationOrderOperationRecordMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * 
 * 预约订单操作日志
 * @author wangyanyong
 * @Date 2020-04-26 15:38:24
 */
@Service
public class BizReservationOrderOperationRecordBiz extends BusinessBiz<BizReservationOrderOperationRecordMapper,BizReservationOrderOperationRecord> {

    /**
     * 预约订单操作日志添加
     * @param orderId
     * @param orderOperationType
     */
    public void addOrderOperationRecord(String orderId,OrderOperationType orderOperationType){
        BizReservationOrderOperationRecord orderOperationRecord = new BizReservationOrderOperationRecord();
        orderOperationRecord.setId(UUIDUtils.generateUuid());
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        orderOperationRecord.setDescription(orderOperationType.getDescription());
        orderOperationRecord.setCreateBy(BaseContextHandler.getUserID());
        orderOperationRecord.setCreateTime(DateTimeUtil.getLocalTime());
        orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        this.insertSelective(orderOperationRecord);
    }

    /**
     * 预约订单操作日志添加
     * @param orderId
     * @param orderOperationType
     */
    public void addOrderOperationRecord(String orderId,OrderOperationType orderOperationType,String...remark){
        BizReservationOrderOperationRecord orderOperationRecord = new BizReservationOrderOperationRecord();
        orderOperationRecord.setId(UUIDUtils.generateUuid());
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        if(ArrayUtils.isNotEmpty(remark)){
            orderOperationRecord.setDescription(OrderOperationType.getFormatDescription(orderOperationType,remark));
        }
        orderOperationRecord.setCreateBy(BaseContextHandler.getUserID());
        orderOperationRecord.setCreateTime(DateTimeUtil.getLocalTime());
        orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        this.insertSelective(orderOperationRecord);
    }

    /**
     * 预约订单操作日志添加
     * @param orderId
     * @param orderOperationType
     */
    public void updateOrderOperationRecord(String orderId,OrderOperationType orderOperationType,String...remark){
        BizReservationOrderOperationRecord orderOperationRecord = new BizReservationOrderOperationRecord();
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        orderOperationRecord = this.selectOne(orderOperationRecord);
        if(!ObjectUtils.isEmpty(orderOperationRecord)){
            orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
            orderOperationRecord.setModifyTime(new Date());
            if(ArrayUtils.isNotEmpty(remark)){
                orderOperationRecord.setDescription(OrderOperationType.getFormatDescription(orderOperationType,remark));
            }
            this.updateById(orderOperationRecord);
        }
    }
}