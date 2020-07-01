package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BaseUser;

@Tenant()
public interface UserMapper extends CommonMapper<BaseUser> {

}
