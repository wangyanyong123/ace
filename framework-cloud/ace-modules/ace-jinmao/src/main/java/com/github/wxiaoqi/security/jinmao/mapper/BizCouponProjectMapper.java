package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizCouponProject;
import org.apache.ibatis.annotations.Param;

/**
 * 优惠券项目关联表
 * 
 * @author huangxl
 * @Date 2019-04-16 17:42:34
 */
public interface BizCouponProjectMapper extends CommonMapper<BizCouponProject> {

    int deleteProject(@Param("id") String id);
}
