package com.github.wxiaoqi.security.app.biz.order.handler;//package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.app.biz.BizUserProjectBiz;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.entity.BizPostalAddress;
import com.github.wxiaoqi.security.app.mapper.BizCouponMapper;
import com.github.wxiaoqi.security.app.mapper.BizPostalAddressMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductSpecMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.coupon.UserCouponInfo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecDataForCreateOrder;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: guohao
 * @create: 2020-04-19 20:39
 **/
@Slf4j
@Component
public class ReservationOrderContextHandler {

    @Resource
    private BizProductSpecMapper bizProductSpecMapper;
    @Resource
    private BizCouponMapper bizCouponMapper;
    @Resource
    private BizPostalAddressMapper bizPostalAddressMapper;
    @Autowired
    private BizUserProjectBiz bizUserProjectBiz;

    public CreateReservationOrderContext buildCreateOrderContext(BuyProductInfo buyProductInfo){
        CreateReservationOrderContext createReservationOrderContext = new CreateReservationOrderContext();

        createReservationOrderContext.setOrderType(buyProductInfo.getOrderType());
        createReservationOrderContext.setAppType(RequestHeaderUtil.getPlatformIntValue(buyProductInfo.getSource()));
        if(org.springframework.util.StringUtils.isEmpty(buyProductInfo.getProjectId())){
            CurrentUserInfosVo currentUserInfos = bizUserProjectBiz.getCurrentUserInfos();
            if(currentUserInfos !=null){
                buyProductInfo.setProjectId(currentUserInfos.getProjectId());
            }
        }
        createReservationOrderContext.setProjectId(buyProductInfo.getProjectId());
        //收货人信息
        BizPostalAddress bizPostalAddress = bizPostalAddressMapper.selectById(buyProductInfo.getAddressId());
        RecipientInfo recipientInfo = new RecipientInfo();
        recipientInfo.setContactName(buyProductInfo.getContactName());
        recipientInfo.setContactTel(buyProductInfo.getContactTel());
        String addr = bizPostalAddress.getProcName() + bizPostalAddress.getCityName() + bizPostalAddress.getDistrictName() + bizPostalAddress.getAddr();
        recipientInfo.setAddr(addr);
        recipientInfo.setProcCode(bizPostalAddress.getProcCode());
        //直辖市设置城市范围为区的编码
        if(AceDictionary.MUNICIPALITY.containsKey(bizPostalAddress.getProcCode())){
            recipientInfo.setCityCode(bizPostalAddress.getDistrictCode());
        }else{
            recipientInfo.setCityCode(bizPostalAddress.getCityCode());
        }

        createReservationOrderContext.setRecipientInfo(recipientInfo);
        //商户信息
        ReservationTenantInfo reservationTenantInfo =  buildTenantInfo(buyProductInfo.getCompanyProductList().get(0),buyProductInfo.getReservationTime());
        createReservationOrderContext.setReservationTenantInfo(reservationTenantInfo);
        // 订单Id
        createReservationOrderContext.setOrderId(UUIDUtils.generateUuid());
        // 创建时间
        createReservationOrderContext.setCreateTime(new Date());
        return createReservationOrderContext;
    }

    private ReservationTenantInfo buildTenantInfo(CompanyProduct companyProduct,String reservationTime){

        //发票信息
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setInvoiceType(Integer.parseInt(companyProduct.getInvoiceType()));
        invoiceInfo.setInvoiceName(companyProduct.getInvoiceName());
        invoiceInfo.setDutyNum(companyProduct.getDutyNum());

        // 商品信息
        List<ProductSpecDataForCreateOrder> productSpecDataList = getProductSpecDataList(companyProduct);
        ImmutableMap<String, ProductSpecDataForCreateOrder> specMap = Maps.uniqueIndex(productSpecDataList, ProductSpecDataForCreateOrder::getSpecId);
        ReservationInfo reservationInfo = companyProduct.getSubProductList().stream().map(subProduct -> {
            ProductSpecDataForCreateOrder specData = specMap.get(subProduct.getSpecId());
            Assert.notNull(specData,"存在无效商品,"+subProduct.getSpecId());
            ReservationInfo reservation = new ReservationInfo();
            reservation.setQuantity(subProduct.getSubNum());

            BeanUtils.copyProperties(specData,reservation);
            if(StringUtils.isEmpty(reservation.getSpecImage())){
                reservation.setSpecImage(specData.getProductImage());
            }
            return reservation;
        }).collect(Collectors.toList()).get(0);

        //商品总金额
        BigDecimal productPrice = reservationInfo.getSalesPrice().multiply(BigDecimal.valueOf(reservationInfo.getQuantity()));




        ReservationTenantInfo reservationTenantInfo = new ReservationTenantInfo();
        reservationTenantInfo.setDiscountPrice(BigDecimal.ZERO);
        //优惠信息
        if(StringUtils.isNotEmpty(companyProduct.getCouponId())){
            DiscountInfo discountInfo = getDiscountList(companyProduct, productPrice);
            if(!ObjectUtils.isEmpty(discountInfo)){
                BigDecimal discountPrice = ObjectUtils.isEmpty(discountInfo.getDiscountPrice())?BigDecimal.ZERO:discountInfo.getDiscountPrice();
                reservationTenantInfo.setDiscountPrice(discountPrice);
                reservationTenantInfo.setDiscountInfo(discountInfo);
            }


        }


        reservationTenantInfo.setTenantId(companyProduct.getCompanyId());
        reservationTenantInfo.setTotalPrice(companyProduct.getTotalPrice());
        reservationTenantInfo.setProductPrice(productPrice);

        //期望服务时间
        if(com.github.wxiaoqi.security.common.util.StringUtils.isNotEmpty(reservationTime)){
            try {
                Date date = DateUtils.parseDateTime(reservationTime, "yyyy-MM-dd HH:mm");
                //判断是否小于当前时间
                if(date.getTime()< DateTimeUtil.getLocalTime().getTime()){
                    reservationTenantInfo.setReservationTime(new Date());
                }else{
                    reservationTenantInfo.setReservationTime(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        reservationTenantInfo.setRemark(companyProduct.getRemark());
        reservationTenantInfo.setInvoiceInfo(invoiceInfo);
        reservationTenantInfo.setReservationInfo(reservationInfo);

        return reservationTenantInfo;
    }

    /**
     *
     * @param companyProduct
     * @param productPrice
     * @return
     */
    private DiscountInfo getDiscountList(CompanyProduct companyProduct,BigDecimal productPrice){
        DiscountInfo couponDiscountInfo = getCouponDiscountInfo(companyProduct.getCouponId(), productPrice);
        return couponDiscountInfo;
    }

    private DiscountInfo getCouponDiscountInfo(String couponId,BigDecimal productPrice){
        if(StringUtils.isEmpty(couponId)){
            return null;
        }

        String userID = BaseContextHandler.getUserID();
        log.error("服务订单下单时获取的用户ID:{}",userID);
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
            }else{
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

    public static void main(String[] args) {
        double s = 9.5;
        System.out.printf(BigDecimal.valueOf(9.5).toString());
    }

    private  List<ProductSpecDataForCreateOrder> getProductSpecDataList(CompanyProduct companyProduct){
        List<String> specIdList = companyProduct.getSubProductList().stream().map(SubProduct::getSpecId).collect(Collectors.toList());
        return bizProductSpecMapper.selectReservationSpecDataForCreateOrder(specIdList);
    }
}
