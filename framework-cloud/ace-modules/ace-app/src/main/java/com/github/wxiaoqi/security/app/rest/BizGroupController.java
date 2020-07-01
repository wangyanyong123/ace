package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BizGroupBiz;
import com.github.wxiaoqi.security.app.mapper.BizUserProjectMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.group.in.FollowStatus;
import com.github.wxiaoqi.security.app.vo.group.out.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 热门小组表
 *
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
@RestController
@RequestMapping("bizGroup")
@CheckClientToken
@CheckUserToken
@Api(tags = "兴趣小组")
public class BizGroupController {

    @Autowired
    private BizGroupBiz bizGroupBiz;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;

    @RequestMapping(value = "/addToGroupApp" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "加入/取消小组---App端", notes = "兴趣小组模块---加入/取消小组",httpMethod = "POST")
    public ObjectRestResponse addToGroupApp(@RequestBody @ApiParam FollowStatus param) {
        ObjectRestResponse response = new ObjectRestResponse();
        CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        //判断是否为游客
        if ("4".equals(userInfo.getIdentityType())) {
            response.setStatus(101);
            response.setMessage("请前去选择身份验证");
            return response;
        }
        return bizGroupBiz.saveGroupMember(param);
    }
    @RequestMapping(value = "/getAppGroupList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组列表---App端", notes = "查询小组列表---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "searchVal", value = "根据名称模糊查询", dataType = "Integer", paramType = "query", example = "啊啊啊")
    })
    public ObjectRestResponse<ResultAppGroupListVo> getAppGroupList(String projectId, String searchVal) {
        return bizGroupBiz.getAppGroupList(projectId, searchVal);
    }
    @RequestMapping(value = "/getMyGroupList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询我的小组列表---App端", notes = "查询我的小组列表---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<ResultGroupListVo> getMyGroupList(String projectId) {
        return bizGroupBiz.getMyGroupList(projectId);
    }
    @RequestMapping(value = "/getHotGroupList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询热门小组列表---App端", notes = "查询热门小组列表---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<ResultGroupListVo> getHotGroupList(String projectId) {
        return bizGroupBiz.getHotGroupList(projectId);
    }
    @RequestMapping(value = "/getNewGroupList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询最新小组列表---App端", notes = "查询最新小组列表---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<ResultGroupListVo> getNewGroupList(String projectId) {
        return bizGroupBiz.getNewGroupList(projectId);
    }

    @RequestMapping(value = "/getAppGroupInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组详情---App端", notes = "查询小组详情---App端",httpMethod = "GET")
    public ObjectRestResponse<ResultAppGroupInfoVo> getAppGroupInfo( String id){
        return bizGroupBiz.getAppGroupInfo(id);
    }

    @RequestMapping(value = "/getAppGroupMemberInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组成员详情---App端", notes = "查询小组成员详情---App端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "小组id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<ResultAppMemberVo> getAppGroupMemberInfo(String groupId) {
        return bizGroupBiz.getAppGroupMemberInfo(groupId);
    }

    @RequestMapping(value = "/saveGroupMemberSign" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "小组打卡---App端", notes = "兴趣小组模块---小组打卡",httpMethod = "POST")
    public ObjectRestResponse saveGroupMemberSign(String groupId) {
        return bizGroupBiz.saveGroupMemberSign(groupId);
    }








    /**
     * 查询项目下小组积分排名列表
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getGroupIntegralList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询项目下小组积分排名列表---App端", notes = "查询项目下小组积分排名列表---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "10")
    })
    public ObjectRestResponse<List<GroupIntegralVo>> getGroupIntegralList(String projectId,Integer page, Integer limit) {
        return bizGroupBiz.getGroupIntegralList(projectId, page, limit);
    }


    /**
     * 查询当前社区积分
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getCurrentProjectIntegral", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前社区积分---App端", notes = "查询当前社区积分---App端",httpMethod = "GET")
    @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd")
    public ObjectRestResponse<ProjectIntegralVo> getCurrentProjectIntegral( String projectId){
        return bizGroupBiz.getCurrentProjectIntegral(projectId);
    }



    /**
     * 查询社区积分排行榜
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getAllProjectIntegral", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询社区积分排行榜---App端", notes = "查询社区积分排行榜---App端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "10")
    })
    public ObjectRestResponse<List<ProjectIntegralVo>> getAllProjectIntegral(Integer page, Integer limit) {
        return bizGroupBiz.getAllProjectIntegral(page, limit);
    }

}