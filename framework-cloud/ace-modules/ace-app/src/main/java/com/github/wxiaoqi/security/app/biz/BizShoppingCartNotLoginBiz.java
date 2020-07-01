package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.fegin.CodeFeign;
import com.github.wxiaoqi.security.app.fegin.StoreProductFegin;
import com.github.wxiaoqi.security.app.mapper.BizProductLabelMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizShoppingCartMapper;
import com.github.wxiaoqi.security.app.vo.product.out.CouponInfoVo;
import com.github.wxiaoqi.security.app.vo.product.out.PostageInfoVo;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecInfo;
import com.github.wxiaoqi.security.app.vo.shopping.in.SaveShoppingCartNotLogin;
import com.github.wxiaoqi.security.app.vo.shopping.in.UpdateShoppingCartNotLogin;
import com.github.wxiaoqi.security.app.vo.shopping.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品购物车
 *
 * @author zxl
 * @Date 2018-12-12 17:43:04
 */
@Slf4j
@Service
public class BizShoppingCartNotLoginBiz extends BusinessBiz<BizShoppingCartMapper, BizShoppingCart> {

    @Resource
    private BizShoppingCartMapper bizShoppingCartMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Resource
    private BizProductLabelMapper bizProductLabelMapper;
    @Resource
    private BizProductMapper bizProductMapper;
    @Resource
    private StoreProductFegin storeProductFegin;

    /**
     * 保存购物车记录
     * @param param
     * @return
     */
    public ObjectRestResponse saveShoppingCart(SaveShoppingCartNotLogin param){

        String userID = getUserID(param.getOpenId());
        Assert.hasLength(param.getBuyNum(),"购买数量不能为空");
        Assert.isTrue(Integer.parseInt(param.getBuyNum()) > 0,"购买数量需大于0");

        ObjectRestResponse restResponse = new ObjectRestResponse();

        SpecDataForAddCart specData = bizShoppingCartMapper.selectSpecDataById(param.getSpecId());
        if(specData == null){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("无效商品");
            return restResponse;
        }
        //判断商品是否已经添加过
        ProductInfo shoppinginfo = bizShoppingCartMapper.selectWxIsCartByProductId(param.getProjectId(),userID,
                param.getProductId(),param.getSpecId());

        if(shoppinginfo != null){
            restResponse =  additionalCartQuantity(shoppinginfo,param,userID);
        }else{
            restResponse =  saveNew(param,specData,userID);
        }
        return restResponse;
    }


    private ObjectRestResponse saveNew(SaveShoppingCartNotLogin param,SpecDataForAddCart specData,String userId){

        ObjectRestResponse restResponse = new ObjectRestResponse();
        //查询产品的规格名称,以及价格

        BizShoppingCart cart = new BizShoppingCart();
        cart.setId(UUIDUtils.generateUuid());
        cart.setCompanyId(specData.getTenantId());
        cart.setCompanyName(specData.getTenantName());
        cart.setProductId(specData.getProductId());
        cart.setProductName(specData.getProductName());
        cart.setProductNum(new BigDecimal(param.getBuyNum()));
        cart.setSpecId(specData.getSpecId());
        cart.setSpecName(specData.getSpecName());
        cart.setAppType(param.getAppType());
        cart.setOpenId(param.getOpenId());

        restResponse = codeFeign.getCode("Spec","S","5","0");
        if(restResponse.getStatus() == 200){
            cart.setSpecCode(restResponse.getData()==null?"":(String)restResponse.getData());
        }
        cart.setProductPrice(specData.getPrice());
        cart.setUserId(userId);
        cart.setCreateBy(userId);
        cart.setCreateTime(new Date());
        cart.setTimeStamp(new Date());
        cart.setModifyBy(userId);
        cart.setModifyTime(cart.getCreateTime());
        if(bizShoppingCartMapper.insertSelective(cart) < 0){
            restResponse.setStatus(101);
            restResponse.setMessage("保存购物记录失败!");
            return restResponse;
        }
        restResponse.data(1);
        return restResponse;
    }


