package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.api.vo.store.CacheStore;
import com.github.wxiaoqi.security.api.vo.store.CacheStoreProduct;
import com.github.wxiaoqi.security.app.entity.BizProduct;
import com.github.wxiaoqi.security.app.fegin.StoreProductFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.product.out.*;
import com.github.wxiaoqi.security.app.vo.productcomment.in.QueryProductCommentListIn;
import com.github.wxiaoqi.security.app.vo.productcomment.out.ProductCommentListVo;
import com.github.wxiaoqi.security.app.vo.shopping.out.CompanyInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品表
 *
 * @author guohao
 */
@Slf4j
@Service
public class BizProductNewBiz extends BusinessBiz<BizProductMapper, BizProduct> {

    @Resource
    private BizProductMapper bizProductMapper;
    @Resource
    private BizProductLabelMapper bizProductLabelMapper;
    @Resource
    private BizProductSpecMapper bizProductSpecMapper;
    @Resource
    private BizShoppingCartMapper bizShoppingCartMapper;

    /**
     * 查询商品详情
     */
    public ObjectRestResponse<ProductInfo> getProductInfo(String id, boolean share){
        ObjectRestResponse<ProductInfo> msg = new ObjectRestResponse<>();
        ProductInfo productInfo;
        if (!share) {
            productInfo = bizProductMapper.selectProductInfoById(id,BaseContextHandler.getUserID());
        } else {
            productInfo = bizProductMapper.selectShareProductInfoById(id);
        }
        if (productInfo != null) {
            //商品信息
            if(productInfo.getProductImagetextInfo() != null){
                if(productInfo.getProductImagetextInfo().equals("<p><br data-mce-bogus=\"1\"></p>")){
                    productInfo.setProductImagetextInfo("");
                }
            }
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(productInfo.getId());
            productInfo.setLabel(labelList);
            //商品规格单价
            setProductDefaultSpecInfo(productInfo);

            productInfo.setSelectedSpec("");
            productInfo.setBuyNum("0");
            productInfo.setAddress("");
            //设置规格
            setProductSpecList(productInfo);
            //商品所属公司
            CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(id);
            if(companyInfo != null){
                productInfo.setCompanyId(companyInfo.getCompanyId());
                productInfo.setCompanyName(companyInfo.getCompanyName());
                productInfo.setIsClose(companyInfo.getIsClose());
                productInfo.setIsInvoice(companyInfo.getIsInvoice());
                productInfo.setLogoImg(companyInfo.getLogoImg());
            }
            //查询商品的用户评价
            List<ProductCommentListVo> productComment = getProductComment(id);
            productInfo.setCommentList(productComment);
            //优惠券信息
            List<CouponInfoVo> couponList = bizProductMapper.getProductCouponList(id);
            List<String> couponInfo = new ArrayList<>();
            if (couponList.size() != 0 ) {
                for (CouponInfoVo coupon : couponList) {
                    couponInfo.add(coupon.getDiscountNum() == null ? "领券" + coupon.getMinValue() + "减" + coupon.getValue() : "领取" + coupon.getDiscountNum() + "折券");
                }
            }
            productInfo.setCouponList(couponInfo);
            //邮费信息
            List<PostageInfoVo> postageList = bizProductMapper.getPostageList(productInfo.getCompanyId());
            productInfo.setPostageList(postageList);

        }
        if(productInfo == null){
            productInfo = new ProductInfo();
        }
        msg.setData(productInfo);
        return msg;
    }

    private void setProductSpecList(ProductInfo productInfo){
        List<ProductSpecInfo> productSpecInfoList = getProductSpecInfo(productInfo.getId());
        productSpecInfoList.forEach(item->{
            if(StringUtils.isEmpty(item.getSpecImage())){
                item.setSpecImage(productInfo.getProductImage());
            }
        });
//        Collections.sort(productSpecInfoList);
        productInfo.setProductSpecInfo(productSpecInfoList);
    }

    //设置商品详情默认规格信息
    private void setProductDefaultSpecInfo(ProductInfo productInfo){
        SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(productInfo.getId());
        DecimalFormat df =new  DecimalFormat("0.00");
        if(specInfo == null){
            log.warn("商品没有规格信息,productId:{}",productInfo.getId());
            return;
        }
        productInfo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
        if(specInfo.getOriginalPrice() != null){
            productInfo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
        }else{
            productInfo.setOriginalPrice("0");
        }
        if(StringUtils.isNotEmpty(specInfo.getUnit())){
            productInfo.setUnit(specInfo.getUnit());
        }else{
            productInfo.setUnit("");
        }
    }

