package com.github.wxiaoqi.log.biz;

import com.github.wxiaoqi.log.entity.BizLoginLog;
import com.github.wxiaoqi.log.mapper.BizLoginLogMapper;
import com.github.wxiaoqi.log.vo.liveness.LivenessTable;
import com.github.wxiaoqi.log.vo.liveness.UserLiveness;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.IntFunction;

/**
 * 
 *
 * @author zxl
 * @Date 2019-04-08 15:50:07
 */
@Service
@Slf4j
public class BizLoginLogBiz extends BusinessBiz<BizLoginLogMapper,BizLoginLog> {

    @Autowired
    private BizLoginLogMapper loginLogMapper;

    public ObjectRestResponse getUserLivenessCount(String projectId, String dateType, String year,
                                                   String mouth, String week, String startTime, String endTime) {
        ObjectRestResponse response = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int num = 0;
        try {
                num = DateUtils.getDaysBetween(sdf.parse(startTime), sdf.parse(endTime));
            if (num >= 31 && "day".equals(dateType)) {
                response.setStatus(1001);
                response.setMessage("起始日期与截止日期不能超过31天");
                return response;
            }else if (num >= 365 && "week".equals(dateType)){
                response.setStatus(1001);
                response.setMessage("起始日期与截止日期不能超过一年");
                return response;
            } else if (num >= 365 && "month".equals(dateType)) {
                response.setStatus(1001);
                response.setMessage("起始日期与截止日期不能超过一年");
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<UserLiveness> livenesses = loginLogMapper.getCertifiedUser(projectId,startTime,endTime);
        List<UserLiveness> liveByTime = new ArrayList<>();
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("startTime", startTime);
        dateMap.put("endTime", endTime);
        //年
        if ("year".equals(dateType)) {
            liveByTime =  loginLogMapper.getLivenessByYear(projectId,startTime,endTime);
        }else if ("season".equals(dateType)){
            List<UserLiveness> newSeason = new ArrayList<>();
            liveByTime = loginLogMapper.getLivenessBySeason(projectId,startTime,endTime);
//            for (UserLiveness userLiveness : liveByTime) {
//                if ("1".equals(userLiveness.getTime()) || "2".equals(userLiveness.getTime()) ||
//                        "3".equals(userLiveness.getTime()) || "4".equals(userLiveness.getTime())) {
//                    userLiveness.setTime(userLiveness.getYear()+"第"+userLiveness.getTime()+"季度");
//                    for (int i = 1; i <= 4; i++) {
//                        UserLiveness season = new UserLiveness();
//                        if (!String.valueOf(i).equals(userLiveness.getTime())){
//                            season.setTime(userLiveness.getYear()+"第"+i+"季度");
//                            season.setYear(userLiveness.getYear());
//                            season.setLiveCount("0");
//                            newSeason.add(season);
//                        }else {
//                            season.setTime(userLiveness.getYear()+"第"+i+"季度");
//                            season.setYear(userLiveness.getYear());
//                            season.setLiveCount(userLiveness.getLiveCount());
//                            if (!newSeason.contains(season)) {
//                                newSeason.add(season);
//                            }
//                        }
//                    }
//                }else {
//                    userLiveness.setTime(userLiveness.getYear()+"第1季度");
//                    for (int i = 2; i <= 4; i++) {
//                        UserLiveness season = new UserLiveness();
//                        season.setTime(userLiveness.getYear()+"第"+i+"季度");
//                        season.setYear(userLiveness.getYear());
//                        season.setLiveCount("0");
//                        newSeason.add(season);
//                    }
//                }
//            }
//            newSeason = StringUtils.removeDuplicate(newSeason);
//            for (UserLiveness liveness : liveByTime) {
//                for (UserLiveness userLiveness : newSeason) {
//                    if (userLiveness.getTime().equals(liveness.getTime())) {
//                        if ("0" == (userLiveness.getLiveCount())){
//                            userLiveness.setLiveCount(liveness.getLiveCount());
//                        }
//                        break;
//                    }
//                }
//            }
//            liveByTime.addAll(newSeason);
//            liveByTime = StringUtils.removeDuplicate(liveByTime);
//            Collections.sort(liveByTime,Comparator.comparing(UserLiveness::getTime).reversed());
        }else if ("month".equals(dateType)){
            liveByTime = loginLogMapper.getLivenessByMouth(projectId,startTime,endTime);
        }else if ("week".equals(dateType)){
            liveByTime = loginLogMapper.getLivenessByWeek(projectId,startTime,endTime);
            for (UserLiveness userLiveness : liveByTime) {
                userLiveness.setTime(userLiveness.getTime());
            }
        }else if ("day".equals(dateType)) {
            liveByTime = loginLogMapper.getLivenessByDay(projectId, startTime, endTime);
            liveByTime = getDataCompletion(liveByTime, dateMap);
            Collections.sort(liveByTime,Comparator.comparing(UserLiveness::getTime).reversed());
        }
        for (UserLiveness userLiveness : liveByTime) {
            if (livenesses.size() > 0) {
                for (UserLiveness liveness : livenesses) {
                    userLiveness.setCertifiedCount(liveness.getCertifiedCount());
                    userLiveness.setLivePercent(division(Integer.parseInt(userLiveness.getLiveCount()),Integer.parseInt(userLiveness.getCertifiedCount())));
                }
            }else {
                userLiveness.setLivePercent("0%");
                userLiveness.setCertifiedCount("0");
            }
        }


        List<String> date = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (UserLiveness userLiveness : liveByTime) {
            date.add(userLiveness.getTime());
            count.add(Integer.parseInt(userLiveness.getLiveCount()));
        }
        LivenessTable livenessTable = new LivenessTable();
        livenessTable.setLiveList(liveByTime);
        livenessTable.setCount(count);
        livenessTable.setDate(date);

        response.setData(livenessTable);
        return response;
    }

    public static List<UserLiveness> getYearData(List<UserLiveness> data,Map<String, String> dateMap) {
        int startYear = Integer.valueOf(dateMap.get("startTime"));
        int endYear = Integer.valueOf(dateMap.get("endTime"));
        int loop = endYear - startYear;
        for (UserLiveness datum : data) {
            for (int i = 0; i <= loop; i++) {
                if ((endYear - i + "").equals(datum.getTime())) {
//                    break;
                }else {
                    UserLiveness userliveness= new UserLiveness();
                    userliveness.setTime(endYear - i + "");
                    userliveness.setLiveCount("0");
                    data.add(userliveness);
                }
            }
        }
        return data;
    }


    public static String  division(int num1,int num2){
        String rate="0.00%";
        //定义格式化起始位数
        String format="0.00";
        if(num2 != 0 && num1 != 0){
            DecimalFormat dec = new DecimalFormat(format);
            rate =  dec.format((double) num1 / num2*100)+"%";
            while(true){
                if(rate.equals(format+"%")){
                    format=format+"0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate =  dec1.format((double) num1 / num2*100)+"%";
                }else {
                    break;
                }
            }
        }else if(num1 != 0 && num2 == 0){
            rate = "100%";
        }
        return rate;
    }



    public static List<UserLiveness> getDataCompletion (List<UserLiveness> beforeList, Map<String, String> paraMap) {

        List<UserLiveness> dateResult = new ArrayList<>();
        Date dateBegin, dateEnd;
        int days= 0;
        Calendar calendar10 = Calendar.getInstance();
        Calendar calendar5 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String projectId = "";
        String projectName = "";

        try {
            dateBegin = sdf.parse(paraMap.get("startTime"));
            dateEnd = sdf.parse(paraMap.get("endTime"));
            /*
             * 计算开始时间和结束时间之间有几天
             * 如果想显示 01, 02, 03 三天的数据 结束日期需要传04, 因为01日 00:00 -- 04日 00:00 并不包括04
             */
            days = (int) ((dateEnd.getTime() - dateBegin.getTime()) / (1000*3600*24)) + 1;


            calendar10.setTime(dateBegin);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        //循环处理日期数据，把缺失的日期补全。days是时间段内的天数, beforList.size()是要处理的日期集合的天数
        for (int curr = 0; curr < days; curr++) {
            boolean dateExist = false;
            int index = 0;

            for(int i  = 0 ; i < beforeList.size() ; i++){

                try {
                    UserLiveness testaa = beforeList.get(i);
                    Date date2 = sdf.parse(testaa.getTime());
                    calendar5.setTime(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(calendar10.compareTo(calendar5) == 0){
                    dateExist  = true;
                    index = i;
                    break;
                }
            }
            if(dateExist){
                UserLiveness afterDate = beforeList.get(index);
                dateResult.add(afterDate);
            }else{
                UserLiveness complateData= new UserLiveness();
                complateData.setProjectId(projectId);
                complateData.setProjectName(projectName);
                complateData.setTime(sdf.format(calendar10.getTime()));
                complateData.setLiveCount("0");
                dateResult.add(complateData);
            }
            //修改外层循环变量, 是calendar10 +1天, 一天后的日期
            calendar10.add(Calendar.DAY_OF_MONTH, 1 );
//            calendar10.add(Calendar.DAY_OF_YEAR,1);
//            calendar10.add(Calendar.DAY_OF_MONTH,1);
        }


        return dateResult;
    }
}