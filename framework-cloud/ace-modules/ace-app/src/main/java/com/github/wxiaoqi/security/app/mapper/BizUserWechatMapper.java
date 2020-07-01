package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserWechat;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huangxl
 * @Date 2020-04-12 11:18:57
 */
public interface BizUserWechatMapper extends CommonMapper<BizUserWechat> {

    boolean existsWithOpenId(@Param("openId") String openId);

    int bindUser(@Param("openId") String openId,@Param("appType") Integer appType, @Param("userId") String userId, @Param("modifyBy") String modifyBy);

    String selectUserBindOpenIdByAppType(@Param("userId") String userId, @Param("appType") Integer appType);

    String getUserByOpenId(@Param("openId") String openId);
}