    //获取商品规格信息
    private List<ProductSpecInfo>  getProductSpecInfo(String productId){
        //商品所属规格
        List<ProductSpecInfo> productSpecInfoList =
                bizProductSpecMapper.selectSpecListByProductId(productId);
        DecimalFormat df =new  DecimalFormat("0.00");
        for(ProductSpecInfo info : productSpecInfoList) {
            if (info.getLowestNum() == null) {
                info.setLowestNum("");
            }
            if (StringUtils.isEmpty(info.getUnit())) {
                info.setUnit("");
            }
            info.setPrice(df.format(Double.parseDouble(info.getPrice())));
        }
        //设置库存
        setProductSpecStock(productSpecInfoList);
        return productSpecInfoList;
    }

    //设置商品规格库存
//    private void setProductSpecStock(List<ProductSpecInfo> productSpecInfoList){
//        if(CollectionUtils.isEmpty(productSpecInfoList)){
//            return;
//        }
//        List<String> specIdList = productSpecInfoList
//                .stream().map(ProductSpecInfo::getId).collect(Collectors.toList());
//        try {
//            List<CacheStoreProduct> cacheStoreBatch = storeProductFegin.getCacheStoreBatch(specIdList);
//
//            if(CollectionUtils.isEmpty(cacheStoreBatch)){
//                log.error("设置商品库存失败，specIdList:{},message:{}"
//                        , JSON.toJSONString(specIdList),"未查到该商品库存信息");
//            }
//            ImmutableMap<String, CacheStoreProduct> storeMap = Maps.uniqueIndex(cacheStoreBatch, CacheStoreProduct::getSpecId);
//            for (ProductSpecInfo productSpecInfo : productSpecInfoList) {
//                CacheStoreProduct cacheStoreProduct = storeMap.get(productSpecInfo.getId());
//                if(cacheStoreProduct == null){
//                    log.error("设置商品库存失败，specId:{},message:{}"
//                            , productSpecInfo.getId(),"未查到该商品库存信息");
//                }
//                CacheStore cacheStore = cacheStoreProduct.getCacheStore();
//                productSpecInfo.setIsLimit(cacheStore.getIsLimit());
//                productSpecInfo.setStockNum(cacheStore.getStoreNum());
//            }
//        }catch (Exception e){
//            log.error("设置商品库存失败，specIdList:{},message:{}"
//                    , JSON.toJSONString(specIdList),e.getMessage());
//        }
//    }

    private Boolean setProductSpecStock(List<ProductSpecInfo> productSpecInfoList){
        Boolean sellOut = true; // 是否售罄，全部规格售罄才算售罄
//        int productCount = 0; // 剩余份数
//        String productNum = null; // 规格库存存在无上限
        if(CollectionUtils.isEmpty(productSpecInfoList)){
            return false;
        }
        List<String> specIdList = productSpecInfoList
                .stream().map(ProductSpecInfo::getId).collect(Collectors.toList());
        try {
            List<CacheStoreProduct> cacheStoreBatch = storeProductFegin.getCacheStoreBatch(specIdList);

            if(CollectionUtils.isEmpty(cacheStoreBatch)){
                log.error("设置商品库存失败，specIdList:{},message:{}"
                        , JSON.toJSONString(specIdList),"未查到该商品库存信息");
            }
            ImmutableMap<String, CacheStoreProduct> storeMap = Maps.uniqueIndex(cacheStoreBatch, CacheStoreProduct::getSpecId);
            for (ProductSpecInfo productSpecInfo : productSpecInfoList) {
                CacheStoreProduct cacheStoreProduct = storeMap.get(productSpecInfo.getId());
                if(cacheStoreProduct == null){
                    log.error("设置商品库存失败，specId:{},message:{}"
                            , productSpecInfo.getId(),"未查到该商品库存信息");
                }
                CacheStore cacheStore = cacheStoreProduct.getCacheStore();
                productSpecInfo.setIsLimit(cacheStore.getIsLimit());
                productSpecInfo.setStockNum(cacheStore.getStoreNum());
                if(!cacheStore.getIsLimit() || (cacheStore.getIsLimit() && cacheStore.getStoreNum() > 0)){
                    sellOut = false;
                }

//                if(!cacheStore.getIsLimit()){
//                    productNum = "9999";
//                }else {
//                    productCount += cacheStore.getStoreNum();
//                }
            }
//            if(StringUtils.isEmpty(productNum)){
//                productNum = productCount+"";
//            }
//            groupProductInfo.setProductNum(productNum);
        }catch (Exception e){
            log.error("设置商品库存失败，specIdList:{},message:{}"
                    , JSON.toJSONString(specIdList),e.getMessage());
        }
        return sellOut;
    }

