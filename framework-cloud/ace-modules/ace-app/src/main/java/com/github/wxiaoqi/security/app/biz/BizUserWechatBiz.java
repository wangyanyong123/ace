package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.wechat.in.BindPhoneIn;
import com.github.wxiaoqi.security.api.vo.wechat.in.WechatAuthorizeIn;
import com.github.wxiaoqi.security.api.vo.wechat.out.WechatOpenIdResult;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizShoppingCart;
import com.github.wxiaoqi.security.app.entity.BizUserWechat;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BizShoppingCartMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserWechatMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author guohao
 * @Date 2020-04-12 11:18:57
 */
@Slf4j
@Service
public class BizUserWechatBiz extends BusinessBiz<BizUserWechatMapper, BizUserWechat> {

    @Autowired
    private ToolFegin toolFegin;

    @Resource
    private BizUserWechatMapper bizUserWechatMapper;

    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;

    @Resource
    private BizShoppingCartMapper bizShoppingCartMapper;

    public ObjectRestResponse authorize(WechatAuthorizeIn authorizeIn) {
        ObjectRestResponse<WechatOpenIdResult> authorize = toolFegin.authorize(authorizeIn);
        if (!authorize.success()) {
            return authorize;
        }
        WechatOpenIdResult result = authorize.getData();
        boolean exists = bizUserWechatMapper.existsWithOpenId(result.getOpenId());
        if (!exists) {
            String userId = BaseContextHandler.getUserID();
            if (StringUtils.isEmpty(userId)) {
                userId = "admin";
            }
            BizUserWechat userWechat = new BizUserWechat();
            userWechat.setId(UUIDUtils.generateUuid());
            userWechat.setOpenId(result.getOpenId());
            userWechat.setAppId(result.getAppId());
            userWechat.setAppType(result.getAppType());
            userWechat.setUnionId(result.getUnionId());
            userWechat.setCreateBy(userId);
            userWechat.setCreateTime(new Date());
            userWechat.setModifyBy(userId);
            userWechat.setStatus("1");
            bizUserWechatMapper.insertSelective(userWechat);
        }
        return ObjectRestResponse.ok(getOpenIdResultMap(result.getOpenId()));
    }

    public ObjectRestResponse bindPhone(BindPhoneIn bindPhoneIn) {
        ObjectRestResponse<Object> objectRestResponse = new ObjectRestResponse<>();
        String userId;
        if (StringUtils.isNotEmpty(bindPhoneIn.getPhone())) {
            BaseAppClientUser userByMobile = baseAppClientUserBiz.getUserByMobile(bindPhoneIn.getPhone());
            if (userByMobile == null) {
                objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
                objectRestResponse.setMessage("用户不存在，请去注册");
                return objectRestResponse;
            }
            objectRestResponse = toolFegin.checkCode(bindPhoneIn.getPhone(), bindPhoneIn.getSmsCode());
            if (!objectRestResponse.success()) {
                return objectRestResponse;
            }
            userId = userByMobile.getId();
        } else {
            userId = BaseContextHandler.getUserID();
        }
        initOpenId(bindPhoneIn);

        objectRestResponse.setData(getOpenIdResultMap(bindPhoneIn.getOpenId()));
        return objectRestResponse;
    }


    public ObjectRestResponse bindUser(Integer appType,String userId,String openId ){
        ObjectRestResponse restResponse = new ObjectRestResponse();

        String bindOpenIdByAppType = bizUserWechatMapper.selectUserBindOpenIdByAppType(userId, appType);
        if (StringUtils.isNotEmpty(bindOpenIdByAppType)) {
            if (bindOpenIdByAppType.equals(openId)) {
                //当前用户已经使用该微信端绑定过
                restResponse.setMessage("已绑定过。");
                return restResponse;
            } else {
                //当前用户已经绑定过其他微信端
                restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
                restResponse.setMessage("您已绑定过其他微信。无法再次绑定");
                return restResponse;
            }
        }
        String userByOpenId = bizUserWechatMapper.getUserByOpenId(openId);
        if (StringUtils.isNotEmpty(userByOpenId) && !userByOpenId.equals(userId)) {
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("该微信已绑定其他用户，无法再次绑定");
            return restResponse;
        }
        int update = bizUserWechatMapper.bindUser(openId,appType, userId, userId);
        if (update <= 0) {
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("绑定失败。openid 不存在");
            return restResponse;
        }

        updateShoppingCart(openId,userId);
        return restResponse;
    }

