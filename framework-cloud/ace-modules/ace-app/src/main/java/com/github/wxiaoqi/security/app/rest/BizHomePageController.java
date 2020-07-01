package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizActivityBiz;
import com.github.wxiaoqi.security.app.biz.BizProductBiz;
import com.github.wxiaoqi.security.app.biz.BizReservationPersonBiz;
import com.github.wxiaoqi.security.app.config.AppDefaultConfig;
import com.github.wxiaoqi.security.app.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
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
 * 首页
 *
 * @Author guohao
 * @Date 2020/4/16 21:36
 */
@RestController
@RequestMapping("bizHomePage")
@IgnoreClientToken
@IgnoreUserToken
@Api(tags = "首页 -- 微信端")
public class BizHomePageController {

    @Autowired
    private BizProductBiz bizProductBiz;
    @Autowired
    private BizActivityBiz bizActivityBiz;
    @Autowired
    private BizReservationPersonBiz reservationPersonBiz;
    @Autowired
    private AppDefaultConfig appDefaultConfig;

    /**
     * 查询首页推荐商品
     */
    @RequestMapping(value = "/getRecommendProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页推荐商品", notes = "查询首页推荐商品", httpMethod = "GET")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4")
    public ObjectRestResponse getRecommendProductInfo(String projectId) {
        if(StringUtils.isEmpty(projectId)){
           projectId= appDefaultConfig.getDefaultProjectId();
        }
        return bizProductBiz.getRecommendProductInfo(projectId);
    }


    /**
     * 查询首页的推荐商品列表
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getRecommendList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页推荐商品列表---APP端", notes = "查询首页推荐商品列表---APP端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse getRecommendList(String projectId, Integer page, Integer limit) {
        if(StringUtils.isEmpty(projectId)){
           projectId= appDefaultConfig.getDefaultProjectId();
        }
        return bizProductBiz.getRecommendList(projectId, page, limit);
    }


    /**
     * 查询首页推荐团购
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getRecommendGroupInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页团购---APP端", notes = "查询首页推荐团购---APP端", httpMethod = "GET")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4")
    public ObjectRestResponse getRecommendGroupInfo(String projectId) {
        return bizProductBiz.getRecommendGroupInfo(projectId);
    }


    /**
     * 查询首页推荐团购列表
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getRecommendGroupList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询首页团购列表---APP端", notes = "查询首页推荐团购列表---APP端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse getRecommendGroupList(String projectId, Integer page, Integer limit) {
        if(StringUtils.isEmpty(projectId)){
            projectId = appDefaultConfig.getDefaultProjectId();
        }
        return bizProductBiz.getRecommendGroupList(projectId, page, limit);
    }


    /**
     * 查询当前项目下的活动列表
     *
     * @return
     */
    @RequestMapping(value = "/getAppActivityList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前项目下的活动列表---APP端", notes = "查询当前项目下的活动列表---APP端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ActivityVo>> getAppActivityList(String projectId, Integer page, Integer limit) {
        return bizActivityBiz.getAppActivityList(projectId, page, limit);
    }

    @RequestMapping(value = "/getReservationMore", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "首页更多服务预约接口---App端", notes = "首页服务预约接口---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "社区ID", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "10")
    })
    public ObjectRestResponse<List<ReservationVo>> getReservationList(String projectId, Integer page, Integer limit) {
        if(StringUtils.isEmpty(projectId)){
            projectId = appDefaultConfig.getDefaultProjectId();
        }
        return reservationPersonBiz.getReservationMore(projectId, page, limit);
    }

    @RequestMapping(value = "/getReservationIndex", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "首页服务预约接口---App端", notes = "首页服务预约接口---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "社区ID", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ReservationVo>> getReservationIndex(String projectId) {
        if(StringUtils.isEmpty(projectId)){
          projectId=  appDefaultConfig.getDefaultProjectId();
        }
        return reservationPersonBiz.getReservationIndex(projectId);
    }

}