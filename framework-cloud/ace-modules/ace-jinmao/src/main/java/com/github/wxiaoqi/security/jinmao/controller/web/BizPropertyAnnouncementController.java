package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizPropertyAnnouncementBiz;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.InputParam.SaveAnnouncementParam;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAnnouncementInfo;
import com.github.wxiaoqi.security.jinmao.vo.Announcement.OutParam.ResultAnnouncementVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("web/bizPropertyAnnouncement")
@CheckClientToken
@CheckUserToken
@Api(tags = "物业公告")
public class BizPropertyAnnouncementController {

    @Autowired
    private BizPropertyAnnouncementBiz bizPropertyAnnouncementBiz;

    /**
     * 查询物业公告列表
     *
     * @return
     */
    @RequestMapping(value = "/getAnnouncementListPc", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业公告列表---PC端", notes = "查询物业公告列表---PC端", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchVal", value = "根据标题模糊查询", dataType = "String", paramType = "query", example = "1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "Integer", paramType = "query", example = "4"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "Integer", paramType = "query", example = "1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultAnnouncementVo> getAnnouncementListPc(String searchVal, Integer page, Integer limit) {
        List<ResultAnnouncementVo> hotlineList = bizPropertyAnnouncementBiz.getAnnouncementList(searchVal, page, limit);
        int total = bizPropertyAnnouncementBiz.selectAnnouncementCount(searchVal);
        return new TableResultResponse<ResultAnnouncementVo>(total, hotlineList);
    }


    /**
     * 删除物业公告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delAnnouncementInfoPc/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除物业公告---PC端", notes = "删除物业公告---PC端", httpMethod = "DELETE")
    public ObjectRestResponse delAnnouncementInfoPc(@PathVariable String id) {
        return bizPropertyAnnouncementBiz.delAnnouncementInfo(id);
    }


    /**
     * 发布物业公告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/publisherAnnouncementPc/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "发布物业公告---PC端", notes = "发布物业公告---PC端", httpMethod = "DELETE")
    public ObjectRestResponse publisherAnnouncementPc(@PathVariable String id) {
        return bizPropertyAnnouncementBiz.publisherAnnouncement(id);
    }


    /**
     * 保存物业公告
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveAnnouncementPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存物业公告---PC端", notes = "保存物业公告---PC端", httpMethod = "POST")
    public ObjectRestResponse saveAnnouncementPc(@RequestBody @ApiParam SaveAnnouncementParam params) {
        return bizPropertyAnnouncementBiz.saveAnnouncement(params);
    }


    /**
     * 查询物业公告详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAnnouncementInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业公告详情---PC端", notes = "查询物业公告详情---PC端", httpMethod = "GET")
    public TableResultResponse<ResultAnnouncementInfo> getAnnouncementInfoPc(@PathVariable String id) {
        List<ResultAnnouncementInfo> info = bizPropertyAnnouncementBiz.getAnnouncementInfo(id);
        return new TableResultResponse<ResultAnnouncementInfo>(info.size(), info);
    }


    /**
     * 编辑物业公告
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateAnnouncementPc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑物业公告---PC端", notes = "编辑物业公告---PC端", httpMethod = "POST")
    public ObjectRestResponse updateAnnouncementPc(@RequestBody @ApiParam SaveAnnouncementParam params) {
        return bizPropertyAnnouncementBiz.updateAnnouncement(params);
    }


    /**
     * 导出物业公告列表到excel
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "导出物业公告列表excel---PC端", notes = "导出物业公告列表excel---PC端", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "公告ID", dataType = "String", paramType = "query",
                    example = "19a7f18863d04ab98fbfa17bef2f9ce4"),
            @ApiImplicitParam(name = "title", value = "公告标题", dataType = "String", paramType = "query", example = "***公告")
    })
    public ObjectRestResponse exportExcel(String id, String title) {
        return bizPropertyAnnouncementBiz.exportExcel(id, title);
    }

}