    /**
     * 绑定用户时，更新购物车用户id，并合并购物车重复数据
     */
    private void updateShoppingCart(String openId,String userId){

        try {
            List<BizShoppingCart> wxList = bizShoppingCartMapper.selectCartDataByUserId(openId);
            if(CollectionUtils.isEmpty(wxList)){
                return;
            }
            List<BizShoppingCart> userList = bizShoppingCartMapper.selectCartDataByUserId(userId);
            if(CollectionUtils.isEmpty(userList)){
                //设置绑定用户购物车的userid
                bizShoppingCartMapper.updateUserIdByOpenId(openId, userId, new Date());
            }
//            ImmutableMap<String, BizShoppingCart> wxMap = Maps.uniqueIndex(wxList, BizShoppingCart::getSpecId);
//            userList.parallelStream().forEach(item->{
//                BizShoppingCart bizShoppingCart = wxMap.get(item.getSpecId());
//                if(bizShoppingCart != null){
//                    //删除微信产生的记录
//                    bizShoppingCartMapper.delShoppinfById(Collections.singletonList(bizShoppingCart.getId()),userId);
//                    BigDecimal quantity = bizShoppingCart.getProductNum().add(item.getProductNum());
//                    //更新原用户的数量
//                    bizShoppingCartMapper.updateShoppinfBuyNumById(item.getId(),quantity.toString(),userId);
//                }
//            });
        }catch (Exception e){
            log.error("绑定用户更新购物车数据失败，openId:{},userId:{}",openId,userId,e);
        }
    }


    private void initOpenId(BindPhoneIn bindPhoneIn) {
        if (StringUtils.isEmpty(bindPhoneIn.getOpenId())) {
            WechatAuthorizeIn authorizeIn = new WechatAuthorizeIn();
            authorizeIn.setCode(bindPhoneIn.getCode());
            authorizeIn.setAppType(bindPhoneIn.getAppType());
            ObjectRestResponse<Object> authorize = authorize(authorizeIn);
            if (!authorize.success()) {
                throw new BusinessException(authorize.getMessage());
            }
            Map<String, String> result = (Map<String, String>) authorize.getData();
            bindPhoneIn.setOpenId(result.get("openId"));
        }
    }


    private Map<String, String> getOpenIdResultMap(String openId) {
        Map<String, String> map = new HashMap<>();
        map.put("openId", openId);
        return map;
    }

    public ObjectRestResponse sendSmsCode(String phone) {
        ObjectRestResponse result = new ObjectRestResponse();
        if (StringUtils.isEmpty(phone)) {
            result.setStatus(501);
            result.setMessage("手机号为空");
            return result;
        }
//        BaseAppClientUser userByMobile = baseAppClientUserBiz.getUserByMobile(phone);
//        if (null == userByMobile) {
//            result.setStatus(503);
//            result.setMessage("该手机号码尚未注册，请前去注册！");
//            return result;
//        }
        if (StringUtils.isMobile(phone)) {
            try {
                result = toolFegin.getCode(phone, 4, 300, "1", MsgThemeConstants.CUSTOMER_BIND_MOBILE, null);
                if (200 == result.getStatus()) {
                    result.setData(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setMessage("获取验证码失败");
            }
        } else {
            result.setStatus(502);
            result.setMessage("手机号错误！");
            return result;
        }
        return result;
    }

    public String getUserIdByOpenId(String openId) {
        return bizUserWechatMapper.getUserByOpenId(openId);
    }
}