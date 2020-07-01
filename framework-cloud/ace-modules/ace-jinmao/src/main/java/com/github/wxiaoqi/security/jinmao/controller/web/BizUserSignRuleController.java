package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizUserSignRuleBiz;
import com.github.wxiaoqi.security.jinmao.vo.sign.in.SignParams;
import com.github.wxiaoqi.security.jinmao.vo.sign.out.SignList;
import com.github.wxiaoqi.security.jinmao.vo.sign.out.StatDataVo;
import com.github.wxiaoqi.security.jinmao.vo.stat.in.DataExcel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 运营服务-签到规则表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@RestController
@RequestMapping("bizUserSignRule")
@CheckClientToken
@CheckUserToken
@Api(value = "签到管理")
public class BizUserSignRuleController {

    @Autowired
    private BizUserSignRuleBiz signRuleBiz;

    @GetMapping("getSignList")
    @ApiOperation(value = "获取签到列表", notes = "获取签到列表",httpMethod = "GET")
    public TableResultResponse getSignList(String searchVal,String signType, Integer page, Integer limit){
        List<SignList> taskList =  signRuleBiz.getSignList(searchVal,signType,page,limit);
        int total =  signRuleBiz.getSignListTotal(searchVal,signType);
        return new TableResultResponse(total,taskList);
    }

    @GetMapping("getSignDetail")
    @ApiOperation(value = "获取签到详情", notes = "获取签到详情",httpMethod = "GET")
    public ObjectRestResponse getSignDetail(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        return signRuleBiz.getSignDetail(id);
    }

    @DeleteMapping("deleteSign")
    @ApiOperation(value = "删除签到列表", notes = "删除签到列表",httpMethod = "DELETE")
    public ObjectRestResponse deleteSign(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        return signRuleBiz.deleteSign(id);
    }

    @PostMapping("createSign")
    @ApiOperation(value = "保存签到", notes = "保存签到",httpMethod = "POST")
    public ObjectRestResponse createSign(@RequestBody @ApiParam SignParams params){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(params.getSignType())) {
            response.setStatus(101);
            response.setMessage("签到类型不能为空");
            return response;
        }
        if (StringUtils.isEmpty(String.valueOf(params.getIntegral()))) {
            response.setStatus(101);
            response.setMessage("积分不能为空");
            return response;
        }
        return signRuleBiz.createSign(params);
    }

    @PostMapping("updateSign")
    @ApiOperation(value = "修改签到", notes = "修改签到",httpMethod = "POST")
    public ObjectRestResponse updateSign(@RequestBody @ApiParam SignParams params){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(params.getSignType())) {
            response.setStatus(101);
            response.setMessage("签到类型不能为空");
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
        return signRuleBiz.updateSign(params);
    }


    /**
     * 获取签到统计数据列表
     * @return
     */
    @RequestMapping(value = "/getStatSignList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取签到统计数据列表---PC端", notes = "获取签到统计数据列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="dataType",value="1-天,2-周,3-月",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<StatDataVo> getStatSignList(String projectId,String dataType, String startTime, String endTime,
                                                           Integer page, Integer limit){
        TableResultResponse msg = new TableResultResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t2 = "1";
        if(startTime != null  && endTime != null ) {
            int num = 0;
            try {
                num = DateUtils.getDaysBetween(sdf.parse(startTime), sdf.parse(endTime));
                if (t2.equals(dataType)) {
                    //天
                    if (num > 31) {
                        msg.setStatus(1001);
                        msg.setMessage("起始日期与截止日期不能超过31天");
                        return msg;
                    }
                } else {
                    if (num > 365) {
                        msg.setStatus(1001);
                        msg.setMessage("起始日期与截止日期不能超过1年");
                        return msg;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<StatDataVo> statDataVoList =  signRuleBiz.getStatSignList(projectId,dataType, startTime, endTime, page, limit);
        int total = signRuleBiz.selectStatSignCount(projectId, dataType, startTime, endTime);
        return new TableResultResponse<StatDataVo>(total, statDataVoList);
    }



    /**
     * 导出统计数据excel
     * @return
     */
    @RequestMapping(value = "/exportDataSignExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出统计数据excel---PC端", notes = "导出统计数据excel---PC端",httpMethod = "POST")
    public ObjectRestResponse exportDataSignExcel(@RequestBody @ApiParam DataExcel excel){
        return signRuleBiz.exportDataSignExcel(excel.getProjectId(), excel.getDataType(), excel.getStartTime(), excel.getEndTime());
    }









}