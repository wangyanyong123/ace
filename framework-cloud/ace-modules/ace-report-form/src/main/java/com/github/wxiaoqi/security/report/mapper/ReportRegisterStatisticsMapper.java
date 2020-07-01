package com.github.wxiaoqi.security.report.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.report.entity.ReportRegisterStatistics;
import com.github.wxiaoqi.security.report.vo.BuildRegisterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 楼栋用户注册载量表
 * 
 * @author zxl
 * @Date 2019-03-11 14:26:24
 */
public interface ReportRegisterStatisticsMapper extends CommonMapper<ReportRegisterStatistics> {

	int buildRegisterStatistics();

	List<BuildRegisterVo> buildRegister(@Param("projectId") String projectId, @Param("beginDate")String beginDate, @Param("endDate")String endDate, @Param("page") Integer page, @Param("limit") Integer limit);

	int buildRegisterCount(@Param("projectId")String projectId, @Param("beginDate")String beginDate, @Param("endDate")String endDate);

	List<String> selectProjectIdById(@Param("id") String id);
}
