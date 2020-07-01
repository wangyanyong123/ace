package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.api.vo.order.in.SubProduct;
import com.github.wxiaoqi.security.app.entity.BizCoupon;
import com.github.wxiaoqi.security.app.entity.BizCouponUse;
import com.github.wxiaoqi.security.app.entity.BizPnsCall;
import com.github.wxiaoqi.security.app.mapper.BizCouponMapper;
import com.github.wxiaoqi.security.app.mapper.BizCouponUseMapper;
import com.github.wxiaoqi.security.app.mapper.BizPnsCallMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.vo.coupon.CouponListVo;
import com.github.wxiaoqi.security.app.vo.coupon.CouponPriceVo;
import com.github.wxiaoqi.security.app.vo.postage.PostageInfo;
import com.github.wxiaoqi.security.app.vo.postage.PostageVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.apache.ibatis.annotations.Param;
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
 * 号码管理
 */
@Service
public class BizPnsCallBiz extends BusinessBiz<BizPnsCallMapper, BizPnsCall> {

    /**
     * 修改绑定状态
     * @param bindingFlag 绑定状态
     * @param bindId 绑定ID
     * @return
     */
    public int updateByBindId(int bindingFlag,String bindId){
        return this.mapper.updateByBindId(bindingFlag,bindId);
    }

    /**
     * 获取商家手机号
     * @param id 订单号
     * @return
     */
    public String getCompanyTel(String id){
        String mobilePhone = this.mapper.getNewCompanyTel(id);
        if(StringUtils.isEmpty(mobilePhone)){
            mobilePhone = this.mapper.getCompanyTel(id);
        }
        return mobilePhone;
    }

}