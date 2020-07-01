package com.github.wxiaoqi.security.external.controller;

import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.biz.BizPropertyAnnouncementBiz;
import com.github.wxiaoqi.security.external.feign.AnnFeign;
import com.github.wxiaoqi.security.external.vo.AnnInfoQuery;
import com.github.wxiaoqi.security.external.vo.AnnInfoVo;
import com.github.wxiaoqi.security.external.vo.AnnListQuery;
import com.github.wxiaoqi.security.external.vo.ResultAnnList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ：wangjl
 * @date ：Created in 2019/7/29 11:32
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("rest/ann")
@CheckExternalService
@Api(tags="物业公告")
public class AnnounceController {

    @Autowired
    private BizPropertyAnnouncementBiz propertyAnnouncementBiz;


    @RequestMapping(value="getAnnList",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取物业公告列表", notes = "获取物业公告列表",httpMethod = "POST")
    public ObjectRestResponse<List<ResultAnnList>> getAnnList(@RequestBody @ApiParam AnnListQuery query){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(query.getProjectId())) {
            response.setStatus(101);
            response.setMessage("项目编码为空");
            return response;
        }
        return propertyAnnouncementBiz.getAnnouncementList(query.getProjectId(), query.getPage(), query.getLimit());
    }


    @RequestMapping(value="getAnnInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取物业公告详情", notes = "获取物业公告详情",httpMethod = "POST")
    public ObjectRestResponse<AnnInfoVo> getAnnInfo(@RequestBody @ApiParam AnnInfoQuery query){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(query.getId())) {
            response.setStatus(101);
            response.setMessage("物业公告ID为空");
            return response;
        }
        return propertyAnnouncementBiz.getAnnouncementInfo(query.getId());
    }
}
