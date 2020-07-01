package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.SysCoverageStatBiz;
import com.github.wxiaoqi.security.jinmao.vo.CoverageStat.CoverageStatVo;
import com.github.wxiaoqi.security.jinmao.vo.CoverageStat.StatExcel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 覆盖率统计表
 *
 * @author huangxl
 * @Date 2019-10-28 09:57:52
 */
@RestController
@RequestMapping("web/sysCoverageStat")
@CheckClientToken
@CheckUserToken
@Api(tags="覆盖率统计管理")
public class SysCoverageStatController{

    @Autowired
    private SysCoverageStatBiz sysCoverageStatBiz;



    /**
     * 查询统计数据列表
     * @return
     */
    @RequestMapping(value = "/getCoverageStatList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询统计数据列表---PC端", notes = "查询统计数据列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dayTime",value="导出时间",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CoverageStatVo> getCoverageStatList(String dayTime,Integer page, Integer limit){
        List<CoverageStatVo> coverageStatList =  sysCoverageStatBiz.getCoverageStatList(dayTime, page, limit);
        int total = sysCoverageStatBiz.selectCoverageStatCount(dayTime);
        return new TableResultResponse<CoverageStatVo>(total, coverageStatList);
    }



    /**
     * 导出excel
     * @return
     */
    @RequestMapping(value = "/exportExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出excel---PC端", notes = "导出excel---PC端",httpMethod = "POST")
    public ObjectRestResponse exportExcel(@RequestBody @ApiParam StatExcel excel){
        return sysCoverageStatBiz.exportExcel(excel.getDayTime());
    }

}