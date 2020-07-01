package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizAdvertisingPositionBiz;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.advertising.in.SaveAdvert;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.ResultAdvert;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.ResultAdvertList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优选商城广告位
 *
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
@RestController
@RequestMapping("web/bizAdvertisingPosition")
@CheckClientToken
@CheckUserToken
@Api(tags = "优选商场广告位")
public class BizAdvertisingPositionController {

    @Autowired
    private BizAdvertisingPositionBiz bizAdvertisingPositionBiz;

    @RequestMapping(value = "/getAdvertListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询广告位列表---PC端", notes = "查询广告位列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目ID",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据标题模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultAdvertList> getAdvertListPc(String projectId, String searchVal, Integer page, Integer limit) {
        List<ResultAdvertList> advertList = bizAdvertisingPositionBiz.getAdvertList(projectId, searchVal, page, limit);
        int total = bizAdvertisingPositionBiz.selectAdvertCount(projectId,searchVal);
        return new TableResultResponse<ResultAdvertList>(total, advertList);
    }

    /**
     * 根据ID查询广告详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAdvertInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询广告位详情---PC端", notes = "查询广告位详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultAdvert> getAdvertInfoPc(@PathVariable  String id) {
        List<ResultAdvert> advertInfo = bizAdvertisingPositionBiz.getAdvertInfo(id);
        return new TableResultResponse<ResultAdvert>(advertInfo.size(), advertInfo);
    }

    /**
     * 保存广告
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveAdvertInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存优选商城广告位---PC端", notes = "保存优选商城广告位---PC端",httpMethod = "POST")
    public ObjectRestResponse saveAdvertInfoPc(@RequestBody @ApiParam SaveAdvert param) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isAnyoneEmpty(param.getTitle(), String.valueOf(param.getViewSort()))) {
            response.setStatus(101);
            response.setMessage("参数不能为空");
            return response;
        }
        if (param.getAdvertisingImg() == null || param.getAdvertisingImg().size() == 0) {
            response.setStatus(101);
            response.setMessage("参数不能为空");
            return response;
        }
        return bizAdvertisingPositionBiz.saveAdvertInfo(param);
    }

    /**
     * 编辑广告信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateAdvertInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑优选商城广告位---PC端", notes = "编辑优选商城广告位---PC端",httpMethod = "POST")
    public ObjectRestResponse updateAdvertInfoPc(@RequestBody @ApiParam SaveAdvert param) {
        return bizAdvertisingPositionBiz.updateAdvertInfo(param);
    }

    /**
     * 删除广告
     * @return
     */
    @RequestMapping(value = "/deleteAdvertById/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除广告---PC端", notes = "删除广告---PC端", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="主键",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse deleteAdvertById(@PathVariable String id) {
        return bizAdvertisingPositionBiz.deleteAdvertById(id);
    }

    @RequestMapping(value = "/getProjectInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取项目---PC端", notes = "获取项目---PC端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productId",value="商品ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ProjectListVo> getProjectInfo(String productId) {
        List<ProjectListVo> projectInfo = bizAdvertisingPositionBiz.getProjectInfo(productId);
        return new TableResultResponse<>(projectInfo.size(), projectInfo);
    }
}