package com.github.wxiaoqi.security.merchant.mapper;

import com.github.wxiaoqi.security.merchant.entity.BizUserHouse;
import com.github.wxiaoqi.security.merchant.vo.userhouse.out.UserHouseVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户和房屋关系表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
public interface BizUserHouseMapper extends CommonMapper<BizUserHouse> {


	UserHouseVo getCurrentHouseInfoById(@Param("id") String id);
}

