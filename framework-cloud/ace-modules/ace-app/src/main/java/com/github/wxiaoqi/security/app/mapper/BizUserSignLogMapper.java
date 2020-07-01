package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserSignLog;
import com.github.wxiaoqi.security.app.vo.intergral.out.ResignLog;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 运营服务-用户签到日志表
 * 
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
public interface BizUserSignLogMapper extends CommonMapper<BizUserSignLog> {

    int getIsReSign(@Param("date") String date,@Param("userId")String userId);

    int getReSignCount(@Param("date") String date,@Param("userId")String userId);

    List<String> getAllSign(String userId);

    List<ResignLog> getResignLog(@Param("userId")String userId,@Param("page") Integer page,@Param("limit") Integer limit);
}
