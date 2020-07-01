package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizHotHomeServiceBiz;
import com.github.wxiaoqi.security.app.entity.BizHotHomeService;
import com.github.wxiaoqi.security.app.vo.group.out.ResultAppGroupListVo;
import com.github.wxiaoqi.security.app.vo.hhser.out.HotHomeServiceHomeVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.MunicipalityUtil;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页热门到家
 *
 * @author guohao
 * @Date 2020-04-14 19:31:29
 */
@RestController
@RequestMapping("hhs")
public class BizHotHomeServiceController extends BaseController<BizHotHomeServiceBiz, BizHotHomeService, String> {

    @Autowired
    private BizHotHomeServiceBiz bizHotHomeServiceBiz;

    @IgnoreUserToken
    @IgnoreClientToken
    @ApiOperation(value = "获取热门到家列表 -----微信端", notes = "获取热门到家列表 -----微信端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "position", value = "展示位置 1：首页; 默认 1", dataType = "Integer", paramType = "query"),
    })
    @GetMapping("/findHotHomeServiceList")
    public ObjectRestResponse<ResultAppGroupListVo> findHotHomeServiceList(String projectId,
                                                                           @RequestParam(defaultValue = "1") Integer position,
                                                                           HttpServletRequest request) {
        List<HotHomeServiceHomeVo> hotHomeServiceList;
        if(StringUtils.isNotEmpty(projectId)){

            hotHomeServiceList = bizHotHomeServiceBiz.findHotHomeServiceList(projectId, position);
        }else{
            String cityCode = RequestHeaderUtil.getCityCode(request);
            List<String> cityCodeList = MunicipalityUtil.getDeliveryCityCodeList(cityCode);
            hotHomeServiceList = bizHotHomeServiceBiz.findHotHomeServiceSalesMoreListByCityCode(cityCodeList,position);
        }
        return ObjectRestResponse.ok(hotHomeServiceList);
    }
}
