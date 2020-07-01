package com.github.wxiaoqi.security.app.biz.order.handler;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.store.product.ProductStore;
import com.github.wxiaoqi.security.app.biz.BizProductOrderDetailBiz;
import com.github.wxiaoqi.security.app.biz.order.context.CreateOrderContext;
import com.github.wxiaoqi.security.app.biz.order.context.CreateProductOrderContext;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.app.fegin.StoreProductFegin;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductOrderStoreHandler {

    @Autowired
    private BizProductOrderDetailBiz bizProductOrderDetailBiz;
    @Autowired
    private StoreProductFegin storeProductFegin;

    public ObjectRestResponse reduceRedisStock(CreateOrderContext createOrderContext) {
        Assert.isTrue(createOrderContext instanceof CreateProductOrderContext
                ,"非法请求商品订单扣减库存");

            String userID = BaseContextHandler.getUserID();
            List<ProductStore> productStores = buildProductStores((CreateProductOrderContext) createOrderContext,userID,false);
            if(log.isInfoEnabled()){
                log.info("商品下单扣减redis库存，data:{}", JSON.toJSONString(productStores));
            }
            //下单扣减redis 库存
            ObjectRestResponse restResponse = storeProductFegin.saleBatch(productStores);
            if(!restResponse.success()){
                log.error("商品下单扣减redis库存，message:{}, data:{}",restResponse.getMessage(),JSON.toJSONString(productStores));
//                throw new BusinessException("下单扣库失败，"+restResponse.getMessage());
            }
            return restResponse;
    }

    public void backRedisStock(CreateOrderContext createOrderContext) {
        if(createOrderContext instanceof CreateProductOrderContext){
            //下单失败归还redis库存
            String userID = BaseContextHandler.getUserID();
            List<ProductStore> productStores = buildProductStores((CreateProductOrderContext) createOrderContext,userID,true);
            if(log.isInfoEnabled()){
                log.info("商品下单失败归还redis库存，data:{}", JSON.toJSONString(productStores));
            }
            ObjectRestResponse restResponse = storeProductFegin.cancelBatch(productStores);
            if(!restResponse.success()){
                log.error("商品订单下单归还库存失败，{}, data:{}",restResponse.getMessage(),JSON.toJSONString(productStores));
//                throw new BusinessException("商品订单下单归还库存失败，"+restResponse.getMessage());
            }
        }
    }

    public void backRedisStock(String orderId, String handler) {
        List<BizProductOrderDetail> detailList = bizProductOrderDetailBiz.findByOrderId(orderId);
        List<ProductStore> productStores = buildProductStores(detailList,handler, true);
        if(log.isInfoEnabled()){
            log.info("取消商品订单归还库存，data:{}", JSON.toJSONString(productStores));
        }
        ObjectRestResponse restResponse = storeProductFegin.cancelBatch(productStores);
        if(!restResponse.success()){
            log.error("取消商品订单归还库存，{}, data:{}",restResponse.getMessage(),JSON.toJSONString(productStores));
//            throw new BusinessException("取消商品订单归还库存，"+restResponse.getMessage());
        }

    }
    public void reduceDbStock(String orderId, String handler) {
        List<BizProductOrderDetail> detailList = bizProductOrderDetailBiz.findByParentId(orderId);
        List<ProductStore> productStores = buildProductStores(detailList,handler, false);
        if(log.isInfoEnabled()){
            log.info("商品订单支付成功扣减DB库存，data:{}", JSON.toJSONString(productStores));
        }
        try {
            storeProductFegin.paySaleAsyncBatch(productStores);
        }catch (Exception e){
            log.error("商品订单支付成功扣减DB库存失败，{}, data:{}",e.getMessage(),JSON.toJSONString(productStores));
        }

    }
    private List<ProductStore> buildProductStores(List<BizProductOrderDetail> detailList,String handler,boolean isBack){
        return detailList.stream()
                .map(sku ->
                        doBuild(sku.getOrderId(),handler,sku.getSpecId(),sku.getQuantity(),isBack))
                .collect(Collectors.toList());

    }

    private List<ProductStore> buildProductStores(CreateProductOrderContext orderContext,String handler,boolean isBack){
        return orderContext.getTenantList().stream()
                .flatMap(tenantInfo -> tenantInfo.getSkuList().stream())
                .map(sku -> doBuild(orderContext.getOrderId(),handler,sku.getSpecId(),sku.getQuantity(),isBack))
                .collect(Collectors.toList());

    }

    private ProductStore doBuild(String orderId,String userID,String specId,Integer accessNum,boolean isBack){
        ProductStore productStore = new ProductStore();
        productStore.setOrderId(orderId);
        productStore.setSpecId(specId);
        if(isBack){
            productStore.setAccessNum(accessNum);
        }else{
            productStore.setAccessNum(-accessNum);
        }
        productStore.setCreateBy(userID);
        return productStore;
    }

}
