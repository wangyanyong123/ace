package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizUserGradeRuleBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizUserGradeRule;
import com.github.wxiaoqi.security.jinmao.vo.grade.in.GradeParams;
import com.github.wxiaoqi.security.jinmao.vo.grade.out.GradeList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 运营服务-用户等级规则表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@RestController
@RequestMapping("bizUserGradeRule")
@CheckClientToken
@CheckUserToken
@Api(value = "等级管理")
public class BizUserGradeRuleController {
 
    
    @Autowired
    private BizUserGradeRuleBiz gradeRuleBiz;

    @GetMapping("getGradeList")
    @ApiOperation(value = "获取等级列表", notes = "获取等级列表",httpMethod = "GET")
    public TableResultResponse getGradeList(String searchVal,Integer page, Integer limit){
        List<GradeList> taskList =  gradeRuleBiz.getGradeList(searchVal,page,limit);
        int total =  gradeRuleBiz.getGradeListTotal(searchVal);
        return new TableResultResponse(total,taskList);
    }

    @GetMapping("getGradeDetail")
    @ApiOperation(value = "获取等级详情", notes = "获取等级详情",httpMethod = "GET")
    public ObjectRestResponse getGradeDetail(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        return gradeRuleBiz.getGradeDetail(id);
    }

    @DeleteMapping("deleteGrade")
    @ApiOperation(value = "删除等级列表", notes = "删除等级列表",httpMethod = "DELETE")
    public ObjectRestResponse deleteGrade(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        return gradeRuleBiz.deleteGrade(id);
    }

    @PostMapping("createGrade")
    @ApiOperation(value = "保存等级", notes = "保存等级",httpMethod = "POST")
    public ObjectRestResponse createGrade(@RequestBody @ApiParam GradeParams params){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(params.getGradeTitle())) {
            response.setStatus(101);
            response.setMessage("等级头衔不能为空");
            return response;
        }
        if (StringUtils.isEmpty(String.valueOf(params.getIntegral()))) {
            response.setStatus(101);
            response.setMessage("积分不能为空");
            return response;
        }
        if (StringUtils.isEmpty(params.getImgId())) {
            response.setStatus(101);
            response.setMessage("图片不能为空");
            return response;
        }
        return gradeRuleBiz.createGrade(params);
    }

    @PostMapping("updateGrade")
    @ApiOperation(value = "修改等级", notes = "修改等级",httpMethod = "POST")
    public ObjectRestResponse updateGrade(@RequestBody @ApiParam GradeParams params){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(params.getGradeTitle())) {
            response.setStatus(101);
            response.setMessage("等级类型不能为空");
            return response;
        }
        if (StringUtils.isEmpty(String.valueOf(params.getIntegral()))) {
            response.setStatus(101);
            response.setMessage("积分不能为空");
            return response;
        }
        if (StringUtils.isEmpty(params.getId())) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }

        if (StringUtils.isEmpty(params.getImgId())) {
            response.setStatus(101);
            response.setMessage("图片不能为空");
            return response;
        }
        return gradeRuleBiz.updateGrade(params);
    }
}