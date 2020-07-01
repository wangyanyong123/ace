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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品表
 *
 * @author zxl
 * @Date 2018-12-10 16:38:03
 */
@Slf4j
@Service
public class BizProductBiz extends BusinessBiz<BizProductMapper, BizProduct> {

    @Resource
    private BizBusinessClassifyMapper bizBusinessClassifyMapper;
    @Resource
    private BizProductMapper bizProductMapper;
    @Resource
    private BizProductLabelMapper bizProductLabelMapper;
    @Resource
    private BizProductSpecMapper bizProductSpecMapper;
    @Resource
    private BizShoppingCartMapper bizShoppingCartMapper;


    /**
     * 查询优选商品下的商品分类列表
     * @return
     */
    public ObjectRestResponse getClassifyList(String busId){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<ClassifyVo> classifyVoList = bizBusinessClassifyMapper.selectClassifyList(busId);
        if(classifyVoList == null || classifyVoList.size() == 0){
            classifyVoList = new ArrayList<>();
        }
        msg.setData(classifyVoList);
        return msg;
    }


    /**
     * 查询商品分类下的上架商品
     * @param classifyId
     * @return
     */
    public ObjectRestResponse getProductList(String projectId, String classifyId,List<String> cityCodeList,
                                             Integer page, Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
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
        List<ProductVo> productVoList = bizProductMapper.selectProductListByClassifyId(projectId, classifyId,
                cityCodeList,startIndex,limit);
        //更新过期优惠券状态
        if (productVoList.size() != 0){
            List<String> productList = new ArrayList<>();
            for (ProductVo productVo : productVoList) {
                productList.add(productVo.getId());
            }
            List<String> couponIds = bizProductMapper.selectCouponIds(productList);
            if (couponIds.size() != 0) {
                bizProductMapper.updateCouponStatusByProduct(couponIds);
            }
        }
        for(ProductVo vo : productVoList) {
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(vo.getId());
            vo.setLabel(labelList);
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(vo.getProductImage());
            list.add(imgInfo);
            vo.setProductImageList(list);
            //商品规格单价
           SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(vo.getId());
           if(specInfo != null){
               vo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
               if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
                   vo.setUnit(specInfo.getUnit());
               }else{
                   vo.setUnit("");
               }
           }

            //商品优惠券
            CouponInfoVo coupon = bizProductMapper.getProductCoupon(vo.getId());
            if (coupon != null) {
                vo.setCoupon(coupon.getDiscountNum() == null ? "领券满"+coupon.getMinValue()+"减"+coupon.getValue(): "领取"+coupon.getDiscountNum()+"折券");
            }
        }
        if(productVoList == null || productVoList.size() == 0){
            productVoList = new ArrayList<>();
        }
        msg.setData(productVoList);
        return msg;
    }


    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public ObjectRestResponse getProductInfo(String id, boolean share){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
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
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(productInfo.getProductImage());
            list.add(imgInfo);
            productInfo.setProductImageList(list);
            //商品精选图片
            List<ImgInfo> selectList = new ArrayList<>();
            ImgInfo selectLImgInfo = new ImgInfo();
            if(productInfo.getSelectionImage() != null){
                String[] selectLImgs = productInfo.getSelectionImage().split(",");
                for(String url : selectLImgs){
                    selectLImgInfo.setUrl(url);
                    selectList.add(selectLImgInfo);
                }
                productInfo.setSelectionImageList(selectList);
            }
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(productInfo.getId());
            if(specInfo != null){
                productInfo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    productInfo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    productInfo.setOriginalPrice("0");
                }
                if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
                    productInfo.setUnit(specInfo.getUnit());
                }else{
                    productInfo.setUnit("");
                }
            }
            productInfo.setSelectedSpec("");
            productInfo.setBuyNum("0");
            productInfo.setAddress("");

            //商品所属规格
            List<ProductSpecInfo> productSpecInfoList = bizProductSpecMapper.selectSpecListByProductId(id);
            for(ProductSpecInfo info : productSpecInfoList){
                List<ImgInfo> imglist = new ArrayList<>();
                ImgInfo specImgInfo = new ImgInfo();
                if(info.getSpecImage() != null && info.getSpecImage() != ""){
                    specImgInfo.setUrl(info.getSpecImage());
                }else{
                    specImgInfo.setUrl(productInfo.getProductImage());
                }
                if(info.getLowestNum() == null || info.getLowestNum() == ""){
                    info.setLowestNum("");
                }
                if(specInfo.getUnit() == null || "".equals(specInfo.getUnit())){
                    info.setUnit("");
                }
                imglist.add(specImgInfo);
                info.setSpecImageList(imglist);
                info.setPrice(df.format(Double.parseDouble(info.getPrice())));
            }
            //设置库存
            setProductSpecStock(productSpecInfoList);
            productInfo.setProductSpecInfo(productSpecInfoList);
            //商品所属公司
            CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(id);
            if(companyInfo != null){
                productInfo.setCompanyId(companyInfo.getCompanyId());
                productInfo.setCompanyName(companyInfo.getCompanyName());
                //公司logo
                List<ImgInfo> plist = new ArrayList<>();
                ImgInfo pInfo = new ImgInfo();
                pInfo.setUrl(companyInfo.getLogoImg());
                plist.add(pInfo);
                productInfo.setLogoImgList(plist);
                productInfo.setLogoImg(companyInfo.getLogoImg());
                productInfo.setIsClose(companyInfo.getIsClose());
                productInfo.setIsInvoice(companyInfo.getIsInvoice());
            }
            //查询商品的用户评价
            setProductComment(productInfo);
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

    @Autowired
    private StoreProductFegin storeProductFegin;

