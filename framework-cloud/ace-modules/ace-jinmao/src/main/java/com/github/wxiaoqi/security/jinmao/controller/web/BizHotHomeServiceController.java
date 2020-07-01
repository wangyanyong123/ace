package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.jinmao.biz.BizHotHomeServiceBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizHotHomeService;
import com.github.wxiaoqi.security.jinmao.vo.common.ObjectIdAndName;
import com.github.wxiaoqi.security.jinmao.vo.hhser.in.EditHotHomeServiceIn;
import com.github.wxiaoqi.security.jinmao.vo.hhser.in.SearchHotHomeServiceIn;
import com.github.wxiaoqi.security.jinmao.vo.hhser.out.HotHomeServiceInfoResult;
import com.github.wxiaoqi.security.jinmao.vo.hhser.out.SearchHotHomeServiceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:34:50
 */
@Api(tags = "热门到家管理")
@RestController
@RequestMapping("web/hhs")
@CheckClientToken
@CheckUserToken
public class BizHotHomeServiceController extends BaseController<BizHotHomeServiceBiz, BizHotHomeService, String> {

    @Autowired
    private BizHotHomeServiceBiz bizHotHomeServiceBiz;

    @ApiOperation("创建热门到家")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ObjectRestResponse edit(@RequestBody EditHotHomeServiceIn editHotHomeServiceIn) {
        editHotHomeServiceIn.check();
        return bizHotHomeServiceBiz.edit(editHotHomeServiceIn);
    }

    @ApiOperation("获取热门到家详情")
    @RequestMapping(value = "/findInfo", method = RequestMethod.GET)
    public ObjectRestResponse findInfo(@RequestParam String id) {
        HotHomeServiceInfoResult info = bizHotHomeServiceBiz.findInfo(id);
        return ObjectRestResponse.ok(info);
    }

    @ApiOperation("获取热门到家列表")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public TableResultResponse<SearchHotHomeServiceResult> search(SearchHotHomeServiceIn searchHotHomeServiceIn) {
        searchHotHomeServiceIn.setTenantId(BaseContextHandler.getTenantID());
        searchHotHomeServiceIn.check();
        return bizHotHomeServiceBiz.search(searchHotHomeServiceIn);
    }

    @ApiOperation("获取项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "busId", value = "具体预约服务id", dataType = "String", paramType = "query", example = "a579e9766af3459a8efe37edf3463bd9")
    })
    @RequestMapping(value = "/findProjectList", method = RequestMethod.POST)
    public ObjectRestResponse<ObjectIdAndName> findProjectList(String busId) {
        List<ObjectIdAndName> projectList = bizHotHomeServiceBiz.findProjectList(busId);
        return ObjectRestResponse.ok(projectList);
    }

    @ApiOperation("删除热门到家")
    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE)
    public ObjectRestResponse deleteById(@RequestParam String id) {
        bizHotHomeServiceBiz.updateStatusInvalid(id);
        return ObjectRestResponse.ok();
    }
}