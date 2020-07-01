package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizChamberTopicTagBiz;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.in.ChanberTopicTagParam;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicTagInfo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicTagVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 议事厅话题标签表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@RestController
@RequestMapping("web/bizChamberTopicTag")
@CheckClientToken
@CheckUserToken
@Api(tags = "议事厅话题标签")
public class BizChamberTopicTagController {

    @Autowired
    private BizChamberTopicTagBiz bizChamberTopicTagBiz;


    /**
     * 查询议事厅话题标签列表
     * @return
     */
    @RequestMapping(value = "/getTopicTagList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询议事厅话题标签列表---PC端", notes = "查询议事厅话题标签列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据话题标签编码/名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ChamberTopicTagVo> getTopicTagList(String projectId, String searchVal, Integer page, Integer limit){
        List<ChamberTopicTagVo> topicTagList =  bizChamberTopicTagBiz.getTopicTagList(projectId, searchVal, page, limit);
        int total = bizChamberTopicTagBiz.selectTopicTagCount(projectId, searchVal);
        return new TableResultResponse<ChamberTopicTagVo>(total, topicTagList);
    }


    /**
     * 保存话题标签
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTopicTag",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存话题标签---PC端", notes = "保存话题标签---PC端",httpMethod = "POST")
    public ObjectRestResponse saveTopicTag(@RequestBody @ApiParam ChanberTopicTagParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(params.getTopicTagName())){
            msg.setStatus(1002);
            msg.setMessage("话题标签不能为空");
            return msg;
        }
        if(params.getProjectVo() == null || params.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        return bizChamberTopicTagBiz.saveTopicTag(params);
    }

    /**
     * 删除话题标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/delTopicTag/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除话题标签---PC端", notes = "删除话题标签---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delTopicTag(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizChamberTopicTagBiz.delTopicTag(id);
    }


    /**
     * 查询话题标签详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getTopicTagInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询话题标签详情---PC端", notes = "查询话题标签详情---PC端",httpMethod = "GET")
    public ObjectRestResponse<ChamberTopicTagInfo> getTopicTagInfo(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizChamberTopicTagBiz.getTopicTagInfo(id);
    }


    /**
     * 编辑话题标签
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateTopicTag",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑话题标签---PC端", notes = "编辑话题标签---PC端",httpMethod = "POST")
    public ObjectRestResponse updateTopicTag(@RequestBody @ApiParam ChanberTopicTagParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(params.getTopicTagName())){
            msg.setStatus(1002);
            msg.setMessage("话题标签不能为空");
            return msg;
        }
        if(params.getProjectVo() == null || params.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        return bizChamberTopicTagBiz.updateTopicTag(params);
    }




}