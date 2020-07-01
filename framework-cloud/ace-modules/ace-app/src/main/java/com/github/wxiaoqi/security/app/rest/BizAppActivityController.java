package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizActivityBiz;
import com.github.wxiaoqi.security.app.vo.activity.out.ActivityInfo;
import com.github.wxiaoqi.security.app.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.app.vo.activity.out.ApplyVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
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
 * 小组活动表
 *
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@RestController
@RequestMapping("bizAppActivity")
@CheckClientToken
@CheckUserToken
@Api(tags = "小组活动(业主圈)")
public class BizAppActivityController {

    @Autowired
    private BizActivityBiz bizActivityBiz;


    /**
     * 查询小组活动贴列表
     * @return
     */
    @RequestMapping(value = "/getActivityList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组活动贴列表---APP端", notes = "查询小组活动贴列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="groupId",value="小组id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ActivityVo>> getActivityList(String groupId, Integer page, Integer limit){
        return  bizActivityBiz.getActivityList(groupId, page, limit);
    }


    /**
     * 查询app邻里活动
     * @return
     */
    @RequestMapping(value = "/getHoodActivityList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询app邻里活动---APP端", notes = "查询app邻里活动---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ActivityVo>> getHoodActivityList(String projectId, Integer page, Integer limit){
        return  bizActivityBiz.getHoodActivityList(projectId, page, limit);
    }




    /**
     * 查询小组活动详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getActivityInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组活动详情---APP端", notes = "查询小组活动详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ActivityInfo> getActivityInfo(String id){
        return bizActivityBiz.getActivityInfo(id);
    }


    /**
     * 分享小组活动详情
     * @param id
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/shareActivityInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享小组活动详情---APP端", notes = "分享小组活动详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ActivityInfo> shareActivityInfo(String id){
        return bizActivityBiz.getActivityInfo(id);
    }




    /**
     * 活动报名
     * @param id
     * @return
     */
    @RequestMapping(value = "/saveApplyUser" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动报名---APP端", notes = "活动报名---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse saveApplyUser(String id){
        return  bizActivityBiz.saveApplyUser(id);
    }




    /**
     * 取消活动报名
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancelApply" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "取消活动报名---APP端", notes = "取消活动报名---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse cancelApply(String id){
        return  bizActivityBiz.cancelApply(id);
    }



    /**
     * 查询活动报名列表
     * @return
     */
    @RequestMapping(value = "/getApplyList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询活动报名列表---APP端", notes = "查询活动报名列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ApplyVo>> getApplyList(String id, Integer page, Integer limit){
        return  bizActivityBiz.getApplyList(id, page, limit);
    }



    /**
     * 查询我的活动
     * @return
     */
    @RequestMapping(value = "/getUserActivityList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询我的活动---APP端", notes = "查询我的活动---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ActivityVo>> getUserActivityList(Integer page, Integer limit){
        return  bizActivityBiz.getUserActivityList(page, limit);
    }



    /**
     * 扫一扫判断该用户是否签到
     * @return
     */
    @RequestMapping(value = "/selectIsSigntype" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "扫一扫判断该用户是否签到---APP端", notes = "扫一扫判断该用户是否签到---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse selectIsSigntype(String userId, String id){
        return  bizActivityBiz.selectIsSigntype(userId, id);
    }




}