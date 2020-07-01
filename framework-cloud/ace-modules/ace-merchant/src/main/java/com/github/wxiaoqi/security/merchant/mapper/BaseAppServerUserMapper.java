package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.entity.BaseAppServerUser;
import org.apache.ibatis.annotations.Param;

/**s
 * app服务端用户表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
public interface BaseAppServerUserMapper extends CommonMapper<BaseAppServerUser> {

    int isHasSupervision(@Param("userId") String userId);

    String selectCompanyNameById(@Param("userId") String userId);

}
