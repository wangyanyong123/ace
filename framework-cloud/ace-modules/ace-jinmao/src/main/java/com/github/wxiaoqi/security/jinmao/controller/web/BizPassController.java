package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizPassBiz;
import com.github.wxiaoqi.security.jinmao.vo.passlog.PassExcel;
import com.github.wxiaoqi.security.jinmao.vo.passlog.QrPassListVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 *
 * @author huangxl
 * @Date 2019-04-02 15:27:58
 */
@RestController
@RequestMapping("web/bizPass")
@CheckClientToken
@CheckUserToken
@Api(tags = "二维码通行")
public class BizPassController {

    @Autowired
    private BizPassBiz bizPassBiz;

    @RequestMapping(value = "/getQrPassList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询二维码通行记录---PC端", notes = "查询二维码通行记录---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="searchVal",value="姓名/电话模糊查询",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<QrPassListVo> getQrPassList(String projectId,String startDate,String endDate,String searchVal, Integer page, Integer limit) {
        List<QrPassListVo> qrPassList = bizPassBiz.getQrPassList(projectId,startDate,endDate,searchVal, page, limit);
        int total = bizPassBiz.getQrPassCount(projectId,startDate,endDate,searchVal);
        return new TableResultResponse<QrPassListVo>(total, qrPassList);
    }


    @RequestMapping(value = "/doPassLogExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "二维码通行记录导出---PC端", notes = "二维码通行记录导出 ---PC端",httpMethod = "POST")
    public ObjectRestResponse doPassLogExcel(@RequestBody @ApiParam PassExcel excel){
        return bizPassBiz.doPassLogExcel(excel.getProjectId(),excel.getStartDate(), excel.getEndDate(), excel.getSearchVal());
    }
}