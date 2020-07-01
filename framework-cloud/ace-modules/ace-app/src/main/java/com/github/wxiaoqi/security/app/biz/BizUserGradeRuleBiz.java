package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizCashProduct;
import com.github.wxiaoqi.security.app.entity.BizUserIntegral;
import com.github.wxiaoqi.security.app.entity.BizUserSignLog;
import com.github.wxiaoqi.security.app.mapper.BizCashProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserIntegralMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserSignLogMapper;
import com.github.wxiaoqi.security.app.vo.intergral.CalendarDate;
import com.github.wxiaoqi.security.app.vo.intergral.CalendarSign;
import com.github.wxiaoqi.security.app.vo.intergral.in.UserSignIn;
import com.github.wxiaoqi.security.app.vo.intergral.out.*;
import com.github.wxiaoqi.security.app.vo.propertybill.out.UserBillList;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.IntervalUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.Factory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizUserGradeRule;
import com.github.wxiaoqi.security.app.mapper.BizUserGradeRuleMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

/**
 * 运营服务-用户等级规则表
 *
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@Service
public class BizUserGradeRuleBiz extends BusinessBiz<BizUserGradeRuleMapper,BizUserGradeRule> {

    @Autowired
    private BizUserIntegralMapper integralMapper;

    @Autowired
    private BizUserGradeRuleMapper gradeRuleMapper;

    @Autowired
    private BizUserSignLogMapper userSignLogMapper;
    @Autowired
    private BizCashProductMapper cashProductMapper;

    public ObjectRestResponse<UserGradeVo> getMyGradeAndTask() {
        UserGradeVo userGradeVo = new UserGradeVo();
        UserSignIn userInteral = integralMapper.getUserInteral(BaseContextHandler.getUserID());
        BizUserIntegral userIntegral = new BizUserIntegral();
        Integer total = 0;
        Integer historyTotal = 0;
        if (userInteral == null) {
            userIntegral.setId(UUIDUtils.generateUuid());
            userIntegral.setUserId(BaseContextHandler.getUserID());
            userIntegral.setSignCount(0);
            userIntegral.setSignPoints(0);
            userIntegral.setCreateBy(BaseContextHandler.getUserID());
            userIntegral.setCreateTime(new Date());
            userIntegral.setTimeStamp(new Date());
            integralMapper.insertSelective(userIntegral);
        }else {
//             total = userInteral.getSignPoints() + userInteral.getTaskPoints() - userInteral.getConsumePoints();
            historyTotal = userInteral.getSignPoints() + userInteral.getTaskPoints();
            userIntegral.setId(userInteral.getId());
            userIntegral.setHistoryPoints(historyTotal);
            integralMapper.updateByPrimaryKeySelective(userIntegral);
        }
        List<UserGradeVo> allGrade =  gradeRuleMapper.getGradeRule();
        for (int i = 0; i < allGrade.size(); i++) {
            if (i == allGrade.size() - 1) {
                String interval = "["+allGrade.get(i).getIntegral()+","+Double.POSITIVE_INFINITY+"]";
                if (IntervalUtil.isInTheInterval(String.valueOf(historyTotal), interval)) {
                    userGradeVo.setGradeImg(allGrade.get(i).getGradeImg());
                    userGradeVo.setGradeTitle(allGrade.get(i).getGradeTitle());
                    userGradeVo.setIntegral(0);
                    userGradeVo.setTotalIntegral(historyTotal);
                }
            }else {
                String interval = "["+allGrade.get(i).getIntegral()+","+allGrade.get(i+1).getIntegral()+"]";
                if (IntervalUtil.isInTheInterval(String.valueOf(historyTotal), interval)) {
                    userGradeVo.setGradeImg(allGrade.get(i).getGradeImg());
                    userGradeVo.setGradeTitle(allGrade.get(i).getGradeTitle());
                    userGradeVo.setIntegral(allGrade.get(i+1).getIntegral()- historyTotal);
                    userGradeVo.setTotalIntegral(historyTotal);
                }

            }
        }
        String today = DateUtils.dateToString(new Date(), DateUtils.NORMAL_DATE_FORMAT);
        List<DailyTask> todayTask = integralMapper.getTodayTask(today);
        List<TaskVo> task = new ArrayList<>();
        for (DailyTask dailyTask : todayTask) {
            TaskVo taskVo = new TaskVo();
            taskVo.setTaskIntegral(dailyTask.getTaskIntegral());
            taskVo.setTaskName(dailyTask.getTaskName());
            task.add(taskVo);
        }
        userGradeVo.setTask(task);
        return ObjectRestResponse.ok(userGradeVo);
    }

    public ObjectRestResponse<IntegralRuleVo> getIntegralRule() {
        IntegralRuleVo integralRuleVo = new IntegralRuleVo();
        List<UserGradeVo> gradeRule = gradeRuleMapper.getGradeRule();
        List<TaskVo> allTask = gradeRuleMapper.getAllTask();
        List<SignInfo> allSignDay = gradeRuleMapper.getAllSignDay();
        int normalSign = gradeRuleMapper.getNormalSign();
        String taskName = "";
        StringBuilder taskSB = new StringBuilder();
        for (TaskVo taskVo : allTask) {
            taskSB.append(taskVo.getTaskName()+"、");
        }
        taskName = taskSB.substring(0, taskSB.length() - 1);
        integralRuleVo.setUserGradeVo(gradeRule);
        integralRuleVo.setTaskVoList(allTask);
        String taskRule = "1.每天从全部任务中随机生成五个每日任务，用户可完成每日任务获得积分。\n"+"2.完成"+taskName+"可分别获得相应的积分。";
        integralRuleVo.setTaskRuleVo(taskRule);
        String signDayStr = "";
        String integralStr = "";
        StringBuilder daySB = new StringBuilder();
        StringBuilder interalSB = new StringBuilder();
        for (SignInfo signInfo : allSignDay) {
            daySB.append(signInfo.getDay() + "天" + "、");
            interalSB.append("连续签到"+signInfo.getDay()+"天"+"单日签到可获得"+signInfo.getIntegral()+"积分\n");
        }
        signDayStr = daySB.substring(0, daySB.length() - 1);
        integralStr = interalSB.toString();//
        String signRule = "1.每天可签到一次，连续签到积分奖励增加；\n"
                        + "2.可连续签到，若签到中断，需从头再来；\n"
                        +"3.签到中断后，可当月使用补签卡进行补签，每月可最多补签3次，补签卡可从积分商城兑换；\n"
                        +"4.每日正常签到： "+normalSign+"积分；\n"
                        + "特殊日期设置：" + signDayStr+"；\n"
                         +"特殊日期签到奖励：\n" + integralStr;
        integralRuleVo.setSignRuleVo(signRule);
        return ObjectRestResponse.ok(integralRuleVo);
    }

    public ObjectRestResponse<IntegralLogVo> getIntegralLog(Integer page,Integer limit) {
        IntegralLogVo integralLogVo = new IntegralLogVo();
        UserSignIn userInteral = integralMapper.getUserInteral(BaseContextHandler.getUserID());
        BizUserIntegral userIntegral = new BizUserIntegral();
        List<IntegralDetailVo> integralLog = new ArrayList<>();
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
        Integer total = 0;
        if (userInteral == null) {
            userIntegral.setId(UUIDUtils.generateUuid());
            userIntegral.setUserId(BaseContextHandler.getUserID());
            userIntegral.setSignCount(0);
            userIntegral.setSignPoints(0);
            userIntegral.setCreateBy(BaseContextHandler.getUserID());
            userIntegral.setCreateTime(new Date());
            userIntegral.setTimeStamp(new Date());
            integralMapper.insertSelective(userIntegral);
        }else {
            total = userInteral.getSignPoints() + userInteral.getTaskPoints() - userInteral.getConsumePoints();
            List<IntegralDetailVo> signLog =  integralMapper.getIntegralLog(BaseContextHandler.getUserID(),startIndex,limit);
            integralLog.addAll(signLog);
        }
        integralLogVo.setCurIntegral(total);
        integralLogVo.setIntegralLog(integralLog);
        return ObjectRestResponse.ok(integralLogVo);
    }

    public <T> List<CalendarDate<T>> getSignCalendar(Function<String, Optional<T>> function) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        List<CalendarDate<T>> calenderList = new ArrayList<>();
        List<CalendarDate<T>> calendarDates = makeSignCalendar(year, month, function);
        String date = "";
        if (month < 10) {
            date = year + "-0" + month;
        }else {
            date = year + "-" + month;
        }
        List<CalendarSign> userSigns = integralMapper.getIntegralCalendar(date, BaseContextHandler.getUserID());
        for (CalendarDate<T> calendarDate : calendarDates) {
            CalendarDate<T> info = new CalendarDate<>();
            calendarDate.setIsSign("2");
            for (CalendarSign userSign : userSigns) {
                if (userSign.getSignDate().equals(calendarDate.date)) {
                    if ("0".equals(userSign.getSignType())) {
                        calendarDate.setIsSign("0");
                    }else {
                        calendarDate.setIsSign("1");
                    }
                    break;
                }
            }
            calenderList.add(calendarDate);
        }
        return calenderList;
    }

    public <T> List<CalendarDate<T>> makeSignCalendar(int year, int month, Function<String, Optional<T>> function) {
        List<CalendarDate<T>> cdList = new ArrayList<>();
        int monthDays = monthDays(year, month);
        CalendarDate<T> cdR;
        for (int day = 1; day <= monthDays; day++) {
            cdR = new CalendarDate<>();
            cdR.day = day;
            LocalDate date = LocalDate.of(year, month, day);
            cdR.weekDay = dayOfWeek(date);
            cdR.isToday = isToday(date);
            if (function != null) {
                Optional<T> optional = function.apply(date.toString());
                if (optional.isPresent()) {
                    cdR.info = optional.get();
                    cdR.date = optional.get().toString().replace("[", "").replaceAll("]","");
                }
            }
            cdList.add(cdR);
        }
        return cdList;
    }

    private static String isToday(LocalDate date) {
        LocalDate today = LocalDate.now();
        String isToday = "";
        if (date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDayOfMonth() == today.getDayOfMonth()){
            isToday = "1";
        }else {
            isToday = "0";
        }
        return isToday;
    }

    private static String dayOfWeek(LocalDate date) {
        String week = "";
        if (date != null) {
            String[] weekDays = {"一", "二", "三", "四", "五", "六","日"};
            int w = date.getDayOfWeek().getValue()-1;
            if (w < 0) {
                w = 0;
            }
            week = weekDays[w];
        }
//        return null == date ? 0 : date.getDayOfWeek().getValue();
        return week;
    }


    private static int monthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    public ObjectRestResponse reSign(String date,String projectId) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizUserIntegral userIntegral = new BizUserIntegral();
        BizUserSignLog userSignLog = new BizUserSignLog();
        int result =  userSignLogMapper.getIsReSign(date,BaseContextHandler.getUserID());
        int resignCard =  integralMapper.getResignCardCount(BaseContextHandler.getUserID());
        int resignCount = userSignLogMapper.getReSignCount(date.substring(0,7), BaseContextHandler.getUserID());
        try {
            if (DateUtils.stringToDate(date,"yyyy-MM-dd").after(Calendar.getInstance().getTime())) {
                response.setStatus(501);
                response.setMessage("不能补签大于今天的日期");
                return response;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (resignCard == 0) {
            response.setStatus(501);
            response.setMessage("无补签卡，是否前去兑换");
            return response;
        }
        if (result > 0) {
            response.setStatus(501);
            response.setMessage("不能重复补签！");
            return response;
        }
        if (resignCount >= 3) {
            response.setStatus(501);
            response.setMessage("本月已补签3次，无法再次补签");
            return response;
        }
        try {
            //增加补签日期
            Date signDate= DateUtils.stringToDate(date, "yyyy-MM-dd");
            userSignLog.setId(UUIDUtils.generateUuid());
            userSignLog.setUserId(BaseContextHandler.getUserID());
            userSignLog.setSignDate(signDate);
            userSignLog.setCreateTime(new Date());
            userSignLog.setCreateBy(BaseContextHandler.getUserID());
            userSignLog.setSignType("1");
            userSignLog.setProjectId(projectId);
            userSignLog.setTimeStamp(new Date());
            List<SignInfo> signInfos =  integralMapper.getAllSignRule();
            int signPoints = 0;
            for (SignInfo signInfo : signInfos) {
                if (0 == signInfo.getDay()) {
                    signPoints = signInfo.getIntegral();
                    break;
                }
            }
            userSignLog.setSignPoint(signPoints);
            UserSignIn userSign =  integralMapper.getUserInteral(BaseContextHandler.getUserID());
            if (userSignLogMapper.insertSelective(userSignLog)>0) {
                List<String> dateList =  userSignLogMapper.getAllSign(BaseContextHandler.getUserID());
                int signCount = getSignCount(dateList);
                //更新连续天数和签到积分
                userIntegral.setId(userSign.getId());
                userIntegral.setSignPoints(userSign.getSignPoints()+signPoints);
                userIntegral.setSignCount(signCount);
                integralMapper.updateByPrimaryKeySelective(userIntegral);
                //更新补签卡使用记录
                String cardId =  integralMapper.getResignCard(BaseContextHandler.getUserID());
                BizCashProduct cashProduct = new BizCashProduct();
                cashProduct.setId(cardId);
                cashProduct.setUseTime(new Date());
                cashProduct.setSignDate(signDate);
                cashProduct.setModifyBy(BaseContextHandler.getUserID());
                cashProduct.setModifyTime(new Date());
                cashProduct.setStatus("0");
                cashProductMapper.updateByPrimaryKeySelective(cashProduct);
                response.setMessage("补签成功：+"+signPoints+"积分");
            }
        } catch (ParseException e) {
            response.setStatus(501);
            response.setMessage("补签失败！");
            return response;
        }
        return response;
    }

    /**
     * 获取连续天数
     * @param dateList
     * @return
     * @throws ParseException
     */
    public int getSignCount(List<String> dateList) throws ParseException {
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
        //判断补签后连续天数
        for (int i = dates.size() - 1; i > 0; i--) {
            if ((dates.get(i).getTime() - dates.get(i - 1).getTime()) / (1000 * 60 * 60 * 24) == 1) {
                count++;
            }else {
                break;
            }
        }
        return count;
    }


    public ObjectRestResponse getReSignLog(Integer page,Integer limit) {

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
        List<ResignLog> resignLogs = userSignLogMapper.getResignLog(BaseContextHandler.getUserID(),startIndex, limit);
        if (resignLogs.size()==0) {
            resignLogs = new ArrayList<>();
        }
        return ObjectRestResponse.ok(resignLogs);
    }
}