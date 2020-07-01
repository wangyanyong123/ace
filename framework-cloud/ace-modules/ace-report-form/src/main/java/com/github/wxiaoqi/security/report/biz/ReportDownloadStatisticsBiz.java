package com.github.wxiaoqi.security.report.biz;

import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.report.entity.ReportDownloadStatistics;
import com.github.wxiaoqi.security.report.mapper.ReportDownloadStatisticsMapper;
import com.github.wxiaoqi.security.report.vo.RegisterAndAuthVo;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户下载量表
 *
 * @author zxl
 * @Date 2019-01-29 14:25:41
 */
@Service
public class ReportDownloadStatisticsBiz extends BusinessBiz<ReportDownloadStatisticsMapper,ReportDownloadStatistics> {
	public void TodayDownloadAndAuthStatistics(){
		int todayRegisterNum = this.mapper.getTodayRegisterNum();
		int todayAuthNum = this.mapper.getTodayAuthNum();
		int todayAddAuthNum = this.mapper.getTodayAddAuthNum();
		int totalRegisterNum = this.mapper.getTotalRegisterNum();
		int totalAuthNum = this.mapper.getTotalAuthNum();
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		String datetime = sp.format(new Date());
		ReportDownloadStatistics statistics = this.mapper.getByStatisticalDate(datetime);
		boolean falg = false;
		if(null == statistics){
			falg = true;
			statistics = new ReportDownloadStatistics();
		}
		statistics.setTodayAuthNum(todayAddAuthNum);
		statistics.setTodayRegisterNum(todayRegisterNum);
		statistics.setTotalRegisterNum(totalRegisterNum);
		statistics.setAuthNum(totalAuthNum);
		statistics.setTodayUnauthNum(todayRegisterNum - todayAuthNum);
		statistics.setUnauthNum(totalRegisterNum - totalAuthNum);
		if(falg){
			statistics.setId(UUIDUtils.generateUuid());
			statistics.setStatus("1");
			statistics.setCreateTime(new Date());
			statistics.setStatisticalDate(datetime);
			this.insertSelective(statistics);
		}else {
			statistics.setModifyTime(new Date());
			this.updateSelectiveById(statistics);
		}

	}

	public void YesterdayDownloadAndAuthStatistics(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date d=cal.getTime();
		SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
		String dateTime=sp.format(d);
		ReportDownloadStatistics statistics = this.mapper.getByStatisticalDate(dateTime);
		if(null != statistics && isNow(statistics.getModifyTime())){
		}else {
			boolean falg = false;
			if(null == statistics){
				falg = true;
				statistics = new ReportDownloadStatistics();
			}
			int yesterdayRegisterNum = this.mapper.getYesterdayRegisterNum();
			int yesterdayAuthNum = this.mapper.getYesterdayAuthNum();
			int yesterdayAddAuthNum = this.mapper.getYesterdayAddAuthNum();
			int yesterdayTotalRegisterNum = this.mapper.getYesterdayTotalRegisterNum();
			int yesterdayTotalAuthNum = this.mapper.getYesterdayTotalAuthNum();
			statistics.setTodayAuthNum(yesterdayAddAuthNum);
			statistics.setTodayRegisterNum(yesterdayRegisterNum);
			statistics.setTotalRegisterNum(yesterdayTotalRegisterNum);
			statistics.setAuthNum(yesterdayTotalAuthNum);
			statistics.setTodayUnauthNum(yesterdayRegisterNum - yesterdayAuthNum);
			statistics.setUnauthNum(yesterdayTotalRegisterNum - yesterdayTotalAuthNum);
			if(falg){
				statistics.setId(UUIDUtils.generateUuid());
				statistics.setStatus("1");
				statistics.setCreateTime(new Date());
				statistics.setStatisticalDate(dateTime);
				statistics.setModifyTime(new Date());
				this.insertSelective(statistics);
			}else {
				statistics.setModifyTime(new Date());
				this.updateSelectiveById(statistics);
			}
		}
	}
	private static boolean isNow(Date date){
		if(null == date){
			return false;
		}
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String nowDay = sf.format(now);
		String day = sf.format(date);
		return day.equals(nowDay);
	}

	public List<RegisterAndAuthVo> registerAndAuth(String beginDate, String endDate, Integer page, Integer limit) {
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
		return this.mapper.registerAndAuth(beginDate, endDate, startIndex, limit);
	}

	public int registerAndAuthCount(String beginDate, String endDate) {
		return this.mapper.registerAndAuthCount(beginDate, endDate);
	}
}
