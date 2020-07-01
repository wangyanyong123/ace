package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.waiter.OrderWaiterVO;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderWaiter;
import com.github.wxiaoqi.security.merchant.mapper.BizReservationOrderWaiterMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Date;
import java.util.List;

/**
 * 服务订单分配人员表
 *
 * @author wangyanyong
 * @Date 2020-04-24 17:50:27
 */
@Service
public class BizReservationOrderWaiterBiz extends BusinessBiz<BizReservationOrderWaiterMapper,BizReservationOrderWaiter> {

    public void saveWaiter(OrderWaiterVO orderWaiterVO){
        BizReservationOrderWaiter bizReservationOrderWaiter = new BizReservationOrderWaiter();
        BeanUtils.copyProperties(orderWaiterVO,bizReservationOrderWaiter);
        bizReservationOrderWaiter.setId(UUIDUtils.generateUuid());
        String userId = BaseContextHandler.getUserID();
        bizReservationOrderWaiter.setCreateBy(userId);
        Date date = new Date();
        bizReservationOrderWaiter.setCreateTime(date);
        bizReservationOrderWaiter.setModifyBy(userId);
        bizReservationOrderWaiter.setModifyTime(date);
        this.insertSelective(bizReservationOrderWaiter);
    }


    public void saveOrUpdateWaiter(OrderWaiterVO orderWaiterVO){
        BizReservationOrderWaiter bizReservationOrderWaiter = new BizReservationOrderWaiter();
        bizReservationOrderWaiter.setOrderId(orderWaiterVO.getOrderId());
        bizReservationOrderWaiter.setStatus(AceDictionary.DATA_STATUS_VALID);
        List<BizReservationOrderWaiter> bizReservationOrderWaiterList = this.selectList(bizReservationOrderWaiter);
        if(CollectionUtils.isNotEmpty(bizReservationOrderWaiterList)){
            bizReservationOrderWaiterList.forEach(orderWaiter->{
                orderWaiter.setStatus(AceDictionary.DATA_STATUS_INVALID);
                orderWaiter.setModifyBy(BaseContextHandler.getUserID());
                orderWaiter.setDeleteTime(new Date());
                this.updateById(orderWaiter);
            });
        }
        BeanUtils.copyProperties(orderWaiterVO,bizReservationOrderWaiter);
        bizReservationOrderWaiter.setId(UUIDUtils.generateUuid());
        String userId = BaseContextHandler.getUserID();
        bizReservationOrderWaiter.setCreateBy(userId);
        Date date = new Date();
        bizReservationOrderWaiter.setCreateTime(date);
        bizReservationOrderWaiter.setModifyBy(userId);
        bizReservationOrderWaiter.setModifyTime(date);
        this.insertSelective(bizReservationOrderWaiter);
    }
}