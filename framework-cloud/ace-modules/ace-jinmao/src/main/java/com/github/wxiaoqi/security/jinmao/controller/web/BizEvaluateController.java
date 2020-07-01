package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizEvaluateBiz;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.EvaluateVo;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.HousekeeperInfo;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.PropertyEvaluateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管家评价表
 *
 * @author huangxl
 * @Date 2019-02-19 16:55:19
 */
@RestController
@RequestMapping("web/bizEvaluate")
@CheckClientToken
@CheckUserToken
@Api(tags = "评价管理")
public class BizEvaluateController {

    @Autowired
    private BizEvaluateBiz bizEvaluateBiz;


    /**
     * 查询管家评价列表
     * @return
     */
    @RequestMapping(value = "/getEvaluateListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询管家评价列表---PC端", notes = "查询管家评价列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="评价时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="evaluateType",value="评价等级(1-不满意,2-一般,3-满意)",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据用户名称,管家名称模糊查询",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="housekeeperId",value="管家id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<EvaluateVo> getEvaluateListPc(String time, String evaluateType, String searchVal,String projectId, String housekeeperId,
    Integer page, Integer limit){
        List<EvaluateVo> evaluateVoList = bizEvaluateBiz.getEvaluateList(time, evaluateType, searchVal, projectId, housekeeperId, page, limit);
        int total = bizEvaluateBiz.selectEvaluateCount(time, evaluateType, searchVal, housekeeperId,projectId);
        return new TableResultResponse<EvaluateVo>(total, evaluateVoList);
    }

    /**
     * 查询租户下所属管家
     * @return
     */
    @RequestMapping(value = "/getHousekeeperListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询租户下所属管家---PC端", notes = "查询租户下所属管家---PC端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    public TableResultResponse<HousekeeperInfo> getHousekeeperListPc(String projectId){
        List<HousekeeperInfo> HousekeeperList = bizEvaluateBiz.getHousekeeperList(projectId);
        return new TableResultResponse<HousekeeperInfo>(HousekeeperList.size(), HousekeeperList);
    }

    /**
     * 查询物业评价列表
     * @return
     */
    @RequestMapping(value = "/getPropertyEvaluateListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业评价列表---PC端", notes = "查询物业评价列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="评价时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="evaluateType",value="评价等级(1-不满意,2-一般,3-满意)",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据用户名称模糊查询",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<PropertyEvaluateVo> getPropertyEvaluateListPc(String time, String evaluateType, String searchVal, String projectId,
                                                                     Integer page, Integer limit){
        List<PropertyEvaluateVo> evaluateVoList = bizEvaluateBiz.getPropertyEvaluateList(time, evaluateType, searchVal, projectId, page, limit);
        int total = bizEvaluateBiz.selectPropertyEvaluateCount(time, evaluateType, searchVal, projectId );
        return new TableResultResponse<PropertyEvaluateVo>(total, evaluateVoList);
    }





}