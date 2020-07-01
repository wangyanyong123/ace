package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.app.biz.BizCouponBiz;
import com.github.wxiaoqi.security.app.biz.BizUserProjectBiz;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.entity.BizPostalAddress;
import com.github.wxiaoqi.security.app.mapper.BizCouponMapper;
import com.github.wxiaoqi.security.app.mapper.BizPostalAddressMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductSpecMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.coupon.UserCouponInfo;
import com.github.wxiaoqi.security.app.vo.postage.PostageInfo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecDataForCreateOrder;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: guohao
 * @create: 2020-04-19 20:39
 **/
@Component
public class ProductOrderContextHandler {

    @Resource
    private BizProductSpecMapper bizProductSpecMapper;

    @Autowired
    private BizCouponBiz bizCouponBiz;

    @Resource
    private BizCouponMapper bizCouponMapper;

    @Resource
    private BizPostalAddressMapper bizPostalAddressMapper;
    @Autowired
    private BizUserProjectBiz bizUserProjectBiz;

    public CreateOrderContext buildCreateOrderContext(BuyProductInfo buyProductInfo){
        CreateProductOrderContext createOrderContext = new CreateProductOrderContext();

        createOrderContext.setUserId(BaseContextHandler.getUserID());
        createOrderContext.setOrderType(buyProductInfo.getOrderType());
        createOrderContext.setAppType(RequestHeaderUtil.getPlatformIntValue(buyProductInfo.getSource()));

        if(StringUtils.isEmpty(buyProductInfo.getProjectId())){
                CurrentUserInfosVo currentUserInfos = bizUserProjectBiz.getCurrentUserInfos();
                if(currentUserInfos !=null){
                    buyProductInfo.setProjectId(currentUserInfos.getProjectId());
                }
        }
        createOrderContext.setProjectId(buyProductInfo.getProjectId());
        //收货人信息
        BizPostalAddress bizPostalAddress = bizPostalAddressMapper.selectById(buyProductInfo.getAddressId());
        RecipientInfo recipientInfo = new RecipientInfo();
        recipientInfo.setContactName(bizPostalAddress.getContactName());
        recipientInfo.setContactTel(bizPostalAddress.getContactTel());
        String addr = bizPostalAddress.getProcName() + bizPostalAddress.getCityName() + bizPostalAddress.getDistrictName() + bizPostalAddress.getAddr();
        recipientInfo.setAddr(addr);
        recipientInfo.setProcCode(bizPostalAddress.getProcCode());

        //直辖市设置城市范围为区的编码
        if(AceDictionary.MUNICIPALITY.containsKey(bizPostalAddress.getProcCode())){
            recipientInfo.setCityCode(bizPostalAddress.getDistrictCode());
        }else{
            recipientInfo.setCityCode(bizPostalAddress.getCityCode());
        }
        createOrderContext.setRecipientInfo(recipientInfo);
        //商户信息
        List<TenantInfo> tenantInfoList = buyProductInfo.getCompanyProductList().stream()
                .map(this::buildTenantInfo).collect(Collectors.toList());

        createOrderContext.setTenantList(tenantInfoList);
        return createOrderContext;
    }

