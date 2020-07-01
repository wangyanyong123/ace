package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.logistics.OrderLogisticsVO;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.merchant.entity.BizExpressCompany;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrderDetail;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.merchant.entity.BizOrderLogistics;
import com.github.wxiaoqi.security.merchant.mapper.BizOrderLogisticsMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Date;
import java.util.List;

/**
 * 商品订单物流信息表
 *
 * @author wangyanyong
 * @Date 2020-04-24 16:49:37
 */
@Service
public class BizOrderLogisticsBiz extends BusinessBiz<BizOrderLogisticsMapper,BizOrderLogistics> {

    public void saveOrderLogistics(OrderLogisticsVO orderLogisticsVO,BizExpressCompany bizExpressCompany, List<BizProductOrderDetail> bizProductOrderDetails){
        String orderDetailId = bizProductOrderDetails.get(0).getId();
        BizOrderLogistics bizOrderLogistics = new BizOrderLogistics();
        BeanUtils.copyProperties(orderLogisticsVO,bizOrderLogistics);
        bizOrderLogistics.setId(UUIDUtils.generateUuid());
        String userId = BaseContextHandler.getUserID();
        bizOrderLogistics.setCreateBy(userId);
        Date date = new Date();
        bizOrderLogistics.setOrderDetailId(bizProductOrderDetails.size()==1?orderDetailId:"0");
        bizOrderLogistics.setCreateTime(date);
        bizOrderLogistics.setLogisticsCode(bizExpressCompany.getCompanyCode());
        bizOrderLogistics.setLogisticsName(bizExpressCompany.getCompanyName());
        bizOrderLogistics.setSendTime(date);
        bizOrderLogistics.setModifyBy(userId);
        bizOrderLogistics.setModifyTime(date);
        this.insertSelective(bizOrderLogistics);
    }

    public void saveOrUpdateOrderLogistics(OrderLogisticsVO orderLogisticsVO,BizExpressCompany bizExpressCompany, List<BizProductOrderDetail> bizProductOrderDetails){
        String orderDetailId = bizProductOrderDetails.get(0).getId();
        BizOrderLogistics bizOrderLogistics = new BizOrderLogistics();
        bizOrderLogistics.setOrderId(orderLogisticsVO.getOrderId());
        bizOrderLogistics.setStatus(AceDictionary.DATA_STATUS_VALID);
        List<BizOrderLogistics> bizOrderLogisticsList = this.selectList(bizOrderLogistics);
        if(CollectionUtils.isNotEmpty(bizOrderLogisticsList)){
            bizOrderLogisticsList.forEach(orderLogistics->{
                orderLogistics.setStatus(AceDictionary.DATA_STATUS_INVALID);
                orderLogistics.setModifyBy(BaseContextHandler.getUserID());
                orderLogistics.setDeleteTime(new Date());
                this.updateById(orderLogistics);
            });
        }

        BeanUtils.copyProperties(orderLogisticsVO,bizOrderLogistics);
        bizOrderLogistics.setId(UUIDUtils.generateUuid());
        String userId = BaseContextHandler.getUserID();
        bizOrderLogistics.setCreateBy(userId);
        Date date = new Date();
        bizOrderLogistics.setOrderDetailId(bizProductOrderDetails.size()==1?orderDetailId:"0");
        bizOrderLogistics.setCreateTime(date);
        bizOrderLogistics.setLogisticsCode(bizExpressCompany.getCompanyCode());
        bizOrderLogistics.setLogisticsName(bizExpressCompany.getCompanyName());
        bizOrderLogistics.setSendTime(date);
        bizOrderLogistics.setModifyBy(userId);
        bizOrderLogistics.setModifyTime(date);
        this.insertSelective(bizOrderLogistics);
    }
}