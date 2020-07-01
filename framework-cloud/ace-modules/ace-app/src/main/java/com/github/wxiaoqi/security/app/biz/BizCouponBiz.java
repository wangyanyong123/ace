package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.app.entity.BizCoupon;
import com.github.wxiaoqi.security.app.entity.BizCouponUse;
import com.github.wxiaoqi.security.app.mapper.BizCouponMapper;
import com.github.wxiaoqi.security.app.mapper.BizCouponUseMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.vo.coupon.CouponListVo;
import com.github.wxiaoqi.security.app.vo.coupon.CouponPriceVo;
import com.github.wxiaoqi.security.app.vo.postage.PostageInfo;
import com.github.wxiaoqi.security.app.vo.postage.PostageVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 优惠券使用表
 *
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
@Service
public class BizCouponBiz extends BusinessBiz<BizCouponMapper, BizCoupon> {

    private Logger logger = LoggerFactory.getLogger(BizCouponBiz.class);
    @Autowired
    private BizCouponMapper bizCouponMapper;
    @Autowired
    private BizCouponUseMapper bizCouponUseMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 获取我的优惠券
     * @return
     */
    public ObjectRestResponse<CouponListVo> getMyCoupon(String couponStatus) {
        ObjectRestResponse response = new ObjectRestResponse();
        //更新过期优惠券状态
        List<String> couponIds = bizCouponMapper.selectMyCouponIds(BaseContextHandler.getUserID());
        if (couponIds.size() != 0) {
            bizCouponMapper.updateCouponStatusByUser(couponIds);
        }
        List<CouponListVo> myCoupon = bizCouponMapper.getMyCoupon(BaseContextHandler.getUserID(),couponStatus);
        if (myCoupon == null || myCoupon.size() == 0) {
            myCoupon = new ArrayList<>();
        }
        response.setData(myCoupon);
        return response;
    }

