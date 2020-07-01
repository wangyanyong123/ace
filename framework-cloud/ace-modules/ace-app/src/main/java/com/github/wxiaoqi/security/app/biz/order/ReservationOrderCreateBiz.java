package com.github.wxiaoqi.security.app.biz.order;//package com.github.wxiaoqi.security.app.biz.order;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.api.vo.postaladdress.out.PostalAddressDeliveryOut;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.app.biz.BizReservationDeliveryBiz;
import com.github.wxiaoqi.security.app.biz.BizReservationOrderBiz;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.biz.order.handler.ReservationOrderAfterCreateHandler;
import com.github.wxiaoqi.security.app.biz.order.handler.ReservationOrderContextHandler;
import com.github.wxiaoqi.security.app.biz.order.handler.ReservationOrderCreateHandler;
import com.github.wxiaoqi.security.app.entity.BizProductSpec;
import com.github.wxiaoqi.security.app.fegin.StoreReservationFegin;
import com.github.wxiaoqi.security.app.mapper.BizPostalAddressMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductSpecMapper;
import com.github.wxiaoqi.security.app.mapper.BizReservationPersonMapper;
import com.github.wxiaoqi.security.app.reservation.dto.ReservationInfoDTO;
import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: guohao
 * @create: 2020-04-19 10:02
 **/
@Slf4j
@Component
public class ReservationOrderCreateBiz extends AbstractOrderCreateBiz{

    @Resource
    private BizPostalAddressMapper bizPostalAddressMapper;
    @Resource
    private ReservationOrderContextHandler reservationOrderContextHandler;
    @Autowired
    private ReservationOrderCreateHandler reservationOrderCreateHandler;
    @Autowired
    private ReservationOrderAfterCreateHandler reservationOrderAfterCreateHandler;

    @Autowired
    private BizReservationOrderBiz bizReservationOrderBiz;
    @Resource
    private StoreReservationFegin storeReservationFegin;
    @Resource
    private BizReservationPersonMapper bizReservationPersonMapper;
    @Resource
    private BizProductSpecMapper bizProductSpecBizMapper;
    @Resource
    private BizReservationDeliveryBiz bizReservationDeliveryBiz;

    @Value("${spring.holiday.time.begin}")
    private String springBeginTime;
    @Value("${spring.holiday.time.end}")
    private String springEndTime;


