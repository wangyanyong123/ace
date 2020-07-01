package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizGroupBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizServiceHotlineBiz;
import com.github.wxiaoqi.security.jinmao.vo.group.in.GroupMemberStatus;
import com.github.wxiaoqi.security.jinmao.vo.group.in.GroupStatus;
import com.github.wxiaoqi.security.jinmao.vo.group.in.SaveGroupVo;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultGroupInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultGroupListVo;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultGroupMemberInfoVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 热门小组表
 *
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
@RestController
@RequestMapping("web/bizGroup")
@CheckClientToken
@CheckUserToken
@Api(tags = "兴趣小组")
public class BizGroupController  {

    @Autowired
    private BizGroupBiz bizGroupBiz;

	@Autowired
	private BizServiceHotlineBiz bizServiceHotlineBiz;

    /**
     * 查询小组列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getGroupInfoListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组列表---PC端", notes = "查询小组列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(1-草稿，2-已发布，3-已撤回,4-全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据组名模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultGroupListVo> getGroupInfoListPc(String enableStatus, String searchVal, Integer page, Integer limit){

		List<String> projectIdList = bizServiceHotlineBiz.getProjectList().stream().map(book -> book.getId()).collect(Collectors.toList());
        List<ResultGroupListVo> groupListVos = bizGroupBiz.selectGroupList(projectIdList,enableStatus, searchVal, page, limit);
        int total = bizGroupBiz.selectGroupCount(projectIdList,enableStatus, searchVal);
        return new TableResultResponse<ResultGroupListVo>(total, groupListVos);
    }

    /**
     * 查询小组详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getGroupInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组详情---PC端", notes = "查询小组详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultGroupInfoVo> getGroupInfoPc(@PathVariable String id) {
        List<ResultGroupInfoVo> resultGroupInfoVo = bizGroupBiz.selectGroupInfoById(id);
        return new TableResultResponse<ResultGroupInfoVo>(resultGroupInfoVo.size(),resultGroupInfoVo);
    }

    /**
     * 修改小组状态
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGroupStatusPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改小组状态---PC端", notes = "修改小组状态---PC端", httpMethod = "POST")
    public ObjectRestResponse updateGroupStatusPc(@RequestBody @ApiParam GroupStatus param) {
        return bizGroupBiz.updateGroupStatus(param);
    }

    /**
     * 获取小组信息
     * @param groupId
     * @param searchVal
     * @return
     */
    @RequestMapping(value = "/getGroupMemberInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询小组成员列表---PC端", notes = "查询小组成员列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="groupId",value="小组ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据姓名/手机号模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultGroupMemberInfoVo> getGroupMemberInfoPc(String groupId, String searchVal, Integer page, Integer limit) {
        List<ResultGroupMemberInfoVo> groupMemberInfoList = bizGroupBiz.getGroupMemberInfoList(groupId, searchVal,page,limit);
        int total = bizGroupBiz.getGroupMemberCount(groupId, searchVal);
        return new TableResultResponse<ResultGroupMemberInfoVo>(total, groupMemberInfoList);
    }

    /**
     * 保存小组信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveGroupInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存小组信息---PC端", notes = "保存小组信息---PC端",httpMethod = "POST")
    public ObjectRestResponse saveGroupInfoPc(@RequestBody @ApiParam SaveGroupVo param) {
        return bizGroupBiz.saveGroupInfo(param);
    }

    /**
     * 编辑小组信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGroupInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑小组信息---PC端", notes = "编辑小组信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateGroupInfoPc(@RequestBody @ApiParam SaveGroupVo param) {
        return bizGroupBiz.updateGroupInfo(param);
    }

    /**
     * 加入/取消组长
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGroupLeaderStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "加入/取消组长---PC端", notes = "加入/取消组长---PC端",httpMethod = "POST")
    public ObjectRestResponse updateGroupLeaderStatusPc(@RequestBody @ApiParam GroupMemberStatus param) {
        return bizGroupBiz.updateGroupLeaderStatus(param);
    }

    /**
     * 删除小组
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteGroupByIdPc/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除小组---PC端", notes = "删除小组---PC端",httpMethod = "DELETE")
    public ObjectRestResponse deleteGroupByIdPc(@PathVariable String id) {
        return bizGroupBiz.deleteGroupById(id);
    }
}