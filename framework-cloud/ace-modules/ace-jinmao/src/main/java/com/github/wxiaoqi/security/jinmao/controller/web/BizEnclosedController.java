package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizEnclosedBiz;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.in.SaveEnclosedParam;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.in.SaveFacilitiesParam;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.in.SetEnclosedParam;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 围合表
 *
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
@RestController
@RequestMapping("web/bizEnclosed")
@CheckClientToken
@CheckUserToken
@Api(tags = "围合管理")
public class BizEnclosedController {

    @Autowired
    private BizEnclosedBiz bizEnclosedBiz;

    @RequestMapping(value = "/getEnclosedTreeListPc/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询围合树---PC端", notes = "查询物业分类树---PC端", httpMethod = "GET")
    public TableResultResponse<EnclosedVo> getEnclosedTreeListPc(@PathVariable String projectId) {
        List<EnclosedVo> enclosedTreeList  =  bizEnclosedBiz.selectEnclosedTreeList(projectId);
        return new TableResultResponse<EnclosedVo>(enclosedTreeList.size(),enclosedTreeList);
    }

    @RequestMapping(value = "/saveEnclosedInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存围合信息---PC端", notes = "保存围合信息---PC端",httpMethod = "POST")
    public ObjectRestResponse saveEnclosedInfoPc(@RequestBody @ApiParam SaveEnclosedParam param) {
        return bizEnclosedBiz.saveEnclosedInfo(param);
    }

    @RequestMapping(value = "/getEncloseInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询围合信息详情---PC端", notes = "根据围合信息详情---PC端",httpMethod = "GET")
    public TableResultResponse<EnclosedInfoVo> getEncloseInfoPc(@PathVariable String id) {
        List<EnclosedInfoVo> enclosedInfoById = bizEnclosedBiz.getEnclosedInfoById(id);
        return new TableResultResponse<>(enclosedInfoById.size(), enclosedInfoById);
    }

    @RequestMapping(value = "/updateEnclosedInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑围合信息---PC端", notes = "编辑围合信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateEnclosedInfoPc(@RequestBody @ApiParam SaveEnclosedParam param) {
        return bizEnclosedBiz.updateEnclosedInfo(param);
    }

    @RequestMapping(value = "/saveFacilitiesInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存道闸信息---PC端", notes = "保存道闸信息---PC端",httpMethod = "POST")
    public ObjectRestResponse saveFacilitiesInfoPc(@RequestBody @ApiParam  SaveFacilitiesParam param) {
        return bizEnclosedBiz.saveFacilitiesInfo(param);
    }

    @RequestMapping(value = "/getFacilitiesInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询道闸详情---PC端", notes = "查询道闸详情---PC端",httpMethod = "GET")
    public TableResultResponse<FacilitiesInfoVo> getFacilitiesInfoPc(@PathVariable String id) {
        List<FacilitiesInfoVo> facilitiesInfo= bizEnclosedBiz.getFacilitiesInfoById(id);
        return new TableResultResponse<FacilitiesInfoVo>(facilitiesInfo.size(), facilitiesInfo);
    }

    @RequestMapping(value = "/updateFacilitiesInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑道闸信息---PC端", notes = "编辑道闸信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateFacilitiesInfo(@RequestBody @ApiParam SaveFacilitiesParam param) {
        return bizEnclosedBiz.updateFacilitiesInfo(param);
    }

    @RequestMapping(value = "/setEnclosedOnUnit",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置单元围合---PC端", notes = "设置单元围合---PC端",httpMethod = "POST")
    public ObjectRestResponse setEnclosedOnUnit(@RequestBody @ApiParam SetEnclosedParam param) {
        return bizEnclosedBiz.setEnclosedOnUnit(param);
    }

    @RequestMapping(value = "/getTopEnclosedInfoPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询顶级围合---PC端", notes = "查询顶级围合---PC端", httpMethod = "GET")
    public TableResultResponse<EnclosedInfoVo> getTopEnclosedInfoPc() {
        List<EnclosedInfoVo> topEnclosedInfo = bizEnclosedBiz.getTopEnclosedInfo();
        return new TableResultResponse<EnclosedInfoVo>(topEnclosedInfo.size(), topEnclosedInfo);
    }

    @RequestMapping(value = "/getProjectTreeListPc/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询项目树---PC端", notes = "查询项目树---PC端", httpMethod = "GET")
    public TableResultResponse<ProjectVo> getProjectTreeListPc(@PathVariable String projectId) {
        List<ProjectVo> projectVos = bizEnclosedBiz.selectProjectTreeList(projectId);
        return new TableResultResponse<ProjectVo>(projectVos.size(), projectVos);
    }

    @RequestMapping(value = "/getChosenUnitIdPc/{enclosedId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取选中单元---PC端", notes = "获取选中单元---PC端",httpMethod = "GET")
    public TableResultResponse<ChosenUnitVo> getChosenUnitIdPc(@PathVariable String enclosedId) {
        List<ChosenUnitVo> chosenUnitId = bizEnclosedBiz.getChosenUnitId(enclosedId);
        return new TableResultResponse<ChosenUnitVo>(chosenUnitId.size(), chosenUnitId);
    }

    @RequestMapping(value = "/delEnclosedInfoPc/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除围合---PC端", notes = "删除围合---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delEnclosedInfoPc(@PathVariable String id) {
        return bizEnclosedBiz.deleteEnclosedById(id);
    }

    @RequestMapping(value = "/delFacilitiesInfoPc/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除道闸---PC端", notes = "删除道闸---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delFacilitiesInfoPc(@PathVariable String id) {
        return bizEnclosedBiz.deleteFacilitiesById(id);
    }

    @RequestMapping(value = "/getFacilitiesListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询道闸列表---PC端", notes = "查询好物探访列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enclosedId",value="围合ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="10")
    })
    public TableResultResponse<FacilitiesInfoVo> getFacilitiesListPc(String enclosedId, String searchVal, Integer page, Integer limit) {
        List<FacilitiesInfoVo> facilitiesList = bizEnclosedBiz.getFacilitiesList(enclosedId,searchVal, page, limit);
        return new TableResultResponse<FacilitiesInfoVo>(facilitiesList.size(), facilitiesList);
    }
}