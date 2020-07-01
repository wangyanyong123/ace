package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizOrderOperationRecord;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderOperationRecord;
import com.github.wxiaoqi.security.app.mapper.BizReservationOrderOperationRecordMapper;
import com.github.wxiaoqi.security.app.reservation.vo.ReservationOrderOperationRecordVO;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.app.vo.order.out.OrderOperationRecordInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author wangyanyong
 * @Date 2020-04-25 21:30:35
 */
@Service
public class BizReservationOrderOperationRecordBiz extends BusinessBiz<BizReservationOrderOperationRecordMapper, BizReservationOrderOperationRecord> {
    public List<ReservationOrderOperationRecordVO> queryReservationOrderOperationRecord(String orderId){
        return this.mapper.queryReservationOrderOperationRecord(orderId);
    }

    public void addOrderOperationRecord(String orderId,OrderOperationType orderOperationType,String...remark){

        BizReservationOrderOperationRecord bizOrderOperationRecord = new BizReservationOrderOperationRecord();
        bizOrderOperationRecord.setId(UUIDUtils.generateUuid());
        bizOrderOperationRecord.setOrderId(orderId);
        bizOrderOperationRecord.setStepStatus(orderOperationType.getStepStatus());
        bizOrderOperationRecord.setCurrStep(orderOperationType.getCurrStep());
        bizOrderOperationRecord.setDescription(orderOperationType.getDescription());
        if(ArrayUtils.isNotEmpty(remark)){
            bizOrderOperationRecord.setDescription(OrderOperationType.getFormatDescription(orderOperationType,remark));
        }

        bizOrderOperationRecord.setCreateBy(BaseContextHandler.getUserID());
        bizOrderOperationRecord.setCreateTime(DateTimeUtil.getLocalTime());
        bizOrderOperationRecord.setModifyBy(BaseContextHandler.getUserID());
        bizOrderOperationRecord.setStatus(AceDictionary.DATA_STATUS_VALID);

        this.mapper.insertSelective(bizOrderOperationRecord);

    }
}