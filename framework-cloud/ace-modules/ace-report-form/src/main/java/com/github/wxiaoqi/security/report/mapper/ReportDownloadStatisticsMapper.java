package com.github.wxiaoqi.security.report.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.report.entity.ReportDownloadStatistics;
import com.github.wxiaoqi.security.report.vo.RegisterAndAuthVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户下载量表
 * 
 * @author zxl
 * @Date 2019-01-29 14:25:41
 */
public interface ReportDownloadStatisticsMapper extends CommonMapper<ReportDownloadStatistics> {
	int getTodayRegisterNum();
	int getTodayAuthNum();
	int getTodayAddAuthNum();
	int getTotalRegisterNum();
	int getTotalAuthNum();
	ReportDownloadStatistics getByStatisticalDate(@Param("statisticalDate")String statisticalDate);

	int getYesterdayRegisterNum();
	int getYesterdayAuthNum();
	int getYesterdayAddAuthNum();
	int getYesterdayTotalRegisterNum();
	int getYesterdayTotalAuthNum();

	List<RegisterAndAuthVo> registerAndAuth(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("page") Integer page, @Param("limit") Integer limit);

	int registerAndAuthCount(@Param("beginDate") String beginDate, @Param("endDate") String endDate);
}
