package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.app.entity.BizPostalAddress;
import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.mapper.BizPostalAddressMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductLabelMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizShoppingCartMapper;
import com.github.wxiaoqi.security.app.vo.invoice.InvoiceParams;
import com.github.wxiaoqi.security.app.vo.postage.PostageInfo;
import com.github.wxiaoqi.security.app.vo.product.out.CouponInfoVo;
import com.github.wxiaoqi.security.app.vo.product.out.PostageInfoVo;
import com.github.wxiaoqi.security.app.vo.productdelivery.out.ProductDeliveryData;
import com.github.wxiaoqi.security.app.vo.shopping.in.BuySpecInfoParam;
import com.github.wxiaoqi.security.app.vo.shopping.in.QueryConfirmOrderInfoIn;
import com.github.wxiaoqi.security.app.vo.shopping.in.SaveShoppingCart;
import com.github.wxiaoqi.security.app.vo.shopping.in.UpdateShoppingCart;
import com.github.wxiaoqi.security.app.vo.shopping.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 商品购物车
 *
 * @author zxl
 * @Date 2018-12-12 17:43:04
 */
@Service
public class BizShoppingCartBiz extends BusinessBiz<BizShoppingCartMapper, BizShoppingCart> {

    @Autowired
    private BizShoppingCartMapper bizShoppingCartMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BizProductLabelMapper bizProductLabelMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 保存购物车记录
     * @param param
     * @return
     */
    public ObjectRestResponse saveShoppingCart(SaveShoppingCart param){
        ObjectRestResponse msg = new ObjectRestResponse();
        //判断商品是否已经添加过
        ProductInfo shoppinginfo = bizShoppingCartMapper.selectIsCartByProductId(param.getProjectId(),BaseContextHandler.getUserID(),
                param.getProductId(),param.getSpecId());
        //查询产品所属公司,其名称
        CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(param.getProductId());
        String companyName = "";
        if(companyInfo != null){
            companyName = bizShoppingCartMapper.selectNameByCompanyId(companyInfo.getCompanyId());
            if(StringUtils.isEmpty(companyName)){
                msg.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
                msg.setMessage("商户不存在");
                return msg;
            }
        }
        if(shoppinginfo != null){
            //商品数量加一
            BizShoppingCart cart = new BizShoppingCart();
            cart.setId(shoppinginfo.getId());
            if(!companyName.equals(companyInfo.getCompanyName())){
                cart.setCompanyName(companyName);
            }
            cart.setProductNum(new BigDecimal((Integer.parseInt(shoppinginfo.getBuyNum())+1)));
            cart.setTimeStamp(new Date());
            cart.setModifyBy(BaseContextHandler.getUserID());
            cart.setModifyTime(new Date());
            if(bizShoppingCartMapper.updateByPrimaryKeySelective(cart) > 0){
                msg.setStatus(200);
                msg.setMessage("保存购物记录成功!");
                return msg;
            }else{
                msg.setStatus(201);
                msg.setMessage("保存购物记录失败!");
                return msg;
            }
        }else{
            ObjectRestResponse objectRestResponse = codeFeign.getCode("Spec","S","5","0");
            //查询产品的规格名称,以及价格
            SpecInfo specInfo = bizShoppingCartMapper.selectSpecInfoById(param.getSpecId());
            if(companyInfo != null && specInfo != null){
                BizShoppingCart cart = new BizShoppingCart();
                cart.setId(UUIDUtils.generateUuid());
                cart.setCompanyId(companyInfo.getCompanyId());
                cart.setProjectId(param.getProjectId());
                cart.setCompanyName(companyInfo.getCompanyName());
                cart.setProductId(param.getProductId());
                cart.setProductName(companyInfo.getProductName());
                cart.setProductNum(new BigDecimal(param.getBuyNum()));
                cart.setSpecId(param.getSpecId());
                cart.setSpecName(specInfo.getSpecName());
                if(objectRestResponse.getStatus() == 200){
                    cart.setSpecCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
                }
                cart.setProductPrice(new BigDecimal(specInfo.getPrice()));
                cart.setUserId(BaseContextHandler.getUserID());
                cart.setCreateBy(BaseContextHandler.getUserID());
                cart.setCreateTime(new Date());
                cart.setTimeStamp(new Date());
                cart.setAppType(param.getAppType());
                if(bizShoppingCartMapper.insertSelective(cart) < 0){
                    msg.setStatus(101);
                    msg.setMessage("保存购物记录失败!");
                    return msg;
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 编辑购物车购买数量
     * @param param
     * @return
     */
    public ObjectRestResponse updateShoppingCart(UpdateShoppingCart param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(bizShoppingCartMapper.updateShoppinfBuyNumById(param.getId(),param.getBuyNum(),BaseContextHandler.getUserID()) < 0){
            msg.setStatus(101);
            msg.setMessage("编辑购买数量失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 删除购物车记录
     * @param id
     * @return
     */
    public ObjectRestResponse delShoppingCart(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空");
            return msg;
        }
        List<String> idList= Arrays.asList(id.split(","));
        if(bizShoppingCartMapper.delShoppinfById(idList,BaseContextHandler.getUserID()) < 0){
            msg.setStatus(102);
            msg.setMessage("删除购物车记录失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询购物车信息列表
     * @return
     */
    public ObjectRestResponse getShoppingCartList(String projectId,Integer page,Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        DecimalFormat df =new  DecimalFormat("0.00");
        List<ShoppingVo> shoppingVoList = bizShoppingCartMapper.selectShoppingCartCompany(BaseContextHandler.getUserID(),projectId,startIndex,limit);
        for(ShoppingVo vo : shoppingVoList){
            String companyName = bizShoppingCartMapper.selectNameByCompanyId(vo.getCompanyId());
            List<ProductInfo> productInfoList = bizShoppingCartMapper.selectProductInfoByCompanyId(projectId,BaseContextHandler.getUserID(),vo.getCompanyId());
            for (ProductInfo info : productInfoList){
                if(companyName.equals(vo.getCompanyName())){
                    BizShoppingCart cart = new BizShoppingCart();
                    cart.setId(info.getId());
                    cart.setCompanyName(companyName);
                    cart.setTimeStamp(new Date());
                    cart.setModifyBy(BaseContextHandler.getUserID());
                    cart.setModifyTime(new Date());
                    bizShoppingCartMapper.updateByPrimaryKeySelective(cart);
                }
                info.setPrice(df.format(Double.parseDouble(info.getPrice())));
                if(info.getUnit() == null || "".equals(info.getUnit())){
                    info.setUnit("");
                }
                //商品标签
                List<String> labelList = bizProductLabelMapper.selectLabelList(info.getProductId());
                info.setLabel(labelList);
                if(info.getLowestNum() == null || info.getLowestNum() == ""){
                    info.setLowestNum("");
                }
                List<ImgInfo> imglist = new ArrayList<>();
                ImgInfo specImgInfo = new ImgInfo();
                if(info.getSpecImage() != null && info.getSpecImage() != ""){
                    specImgInfo.setUrl(info.getSpecImage());
                }else{
                    specImgInfo.setUrl(info.getProductImage());
                }

                imglist.add(specImgInfo);
                info.setSpecImageList(imglist);
            }
            //优惠券信息
            List<CouponInfoVo> couponList = bizProductMapper.getCartCouponList(vo.getCompanyId());
            List<String> couponInfo = new ArrayList<>();
            if (couponList.size() != 0 ) {
                for (CouponInfoVo coupon : couponList) {
                    couponInfo.add(coupon.getDiscountNum() == null
                            ? "满" + coupon.getMinValue() + "减" + coupon.getValue() :  coupon.getDiscountNum() + "折券");
                }
            }
            vo.setCouponList(couponInfo);
            //邮费信息
            List<PostageInfoVo> postageList = bizProductMapper.getPostageList(vo.getCompanyId());
            if (postageList == null || postageList.size() == 0) {
                postageList = new ArrayList<>();
            }else {
                vo.setPostageList(postageList);
            }
            if(productInfoList == null || productInfoList.size() ==0){
                shoppingVoList = new ArrayList<>();
            }else{
                vo.setProductInfo(productInfoList);
            }
        }
        msg.setData(shoppingVoList);
        return msg;
    }

    /**
     * 统计购物车金额
     * @param specIds
     * @param selectIsAll
     * @return
     */
    public ObjectRestResponse getShoppingStatisInfo(String projectId,String specIds,String selectIsAll){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<String> specIdList = new ArrayList<>();
        if("0".equals(selectIsAll)){
            specIdList = Arrays.asList(specIds.split(","));
        }
        ShoppingStatisInfo statisInfo = bizShoppingCartMapper.selectShoppingCartMoneyAndCount(projectId,BaseContextHandler.getUserID(), specIdList,selectIsAll);
        if(statisInfo == null){
            statisInfo = new ShoppingStatisInfo();
        }
        msg.setData(statisInfo);
        return msg;
    }

    /**
     * 查询购物车数量
     * @return
     */
    public ObjectRestResponse getShoppingCartCount(String projectId){
        ObjectRestResponse msg = new ObjectRestResponse();
        int count = bizShoppingCartMapper.selectCartCountByUserId(projectId,BaseContextHandler.getUserID());
        msg.setData(count);
        return msg;
    }


    @Autowired
    private BizCouponBiz bizCouponBiz;
    @Autowired
    private BizInvoiceBiz bizInvoiceBiz;
    @Autowired
    private BizProductDeliveryBiz bizProductDeliveryBiz;
    @Autowired
    private BizPostalAddressMapper bizPostalAddressMapper;
    public ConfirmOrderInfo getConfirmOrderInfo(QueryConfirmOrderInfoIn queryConfirmOrderInfoIn) {
        queryConfirmOrderInfoIn.check();

        List<String> specIdList = queryConfirmOrderInfoIn.getSpecList().stream()
                .map(BuySpecInfoParam::getSpecId).distinct().collect(Collectors.toList());
        List<CartTenantInfoVo> cartTenantInfoVos = bizShoppingCartMapper.selectTenantProductInfo(specIdList);
        ImmutableMap<String, BuySpecInfoParam> specMap
                = Maps.uniqueIndex(queryConfirmOrderInfoIn.getSpecList(), BuySpecInfoParam::getSpecId);

        boolean isInvoice = false;

        String cityCode = getCityCode(queryConfirmOrderInfoIn.getAddressId());

        for (CartTenantInfoVo tenantInfoVo : cartTenantInfoVos) {
            List<String> productIdList = tenantInfoVo.getProductList().stream().map(CartProductInfo::getProductId)
                    .distinct().collect(Collectors.toList());
            List<ProductDeliveryData> productDeliveryList = bizProductDeliveryBiz.findProductDeliveryList(tenantInfoVo.getTenantId(), productIdList);
            ImmutableMap<String, ProductDeliveryData> productDeliveryMap = Maps.uniqueIndex(productDeliveryList, ProductDeliveryData::getProductId);
            BigDecimal productPrice = BigDecimal.ZERO;
            for (CartProductInfo spec : tenantInfoVo.getProductList()) {
                BuySpecInfoParam buySpecInfoParam = specMap.get(spec.getSpecId());
                spec.setCartId(buySpecInfoParam.getCartId());
                spec.setQuantity(buySpecInfoParam.getQuantity());
                ProductDeliveryData productDeliveryData = productDeliveryMap.get(spec.getProductId());
                if(productDeliveryData == null
                        || !productDeliveryData.getCityCodeList()
                        .contains(cityCode)){
                    spec.setMsg("当前地址不支持配送");
                }

                productPrice = productPrice.add(spec.getPrice().multiply(new BigDecimal(spec.getQuantity())));
            }
            PostageInfo postageList = bizCouponBiz.getPostageList(tenantInfoVo.getTenantId(), productPrice);
            if("1".equals(tenantInfoVo.getIsInvoice())){
                isInvoice = true;
            }
            tenantInfoVo.setProductPrice(productPrice);
            tenantInfoVo.setExpressPrice(postageList.getPostage());
            tenantInfoVo.setPostageInfo(postageList);
        }

        ConfirmOrderInfo confirmOrderInfo = new ConfirmOrderInfo();
        confirmOrderInfo.setTenantInfoList(cartTenantInfoVos);
        if(isInvoice){
            ObjectRestResponse<InvoiceParams> defaultInvoice = bizInvoiceBiz.getDefaultInvoice();
            confirmOrderInfo.setDefaultInvoice(defaultInvoice.getData());
        }

        return confirmOrderInfo;
    }

    private String getCityCode(String addressId){
        if(StringUtils.isBlank(addressId)){
            return "";
        }
        BizPostalAddress bizPostalAddress = bizPostalAddressMapper.selectById(addressId);
        String userID = BaseContextHandler.getUserID();
        Assert.isTrue(bizPostalAddress != null && userID.equals(bizPostalAddress.getUserId())
                ,"错误的收货地址");
        if(AceDictionary.MUNICIPALITY.containsKey(bizPostalAddress.getProcCode())){
            return bizPostalAddress.getDistrictCode();
        }else{
            return bizPostalAddress.getCityCode();
        }
    }
}