    private ObjectRestResponse additionalCartQuantity(ProductInfo shoppinginfo,
                                                      SaveShoppingCartNotLogin param,String userId){
        ObjectRestResponse restResponse =new ObjectRestResponse();

        BizShoppingCart cart = new BizShoppingCart();
        cart.setId(shoppinginfo.getId());
        String buyNum = shoppinginfo.getBuyNum();
        BigDecimal sourceNum = new BigDecimal(shoppinginfo.getBuyNum());
        BigDecimal thisNum = new BigDecimal(param.getBuyNum());
        cart.setProductNum(sourceNum.add(thisNum));
        cart.setTimeStamp(new Date());
        cart.setModifyBy(userId);
        cart.setModifyTime(new Date());
        if(bizShoppingCartMapper.updateByPrimaryKeySelective(cart) > 0){
            restResponse.setStatus(200);
            restResponse.setMessage("保存购物记录成功!");
            return restResponse;
        }else{
            restResponse.setStatus(201);
            restResponse.setMessage("保存购物记录失败!");
            return restResponse;
        }
    }
    /**
     * 编辑购物车购买数量
     */
    public ObjectRestResponse updateShoppingCart(UpdateShoppingCartNotLogin param){
        ObjectRestResponse msg = new ObjectRestResponse();
        Assert.hasLength(param.getBuyNum(),"购买数量不能为空");
        Assert.isTrue(Integer.parseInt(param.getBuyNum()) > 0,"购买数量需大于0");
        String userID = getUserID(param.getOpenId());

        if(bizShoppingCartMapper.updateShoppinfBuyNumById(param.getId(),param.getBuyNum(),userID) < 0){
            msg.setStatus(101);
            msg.setMessage("编辑购买数量失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData(1);
        return msg;
    }

    /**
     * 删除购物车记录
     * @param id 购物车Id
     * @param openId openId
     */
    public ObjectRestResponse delShoppingCart(String id,String openId){
        Assert.hasLength(id,"id 不能为空。");
        ObjectRestResponse msg = new ObjectRestResponse();
        String userID = getUserID(openId);
        List<String> idList= Arrays.asList(id.split(","));
        if(bizShoppingCartMapper.delShoppinfById(idList,userID) < 0){
            msg.setStatus(102);
            msg.setMessage("删除购物车记录失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData(1);
        return msg;
    }


    /**
     * 查询购物车信息列表
     * @return
     */
    public ObjectRestResponse getShoppingCartList(String projectId,String openId){
        ObjectRestResponse msg = new ObjectRestResponse();
        // chunmei 通产品确认购物车不需要分页
//        if (page == null || page == 0) {
//            page = 1;
//        }
//        if (limit == null || limit ==0) {
//            limit = 10;
//        }
//        //分页
//        Integer startIndex = (page - 1) * limit;
        String userID = getUserID(openId);
        DecimalFormat df =new  DecimalFormat("0.00");
        List<ShoppingVo> shoppingVoList = bizShoppingCartMapper.selectWxShoppingCartCompany(userID,projectId);
        for(ShoppingVo vo : shoppingVoList){
            String companyName = bizShoppingCartMapper.selectNameByCompanyId(vo.getCompanyId());
            List<ProductInfo> productInfoList = bizShoppingCartMapper.selectWxProductInfoByCompanyId(projectId,userID,vo.getCompanyId());
            for (ProductInfo info : productInfoList){
                if(companyName.equals(vo.getCompanyName())){
                    BizShoppingCart cart = new BizShoppingCart();
                    cart.setId(info.getId());
                    cart.setCompanyName(companyName);
                    cart.setTimeStamp(new Date());
                    cart.setModifyBy(userID);
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
            // 设置库存信息
            setProductSpecStock(productInfoList);
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
            vo.setPostageList(postageList);
            vo.setProductInfo(productInfoList);
        }
        msg.setData(shoppingVoList);
        return msg;
    }

    //设置商品规格库存
    private void setProductSpecStock(List<ProductInfo> productInfoList){
        if(CollectionUtils.isEmpty(productInfoList)){
            return;
        }
        List<String> specIdList = productInfoList
                .stream().map(ProductInfo::getSpecId).collect(Collectors.toList());
        try {
            List<CacheStoreProduct> cacheStoreBatch = storeProductFegin.getCacheStoreBatch(specIdList);

            if(CollectionUtils.isEmpty(cacheStoreBatch)){
                log.error("设置商品库存失败，specIdList:{},message:{}"
                        , JSON.toJSONString(specIdList),"未查到该商品库存信息");
            }
            ImmutableMap<String, CacheStoreProduct> storeMap = Maps.uniqueIndex(cacheStoreBatch, CacheStoreProduct::getSpecId);
            for (ProductInfo productInfo : productInfoList) {
                CacheStoreProduct cacheStoreProduct = storeMap.get(productInfo.getSpecId());
                if(cacheStoreProduct == null){
                    log.error("设置商品库存失败，specId:{},message:{}"
                            , productInfo.getSpecId(),"未查到该商品库存信息");
                }
                CacheStore cacheStore = cacheStoreProduct.getCacheStore();
                productInfo.setIsLimit(cacheStore.getIsLimit());
                productInfo.setStockNum(cacheStore.getStoreNum());

            }
        }catch (Exception e){
            log.error("设置商品库存失败，specIdList:{},message:{}"
                    , JSON.toJSONString(specIdList),e.getMessage());
        }

    }

    /**
     * 统计购物车金额
     * @param specIds
     * @param selectIsAll
     * @return
     */
    public ObjectRestResponse getShoppingStatisInfo(String projectId,String specIds,String openId,boolean selectIsAll){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<String> specIdList = new ArrayList<>();
        if(!selectIsAll){
            Assert.hasLength(specIds,"请选择购物车商品");
            specIdList = Arrays.asList(specIds.split(","));
        }
        String userID = getUserID(openId);
        ShoppingStatisInfo statisInfo = bizShoppingCartMapper.selectWxShoppingCartMoneyAndCount(projectId,userID, specIdList,selectIsAll?"1":"0");
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
    public ObjectRestResponse getShoppingCartCount(String projectId,String openId){
        ObjectRestResponse msg = new ObjectRestResponse();
        String userID = getUserID(openId);
        int count = bizShoppingCartMapper.selectWxCartCountByUserId(projectId,userID);
        Map<String,Integer> map = new HashMap<>();
        map.put("count",count);
        msg.setData(map);
        return msg;
    }

    @Autowired
    private BizUserWechatBiz bizUserWechatBiz;

    private String getUserID(String openId){
        Assert.hasLength(openId,"openId 不能为空");
        String userID = BaseContextHandler.getUserID();
        if(StringUtils.isEmpty(userID)){
            userID= bizUserWechatBiz.getUserIdByOpenId(openId);
        }
        if(StringUtils.isEmpty(userID)){
            userID= openId;
        }
        return userID;

    }
}