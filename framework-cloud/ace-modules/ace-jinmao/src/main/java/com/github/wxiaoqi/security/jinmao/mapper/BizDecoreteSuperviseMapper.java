package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizDecoreteSupervise;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out.DecoreteDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out.DecoreteListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 装修监理表
 * 
 * @Date 2019-04-01 15:47:05
 */
public interface BizDecoreteSuperviseMapper extends CommonMapper<BizDecoreteSupervise> {

    List<DecoreteListVo> getDecoreteSuperviseList(@Param("type")String type,@Param("tenantId")String tenantId,@Param("projectId") String projectId, @Param("status") String status,
                                                  @Param("page") Integer page, @Param("limit") Integer limit);

    DecoreteDetailVo getDecoreteDetail(@Param("id") String id);

    int getDecoreteSuperviseCount(@Param("type")String type,@Param("tenantId")String tenantId,@Param("projectId") String projectId,@Param("status") String status);

    int deleteDecoreteSupervise(@Param("id") String id);

    int updateStatus(@Param("id") String id,@Param("status")String status);

    String getProjectByDecorete(@Param("projectId") String projectId);
}
