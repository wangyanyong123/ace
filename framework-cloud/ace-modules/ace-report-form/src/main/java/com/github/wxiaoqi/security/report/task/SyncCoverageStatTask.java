package com.github.wxiaoqi.security.report.task;

import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.report.entity.SysCoverageStat;
import com.github.wxiaoqi.security.report.mapper.SysCoverageStatMapper;
import com.github.wxiaoqi.security.report.vo.CoverageStatVo;
import lombok.extern.slf4j.Slf4j;
import org.jolokia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 定时任务 - 覆盖率统计数据查询
 * @author liuam
 * @date 2019-08-14 10:32
 */
@Component
@Slf4j
public class SyncCoverageStatTask {


    @Autowired
    private SysCoverageStatMapper sysCoverageStatMapper;


    /**
     * 定时任务
     */
    @Scheduled(cron = "0 20 3 * * ? ")
    public void addCoverageStatData(){
        //获取当前时间
        String currTime = getPastDate(1);
        //获取当前时间前一天的日期
        String lastTime = getPastDate(2);
        log.info("[定时任务 - 覆盖率统计数据]lastTime:{},currTime:{}",lastTime,currTime);
        List<CoverageStatVo> coverageStatVoList = sysCoverageStatMapper.selectCoverageStatByProject(lastTime,currTime);
        if(coverageStatVoList != null && coverageStatVoList.size() > 0){
            for (CoverageStatVo statVo : coverageStatVoList){
                SysCoverageStat coverageStat = new SysCoverageStat();
                coverageStat.setId(UUIDUtils.generateUuid());
                coverageStat.setDay(DateUtils.addDays(new Date(),-1));
                coverageStat.setProjectCode(statVo.getProjectCode());
                coverageStat.setProjectName(statVo.getProjectName());
                coverageStat.setSumUserNum(statVo.getSumUserNum());
                coverageStat.setSumHouseNum(statVo.getSumHouseNum());
                coverageStat.setAddUserNum(statVo.getAddUserNum());
                coverageStat.setAddHouseNum(statVo.getAddHouseNum());
                coverageStat.setTimeStamp(System.currentTimeMillis());
                coverageStat.setCreateBy("admin");
                coverageStat.setCreateTime(new Date());
                sysCoverageStatMapper.insertSelective(coverageStat);
            }
        }else{
            log.info("[定时任务 - 覆盖率统计数据] 无数据插入");
        }
    }



    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        String result = "";
        c.setTime(new Date());
        c.add(Calendar.DATE, - past);
        Date d = c.getTime();
        result = format.format(d);
        return result;
    }


}