    @Override
    protected void checkParam(BuyProductInfo buyProductInfo) {
        Assert.notNull(buyProductInfo,"参数为空");
        Assert.notNull(buyProductInfo.getOrderType(),"订单类型为空");
        Assert.hasLength(buyProductInfo.getContactName(),"联系人为空");
        Assert.hasLength(buyProductInfo.getContactTel(),"联系人手机号为空");
        Assert.hasLength(buyProductInfo.getAddr(),"联系人收货地址为空");
        Assert.hasLength(buyProductInfo.getProcCode(),"收货地址省编码为空");
//        Assert.hasLength(buyProductInfo.getProjectId(),"所属项目为空");
        Assert.hasLength(buyProductInfo.getSource(),"来源为空");
        Assert.hasLength(buyProductInfo.getReservationTime(),"预约服务时间为空");


        Date reservationDate = null;
        Date reservationTime = null;
        Date chunjieStartDate = null;
        Date chunjieEndDate = null;

        try {
            reservationDate = DateUtils.parseDateTime(buyProductInfo.getReservationTime(), DateUtils.DATETIME_HHMM_FORMAT);
            reservationTime = DateUtils.getTime(reservationDate);
            chunjieStartDate = DateUtils.parseDateTime(springBeginTime, DateUtils.DATETIME_HHMM_FORMAT);
            chunjieEndDate = DateUtils.parseDateTime(springEndTime, DateUtils.DATETIME_HHMM_FORMAT);
        } catch (ParseException e) {
            throw new BusinessException("输入的预约服务时间格式有误!");
        }

        //春节打烊
        boolean flag = reservationDate.getTime() > chunjieStartDate.getTime() && reservationDate.getTime() <= chunjieEndDate.getTime();
        Assert.isTrue(!flag,"春节休息，服务打烊~\n"+"2月6日开门营业，欢迎预约！");

        Assert.notEmpty(buyProductInfo.getCompanyProductList(),"商户产品信息为空");
        for (CompanyProduct companyProduct : buyProductInfo.getCompanyProductList()) {
            Assert.hasLength(companyProduct.getCompanyId(),"商户id为空");
            Assert.notNull(companyProduct.getTotalPrice(),"商户金额为空");
            Assert.hasLength(companyProduct.getInvoiceType(),"发票类型为空");
            if(!"0".equals(companyProduct.getInvoiceType())){
                Assert.hasLength(companyProduct.getInvoiceName(),"发票名称为空");
            }
            Assert.notEmpty(companyProduct.getSubProductList(),"商户商品信息为空");
            for (SubProduct subProduct : companyProduct.getSubProductList()) {
                Assert.hasLength(subProduct.getProductId(),"商品id为空");
                Assert.hasLength(subProduct.getSpecId(),"规格id为空");
                Assert.isTrue(subProduct.getSubNum() > 0,"购买数量应大于0");
                com.github.wxiaoqi.security.app.vo.reservation.out.ReservationInfo reservationInfo = bizReservationPersonMapper.selectShareNewReservationInfoById(subProduct.getProductId());
                Assert.isTrue(!ObjectUtils.isEmpty(reservationInfo),"商品信息无效");
                BizProductSpec bizProductSpec = bizProductSpecBizMapper.selectByPrimaryKey(subProduct.getSpecId());
                Assert.isTrue(!ObjectUtils.isEmpty(bizProductSpec),"规格信息无效");
                if(!ObjectUtils.isEmpty(bizProductSpec.getLowestNum()) && bizProductSpec.getLowestNum().compareTo(BigDecimal.ZERO) > 0){
                    Assert.isTrue(BigDecimal.valueOf(subProduct.getSubNum()).compareTo(bizProductSpec.getLowestNum()) > -1 ,"规格最小购买数量为"+bizProductSpec.getLowestNum()+bizProductSpec.getUnit());
                }

                try {
                    //判断是否小于当前时间+24小时
                    flag = reservationDate.getTime()< DateUtils.addHours(new Date(),AceDictionary.RESERVATION_HOW_MANY_HOURS_LATER).getTime();
                    System.out.printf("只能预约24小时以后的时间"+flag);
                    Assert.isTrue(!flag,"只能预约24小时以后的时间");
                    String months = reservationInfo.getDataScopeVal();
                    if(StringUtils.isNotEmpty(months)){
                        Date maxTime = DateUtils.addMonths(new Date(),Integer.parseInt(months));
                        flag = reservationDate.getTime()> maxTime.getTime();
                        System.out.printf("只能预约"+months+"个月时以内的时间"+flag);
                        Assert.isTrue(!flag,"只能预约"+months+"个月时以内的时间");
                    }

                    Date amStartTime = DateUtils.getTime(reservationInfo.getForenoonStartTime());
                    Date amEndTime = DateUtils.getTime(reservationInfo.getForenoonEndTime());
                    Date pmStartTime = DateUtils.getTime(reservationInfo.getAfternoonStartTime());
                    Date pmEndTime = DateUtils.getTime(reservationInfo.getAfternoonEndTime());

                    flag = (!DateUtils.belongCalendar(reservationTime,amStartTime,amEndTime)) && (!DateUtils.belongCalendar(reservationTime,pmStartTime,pmEndTime));

                    System.out.printf("预约时间段：上午："+reservationInfo.getForenoonStartTime()+"~"+reservationInfo.getForenoonEndTime()
                            +"，下午："+reservationInfo.getAfternoonStartTime()+"~"+reservationInfo.getAfternoonEndTime()+":"+flag);
                    Assert.isTrue(!flag,"预约时间段：上午："+reservationInfo.getForenoonStartTime()+"~"+reservationInfo.getForenoonEndTime()
                            +"，下午："+reservationInfo.getAfternoonStartTime()+"~"+reservationInfo.getAfternoonEndTime());



                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    protected CreateReservationOrderContext buildContext(BuyProductInfo buyProductInfo) {
        return reservationOrderContextHandler.buildCreateOrderContext(buyProductInfo);
    }

    @Override
    protected void beforeCreate(CreateOrderContext createOrderContext) {
        CreateReservationOrderContext createReservationOrderContext = (CreateReservationOrderContext) createOrderContext;
        //验证商品
        ReservationTenantInfo reservationTenantInfo = createReservationOrderContext.getReservationTenantInfo();

        // 判断配送地址
        //获取预约配送范围
        String productId = reservationTenantInfo.getReservationInfo().getProductId();
        String cityCode = createReservationOrderContext.getRecipientInfo().getCityCode();

        ProductDeliveryData deliveryData = getDeliveryData(reservationTenantInfo.getTenantId(), productId);
        Assert.isTrue(deliveryData!= null && deliveryData.getCityCodeList().contains(cityCode),
                "当前地址不配送");

        // 商品金额-优惠金额=实际支付金额
        BigDecimal realTotalPrice = reservationTenantInfo.getProductPrice().subtract(reservationTenantInfo.getDiscountPrice());
        Assert.isTrue(realTotalPrice.compareTo(reservationTenantInfo.getTotalPrice()) ==0 ,"订单金额有误");
        ReservationInfo reservationInfo = reservationTenantInfo.getReservationInfo();
        //非上架状态
        Assert.isTrue("3".equals(reservationInfo.getBusStatus()),reservationInfo.getProductName()+" 已下架");
        //预约增加配送范围后，无需验证项目
        //        Assert.isTrue(reservationInfo.getProjectIdList().contains(createReservationOrderContext.getProjectId()),
//                reservationInfo.getProductName()+" 非本项目商品");
//        // 获取商品规则信息
        ReservationInfoDTO reservationInfoDTO = bizReservationOrderBiz.selectReservationInfo(reservationInfo.getProductId(), BaseContextHandler.getUserID());
        if(reservationInfoDTO.getLimitNum() != -1){
            Assert.isTrue( reservationInfoDTO.getLimitNum() >= (reservationInfoDTO.getBoughtNum() + reservationInfo.getQuantity()),
                    reservationInfo.getProductName()+ "限制购买数量为："+reservationInfoDTO.getLimitNum()+ reservationInfo.getUnit());
        }

    }

    private ProductDeliveryData getDeliveryData(String tenantId,String productId){
        List<ProductDeliveryData> reservationDeliveryList = bizReservationDeliveryBiz
                .findReservationDeliveryList(tenantId, Collections.singletonList(productId));
        if(CollectionUtils.isNotEmpty(reservationDeliveryList)){
            return reservationDeliveryList.get(0);
        }else{
            return null;
        }

    }

    @Override
    protected BuyProductOutVo doCreate(CreateOrderContext createOrderContext) {
        CreateReservationOrderContext createReservationOrderContext = (CreateReservationOrderContext) createOrderContext;
        reservationOrderCreateHandler.createOrder(createReservationOrderContext);
        return createOrderContext.getBuyProductOutVo();
    }

    @Override
    protected void afterCreate(CreateOrderContext createOrderContext) {
        CreateReservationOrderContext createReservationOrderContext = (CreateReservationOrderContext) createOrderContext;
        reservationOrderAfterCreateHandler.execute(createReservationOrderContext);
    }

    public ObjectRestResponse reduceStock(CreateOrderContext createOrderContext){
        CreateReservationOrderContext createReservationOrderContext = (CreateReservationOrderContext) createOrderContext;
        ReservationInfo reservationInfo = createReservationOrderContext.getReservationTenantInfo().getReservationInfo();
        ObjectRestResponse objectRestResponse = this.saleStore(createReservationOrderContext.getOrderId(),reservationInfo.getQuantity(),reservationInfo.getSpecId(),createReservationOrderContext.getReservationTenantInfo().getReservationTime());
        return objectRestResponse;
    }

    public ObjectRestResponse backStock(CreateOrderContext createOrderContext){
        CreateReservationOrderContext createReservationOrderContext = (CreateReservationOrderContext) createOrderContext;
        ReservationInfo reservationInfo = createReservationOrderContext.getReservationTenantInfo().getReservationInfo();
        ObjectRestResponse objectRestResponse = this.backStore(createReservationOrderContext.getOrderId(),reservationInfo.getQuantity(),reservationInfo.getSpecId(),createReservationOrderContext.getReservationTenantInfo().getReservationTime());
        return objectRestResponse;
    }

    // 销售出库
    private ObjectRestResponse saleStore(String orderId,Integer quantity,String SpecId,Date reservationTime){
        ReservationStore reservationStore = new ReservationStore();
        reservationStore.setAccessNum(quantity * -1);
        reservationStore.setCreateBy(BaseContextHandler.getUserID());
        reservationStore.setOrderId(orderId);
        reservationStore.setSpecId(SpecId);
        reservationStore.setReservationTime(reservationTime);
        return storeReservationFegin.sale(reservationStore);
    }


    // 取消入库
    private ObjectRestResponse backStore(String orderId,Integer quantity,String SpecId,Date reservationTime){
        ReservationStore reservationStore = new ReservationStore();
        reservationStore.setAccessNum(quantity);
        reservationStore.setCreateBy(BaseContextHandler.getUserID());
        reservationStore.setOrderId(orderId);
        reservationStore.setSpecId(SpecId);
        reservationStore.setReservationTime(reservationTime);
        return storeReservationFegin.cancel(reservationStore);
    }
}
