package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BizUserGradeRuleBiz;
import com.github.wxiaoqi.security.app.mapper.BizUserIntegralMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserSignLogMapper;
import com.github.wxiaoqi.security.app.vo.intergral.CalendarDate;
import com.github.wxiaoqi.security.app.vo.intergral.SignDetail;
import com.github.wxiaoqi.security.app.vo.intergral.in.FinishTaskParams;
import com.github.wxiaoqi.security.app.vo.intergral.in.UserSignIn;
import com.github.wxiaoqi.security.app.vo.intergral.out.*;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizUserIntegralBiz;
import com.github.wxiaoqi.security.app.entity.BizUserIntegral;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.netty.util.internal.UnstableApi;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 运营服务-用户综合积分表
 *
 * @author huangxl
 * @Date 2019-08-05 14:38:12
 */
@RestController
@RequestMapping("bizUserIntegral")
@CheckClientToken
@CheckUserToken
@Api(tags = "运营-用户签到信息")
public class BizUserIntegralController {

    @Autowired
    private BizUserIntegralBiz integralBiz;;
    @Autowired
    private BizUserGradeRuleBiz gradeRuleBiz;
    @Autowired
    private BizUserSignLogMapper userSignLogMapper;
    @Autowired
    private BizUserIntegralMapper userIntegralMapper;


    @GetMapping("getUserIntegral")
    @ApiOperation(value = "获取用户签到/每日任务信息", notes = "获取用户签到/每日任务信息",httpMethod = "GET")
    public ObjectRestResponse<UserIntegralInfo> getUserIntegral() {
        return integralBiz.getUserIntegralInfo();
    }

    @GetMapping("finishDailyTask")
    @ApiOperation(value = "完成每日任务", notes = "完成每日任务",httpMethod = "GET")
    @ApiImplicitParam(name="taskCode",value="任务参数(task_100-帖子点赞、task_101-帖子分享、task_102-阅读平台文章、task_103-浏览优选商城、task_104-与超级管家对话、task_105-使用智慧通行)",
            dataType="String",required = true ,paramType = "query",example="task_100")
    @IgnoreUserToken
    public ObjectRestResponse finishDailyTask(String taskCode,String userId) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(taskCode)) {
            response.setStatus(101);
            response.setMessage("任务参数为空");
            return response;
        }
        integralBiz.finishDailyTask(taskCode,userId);
        return response;
    }

    @GetMapping("userSign")
    @ApiOperation(value = "用户签到", notes = "用户签到",httpMethod = "GET")
    public ObjectRestResponse userSign(String projectId) {
        return integralBiz.userSign(projectId);
    }

    @GetMapping("getMyGradeAndTask")
    @ApiOperation(value = "获取我的等级和积分任务", notes = "获取我的等级和积分任务",httpMethod = "GET")
    public ObjectRestResponse<UserGradeVo> getMyGradeAndTask() {
        return gradeRuleBiz.getMyGradeAndTask();
    }

    @GetMapping("getRuleIntro")
    @ApiOperation(value = "获取规则介绍", notes = "获取规则介绍",httpMethod = "GET")
    public ObjectRestResponse<IntegralRuleVo> getRuleIntro() {
        return gradeRuleBiz.getIntegralRule();
    }

    @GetMapping("getIntegralLog")
    @ApiOperation(value = "获取积分日志详情", notes = "获取积分日志详情",httpMethod = "GET")
    public ObjectRestResponse<IntegralLogVo> getIntegralLog(Integer page,Integer limit) {
        return gradeRuleBiz.getIntegralLog(page,limit);
    }

    @GetMapping("getSignCalendar")
    @ApiOperation(value = "获取签到日历", notes = "获取签到日历",httpMethod = "GET")
    public ObjectRestResponse<SignDetail> getSignCalendar(String projectId) {
        Function<String, Optional<List<String>>> function = day -> {
            List<String> datas = new ArrayList<>();
            datas.add(day);
            return Optional.of(datas);
        };
        List<CalendarDate<List<String>>> signCalendar = gradeRuleBiz.getSignCalendar(function);
        int resignCount = 0;
        int resignCard =  userIntegralMapper.getResignCardCount(BaseContextHandler.getUserID());
        String cardId =  userIntegralMapper.getResignCardId(projectId);
        for (CalendarDate<List<String>> listCalendarDate : signCalendar) {
            resignCount = userSignLogMapper.getReSignCount(listCalendarDate.getDate().substring(0,7), BaseContextHandler.getUserID());
            if (resignCount == 3) {
                resignCount = 0;
            }else {
                resignCount = 3 - resignCount;
            }
        }
        String isHaveCard = "";
        if (resignCard == 0) {
            isHaveCard = "0";
        }else {
            isHaveCard = "1";
        }
        if (cardId == null) {
            cardId = "";
        }
        SignDetail signDetail = new SignDetail();
        signDetail.setResignCount(resignCount);
        signDetail.setCalenderList(signCalendar);
        signDetail.setIsHaveCard(isHaveCard);
        signDetail.setCardId(cardId);
        return ObjectRestResponse.ok(signDetail);
    }

    @GetMapping("reSign")
    @ApiOperation(value = "用户补签接口", notes = "用户补签接口",httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name="date",value="yyyy-MM-dd格式",
            dataType="String",required = true ,paramType = "query",example="2019-09-09"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="14da546d"),
            })
    public ObjectRestResponse reSign(String date,String projectId) {
        return gradeRuleBiz.reSign(date,projectId);

    }

    @GetMapping("getReSignLog")
    @ApiOperation(value = "获取补签记录", notes = "获取补签记录",httpMethod = "GET")
    public ObjectRestResponse<ResignLog> getReSignLog(Integer page, Integer limit) {
        return gradeRuleBiz.getReSignLog(page,limit);

    }


}