    private TenantInfo buildTenantInfo(CompanyProduct companyProduct){

        //发票信息
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setInvoiceType(Integer.parseInt(companyProduct.getInvoiceType()));
        invoiceInfo.setInvoiceName(companyProduct.getInvoiceName());
        invoiceInfo.setDutyNum(companyProduct.getDutyNum());

        // 商品信息
        List<ProductSpecDataForCreateOrder> productSpecDataList = getProductSpecDataList(companyProduct);
        String tenantId = productSpecDataList.get(0).getTenantId();
        Assert.isTrue(tenantId.equals(companyProduct.getCompanyId()),"商户与商品不匹配");

        ImmutableMap<String, ProductSpecDataForCreateOrder> specMap = Maps.uniqueIndex(productSpecDataList, ProductSpecDataForCreateOrder::getSpecId);
        List<SkuInfo> skuInfoList = companyProduct.getSubProductList().stream().map(subProduct -> {
            ProductSpecDataForCreateOrder specData = specMap.get(subProduct.getSpecId());
            Assert.notNull(specData,"存在无效商品,"+subProduct.getSpecId());
            SkuInfo skuInfo = new SkuInfo();
            skuInfo.setQuantity(subProduct.getSubNum());
            skuInfo.setShoppingCartId(subProduct.getShoppingCartId());
            BeanUtils.copyProperties(specData,skuInfo);
            if(StringUtils.isEmpty(skuInfo.getSpecImage())){
                skuInfo.setSpecImage(specData.getProductImage());
            }
            return skuInfo;
        }).collect(Collectors.toList());
        //商品总金额
        BigDecimal productPrice = skuInfoList.stream().map(sku -> sku.getSalesPrice().multiply(BigDecimal.valueOf(sku.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //运费
        BigDecimal expressPrice = getExpressPrice(companyProduct.getCompanyId(), productPrice);
        //优惠信息
        List<DiscountInfo> discountList = getDiscountList(companyProduct, productPrice);
        BigDecimal discountPrice = discountList.stream().map(DiscountInfo::getDiscountPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        TenantInfo tenantInfo = new TenantInfo();
        tenantInfo.setTenantId(companyProduct.getCompanyId());
        tenantInfo.setTotalPrice(companyProduct.getTotalPrice());
        tenantInfo.setProductPrice(productPrice);
        tenantInfo.setExpressPrice(expressPrice);
        tenantInfo.setRemark(companyProduct.getRemark());
        tenantInfo.setInvoiceInfo(invoiceInfo);
        tenantInfo.setSkuList(skuInfoList);
        tenantInfo.setDiscountList(discountList);
        tenantInfo.setDiscountPrice(discountPrice);
        return tenantInfo;
    }

    private BigDecimal getExpressPrice(String tenantId,BigDecimal productPrice){
        PostageInfo postageInfo = bizCouponBiz.getPostageList(tenantId, productPrice);
        BigDecimal expressPrice = BigDecimal.ZERO;
        if(postageInfo!=null && postageInfo.getPostage()!=null){
            expressPrice= postageInfo.getPostage();
        }
        return  expressPrice;
    }

    private List<DiscountInfo> getDiscountList(CompanyProduct companyProduct,BigDecimal productPrice){
        List<DiscountInfo> discountInfoList = new ArrayList<>();
        DiscountInfo couponDiscountInfo = getCouponDiscountInfo(companyProduct.getCouponId(), productPrice);
        if(couponDiscountInfo != null){
            discountInfoList.add(couponDiscountInfo);
        }
        return discountInfoList;
    }

    private DiscountInfo getCouponDiscountInfo(String couponId,BigDecimal productPrice){
        if(StringUtils.isEmpty(couponId)){
            return null;
        }

        String userID = BaseContextHandler.getUserID();
        UserCouponInfo userCouponInfo = bizCouponMapper.getUserCouponInfo(couponId);
        Assert.notNull(userCouponInfo,"未知的优惠券");
        Assert.isTrue(userID.equals(userCouponInfo.getUserId()),"错误的优惠券id");
        Assert.isTrue(System.currentTimeMillis() <= userCouponInfo.getEndUseTime().getTime()
                        && System.currentTimeMillis() >= userCouponInfo.getStartUseTime().getTime()
                ,"优惠券不在使用期间内");
        Assert.isTrue(productPrice.compareTo(userCouponInfo.getMinValue() ) >= 0,
                "优惠券不满足最低消费金额，"+userCouponInfo.getMinValue());
        Assert.isTrue(AceDictionary.USER_COUPON_RECEIVED.equals(userCouponInfo.getUseStatus())
                ||AceDictionary.USER_COUPON_REFUND.equals(userCouponInfo.getUseStatus())
                ,"无效的优惠券");

        BigDecimal couponDiscountPrice;
        if("2".equals(userCouponInfo.getCouponType())){
            //折扣券
            BigDecimal resultPrice = productPrice.multiply(userCouponInfo.getDiscountNum()).setScale(2, BigDecimal.ROUND_HALF_UP);
            if(resultPrice.compareTo(userCouponInfo.getMaxValue()) > 0){
                couponDiscountPrice =  userCouponInfo.getMaxValue();
            } else{
                couponDiscountPrice = resultPrice;
            }
        }else{
            couponDiscountPrice = userCouponInfo.getFaceValue();
        }
        //如果优惠金额大于商品，优惠金额=商品金额
        couponDiscountPrice= couponDiscountPrice.compareTo(productPrice) > 0 ? productPrice:couponDiscountPrice;
        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setDiscountType(AceDictionary.ORDER_DISCOUNT_TYPE_COUPON);
        discountInfo.setDiscountPrice(couponDiscountPrice);
        discountInfo.setRelationId(couponId);
        return discountInfo;
    }


    private  List<ProductSpecDataForCreateOrder> getProductSpecDataList(CompanyProduct companyProduct){
        List<String> specIdList = companyProduct.getSubProductList().stream().map(SubProduct::getSpecId).collect(Collectors.toList());
        return bizProductSpecMapper.selectSpecDataForCreateOrder(specIdList);
    }
}
