package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizUserIntegral;
import com.github.wxiaoqi.security.app.vo.intergral.CalendarSign;
import com.github.wxiaoqi.security.app.vo.intergral.in.UserSignIn;
import com.github.wxiaoqi.security.app.vo.intergral.out.*;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 运营服务-用户综合积分表
 * 
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
public interface BizUserIntegralMapper extends CommonMapper<BizUserIntegral> {

    int updateTotalIntegral(@Param("userId") String userId,@Param("totalIntegral") Integer totalIntegral);

    int getUserSignStatus(@Param("userId") String userId);

    List<String> getUserSignLog(String userId);

    UserSignIn getUserInteral(String userId);

    Date getUserLastSign(String userId);

    NextSignInfo getNextSingInfo(Integer signCount);

    List<SignInfo> getSevenSignInfo();

    List<RandomTaskVo> getRandomTask();

    List<DailyTask> getTodayTask(String today);

    List<String> getUserTaskLog(@Param("today") String today,@Param("userId")String userId);

    int getTaskStatus(@Param("taskCode") String taskCode, @Param("userId")String userId,@Param("today") String today);

    List<SignInfo> getAllSignRule();

    int getTaskIntegral(String taskCode);

    List<IntegralDetailVo> getIntegralLog(@Param("userId") String userId,@Param("page")Integer page,@Param("limit")Integer limit);

    List<CalendarSign> getIntegralCalendar(@Param("time") String time, @Param("userId")String userId);

    int updateSignCount(@Param("userId") String userId,@Param("signCount") int signCount,@Param("signPoint")int signPoint);

    int getResignCardCount(String userId);

    String getResignCard(String userId);

    String getResignCardId(String projectId);
}
