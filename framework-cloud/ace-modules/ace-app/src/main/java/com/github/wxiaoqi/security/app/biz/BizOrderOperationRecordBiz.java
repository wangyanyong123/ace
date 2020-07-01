package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizOrderOperationRecord;
import com.github.wxiaoqi.security.app.mapper.BizOrderOperationRecordMapper;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.app.vo.order.out.OrderOperationRecordInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author guohao
 * @Date 2020-04-23 10:28:58
 */
@Service
public class BizOrderOperationRecordBiz extends BusinessBiz<BizOrderOperationRecordMapper,BizOrderOperationRecord> {


    public List<OrderOperationRecordInfo> findInfoVoByOrderId(String orderId){
       return this.mapper.selectInfoVoByOrderId(orderId);
    }

    public void addRecordByOrderIdResultList(List<OrderIdResult> orderIdResultList
            ,OrderOperationType orderOperationType,String desc){
        for (OrderIdResult orderIdResult : orderIdResultList) {
           this.addRecordByOrderIdResult(orderIdResult,orderOperationType,desc);
        }
    }
    public void addRecordByOrderIdResult(OrderIdResult orderIdResult,OrderOperationType orderOperationType,String desc){
        this.addOrderOperationRecord(orderIdResult.getOrderId()
                    ,orderIdResult.getParentId()
                    , orderOperationType,desc);
    }



    public void addOrderOperationRecord(String orderId, String parentId,
                                         OrderOperationType orderOperationType,String desc){

        BizOrderOperationRecord orderOperationRecord = new BizOrderOperationRecord();
        orderOperationRecord.setId(UUIDUtils.generateUuid());
        orderOperationRecord.setOrderId(orderId);
        orderOperationRecord.setParentId(parentId);
        orderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        orderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        if(StringUtils.isNotEmpty(desc)){
            orderOperationRecord.setDescription(desc);
        }else{
            orderOperationRecord.setDescription(orderOperationType.getDescription());
        }
        orderOperationRecord.setCreateBy(BaseContextHandler.getUserID());
        orderOperationRecord.setCreateTime(DateTimeUtil.getLocalTime());
        orderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
        orderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);

        this.mapper.insertSelective(orderOperationRecord);

    }

}