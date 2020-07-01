package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizDecisionBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizDecision;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.EditDecisionRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.in.QueryDecisionRequest;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.decision.out.DecisionListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 决策表
 *
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
@RestController
@RequestMapping("bizDecision")
@CheckClientToken
@CheckUserToken
public class BizDecisionController {

    @Autowired
    private BizDecisionBiz bizDecisionBiz;

    @PostMapping(value = "edit")
    public ObjectRestResponse edit(@RequestBody EditDecisionRequest request){
        bizDecisionBiz.edit(request);
        return ObjectRestResponse.ok();
    }
    @GetMapping(value = "findInfo")
    public ObjectRestResponse findInfo(String id){
        DecisionInfoVo info = bizDecisionBiz.findInfo(id);
        return ObjectRestResponse.ok(info);
    }
    @PostMapping(value = "search")
    public TableResultResponse<DecisionListVo> search(QueryDecisionRequest request){
        int count = bizDecisionBiz.countList(request);
        List<DecisionListVo> list;
        if(count >0){
            list = bizDecisionBiz.findList(request);
        }else{
            list = Collections.emptyList();
        }
        return new TableResultResponse<>(count, list);
    }

    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public ObjectRestResponse deleteById(@RequestParam String id,String remark){
        bizDecisionBiz.deleteById(id,remark);
        return ObjectRestResponse.ok();
    }
}