    private List<ProductCommentListVo>  getProductComment(String productId){
        //查询商品的用户评价
        QueryProductCommentListIn queryProductCommentListIn = new QueryProductCommentListIn();
        queryProductCommentListIn.setProductId(productId);
        queryProductCommentListIn.setLimit(2);
        return bizProductCommentBiz.findProductCommentList(queryProductCommentListIn);
    }

    @Autowired
    private StoreProductFegin storeProductFegin;
    @Autowired
    private BizProductCommentBiz bizProductCommentBiz;

    /**
     *查询拼团抢购下的商品列表
     * @return
     */
    public ObjectRestResponse getGroupProductList(String project,Integer page, Integer limit,
                                                  Integer groupStatus,Integer type,List<String> cityCodeList){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
        if (page == null || page == 0) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        //结束后的疯抢，结束后再多显示7天后消失；
        String day = "7";
        List<GroupProductVo> groupProductVoList =  bizProductMapper.
                selectGroupProductList(day,project,startIndex, limit,groupStatus,type,cityCodeList);
        for(GroupProductVo group : groupProductVoList){
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(group.getId());
            if(specInfo != null){
                group.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    group.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    group.setOriginalPrice("0");
                }
                if(StringUtils.isNotEmpty(specInfo.getUnit())){
                    group.setUnit(specInfo.getUnit());
                }else{
                    group.setUnit("");
                }
            }



        }
        msg.setData(groupProductVoList);
        return msg;
    }

    /**
     * 查询拼购商品详情
     * @param id
     * @return
     */
    public ObjectRestResponse getGroupProductInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
        GroupProductInfo groupProductInfo = bizProductMapper.selectGroupProductInfo(id);
        if(groupProductInfo != null){
            //商品信息
            if(groupProductInfo.getProductImagetextInfo() != null){
                if(groupProductInfo.getProductImagetextInfo().equals("<p><br data-mce-bogus=\"1\"></p>")){
                    groupProductInfo.setProductImagetextInfo("");
                }
            }
            //参团人头像
            List<ImgInfo> imgInfos = bizProductMapper.selectGroupBuyUserPhoto(id);
            groupProductInfo.setImgList(imgInfos);
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(groupProductInfo.getId());
            groupProductInfo.setLabel(labelList);

            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(groupProductInfo.getId());
            if(specInfo != null){
                groupProductInfo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    groupProductInfo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    groupProductInfo.setOriginalPrice("0");
                }
                if(StringUtils.isNotEmpty(specInfo.getUnit())){
                    groupProductInfo.setUnit(specInfo.getUnit());
                }else{
                    groupProductInfo.setUnit("");
                }
            }
            groupProductInfo.setSelectedSpec("");
            groupProductInfo.setAddress("");

            //商品所属规格
            List<ProductSpecInfo> productSpecInfoList = bizProductSpecMapper.selectSpecListByProductId(id);
            for(ProductSpecInfo info : productSpecInfoList){

                if(info.getSpecImage() != null && info.getSpecImage() != ""){
                    info.setSpecImage(info.getSpecImage());
                }else{
                    info.setSpecImage(groupProductInfo.getProductImage());
                }
                if(StringUtils.isEmpty(info.getUnit())){
                    info.setUnit("");
                }
                info.setPrice(df.format(Double.parseDouble(info.getPrice())));
            }
            //设置库存
            setProductSpecStock(productSpecInfoList);
