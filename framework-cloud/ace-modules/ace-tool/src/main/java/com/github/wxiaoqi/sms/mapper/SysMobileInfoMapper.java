package com.github.wxiaoqi.sms.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.sms.entity.SysMobileInfo;

/**
 * 手机信息
 * 
 * @author zxl
 * @Date 2018-11-20 16:04:25
 */
public interface SysMobileInfoMapper extends CommonMapper<SysMobileInfo> {
    SysMobileInfo getMobileInfoByUserId(String userId);

    int insertMobileInfo(SysMobileInfo mobileInfo);

    int updateMobileInfo(SysMobileInfo mobileInfo);

    int insertMobileInfoLog(SysMobileInfo mobileInfo);

    int delDirtyData(SysMobileInfo mobileInfo);
}
