package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.merchant.entity.BizOrderOperationRecord;
import com.github.wxiaoqi.security.merchant.mapper.BizOrderOperationRecordMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * 操作记录
 */
@Service
public class BizOrderOperationRecordBiz extends BusinessBiz<BizOrderOperationRecordMapper,BizOrderOperationRecord> {


    public void addOrderOperationRecord(String orderId, String parentId,OrderOperationType orderOperationType){
        BizOrderOperationRecord orderOperationRecord = new BizOrderOperationRecord();
        orderOperationRecord.setId(UUIDUtils.generateUuid());
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setParentId(parentId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        orderOperationRecord.setDescription(orderOperationType.getDescription());
        orderOperationRecord.setCreateBy(BaseContextHandler.getUserID());
        orderOperationRecord.setCreateTime(DateTimeUtil.getLocalTime());
        orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        this.insertSelective(orderOperationRecord);
    }


    public void addOrderOperationRecord(String orderId, String parentId,
                                        OrderOperationType orderOperationType,String...remark) {
        BizOrderOperationRecord orderOperationRecord = new BizOrderOperationRecord();
        orderOperationRecord.setId(UUIDUtils.generateUuid());
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setParentId(parentId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        orderOperationRecord.setDescription(orderOperationType.getDescription());
        if(ArrayUtils.isNotEmpty(remark)){
            orderOperationRecord.setDescription(OrderOperationType.getFormatDescription(orderOperationType,remark));
        }
        orderOperationRecord.setCreateBy(BaseContextHandler.getUserID());
        orderOperationRecord.setCreateTime(DateTimeUtil.getLocalTime());
        orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        this.insertSelective(orderOperationRecord);
    }

    public void updateOrderOperationRecord(String orderId, String parentId,
                                        OrderOperationType orderOperationType,String...remark) {
        BizOrderOperationRecord orderOperationRecord = new BizOrderOperationRecord();
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setParentId(parentId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        orderOperationRecord = this.selectOne(orderOperationRecord);
        if(!ObjectUtils.isEmpty(orderOperationRecord)){
            if(ArrayUtils.isNotEmpty(remark)){
                orderOperationRecord.setDescription(OrderOperationType.getFormatDescription(orderOperationType,remark));
            }
            orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
            orderOperationRecord.setModifyTime(new Date());
            this.updateById(orderOperationRecord);
        }
    }

}