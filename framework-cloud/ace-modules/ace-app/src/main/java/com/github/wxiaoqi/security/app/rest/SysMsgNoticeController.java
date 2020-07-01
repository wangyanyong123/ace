package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.SysMsgNoticeBiz;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeList;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息通知
 *
 * @Date 2019-02-27 11:58:04
 */
@RestController
@RequestMapping("sysMsgNotice")
@CheckClientToken
@CheckUserToken
@Api(tags="消息列表通知")
public class SysMsgNoticeController {

    @Autowired
    private SysMsgNoticeBiz sysMsgNoticeBiz;

    @GetMapping("getSmsNoticeList")
    @ApiOperation(value = "获取消息列表", notes = "获取消息列表",httpMethod = "GET")
    public ObjectRestResponse<SmsNoticeList> getSmsNoticeList(Integer page, Integer limit) {
        return sysMsgNoticeBiz.getSmsNoticeList(page,limit);
    }

    @RequestMapping(value = "/saveSmsNotice",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加消息列表", notes = "添加消息列表",httpMethod = "POST")
    @IgnoreClientToken
    @IgnoreUserToken
    public ObjectRestResponse saveSmsNotice(@RequestBody @ApiParam SmsNoticeVo smsNoticeVo) {
        return sysMsgNoticeBiz.saveSmsNotice(smsNoticeVo);
    }

    @GetMapping("getSmsNoticeSign")
    @ApiOperation(value = "用户是否有未读消息", notes = "用户是否有未读消息",httpMethod = "GET")
    public ObjectRestResponse getSmsNoticeSign() {
        ObjectRestResponse response = new ObjectRestResponse();
        response.setData(this.sysMsgNoticeBiz.getSmsNoticeSign());
        return response;
    }

    @GetMapping("getSmsNoticeDetail")
    @ApiOperation(value = "查看消息详情", notes = "查看消息详情",httpMethod = "GET")
    public ObjectRestResponse getSmsNoticeDetail(String id) {
        return this.sysMsgNoticeBiz.getSmsNoticeDetail(id);
    }


}