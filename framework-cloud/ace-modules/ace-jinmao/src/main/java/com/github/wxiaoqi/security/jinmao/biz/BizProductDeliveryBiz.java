package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizProduct;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.ProductDeliveryData;
import com.github.wxiaoqi.security.jinmao.vo.productdelivery.in.SaveProductDeliveryIn;
import com.netflix.client.ssl.AbstractSslContextFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizProductDelivery;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductDeliveryMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品配送范围
 *
 * @author guohao
 * @Date 2020-04-25 13:50:35
 */
@Service
public class BizProductDeliveryBiz extends BusinessBiz<BizProductDeliveryMapper,BizProductDelivery> {


    @Resource
    private BizProductDeliveryMapper bizProductDeliveryMapper;
    @Resource
    private BizProductBiz bizProductBiz;

    public ObjectRestResponse editProductDelivery(SaveProductDeliveryIn editProductDeliveryIn) {
        editProductDeliveryIn.check();

        List<String> oldCityCodeList = bizProductDeliveryMapper.findProductDeliveryCityCodeList(editProductDeliveryIn.getProductId());

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
                bizProductDeliveryMapper.findProductDeliveryCityCodeList(productId);

        BizProduct bizProduct = bizProductBiz.selectById(productId);
        list.forEach(item->{
            if(productDeliveryCityCodeList.contains(item.getCityCode())){
                return;
            }
            BizProductDelivery bizProductDelivery = buildProductDelivery(item);
            bizProductDelivery.setCompanyId(bizProduct.getTenantId());
            bizProductDelivery.setProductId(productId);
            this.mapper.insertSelective(bizProductDelivery);
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
            this.mapper.deleteByCityCode(editProductDeliveryIn.getProductId(),deleteIdList,BaseContextHandler.getUserID(),DateTimeUtil.getLocalTime());
        }

    }

    private BizProductDelivery buildProductDelivery(ProductDeliveryData productDeliveryData){
        BizProductDelivery bizProductDelivery = new BizProductDelivery();
        BeanUtils.copyProperties(productDeliveryData,bizProductDelivery);
        bizProductDelivery.setId(UUIDUtils.generateUuid());
        bizProductDelivery.setFullName(bizProductDelivery.getProcName()+bizProductDelivery.getCityName());
        bizProductDelivery.setCreateBy(BaseContextHandler.getUserID());
        bizProductDelivery.setCreateTime(DateTimeUtil.getLocalTime());
        bizProductDelivery.setModifyBy(BaseContextHandler.getUserID());
        bizProductDelivery.setStatus(AceDictionary.DATA_STATUS_VALID);
        return bizProductDelivery;
    }

    public List<String> findProductDeliveryCityCodeList(String productId) {
        return this.mapper.findProductDeliveryCityCodeList(productId);
    }

    public List<ProductDeliveryData> findProductDeliveryList(String productId) {
        return this.mapper.findProductDeliveryList(productId);
    }

    public int deleteByIds(List<String> idList) {
        Assert.notEmpty(idList,"id 不能为空");

        return  this.mapper.deleteByIds(idList,BaseContextHandler.getUserID(),DateTimeUtil.getLocalTime());
    }
}