package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.vo.city.out.UnitInfoVo;
import com.github.wxiaoqi.security.app.vo.decision.in.QueryDecisionRequest;
import com.github.wxiaoqi.security.app.vo.decision.in.SubmitVoteRequest;
import com.github.wxiaoqi.security.app.vo.decision.out.DecisionInfoVo;
import com.github.wxiaoqi.security.app.vo.decision.out.DecisionListVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.app.biz.BizDecisionBiz;
import com.github.wxiaoqi.security.app.entity.BizDecision;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 决策表
 *
 * @author guohao
 * @Date 2020-06-04 21:29:06
 */
@RestController
@RequestMapping("bizDecision")
@CheckClientToken
@CheckUserToken
public class BizDecisionController extends BaseController<BizDecisionBiz,BizDecision,String> {

    @PostMapping(value = "/submitVote")
    @ApiOperation(value = "提交决策投票", notes = "提交决策投票",httpMethod = "POST")
    public ObjectRestResponse submitVote(SubmitVoteRequest request){
        this.baseBiz.submitVote(request);
        return ObjectRestResponse.ok();
    }
    @GetMapping(value = "/findInfo")
    @ApiOperation(value = "获取决策信息", notes = "获取决策信息",httpMethod = "GET")
    public ObjectRestResponse<DecisionInfoVo> findInfo(String decisionId){
        DecisionInfoVo infoVo = this.baseBiz.findInfoVo(decisionId);
        return ObjectRestResponse.ok(infoVo);
    }

    @PostMapping(value = "/findList")
    @ApiOperation(value = "获取项目决策列表", notes = "获取决策信息",httpMethod = "POST")
    public ObjectRestResponse<DecisionListVo> findList(QueryDecisionRequest request){
        List<DecisionListVo> list = this.baseBiz.findList(request);
        return ObjectRestResponse.ok(list);

    }


}