package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizReservationPersonBiz;
import com.github.wxiaoqi.security.app.biz.BizSubBiz;
import com.github.wxiaoqi.security.app.config.AppDefaultConfig;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizReservationPersonMapper;
import com.github.wxiaoqi.security.app.mapper.BizSubProductMapper;
import com.github.wxiaoqi.security.app.vo.product.out.ProductSpecInfo;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationInfo;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.MunicipalityUtil;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 预约服务相关
 *
 * @author guohao
 * @Date 2019-03-12 09:26:32
 */
@RestController
@RequestMapping("bizReservation")
@IgnoreClientToken
@IgnoreUserToken
@Api(tags = "预约服务相关-- 微信端")
public class BizReservationController {

    @Autowired
    private BizReservationPersonBiz bizReservationPersonBiz;


    /**
     * 根据分类id查询预约服务列表
     */
    @RequestMapping(value = "/getReservationList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据分类id查询预约服务列表", notes = "根据分类id查询预约服务列表---APP端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "classifyId", value = "分类id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ReservationVo>> getReservationList(String projectId, String classifyId,
                                                                      Integer page, Integer limit, HttpServletRequest request) {
        String cityCode = RequestHeaderUtil.getCityCode(request);
        List<String> cityCodeList = MunicipalityUtil.getDeliveryCityCodeList(cityCode);
        return bizReservationPersonBiz.getReservationList(projectId, classifyId, page, limit,cityCodeList);
    }

    /**
     * 查询预约服务详情
     */
    @RequestMapping(value = "/getReservationInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询预约服务详情(新接口)---APP端", notes = "查询预约服务详情---APP端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务id", dataType = "String", paramType = "query", required = true)
    })
    @ApiImplicitParam(name = "id", value = "服务id", dataType = "String", paramType = "query", example = "4")
    public ObjectRestResponse<ReservationInfo> getNewReservationInfo(@RequestParam String id) {
        return bizReservationPersonBiz.getNewReservationInfo(id, false);
    }

    /**
     * 查询预约服务详情(新接口) by 分享
     */
    @RequestMapping(value = "/getShareReservationInfo", method = RequestMethod.GET)
    @ResponseBody
    @IgnoreUserToken
    @ApiOperation(value = "查询预约服务详情--分享", notes = "查询预约服务详情---分享", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务id", dataType = "String", paramType = "query", required = true)
    })
    @ApiImplicitParam(name = "id", value = "服务id", dataType = "String", paramType = "query", example = "4")
    public ObjectRestResponse<ReservationInfo> getShareNewReservationInfo(@RequestParam String id) {
        return bizReservationPersonBiz.getNewReservationInfo(id, true);
    }
}
