package com.github.wxiaoqi.security.app.biz;
import com.github.wxiaoqi.security.app.entity.BizDailyTask;
import com.github.wxiaoqi.security.app.entity.BizUserTaskLog;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.intergral.in.FinishTaskParams;
import com.github.wxiaoqi.security.app.vo.intergral.in.UserSignIn;
import com.github.wxiaoqi.security.app.vo.intergral.out.*;
import com.google.common.collect.Lists;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizUserSignLog;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizUserIntegral;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 运营服务-用户综合积分表
 *
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@Service
@Slf4j
public class BizUserIntegralBiz extends BusinessBiz<BizUserIntegralMapper,BizUserIntegral> {

    @Autowired
    private BizUserIntegralMapper integralMapper;
    @Autowired
    private BizUserSignLogMapper signLogMapper;
    @Autowired
    private BizDailyTaskMapper bizDailyTaskMapper;
    @Autowired
    private BizUserTaskLogMapper taskLogMapper;
    @Autowired
    private BizUserGradeRuleBiz userGradeRuleBiz;
    /**
     * 查询当前用户总积分
     * 查询7天数的积分：超过7天隐藏
     * 查询已签到天数，和距离下一里程的天数、里程积分
     * 查询每日任务、每日任务完成情况
     * 每日任务定点更新，入库。所有用户通用
     * 第一次签到成功提示，限制二次签到
     */
    public ObjectRestResponse getUserIntegralInfo() {

        UserIntegralInfo userIntegralInfo = new UserIntegralInfo();
        //获取总积分   当前总积分=累计签到积+累计任务积分-消费积分
        String userID = BaseContextHandler.getUserID();
        UserSignIn userSign =  integralMapper.getUserInteral(userID);
        Integer totalIntegral =  userSign.getSignPoints() + userSign.getTaskPoints() - userSign.getConsumePoints();
        Integer historyTotal = userSign.getSignPoints() + userSign.getTaskPoints();
        //获取总积分后、更新用户当前总积分、历史总积分
        int result =  integralMapper.updateTotalIntegral(userID,totalIntegral);
        BizUserIntegral userIntegral = new BizUserIntegral();
        userIntegral.setId(userSign.getId());
        userIntegral.setHistoryPoints(historyTotal);
        integralMapper.updateByPrimaryKeySelective(userIntegral);

        userIntegralInfo.setTotalIntegral(totalIntegral);
        //获取用户签到情况
        int isSignToday = integralMapper.getUserSignStatus(userID);
        SignSchedule signSchedule = new SignSchedule();
        //今日签到状态
        if (isSignToday == 1) {
            signSchedule.setIsSignToday("1");
        }else {
            signSchedule.setIsSignToday("2");
        }
        //连续累计天数
        signSchedule.setSignCount(userSign.getSignCount());
        //获取下一里程签到
        NextSignInfo nextSingInfo = integralMapper.getNextSingInfo(userSign.getSignCount());
        if (nextSingInfo != null) {
            signSchedule.setNextSignDay(nextSingInfo.getSignDay() - userSign.getSignCount());
            signSchedule.setNextSignIntegral(nextSingInfo.getIntegral());
        }else {
            signSchedule.setNextSignDay(0);
            signSchedule.setNextSignIntegral(0);
        }
        List<SignInfo> signInfo = integralMapper.getSevenSignInfo();
        List<SignInfo> sevenSignInfo = new ArrayList<>();
        int integral = 0;
        for (int i = 0; i < signInfo.size(); i++) {
            if ("1".equals(signInfo.get(i).getSignType())) {
                integral = signInfo.get(i).getIntegral();
            }
        }
        for (int i = 0; i < 7; i++) {
            SignInfo sign = new SignInfo();
            sign.setIntegral(integral);
            sign.setDay(i + 1);
            sevenSignInfo.add(sign);
        }
        for (SignInfo info : signInfo) {
            for (SignInfo sign : sevenSignInfo) {
                if ("2".equals(info.getSignType()) && info.getDay().equals(sign.getDay())) {
                    sign.setIntegral(info.getIntegral());
                }
            }
        }
        //前七天的签到信息
        if (sevenSignInfo.size() > 0) {
            signSchedule.setSignInfo(sevenSignInfo);
        }else {
            signSchedule.setSignInfo(new ArrayList<>());
        }

        userIntegralInfo.setSignSchedule(signSchedule);
        String today = DateUtils.dateToString(new Date(), DateUtils.NORMAL_DATE_FORMAT);
        //获取每天随机任务
        List<DailyTask> todayTask = integralMapper.getTodayTask(today);
        //获取用户完成任务
        List<String> userTaskLog = integralMapper.getUserTaskLog(today,userID);
        //比较得出完成情况
        if (userTaskLog.size() > 0) {
            for (String taskCode : userTaskLog) {
                for (DailyTask dailyTask : todayTask) {
                    if (dailyTask.getTaskCode().equals(taskCode)) {
                        dailyTask.setIsFinish("1");
                    }
                }
            }
        }
        String isMiss = "";
        try {
            List<String> dateList =  signLogMapper.getAllSign(BaseContextHandler.getUserID());
            isMiss = isMissSign(dateList);
            //判断是否漏签
            if (dateList.size() == 1) {
                for (String dateStr : dateList) {
                    Calendar calendarTo = Calendar.getInstance();
                    calendarTo.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
                    int year = calendarTo.get(Calendar.YEAR);
                    int month = calendarTo.get(Calendar.MONTH);
                    int date = calendarTo.get(Calendar.DATE);
                    if (date == 1) {
                        isMiss = "1";
                    }else {
                        isMiss = "0";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userIntegralInfo.setDailyTask(todayTask);
        userIntegralInfo.setIsMissSign(isMiss);
        return ObjectRestResponse.ok(userIntegralInfo);
    }


    public static String isMissSign(List<String> dateList) throws ParseException {
        List<Date> dates = new ArrayList<>();
        for (String dateStr : dateList) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(0);
            c.set(calendarTo.get(Calendar.YEAR), calendarTo.get(Calendar.MONTH),
                    calendarTo.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            dates.add(c.getTime());

        }
        int count = 1;
        String miss = "1";
        //判断补签后连续天数
        for (int i = dates.size() - 1; i > 0; i--) {
            if ((dates.get(i).getTime() - dates.get(i - 1).getTime()) / (1000 * 60 * 60 * 24) == 1) {
                count++;
            }else {
                miss = "0";
                break;
            }
        }
        return miss;
    }

    public ObjectRestResponse userSign(String projectId) {
        ObjectRestResponse response = new ObjectRestResponse();
        String userID = BaseContextHandler.getUserID();
        int result = integralMapper.getUserSignStatus(userID);
        //判断是否已签到
        if (result > 0) {
            response.setMessage("今日已签到");
            response.setStatus(101);
            return response;
        }
        //记录签到日志
        BizUserSignLog signLog = new BizUserSignLog();
        signLog.setId(UUIDUtils.generateUuid());
        signLog.setUserId(userID);
        signLog.setSignDate(new Date());
        signLog.setCreateBy(userID);
        signLog.setTimeStamp(new Date());
        signLog.setCreateTime(new Date());
        signLog.setProjectId(projectId);
        //sign_point
        signLogMapper.insertSelective(signLog);
        UserSignIn userSign =  integralMapper.getUserInteral(userID);
        BizUserIntegral userIntegral = new BizUserIntegral();
        List<SignInfo> signInfos =  integralMapper.getAllSignRule();
        int signPoints = 0;
        for (SignInfo signInfo : signInfos) {
            if (0 == signInfo.getDay()) {
                signPoints = signInfo.getIntegral();
            }
        }
        if (userSign == null) {
            userIntegral.setId(UUIDUtils.generateUuid());
            userIntegral.setUserId(BaseContextHandler.getUserID());
            userIntegral.setSignCount(1);
            userIntegral.setSignPoints(signPoints);
            userIntegral.setLastSignDate(new Date());
            userIntegral.setCreateBy(BaseContextHandler.getUserID());
            userIntegral.setCreateTime(new Date());
            userIntegral.setTimeStamp(new Date());
            if (integralMapper.insertSelective(userIntegral) > 0) {
                response.setData("签到成功，+"+signPoints+"积分");
                response.setMessage("签到成功，+"+signPoints+"积分");
                return response;
            }
        }else {
            try {
                Date lastSign = integralMapper.getUserLastSign(userID);
                userIntegral.setId(userSign.getId());
                for (SignInfo signInfo : signInfos) {
                    if (signInfo.getDay().equals(userSign.getSignCount() + 1)) {
                        signPoints = signInfo.getIntegral();
                    }
                }
                if (lastSign == null || DateUtils.daysBetween(lastSign, new Date()) > 1) {
                    //中断签到，从0开始
                    userIntegral.setSignCount(1);
                }else {
                    userIntegral.setSignCount(userSign.getSignCount() + 1);
                }
                userIntegral.setSignPoints(userSign.getSignPoints()+ signPoints);
                userIntegral.setLastSignDate(new Date());
                if (integralMapper.updateByPrimaryKeySelective(userIntegral)>0) {
                    response.setMessage("签到成功，+"+signPoints+"积分");
                    response.setData("签到成功，+"+signPoints+"积分");
                }
                signLog.setSignPoint(signPoints);
                signLogMapper.updateByPrimaryKeySelective(signLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 定时生成随机每日任务
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void createRandomTask(){
        List<RandomTaskVo> randomTask = integralMapper.getRandomTask();
        int i = 0;
        for (RandomTaskVo randomTaskVo : randomTask) {
            i++;
            BizDailyTask dailyTask = new BizDailyTask();
            dailyTask.setId(UUIDUtils.generateUuid());
            dailyTask.setTaskId(randomTaskVo.getId());
            dailyTask.setTaskPoints(randomTaskVo.getIntegral());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND,i);
            dailyTask.setDate(new Date());
            dailyTask.setCreateBy("admin");
            dailyTask.setCreateTime(calendar.getTime());
            bizDailyTaskMapper.insertSelective(dailyTask);
        }


    }

    public void finishDailyTask(String taskCode,String userId) {
        String today = DateUtils.dateToString(new Date(), DateUtils.NORMAL_DATE_FORMAT);
        if (integralMapper.getTaskStatus(taskCode,userId,today) > 0) {
            //已完成任务
            return ;
        }
        if (userId == null) {
            log.error("每日任务-用户ID为空！");
            return ;
        }
        //获取任务积分
        Integer integral =  integralMapper.getTaskIntegral(taskCode);
        if (integral != null) {
            //记录完成任务日志
            BizUserTaskLog userTaskLog = new BizUserTaskLog();
            userTaskLog.setId(UUIDUtils.generateUuid());
            userTaskLog.setUserId(userId);
            userTaskLog.setTaskInRecords(taskCode);
            userTaskLog.setTaskPoints(integral);
            userTaskLog.setTaskDate(new Date());
            userTaskLog.setCreateBy(userId);
            userTaskLog.setCreateTime(new Date());
            userTaskLog.setTimeStamp(new Date());
            if(taskLogMapper.insertSelective(userTaskLog) > 0){
                UserSignIn userSign =  integralMapper.getUserInteral(userId);
                BizUserIntegral userIntegral = new BizUserIntegral();
                if (userSign != null) {
                    userIntegral.setId(userSign.getId());
                    userIntegral.setTaskPoints(userSign.getTaskPoints() + integral);
                    integralMapper.updateByPrimaryKeySelective(userIntegral);
                }else {
                    userIntegral.setId(UUIDUtils.generateUuid());
                    userIntegral.setUserId(userId);
                    userIntegral.setTaskPoints(integral);
                    userIntegral.setCreateBy(userId);
                    userIntegral.setCreateTime(new Date());
                    userIntegral.setTimeStamp(new Date());
                    integralMapper.insertSelective(userIntegral);
                }
            }
        }

    }
}