//    //设置商品规格库存
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
//
//            }
//        }catch (Exception e){
//            log.error("设置商品库存失败，specIdList:{},message:{}"
//                    , JSON.toJSONString(specIdList),e.getMessage());
//        }
//
//    }

    @Autowired
    private BizProductCommentBiz bizProductCommentBiz;
    private void  setProductComment(ProductInfo productInfo){
        //查询商品的用户评价
        QueryProductCommentListIn queryProductCommentListIn = new QueryProductCommentListIn();
        queryProductCommentListIn.setProductId(productInfo.getId());
        queryProductCommentListIn.setLimit(2);
        List<ProductCommentListVo> productCommentList =
                bizProductCommentBiz.findProductCommentList(queryProductCommentListIn);
        productInfo.setCommentList(productCommentList);
    }
    private void  setProductCommentOld(ProductInfo productInfo){
        //查询商品的用户评价
        List<UserCommentVo> userCommentList = bizProductMapper.selectProductCommentDetail(productInfo.getId(),0,2);
        for (UserCommentVo commentVo : userCommentList){
            //用户头像
            List<ImgInfo> listc = new ArrayList<>();
            ImgInfo imgInfoc = new ImgInfo();
            imgInfoc.setUrl(commentVo.getProfilePhoto());
            listc.add(imgInfoc);
            commentVo.setProfilePhotoList(listc);
            //评价图片
            List<ImgInfo> listvo = new ArrayList<>();
            if(commentVo.getImgUrl() != null){
                String[] imgUrlList = commentVo.getImgUrl().split(",");
                for (String url : imgUrlList){
                    ImgInfo imgInfovo = new ImgInfo();
                    imgInfovo.setUrl(url);
                    listvo.add(imgInfovo);
                }
            }
            commentVo.setImgUrlList(listvo);
        }
        if(userCommentList == null || userCommentList.size() == 0){
            userCommentList = new ArrayList<>();
        }
        productInfo.setUserCommentList(userCommentList);
    }
    /**
     *查询拼团抢购下的商品列表
     * @return
     */
    public ObjectRestResponse getGroupProductList(String project,Integer page, Integer limit,Integer groupStatus,Integer type){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
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
        //结束后的疯抢，结束后再多显示7天后消失；
        String day = "7";
        List<GroupProductVo> groupProductVoList =  bizProductMapper.selectGroupProductList(day,project,startIndex, limit,groupStatus,type,null);
        for(GroupProductVo group : groupProductVoList){
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(group.getProductImage());
            list.add(imgInfo);
            group.setProductImageList(list);
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(group.getId());
            if(specInfo != null){
                group.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    group.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    group.setOriginalPrice("0");
                }
                if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
                    group.setUnit(specInfo.getUnit());
                }else{
                    group.setUnit("");
                }
            }


            //商品所属规格
            List<ProductSpecInfo> productSpecInfoList = bizProductSpecMapper.selectSpecListByProductId(group.getId());

            //设置库存
            Boolean sellOut = setProductSpecStock(productSpecInfoList);

            if("2".equals(group.getGroupStatus()) || "4".equals(group.getGroupStatus())){
                if(sellOut){
                    group.setGroupStatus("4");
                }else {
                    group.setGroupStatus("2");
                }

            }
        }
        if(groupProductVoList == null || groupProductVoList.size() == 0){
            groupProductVoList = new ArrayList<>();
        }
        msg.setData(groupProductVoList);
        return msg;
    }


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
//                    productNum = "无上限";
//                }else {
//                    productCount += cacheStore.getStoreNum();
//                }
            }
