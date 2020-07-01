package com.github.wxiaoqi.security.report.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.report.entity.ReportRegisterStatistics;
import com.github.wxiaoqi.security.report.mapper.ReportRegisterStatisticsMapper;
import com.github.wxiaoqi.security.report.vo.BuildRegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;


/**
 * 楼栋用户注册载量表
 *
 * @author zxl
 * @Date 2019-03-11 14:26:24
 */
@Service
@Slf4j
public class ReportRegisterStatisticsBiz extends BusinessBiz<ReportRegisterStatisticsMapper,ReportRegisterStatistics> {
	public void buildRegisterStatistics() {
//		Calendar cal=Calendar.getInstance();
//		cal.add(Calendar.DATE,-1);
//		Date d=cal.getTime();
//		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
//		String dateTime=sp.format(d);
		try {
			log.info("统计昨天的楼栋认证用户量开始");
			int statistics = this.mapper.buildRegisterStatistics();
			log.info("统计昨天的楼栋认证用户量结束，新增条数:"+statistics);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public List<BuildRegisterVo> buildRegister(String projectId, String beginDate, String endDate, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		//分页
		Integer startIndex = (page - 1) * limit;
//		if(StringUtils.isEmpty(projectId)){
//			List<String> projectIds = this.mapper.selectProjectIdById(BaseContextHandler.getTenantID());
//			if(projectIds != null && projectIds.size() > 0){
//				projectId = projectIds.get(0);
//			}
//		}
		return this.mapper.buildRegister(projectId, beginDate, endDate, startIndex, limit);
	}

	public int buildRegisterCount(String projectId,String beginDate, String endDate) {
//		if(StringUtils.isEmpty(projectId)){
//			List<String> projectIds = this.mapper.selectProjectIdById(BaseContextHandler.getTenantID());
//			if(projectIds != null && projectIds.size() > 0){
//				projectId = projectIds.get(0);
//			}
//		}
		return this.mapper.buildRegisterCount(projectId, beginDate, endDate);
	}
}