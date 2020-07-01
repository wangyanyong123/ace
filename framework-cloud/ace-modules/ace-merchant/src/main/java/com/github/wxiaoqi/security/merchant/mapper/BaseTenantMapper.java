package com.github.wxiaoqi.security.merchant.mapper;



import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.merchant.entity.BaseTenant;
import com.github.wxiaoqi.security.merchant.vo.user.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 租户表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
public interface BaseTenantMapper extends CommonMapper<BaseTenant> {



    /**
     * 查询当前用户所属角色
     * @param userId
     * @return
     */
    UserInfo selectRoleTypeByUser(String userId);


    /**
     * 查询当前用户所属角色
     * @param userId
     * @return
     */
    UserInfo selectRoleTypeByUserId(String userId);


}