    /**
     * 领取优惠券
     * @param couponId
     * @return
     */
    public synchronized ObjectRestResponse getCoupon(String couponId) {
        ObjectRestResponse response = new ObjectRestResponse();
        String oneCoupon = bizCouponMapper.getOneByCouponId(couponId);
        if (oneCoupon != null){
            BizCoupon coupon = bizCouponMapper.selectByPrimaryKey(couponId);
            int count = bizCouponMapper.getUserCouponCount(couponId, BaseContextHandler.getUserID());
            if (coupon.getGetLimit() == count) {
                response.setStatus(101);
                response.setMessage("您已领过该优惠券");
                return response;
            }
            BizCouponUse bizCouponUse = new BizCouponUse();
            bizCouponUse.setId(oneCoupon);
            bizCouponUse.setUserId(BaseContextHandler.getUserID());
            bizCouponUse.setUseStatus("1");
            if (bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse)>0) {
                if ("1".equals(coupon.getUseStatus())) {
                    BizCoupon bizCoupon = new BizCoupon();
                    bizCoupon.setId(couponId);
                    bizCoupon.setUseStatus("2");
                    bizCouponMapper.updateByPrimaryKeySelective(bizCoupon);
                }
                response.setMessage("领取成功");
            }else {
                logger.info("优惠券发放至用户失败,优惠券ID:{},用户:{}",couponId,BaseContextHandler.getUserID());
                response.setStatus(101);
                response.setMessage("领取失败，请稍后再试");
                return response;
            }
        }else {
            response.setStatus(101);
            response.setMessage("该优惠券已被抢完!");
            return response;
        }
        return response;
    }


    public ObjectRestResponse getCouponList(String productId) {
        ObjectRestResponse response = new ObjectRestResponse();
        List<CouponListVo> couponList = bizCouponMapper.getCouponList(productId);
        if (couponList==null || couponList.size() == 0) {
            couponList = new ArrayList<>();
        }
        for (CouponListVo couponListVo : couponList) {
            BizCoupon coupon = bizCouponMapper.selectByPrimaryKey(couponListVo.getCouponId());
            int count = bizCouponMapper.getUserCouponCount(couponListVo.getCouponId(), BaseContextHandler.getUserID());
            int surplusCount = bizCouponMapper.getSurplusCount(couponListVo.getCouponId());
            if (surplusCount == 0) {
                couponListVo.setGetStatus("0");
            }else if (coupon.getGetLimit() == count) {
                couponListVo.setGetStatus("2");
            } else if (coupon.getGetLimit() > count || count == 0) {
                couponListVo.setGetStatus("1");
            }
        }
        response.setData(couponList);
        return response;
    }

    /**
     * 获取可用优惠券并返回优惠金额
     * @param productVo
     * @return
     */
    public ObjectRestResponse getViableCouponList(BuyProductInfo productVo) {
        ObjectRestResponse response = new ObjectRestResponse();
        List<CouponListVo> resultCoupon = new ArrayList<>();
        List<String> productIds = new ArrayList<>();
        for (CompanyProduct companyProduct : productVo.getCompanyProductList()) {
            //获取符合优惠券ID
            List<String> couponIds = bizCouponMapper.getViableCouponIds(companyProduct.getCompanyId(), companyProduct.getTotalPrice(), BaseContextHandler.getUserID(),null);
            if (couponIds.size() == 0) {
                response.setData(new ArrayList<>());
                return response;
            }
            for (SubProduct subProduct : companyProduct.getSubProductList()) {
                productIds.add(subProduct.getProductId());
            }
            //获取符合优惠券优惠金额
            List<CouponPriceVo> couponValue = bizCouponMapper.getViableCouponList(couponIds, productIds,null);
            if (couponValue.size() == 0) {
                response.setData(new ArrayList<>());
                return response;
            }
            for (CouponPriceVo couponPriceVo : couponValue) {
                //计算优惠金额
                CouponListVo couponListVo = bizCouponMapper.getCouponInfo(couponPriceVo.getId());
                if ("1".equals(couponPriceVo.getCouponType())) {
                    BigDecimal resultPrice = couponPriceVo.getValue().setScale(2, BigDecimal.ROUND_HALF_UP);
                    couponListVo.setDiscountPrice(resultPrice);
                } else if ("2".equals(couponPriceVo.getCouponType())) {
                    BigDecimal resultPrice = companyProduct.getTotalPrice()
                                            .multiply(couponPriceVo.getDiscountPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    couponListVo.setDiscountPrice(resultPrice.compareTo(couponPriceVo.getMasValue()) == 1 ?  couponPriceVo.getMasValue(): resultPrice);
                }
                resultCoupon.add(couponListVo);
            }
        }
        Collections.sort(resultCoupon, Comparator.comparing(CouponListVo::getMinValue).reversed().thenComparing(CouponListVo::getValue).reversed());
        response.setData(resultCoupon);
        return response;
    }

    /**
     * 获取当前订单当前优惠券的优惠金额
     * @param companyId  公司ID
     * @param totalPrice 订单金额
     * @param subProductList 订单产品列表
     * @param couponId 所选优惠券ID
     * @param userId 用户ID
     * @return
     */
    public ObjectRestResponse getDiscountPrice(String companyId,BigDecimal totalPrice,List<SubProduct> subProductList,String couponId,String userId) {
        ObjectRestResponse response = new ObjectRestResponse();
        List<CouponListVo> resultCoupon = new ArrayList<>();
        List<String> productIds = new ArrayList<>();
        BigDecimal discountPrice = new BigDecimal(0);
        //获取符合优惠券ID
        List<String> couponIds = bizCouponMapper.getViableCouponIds(companyId, totalPrice, userId,couponId);
        if (couponIds.size() == 0) {
            response.setData(discountPrice);
            return response;
        }
        for (SubProduct subProduct : subProductList) {
            productIds.add(subProduct.getProductId());
        }
        //获取符合优惠券优惠金额
        List<CouponPriceVo> couponValue = bizCouponMapper.getViableCouponList(couponIds, productIds,couponId);
        if (couponValue==null && couponValue.size() == 0) {
            response.setData(discountPrice);
            return response;
        }
        CouponPriceVo couponPriceVo = couponValue.get(0);
        //计算优惠金额
        CouponListVo couponListVo = bizCouponMapper.getCouponInfo(couponPriceVo.getId());
        if ("1".equals(couponPriceVo.getCouponType())) {
            BigDecimal resultPrice = couponPriceVo.getValue().setScale(2, BigDecimal.ROUND_HALF_UP);
            couponListVo.setDiscountPrice(resultPrice);
        } else if ("2".equals(couponPriceVo.getCouponType())) {
            BigDecimal resultPrice = totalPrice.multiply(couponPriceVo.getDiscountPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            couponListVo.setDiscountPrice(resultPrice.compareTo(couponPriceVo.getMasValue()) > -1 ?  couponPriceVo.getMasValue(): resultPrice);
        }
        discountPrice = couponListVo.getDiscountPrice();

        response.setData(discountPrice);
        return response;
    }


    public ObjectRestResponse getPostageInfo(BuyProductInfo productVo) {
        ObjectRestResponse response = new ObjectRestResponse();

        List<PostageInfo> postage = new ArrayList<>();
        for (CompanyProduct companyProduct : productVo.getCompanyProductList()) {
            PostageInfo postageInfo = getPostageList(companyProduct.getCompanyId(),companyProduct.getTotalPrice());
            if (postageInfo != null) {
                List<PostageVo> postageList = bizCouponMapper.getPostageInfo(companyProduct.getCompanyId());
                Collections.sort(postageList,Comparator.comparing(PostageVo::getStartAmount));
                List<String> rules = new ArrayList<>();
                for (PostageVo postageVo : postageList) {
                    String postageRule = "";
                    if (postageVo.getEndAmount().compareTo(new BigDecimal(-1)) == 0
                            && postageVo.getPostage().compareTo(BigDecimal.ZERO) == 0) {
                        postageRule = "订单金额≥"+postageVo.getStartAmount()+"元，包邮";
                    }else if (postageVo.getEndAmount().compareTo(new BigDecimal(-1)) == 0
                            && postageVo.getPostage().compareTo(BigDecimal.ZERO) == 1){
                        postageRule = "订单金额≥"+postageVo.getStartAmount()+"，收取邮费为"+postageVo.getPostage()+"元";
                    }else if (postageVo.getStartAmount().compareTo(BigDecimal.ZERO) == 0){
                        postageRule = "订单金额＜"+postageVo.getEndAmount()+"，收取邮费"+postageVo.getPostage()+"元";
                    }else {
                        postageRule = postageVo.getStartAmount()+"≤订单金额小于"+postageVo.getEndAmount()+"，收取邮费"+postageVo.getPostage()+"元";
                    }

                    rules.add(postageRule);
                }
                postageInfo.setPostageRules(rules);
                postage.add(postageInfo);
            }
        }
        response.setData(postage);
        return response;
    }

    /**
     * 获取商家提交订单时的邮费金额
     * @return
     */
    public PostageInfo getPostageList(String companyId,BigDecimal totalPrice) {


        PostageInfo postage = new PostageInfo();
        List<PostageVo> postageList = bizCouponMapper.getPostageInfo(companyId);
        postage.setCompanyId(companyId);
        if (postageList == null || postageList.size() == 0) {
            postage.setPostage(BigDecimal.ZERO);
            postage.setPostageRules(Collections.EMPTY_LIST);
            return postage;
        }
        //获取符合邮费规则金额
        for (PostageVo postageInfo : postageList) {
            //比较满xx邮费规则
            if ( postageInfo.getEndAmount().compareTo(new BigDecimal(-1))== 0
                    && (totalPrice.compareTo(postageInfo.getStartAmount()) == 1 || postageInfo.getStartAmount().compareTo(totalPrice) == 0)){
                postage.setPostage(postageInfo.getPostage());
                return postage;
            }//比较区间邮费规则
            else if ((totalPrice.compareTo(postageInfo.getStartAmount()) == 1 && totalPrice.compareTo(postageInfo.getEndAmount()) == -1)
                    || (totalPrice.compareTo(postageInfo.getStartAmount()) == 0 && totalPrice.compareTo(postageInfo.getEndAmount()) == -1)) {
                postage.setPostage(postageInfo.getPostage());
                return postage;
            }else {
                postage.setPostage(new BigDecimal(0));
            }
        }
        return postage;
    }
}