package com.github.wxiaoqi.security.app.biz.order;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.BizProductBiz;
import com.github.wxiaoqi.security.app.biz.BizProductDeliveryBiz;
import com.github.wxiaoqi.security.app.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.app.biz.order.context.CreateOrderContext;
import com.github.wxiaoqi.security.app.biz.order.context.CreateProductOrderContext;
import com.github.wxiaoqi.security.app.biz.order.context.SkuInfo;
import com.github.wxiaoqi.security.app.biz.order.context.TenantInfo;
import com.github.wxiaoqi.security.app.biz.order.handler.ProductOrderAfterCreateHandler;
import com.github.wxiaoqi.security.app.biz.order.handler.ProductOrderContextHandler;
import com.github.wxiaoqi.security.app.biz.order.handler.ProductOrderCreateHandler;
import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizShoppingCartMapper;
import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: guohao
 * @create: 2020-04-19 10:02
 **/
@Slf4j
@Component
public class ProductOrderCreateBiz extends AbstractOrderCreateBiz{

    @Autowired
    private BizProductDeliveryBiz bizProductDeliveryBiz;
    @Autowired
    private ProductOrderContextHandler productOrderContextHandler;
      @Autowired
    private ProductOrderCreateHandler productOrderCreateHandler;
    @Autowired
    private ProductOrderAfterCreateHandler productOrderAfterCreateHandler;
    @Resource
    private BizShoppingCartMapper bizShoppingCartMapper;
    @Resource
    private BizProductOrderBiz bizProductOrderBiz;

    @Resource
    private BizProductMapper bizProductMapper;


    @Override
    protected void checkParam(BuyProductInfo buyProductInfo) {
        Assert.notNull(buyProductInfo,"参数为空");
        Assert.notNull(buyProductInfo.getOrderType(),"订单类型为空");
        Assert.hasLength(buyProductInfo.getAddressId(),"收货地址id不能为空");
//        Assert.hasLength(buyProductInfo.getContactName(),"联系人为空");
//        Assert.hasLength(buyProductInfo.getContactTel(),"联系人手机号为空");
//        Assert.hasLength(buyProductInfo.getAddr(),"联系人收货地址为空");
//        Assert.hasLength(buyProductInfo.getProcCode(),"收货地址省编码为空");
//        Assert.hasLength(buyProductInfo.getProjectId(),"所属项目为空");
        Assert.hasLength(buyProductInfo.getSource(),"来源为空");

        Assert.notEmpty(buyProductInfo.getCompanyProductList(),"商户产品信息为空");

        //判断是否为 拼团/疯抢订单，拼团/疯抢订单只有一个商品
        if(AceDictionary.ORDER_TYPE_GROUP.equals(buyProductInfo.getOrderType())
            || AceDictionary.ORDER_TYPE_BERSERK.equals(buyProductInfo.getOrderType())){
            List<CompanyProduct> companyProductList = buyProductInfo.getCompanyProductList();
            Assert.isTrue(companyProductList.size() == 1
                            && companyProductList.get(0).getSubProductList().size() ==1
                    ,AceDictionary.ORDER_TYPE.get(buyProductInfo.getOrderType())+"只能购买一个商品");
        }
        for (CompanyProduct companyProduct : buyProductInfo.getCompanyProductList()) {
            Assert.hasLength(companyProduct.getCompanyId(),"商户id为空");
            Assert.notNull(companyProduct.getTotalPrice(),"商户金额为空");
            if(StringUtils.isEmpty(companyProduct.getInvoiceType())){
                companyProduct.setInvoiceType(AceDictionary.INVOICE_TYPE_NONE.toString());
            }
          if(AceDictionary.INVOICE_TYPE_COMPANY.toString().equals(companyProduct.getInvoiceType())){
                Assert.hasLength(companyProduct.getInvoiceName(),"发票名称为空");
            }
            Assert.notEmpty(companyProduct.getSubProductList(),"商户商品信息为空");
            for (SubProduct subProduct : companyProduct.getSubProductList()) {
                Assert.hasLength(subProduct.getProductId(),"商品id为空");
                Assert.hasLength(subProduct.getSpecId(),"规格id为空");
                Assert.isTrue(subProduct.getSubNum() > 0,"购买数量应大于0");
            }

        }
        //验证用户疯抢商品是否超过购买上限
        if(AceDictionary.ORDER_TYPE_BERSERK.equals(buyProductInfo.getOrderType()) ){
            SubProduct subProduct = buyProductInfo.getCompanyProductList().get(0).getSubProductList().get(0);
            String productId = subProduct.getProductId();
            int subNum = subProduct.getSubNum();
            int count = bizProductMapper.selectIsSeckilByUserIdNew(BaseContextHandler.getUserID(), productId);
            Assert.isTrue(count==0 && subNum == 1,"疯抢商品限购一份");
        }
    }

    @Override
    protected CreateOrderContext buildContext(BuyProductInfo buyProductInfo) {
        return productOrderContextHandler.buildCreateOrderContext(buyProductInfo);
    }

