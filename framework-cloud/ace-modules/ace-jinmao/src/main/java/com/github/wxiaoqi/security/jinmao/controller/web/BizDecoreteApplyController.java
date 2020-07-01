package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizDecoreteApplyBiz;
import com.github.wxiaoqi.security.jinmao.feign.DecoreteFegin;
import com.github.wxiaoqi.security.jinmao.vo.decoreteapply.DecoreteApplyListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 装修监理申请表
 *
 * @author huangxl
 * @Date 2019-04-02 11:27:00
 */
@RestController
@RequestMapping("web/decoreteApply")
@CheckClientToken
@CheckUserToken
@Api(tags = "装修监理管理")
public class BizDecoreteApplyController {

    @Autowired
    private BizDecoreteApplyBiz bizDecoreteApplyBiz;
    @Autowired
    private DecoreteFegin decoreteFegin;

    @RequestMapping(value = "/getDecoreteApplyList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询装修监理管理列表---PC端", notes = "查询装修监理管理列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="status",value="状态查询(0-待发布，1-未发布，2-已发布,3-全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="姓名/电话模糊查询",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<DecoreteApplyListVo> getDecoreteApplyList(String projectId,String status,String searchVal,Integer page,Integer limit) {
        List<DecoreteApplyListVo> decoreteApplyList = bizDecoreteApplyBiz.getDecoreteApplyList(projectId, status, searchVal, page, limit);
        int total = bizDecoreteApplyBiz.getDecoreteApplyCount(projectId, searchVal, status);
        return new TableResultResponse<DecoreteApplyListVo>(total,decoreteApplyList);
    }

    @RequestMapping(value = "/getMyDecoreteInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询装修监理申请详情---PC端", notes = "查询装修监理申请详情---PC端",httpMethod = "GET")
    public ObjectRestResponse getMyDecoreteInfo(String id) {
        return decoreteFegin.getMyDecoreteInfo(id);
    }
}