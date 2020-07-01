package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizProduct;
import com.github.wxiaoqi.security.jinmao.entity.BizProductDelivery;
import com.github.wxiaoqi.security.jinmao.entity.BizReservation;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductDeliveryMapper;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.in.SaveProductDeliveryIn;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizReservationDelivery;
import com.github.wxiaoqi.security.jinmao.mapper.BizReservationDeliveryMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约服务配送范围
 *
 * @author guohao
 * @Date 2020-06-11 12:19:38
 */
@Service
public class BizReservationDeliveryBiz extends BusinessBiz<BizReservationDeliveryMapper,BizReservationDelivery> {
    @Resource
    private BizReservationDeliveryMapper bizReservationDeliveryMapper;
    @Resource
    private BizReservationBiz bizReservationBiz;

    public ObjectRestResponse editDelivery(SaveProductDeliveryIn editProductDeliveryIn) {
        editProductDeliveryIn.check();

        List<String> oldCityCodeList = bizReservationDeliveryMapper.findDeliveryCityCodeList(editProductDeliveryIn.getProductId());

        if(CollectionUtils.isEmpty(oldCityCodeList)){
            save(editProductDeliveryIn.getProductId()
                    ,editProductDeliveryIn.getDeliveryList());
        }else{
            update(editProductDeliveryIn,oldCityCodeList);
        }

        return ObjectRestResponse.ok();
    }

    private void save(String productId, List<ProductDeliveryData> list){

        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        List<String> productDeliveryCityCodeList =
                bizReservationDeliveryMapper.findDeliveryCityCodeList(productId);

        BizReservation reservation = bizReservationBiz.selectById(productId);
        list.forEach(item->{
            if(productDeliveryCityCodeList.contains(item.getCityCode())){
                return;
            }
            BizReservationDelivery delivery = buildProductDelivery(item);
            delivery.setCompanyId(reservation.getTenantId());
            delivery.setProductId(productId);
            this.mapper.insertSelective(delivery);
        });
    }

    private void update(SaveProductDeliveryIn editProductDeliveryIn,List<String> oldCityCodeList){
        List<String> newCityCode = editProductDeliveryIn.getDeliveryList().stream()
                .filter(item -> StringUtils.isNotEmpty(item.getCityCode()))
                .map(ProductDeliveryData::getCityCode).collect(Collectors.toList());
        List<String> deleteIdList = oldCityCodeList.stream().filter(item -> !newCityCode.contains(item)).collect(Collectors.toList());
        List<ProductDeliveryData> newList = editProductDeliveryIn.getDeliveryList().stream()
                .filter(item -> !oldCityCodeList.contains(item.getCityCode()))
                .collect(Collectors.toList());

        save(editProductDeliveryIn.getProductId(),newList);

        if(CollectionUtils.isNotEmpty(deleteIdList)){
            this.mapper.deleteByCityCode(editProductDeliveryIn.getProductId(),deleteIdList, BaseContextHandler.getUserID(), DateTimeUtil.getLocalTime());
        }

    }

    private BizReservationDelivery buildProductDelivery(ProductDeliveryData productDeliveryData){
        BizReservationDelivery delivery = new BizReservationDelivery();
        BeanUtils.copyProperties(productDeliveryData,delivery);
        delivery.setId(UUIDUtils.generateUuid());
        delivery.setFullName(delivery.getProcName()+delivery.getCityName());
        delivery.setCreateBy(BaseContextHandler.getUserID());
        delivery.setCreateTime(DateTimeUtil.getLocalTime());
        delivery.setModifyBy(BaseContextHandler.getUserID());
        delivery.setStatus(AceDictionary.DATA_STATUS_VALID);
        return delivery;
    }

    public List<String> findDeliveryCityCodeList(String productId) {
        return this.mapper.findDeliveryCityCodeList(productId);
    }

    public List<ProductDeliveryData> findDeliveryList(String productId) {
        return this.mapper.findDeliveryList(productId);
    }

    public int deleteByIds(List<String> idList) {
        Assert.notEmpty(idList,"id 不能为空");
        return  this.mapper.deleteByIds(idList,BaseContextHandler.getUserID(),DateTimeUtil.getLocalTime());
    }
}