//            Collections.sort(productSpecInfoList);
            groupProductInfo.setProductSpecInfo(productSpecInfoList);
            //商品所属公司
            CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(id);
            if(companyInfo != null){
                groupProductInfo.setIsInvoice(companyInfo.getIsInvoice());
                groupProductInfo.setCompanyId(companyInfo.getCompanyId());
                groupProductInfo.setCompanyName(companyInfo.getCompanyName());
                groupProductInfo.setLogoImg(companyInfo.getLogoImg());
            }
            //查询商品的用户评价
            List<ProductCommentListVo> productComment = getProductComment(id);
            groupProductInfo.setCommentList(productComment);
        }
        msg.setData(groupProductInfo);
        return msg;
    }



    /**
     * 查询疯抢商品详情
     * @param id
     * @return
     */
    public ObjectRestResponse getBerserkProductInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat df =new  DecimalFormat("0.00");
        GroupProductInfo groupProductInfo = bizProductMapper.selectBerserkProductInfo(id);
        if(groupProductInfo != null){
            groupProductInfo.setCurrentTime(sdf.format(new Date()));
            //商品信息
            if(groupProductInfo.getProductImagetextInfo() != null){
                if(groupProductInfo.getProductImagetextInfo().equals("<p><br data-mce-bogus=\"1\"></p>")){
                    groupProductInfo.setProductImagetextInfo("");
                }
            }
           //查询用户是否已抢购  因为微信端 这里不需要登录所以无需判断
            if(StringUtils.isNotEmpty(BaseContextHandler.getUserID())){
                int count = bizProductMapper.selectIsSeckilByUserIdNew(BaseContextHandler.getUserID(),id);
                if(count > 0){
                    groupProductInfo.setGroupStatus("5");
                }
            }
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(groupProductInfo.getId());
            groupProductInfo.setLabel(labelList);
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(groupProductInfo.getId());
            if(specInfo != null){
                groupProductInfo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    groupProductInfo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    groupProductInfo.setOriginalPrice("0");
                }
                if(StringUtils.isNotEmpty(specInfo.getUnit())){
                    groupProductInfo.setUnit(specInfo.getUnit());
                }else{
                    groupProductInfo.setUnit("");
                }
            }
            groupProductInfo.setSelectedSpec("");
            groupProductInfo.setAddress("");

            //商品所属规格
            List<ProductSpecInfo> productSpecInfoList = bizProductSpecMapper.selectSpecListByProductId(id);
            for(ProductSpecInfo info : productSpecInfoList){

                if(info.getSpecImage() != null && info.getSpecImage() != ""){
                    info.setSpecImage(info.getSpecImage());
                }else{
                    info.setSpecImage(groupProductInfo.getProductImage());
                }
                if(StringUtils.isEmpty(info.getUnit())){
                    info.setUnit("");
                }
                info.setPrice(df.format(Double.parseDouble(info.getPrice())));
            }
            //设置库存
            Boolean sellOut = setProductSpecStock(productSpecInfoList);
            if("2".equals(groupProductInfo.getGroupStatus()) || "4".equals(groupProductInfo.getGroupStatus())){
                if(sellOut){
                    groupProductInfo.setGroupStatus("4");
                }else {
                    groupProductInfo.setGroupStatus("2");
                }
            }
//            Collections.sort(productSpecInfoList);
            groupProductInfo.setProductSpecInfo(productSpecInfoList);
            //商品所属公司
            CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(id);
            if(companyInfo != null){
                groupProductInfo.setIsInvoice(companyInfo.getIsInvoice());
                groupProductInfo.setCompanyId(companyInfo.getCompanyId());
                groupProductInfo.setCompanyName(companyInfo.getCompanyName());
                //公司logo
                groupProductInfo.setLogoImg(companyInfo.getLogoImg());
            }
            //查询商品的用户评价
            List<ProductCommentListVo> productComment = getProductComment(id);
            groupProductInfo.setCommentList(productComment);
        }
        if(groupProductInfo == null){
            groupProductInfo = new GroupProductInfo();
        }
        msg.setData(groupProductInfo);
        return msg;
    }


    /**
     * 查询商家信息
     * @param companyId
     * @return
     */
    public ObjectRestResponse<CompanyVo> getCompanyInfo(String companyId){
        CompanyVo companyVo = bizProductMapper.selectCompanyInfoById(companyId);
        return ObjectRestResponse.ok(companyVo);
    }


    public String getBusNameByPid(String productId) {
        return this.mapper.getBusNameByPid(productId);
    }
}
