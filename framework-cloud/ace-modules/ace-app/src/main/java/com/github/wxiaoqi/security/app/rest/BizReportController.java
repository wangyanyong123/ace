package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizReportBiz;
import com.github.wxiaoqi.security.app.fegin.BizDictFeign;
import com.github.wxiaoqi.security.app.vo.evaluate.out.DictValueVo;
import com.github.wxiaoqi.security.app.vo.report.FeedbackParam;
import com.github.wxiaoqi.security.app.vo.report.ReportParam;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 举报管理表
 *
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
@RestController
@RequestMapping("bizReport")
@CheckClientToken
@CheckUserToken
@Api(tags = "举报管理")
public class BizReportController {

    @Autowired
    private BizReportBiz bizReportBiz;
    @Autowired
    private BizDictFeign bizDictFeign;

    @RequestMapping(value = "/saveReport" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存举报信息---App端", notes = "保存举报信息---App端",httpMethod = "POST")
    public ObjectRestResponse saveReport(@RequestBody @ApiParam ReportParam param) {
        return bizReportBiz.saveReport(param);
    }

    @RequestMapping(value = "/saveReportFeedback" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存意见反馈---App端", notes = "保存意见反馈---App端",httpMethod = "POST")
    public ObjectRestResponse saveReportFeedback(@RequestBody @ApiParam FeedbackParam param) {
        return bizReportBiz.saveReportFeedback(param);
    }


    /**
     * 字典对外提供接口
     * @param code
     * @return
     */
    @RequestMapping(value = "/getDictValueList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "字典对外提供接口---App端", notes = "字典对外提供接口---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "编码id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<DictValueVo> getDictValueList(String code){
        return bizDictFeign.getDictValueByCode(code);
    }

}