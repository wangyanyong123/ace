package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizIntegralRuleBiz;
import com.github.wxiaoqi.security.jinmao.vo.rule.in.SaveRuleParam;
import com.github.wxiaoqi.security.jinmao.vo.rule.in.UpdateRuleStatus;
import com.github.wxiaoqi.security.jinmao.vo.rule.out.RuleVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人积分规则配置表
 *
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@RestController
@RequestMapping("web/bizIntegralRule")
@CheckClientToken
@CheckUserToken
@Api(tags = "积分规则管理")
public class BizIntegralRuleController {

    @Autowired
    private BizIntegralRuleBiz bizIntegralRuleBiz;


    /**
     * 查询积分规则列表
     * @return
     */
    @RequestMapping(value = "/getRuleListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分规则列表---PC端", notes = "查询积分规则列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="适用维度(1-个人，2-小组)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="ruleStatus",value="状态查询(1-草稿，2-已启用，3-已停用,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据规则编码,名称,维度模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<RuleVo> getRuleListPc(String type, String ruleStatus, String searchVal, Integer page, Integer limit){
        List<RuleVo> ruleList = bizIntegralRuleBiz.getRuleList(type, ruleStatus, searchVal, page, limit);
        int total =bizIntegralRuleBiz.selectRuleCount(type,ruleStatus, searchVal);
        return new TableResultResponse<RuleVo>(total, ruleList);
    }




    /**
     * 保存积分规则
     * @param params
     * @return
     */
    @RequestMapping(value = "/SaveIntegraRulePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存积分规则---PC端", notes = "保存积分规则---PC端",httpMethod = "POST")
    public ObjectRestResponse SaveIntegraRulePc(@RequestBody @ApiParam SaveRuleParam params){
        return bizIntegralRuleBiz.SaveIntegraRule(params);
    }


    /**
     * 积分规则操作(0-删除,2-已启用,3-已停用)
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateRuleStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "积分规则操作(0-删除,2-已启用,3-已停用)---PC端", notes = "积分规则操作(0-删除,2-已启用,3-已停用)---PC端",httpMethod = "POST")
    public ObjectRestResponse updateRuleStatusPc(@RequestBody @ApiParam UpdateRuleStatus params){
        return bizIntegralRuleBiz.updateRuleStatus(params);
    }




    /**
     * 查询积分规则详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getRuleInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分规则详情---PC端", notes = "查询积分规则详情---PC端",httpMethod = "GET")
    public TableResultResponse<RuleVo> getRuleInfoPc(@PathVariable String id){
        List<RuleVo> ruleInfo = bizIntegralRuleBiz.getRuleInfo(id);
        return new TableResultResponse<RuleVo>(ruleInfo.size(),ruleInfo);
    }



    /**
     * 编辑积分规则
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateIntegraRulePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑积分规则---PC端", notes = "编辑积分规则---PC端",httpMethod = "POST")
    public ObjectRestResponse updateIntegraRulePc(@RequestBody @ApiParam SaveRuleParam params){
        return bizIntegralRuleBiz.updateIntegraRule(params);
    }













}