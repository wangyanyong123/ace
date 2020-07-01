package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizTaskIntergralRuleBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizTaskIntergralRule;
import com.github.wxiaoqi.security.jinmao.vo.task.TaskList;
import com.github.wxiaoqi.security.jinmao.vo.task.TaskParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 运营服务-任务规则管理表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:53
 */
@RestController
@RequestMapping("bizTaskIntergralRule")
@CheckClientToken
@CheckUserToken
@Api(value = "任务管理")
public class BizTaskIntergralRuleController {

    @Autowired
    private BizTaskIntergralRuleBiz intergralRuleBiz;


    @GetMapping("getTaskList")
    @ApiOperation(value = "获取每日任务", notes = "获取每日任务",httpMethod = "GET")
    public TableResultResponse getTask(String searchVal,Integer page,Integer limit){
        List<TaskList> taskList =  intergralRuleBiz.getTask(searchVal,page,limit);
        int total =  intergralRuleBiz.getTaskTotal(searchVal);
        return new TableResultResponse(total,taskList);
    }


    @PostMapping("updateTask")
    @ApiOperation(value = "修改每日任务", notes = "修改每日任务",httpMethod = "POST")
    public ObjectRestResponse updateTask(@RequestBody @ApiParam TaskParams params){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(params.getId())) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        return intergralRuleBiz.updateTask(params);
    }


    @GetMapping("getTaskDetail")
    @ApiOperation(value = "获取每日任务", notes = "获取每日任务",httpMethod = "GET")
    public ObjectRestResponse getTaskDetail(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }

        return intergralRuleBiz.getTaskDetail(id);
    }

}