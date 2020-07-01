package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizVisitorSignlogsBiz;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.visitsign.ResultVisitListVo;
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
 * 访客登记表
 *
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
@RestController
@RequestMapping("web/visitorSignlogs")
@CheckClientToken
@CheckUserToken
@Api(tags = "访客登记")
public class BizVisitorSignlogsController {

    @Autowired
    private BizVisitorSignlogsBiz bizVisitorSignlogsBiz;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;


    @RequestMapping(value = "/getVisitList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取访客登记列表---PC端", notes = "获取访客登记列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据邀请人,受邀人查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="10")
    })
    public TableResultResponse<ResultVisitListVo> getVisitList(String projectId, String searchVal,Integer page,Integer limit) {

        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType())) {
                //公司用户需要传入项目ID
                projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
            }
        }
        List<ResultVisitListVo> visitSignList = bizVisitorSignlogsBiz.getVisitSignList(projectId, searchVal,page,limit);
        int total = bizVisitorSignlogsBiz.getVisitSignCount(projectId, searchVal);
        return new TableResultResponse(total, visitSignList);
    }

    @RequestMapping(value = "/getVisitInfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取访客登记详情---PC端", notes = "获取访客登记详情---PC端",httpMethod = "GET")
    public ObjectRestResponse getVisitInfo(String id) {
        return bizVisitorSignlogsBiz.getVisitSignInfo(id);
    }
}