    @Override
    protected void beforeCreate(CreateOrderContext createOrderContext) {
        CreateProductOrderContext context = (CreateProductOrderContext) createOrderContext;
        List<TenantInfo> tenantList = context.getTenantList();

        String orderTypeDesc = AceDictionary.ORDER_TYPE.get(((CreateProductOrderContext) createOrderContext).getOrderType());
        //判断是否为 拼团/疯抢订单，拼团/疯抢订单只有一个商品
        if(AceDictionary.ORDER_TYPE_GROUP.equals(context.getOrderType())
           || AceDictionary.ORDER_TYPE_BERSERK.equals(context.getOrderType()) ){
            Assert.isTrue(tenantList.size() == 1
                            && tenantList.get(0).getSkuList().size() ==1
                    ,orderTypeDesc+"只能购买一个商品");
            long currentTimeMillis = System.currentTimeMillis();
            SkuInfo skuInfo = tenantList.get(0).getSkuList().get(0);
            Assert.notNull(skuInfo.getBegTime(),orderTypeDesc+" 开始时间不能为空");
            Assert.notNull(skuInfo.getEndTime(),orderTypeDesc+" 结束时间不能为空");
            Assert.isTrue(currentTimeMillis >= skuInfo.getBegTime().getTime()
                            && currentTimeMillis <= skuInfo.getEndTime().getTime(),
                    "请确认"+orderTypeDesc+"商品的时间。");
        }


        List<String> shoppingCarIdList = new ArrayList<>();
        //验证商品
        for (TenantInfo tenantInfo : context.getTenantList()) {

            BigDecimal realTotalPrice = tenantInfo.getProductPrice().subtract(tenantInfo.getDiscountPrice()).add(tenantInfo.getExpressPrice());
            Assert.isTrue(realTotalPrice.compareTo(tenantInfo.getTotalPrice()) ==0 ,"订单金额有误，"+realTotalPrice);

            ImmutableMap<String, ProductDeliveryData> productDeliveryMap = getProductDeliveryMap(tenantInfo);

            for (SkuInfo skuInfo : tenantInfo.getSkuList()) {
                ProductDeliveryData productDeliveryData = productDeliveryMap.get(skuInfo.getProductId());

                Assert.isTrue(productDeliveryData != null
                                && productDeliveryData.getCityCodeList()
                                .contains(context.getRecipientInfo().getCityCode())
                        ,skuInfo.getProductName() +" 当前地址不配送");
                if(AceDictionary.ORDER_TYPE_ORDINARY.equals(context.getOrderType())){
                    //普通订单验证商品是否为拼团商品
                    Assert.isTrue(skuInfo.getBegTime()== null && skuInfo.getEndTime() == null
                            ,"存在团购/疯抢商品");
                }
                //非上架状态
                Assert.isTrue("3".equals(skuInfo.getBusStatus()),skuInfo.getProductName()+" 已下架");
//                Assert.isTrue(skuInfo.getProjectIdList().contains(context.getProjectId()),
//                        skuInfo.getProductName()+" 非本项目商品");

                if(skuInfo.getLimitNum()!= null && skuInfo.getLimitNum() > 0){
                    int purchasedCount = bizProductOrderBiz.getPurchasedCount(skuInfo.getProductId(), context.getUserId());
                    Assert.isTrue(skuInfo.getLimitNum() >= purchasedCount,"亲，您已超过该商品购买上限："+skuInfo.getLimitNum());
                }
                int quantity = skuInfo.getQuantity();
                int lowestNum = skuInfo.getLowestNum()==null?0:skuInfo.getLowestNum().intValue();
                Assert.isTrue( lowestNum <= quantity,
                        skuInfo.getProductName()+ " 最小购买数量为："+lowestNum+ skuInfo.getUnit());
                if(StringUtils.isNotEmpty(skuInfo.getShoppingCartId())){
                    shoppingCarIdList.add(skuInfo.getShoppingCartId());
                }else{
                    Assert.isTrue(CollectionUtils.isEmpty(shoppingCarIdList),"购物车id 为空");
                }

            }



            if(CollectionUtils.isNotEmpty(shoppingCarIdList)){
                List<BizShoppingCart> bizShoppingCarts = bizShoppingCartMapper.selectByIdList(shoppingCarIdList);
                for (BizShoppingCart bizShoppingCart : bizShoppingCarts) {
                    Assert.isTrue(BaseContextHandler.getUserID().equals(bizShoppingCart.getUserId()),"购物车id 有误");
                }
            }

        }
    }

    private ImmutableMap<String, ProductDeliveryData> getProductDeliveryMap(TenantInfo tenantInfo){
        List<String> productIdList = tenantInfo.getSkuList().stream().map(SkuInfo::getProductId).collect(Collectors.toList());
        List<ProductDeliveryData> productDeliveryList = bizProductDeliveryBiz.findProductDeliveryList(tenantInfo.getTenantId(), productIdList);
        return Maps.uniqueIndex(productDeliveryList, ProductDeliveryData::getProductId);
    }

    @Override
    protected BuyProductOutVo doCreate(CreateOrderContext createOrderContext) {
        CreateProductOrderContext context = (CreateProductOrderContext) createOrderContext;
        productOrderCreateHandler.createOrder(context);
        return context.getBuyProductOutVo();
    }

    @Override
    protected void afterCreate(CreateOrderContext createOrderContext) {
        productOrderAfterCreateHandler.execute(createOrderContext);
    }
}