//            if(StringUtils.isEmpty(productNum)){
//                productNum = productCount+"";
//            }
//            groupProductVo.setProductNum(productNum);
        }catch (Exception e){
            log.error("设置商品库存失败，specIdList:{},message:{}"
                    , JSON.toJSONString(specIdList),e.getMessage());
        }
        return sellOut;
    }

    /**
     * 查询拼购商品详情
     * @param id
     * @return
     */
    public ObjectRestResponse getGroupProductInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat df =new  DecimalFormat("0.00");
        GroupProductInfo groupProductInfo = bizProductMapper.selectGroupProductInfo(id);
        if(groupProductInfo != null){
            //groupProductInfo.setCurrentTime(sdf.format(new Date()));
            //商品信息
            if(groupProductInfo.getProductImagetextInfo() != null){
                if(groupProductInfo.getProductImagetextInfo().equals("<p><br data-mce-bogus=\"1\"></p>")){
                    groupProductInfo.setProductImagetextInfo("");
                }
            }
            //参团人头像
            List<ImgInfo>  listu = new ArrayList<>();
            List<ImgInfo> imgInfoList = bizProductMapper.selectGroupBuyPhoto(id);
            for(ImgInfo url : imgInfoList){
                if(url != null){
                    listu.add(url);
                }
            }
            groupProductInfo.setImgList(listu);
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(groupProductInfo.getId());
            groupProductInfo.setLabel(labelList);
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(groupProductInfo.getProductImage());
            list.add(imgInfo);
            groupProductInfo.setProductImageList(list);
            //商品精选图片
            List<ImgInfo> selectList = new ArrayList<>();
            ImgInfo selectLImgInfo = new ImgInfo();
            if(groupProductInfo.getSelectionImage() != null){
                String[] selectLImgs = groupProductInfo.getSelectionImage().split(",");
                for(String url : selectLImgs){
                    selectLImgInfo.setUrl(url);
                    selectList.add(selectLImgInfo);
                }
                groupProductInfo.setSelectionImageList(selectList);
            }
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(groupProductInfo.getId());
            if(specInfo != null){
                groupProductInfo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    groupProductInfo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    groupProductInfo.setOriginalPrice("0");
                }
                if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
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
                List<ImgInfo> imglist = new ArrayList<>();
                ImgInfo specImgInfo = new ImgInfo();
                if(info.getSpecImage() != null && info.getSpecImage() != ""){
                    specImgInfo.setUrl(info.getSpecImage());
                }else{
                    specImgInfo.setUrl(groupProductInfo.getProductImage());
                }
                if(specInfo.getUnit() == null || "".equals(specInfo.getUnit())){
                    info.setUnit("");
                }
                imglist.add(specImgInfo);
                info.setSpecImageList(imglist);
                info.setPrice(df.format(Double.parseDouble(info.getPrice())));
            }
            groupProductInfo.setProductSpecInfo(productSpecInfoList);
            //商品所属公司
            CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(id);
            if(companyInfo != null){
                groupProductInfo.setIsInvoice(companyInfo.getIsInvoice());
                groupProductInfo.setCompanyId(companyInfo.getCompanyId());
                groupProductInfo.setCompanyName(companyInfo.getCompanyName());
                //公司logo
                List<ImgInfo> plist = new ArrayList<>();
                ImgInfo pInfo = new ImgInfo();
                pInfo.setUrl(companyInfo.getLogoImg());
                plist.add(pInfo);
                groupProductInfo.setLogoImgList(plist);
                groupProductInfo.setLogoImg(companyInfo.getLogoImg());
            }
            //查询商品的用户评价
            List<UserCommentVo> userCommentList = bizProductMapper.selectProductCommentDetail(id,0,2);
            for (UserCommentVo commentVo : userCommentList){
                //用户头像
                List<ImgInfo> listc = new ArrayList<>();
                ImgInfo imgInfoc = new ImgInfo();
                imgInfoc.setUrl(commentVo.getProfilePhoto());
                listc.add(imgInfoc);
                commentVo.setProfilePhotoList(listc);
                //评价图片
                List<ImgInfo> listvo = new ArrayList<>();
                if(commentVo.getImgUrl() != null){
                    String[] imgUrlList = commentVo.getImgUrl().split(",");
                    for (String url : imgUrlList){
                        ImgInfo imgInfovo = new ImgInfo();
                        imgInfovo.setUrl(url);
                        listvo.add(imgInfovo);
                    }
                }
                commentVo.setImgUrlList(listvo);
            }
            if(userCommentList == null || userCommentList.size() == 0){
                userCommentList = new ArrayList<>();
            }
            groupProductInfo.setUserCommentList(userCommentList);
        }
        if(groupProductInfo == null){
            groupProductInfo = new GroupProductInfo();
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
           /* //参团人头像
            List<ImgInfo>  listu = new ArrayList<>();
            List<ImgInfo> imgInfoList = bizProductMapper.selectGroupBuyPhoto(id);
            for(ImgInfo url : imgInfoList){
                if(url != null){
                    listu.add(url);
                }
            }
            groupProductInfo.setImgList(listu);*/
           //查询用户是否已抢购
            int count = bizProductMapper.selectIsSeckilByUserId(BaseContextHandler.getUserID(),BusinessConstant.getSeckillBusId(),id);
            if(count > 0){
                groupProductInfo.setGroupStatus("5");
            }
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(groupProductInfo.getId());
            groupProductInfo.setLabel(labelList);
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(groupProductInfo.getProductImage());
            list.add(imgInfo);
            groupProductInfo.setProductImageList(list);
            //商品精选图片
            List<ImgInfo> selectList = new ArrayList<>();
            ImgInfo selectLImgInfo = new ImgInfo();
            if(groupProductInfo.getSelectionImage() != null){
                String[] selectLImgs = groupProductInfo.getSelectionImage().split(",");
                for(String url : selectLImgs){
                    selectLImgInfo.setUrl(url);
                    selectList.add(selectLImgInfo);
                }
                groupProductInfo.setSelectionImageList(selectList);
            }
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(groupProductInfo.getId());
            if(specInfo != null){
                groupProductInfo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getOriginalPrice() != null){
                    groupProductInfo.setOriginalPrice(df.format(Double.parseDouble(specInfo.getOriginalPrice())));
                }else{
                    groupProductInfo.setOriginalPrice("0");
                }
                if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
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
                List<ImgInfo> imglist = new ArrayList<>();
                ImgInfo specImgInfo = new ImgInfo();
                if(info.getSpecImage() != null && info.getSpecImage() != ""){
                    specImgInfo.setUrl(info.getSpecImage());
                }else{
                    specImgInfo.setUrl(groupProductInfo.getProductImage());
                }
                if(specInfo.getUnit() == null || "".equals(specInfo.getUnit())){
                    info.setUnit("");
                }
                imglist.add(specImgInfo);
                info.setSpecImageList(imglist);
                info.setPrice(df.format(Double.parseDouble(info.getPrice())));
            }
            groupProductInfo.setProductSpecInfo(productSpecInfoList);
            //商品所属公司
            CompanyInfo companyInfo = bizShoppingCartMapper.selectCompanyInfoById(id);
            if(companyInfo != null){
                groupProductInfo.setIsInvoice(companyInfo.getIsInvoice());
                groupProductInfo.setCompanyId(companyInfo.getCompanyId());
                groupProductInfo.setCompanyName(companyInfo.getCompanyName());
                //公司logo
                List<ImgInfo> plist = new ArrayList<>();
                ImgInfo pInfo = new ImgInfo();
                pInfo.setUrl(companyInfo.getLogoImg());
                plist.add(pInfo);
                groupProductInfo.setLogoImgList(plist);
                groupProductInfo.setLogoImg(companyInfo.getLogoImg());
            }
            //查询商品的用户评价
            List<UserCommentVo> userCommentList = bizProductMapper.selectProductCommentDetail(id,0,2);
            for (UserCommentVo commentVo : userCommentList){
                //用户头像
                List<ImgInfo> listc = new ArrayList<>();
                ImgInfo imgInfoc = new ImgInfo();
                imgInfoc.setUrl(commentVo.getProfilePhoto());
                listc.add(imgInfoc);
                commentVo.setProfilePhotoList(listc);
                //评价图片
                List<ImgInfo> listvo = new ArrayList<>();
                if(commentVo.getImgUrl() != null){
                    String[] imgUrlList = commentVo.getImgUrl().split(",");
                    for (String url : imgUrlList){
                        ImgInfo imgInfovo = new ImgInfo();
                        imgInfovo.setUrl(url);
                        listvo.add(imgInfovo);
                    }
                }
                commentVo.setImgUrlList(listvo);
            }
            if(userCommentList == null || userCommentList.size() == 0){
                userCommentList = new ArrayList<>();
            }
            groupProductInfo.setUserCommentList(userCommentList);
        }
        if(groupProductInfo == null){
            groupProductInfo = new GroupProductInfo();
        }
        msg.setData(groupProductInfo);
        return msg;
    }



    /**
     * 查询推荐商品
     * @param projectId
     * @return
     */
    public ObjectRestResponse getRecommendProductInfo(String projectId){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<RecommendVo> recommendVoList = bizProductMapper.selectRecommendProductInfo(projectId);
        for(RecommendVo vo : recommendVoList){
            List<ImgInfo> imglist = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(vo.getProductImage());
            imglist.add(imgInfo);
        }
        if(recommendVoList == null || recommendVoList.size() == 0){
            recommendVoList = new ArrayList<>();
        }
        msg.setData(recommendVoList);
        return msg;
    }



    /**
     * 查询首页推荐商品列表
     * @param projectId
     * @return
     */
    public ObjectRestResponse getRecommendList(String projectId, Integer page, Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
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
        List<ProductVo> productVoList = bizProductMapper.selectRecommendListByClassifyId(projectId, startIndex, limit);
        for(ProductVo vo : productVoList) {
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(vo.getId());
            vo.setLabel(labelList);
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(vo.getProductImage());
            list.add(imgInfo);
            vo.setProductImageList(list);
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(vo.getId());
            if(specInfo != null){
                vo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
                    vo.setUnit(specInfo.getUnit());
                }else{
                    vo.setUnit("");
                }
            }
        }
        if(productVoList == null || productVoList.size() == 0){
            productVoList = new ArrayList<>();
        }
        msg.setData(productVoList);
        return msg;
    }



    /**
     * 查询首页推荐团购
     * @param projectId
     * @return
     */
    public ObjectRestResponse getRecommendGroupInfo(String projectId){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<RecommendVo> recommendVoList = bizProductMapper.selectRecommendGroupInfo(BusinessConstant.getGroupBuyingBusId(),projectId);
        for(RecommendVo vo : recommendVoList){
            List<ImgInfo> imglist = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(vo.getProductImage());
            imglist.add(imgInfo);
        }
        if(recommendVoList == null || recommendVoList.size() == 0){
            recommendVoList = new ArrayList<>();
        }
        msg.setData(recommendVoList);
        return msg;
    }


    /**
     * 查询首页推荐团购列表
     * @return
     */
    public ObjectRestResponse getRecommendGroupList(String projectId,Integer page, Integer limit){
        ObjectRestResponse msg = new ObjectRestResponse();
        DecimalFormat df =new  DecimalFormat("0.00");
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
        List<ProductVo> productVoList = bizProductMapper.selectRecommendGroupList(BusinessConstant.getGroupBuyingBusId(),projectId, startIndex, limit);
        for(ProductVo vo : productVoList) {
            //商品标签
            List<String> labelList = bizProductLabelMapper.selectLabelList(vo.getId());
            vo.setLabel(labelList);
            //商品logo
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(vo.getProductImage());
            list.add(imgInfo);
            vo.setProductImageList(list);
            //商品规格单价
            SpecVo specInfo =  bizProductSpecMapper.selectSpecInfo(vo.getId());
            if(specInfo != null){
                vo.setPrice(df.format(Double.parseDouble(specInfo.getPrice())));
                if(specInfo.getUnit() != null || !"".equals(specInfo.getUnit())){
                    vo.setUnit(specInfo.getUnit());
                }else{
                    vo.setUnit("");
                }
            }
        }
        if(productVoList == null || productVoList.size() == 0){
            productVoList = new ArrayList<>();
        }
        msg.setData(productVoList);
        return msg;
    }

    /**
     * 查询商品的用户评价
     * @param productId
     * @param page
     * @param limit
     * @return
     */
    public ObjectRestResponse<List<UserCommentVo>> getUserCommentList(String productId,Integer page, Integer limit){
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
        List<UserCommentVo> userCommentList = bizProductMapper.selectProductCommentDetail(productId, startIndex, limit);
        for (UserCommentVo commentVo : userCommentList){
            //用户头像
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(commentVo.getProfilePhoto());
            list.add(imgInfo);
            commentVo.setProfilePhotoList(list);
            //评价图片
            List<ImgInfo> listvo = new ArrayList<>();
            ImgInfo imgInfovo = new ImgInfo();
            if(commentVo.getImgUrl() != null){
                String[] imgUrlList = commentVo.getImgUrl().split(",");
                for (String url : imgUrlList){
                    imgInfovo.setUrl(url);
                    listvo.add(imgInfovo);
                }
            }
            commentVo.setImgUrlList(listvo);
        }
        if(userCommentList == null || userCommentList.size() == 0){
            userCommentList = new ArrayList<>();
        }
        return ObjectRestResponse.ok(userCommentList);
    }


    /**
     * 查询商家信息
     * @param companyId
     * @return
     */
    public ObjectRestResponse<CompanyVo> getCompanyInfo(String companyId){
        CompanyVo companyVo = bizProductMapper.selectCompanyInfoById(companyId);
        if(companyVo == null){
            companyVo = new CompanyVo();
        }else{
            List<ImgInfo> selectList = new ArrayList<>();
            ImgInfo selectLImgInfo = new ImgInfo();
            if(companyVo.getQualificImg()!= null){
                String[] selectLImgs = companyVo.getQualificImg().split(",");
                for(String url : selectLImgs){
                    selectLImgInfo.setUrl(url);
                    selectList.add(selectLImgInfo);
                }
                companyVo.setQualificImgList(selectList);
            }
        }
        return ObjectRestResponse.ok(companyVo);
    }


    public String getBusNameByPid(String productId) {
        return this.mapper.getBusNameByPid(productId);
    }
}
