package com.github.wxiaoqi.security.report.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.report.entity.BizWo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工单表
 *
 * @author huangxl
 * @Date 2018-12-03 15:00:10
 */
public interface BizWoMapper extends CommonMapper<BizWo> {

    List<String> getNoSyncWoIds(@Param("projectList") List<String> projectList);
}
