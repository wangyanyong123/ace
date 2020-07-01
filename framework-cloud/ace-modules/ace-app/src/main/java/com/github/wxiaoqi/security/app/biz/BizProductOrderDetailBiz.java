package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderDetailMapper;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderDiscountInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderIncrementInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderTenantInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderTenantProductInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单产品表
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Service
public class BizProductOrderDetailBiz extends BusinessBiz<BizProductOrderDetailMapper,BizProductOrderDetail> {

    public List<BizProductOrderDetail> findByOrderId(String orderId){

        return this.mapper.selectByOrderId(orderId);
    }
    public List<BizProductOrderDetail> findByParentId(String parentId){

        return this.mapper.selectByParentId(parentId);
    }

    public int updateOrderDetailBySplitOrder(String sourceOrderId, String targetOrderId, String tenantId,int detailStatus) {
       return this.mapper.updateOrderDetailBySplitOrder(sourceOrderId,tenantId,targetOrderId,detailStatus);
    }

    public List<String> findUnCommentDetailIdList(String orderId) {
        return this.mapper.selectUnCommentDetailIdList(orderId);
    }

    public int updateCommentStatusByOrderId(Integer commentStatus,String orderId,List<String> odIdList,String modifyBy) {
        return this.mapper.updateCommentStatusByOrderId(orderId,odIdList,commentStatus,modifyBy);
    }

    public List<ProductOrderTenantInfo> findProductOrderTenantInfoList(String orderId) {
        List<ProductOrderTenantInfo> productOrderTenantInfos = this.mapper.selectProductOrderTenantInfoList(orderId);

        productOrderTenantInfos.stream().forEach(item->{
           BigDecimal couponDisPrice= item.getDiscountList().stream()
                    .map(ProductOrderDiscountInfo::getDiscountPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
           BigDecimal expressPrice= item.getIncrementList().stream()
                   .filter(incrementInfo -> AceDictionary.ORDER_INCREMENT_EXPRESS.equals(incrementInfo.getIncrementType()))
                    .map(ProductOrderIncrementInfo::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

           BigDecimal productPrice= item.getProductList().stream()
                    .map(product->product.getSalesPrice().multiply(new BigDecimal(product.getQuantity())))
                   .reduce(BigDecimal.ZERO,BigDecimal::add);
           Integer quantity= item.getProductList().stream()
                    .map(ProductOrderTenantProductInfo::getQuantity)
                   .reduce(0, Integer::sum);
           item.setDiscountPrice(couponDisPrice);
           item.setExpressPrice(expressPrice);
           item.setProductPrice(productPrice);
           item.setQuantity(quantity);
           if(ObjectUtils.isEmpty(item.getRemark())){
                item.setRemark("");
           }
        });
        return productOrderTenantInfos;
    }

    public int updateRefundStatusByOrderId(String orderId, List<String> odIdList,
                                           Integer refundStatus, String modifyBy) {
        return this.mapper.updateRefundStatusByOrderId(orderId,odIdList,refundStatus,modifyBy);
    }

    public int updateOrderDetailStatusByOrderId(String orderId, Integer targetStatus,
                                                 Integer sourceStatus, String modifyBy) {
        return this.mapper.updateOrderDetailStatusByOrderId(orderId,null,targetStatus,sourceStatus,modifyBy);
    }

    public int updateByRefundSuccess(String orderId,List<String> odIdList,Integer detailStatus,Integer refundStatus,String modifyBy) {
        return this.mapper.updateDetailStatusAndRefundStatus(orderId,odIdList,detailStatus,refundStatus,modifyBy);
    }
}