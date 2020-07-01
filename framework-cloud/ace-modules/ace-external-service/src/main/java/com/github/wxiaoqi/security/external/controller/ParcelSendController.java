package com.github.wxiaoqi.security.external.controller;

import com.github.wxiaoqi.security.auth.client.annotation.CheckExternalService;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.external.vo.ParcelParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：wangjl
 * @date ：Created in 2019/7/29 11:32
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("rest/msg")
@CheckExternalService
@Api(tags="邮包代收服务")
public class ParcelSendController {

    @Autowired
    private BaseAppClientUserBiz baseAppClientUserBiz;

    @RequestMapping(value="/appNotice",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "邮包代收服务推送消息", notes = "邮包代收服务推送消息",httpMethod = "POST")
    public ObjectRestResponse sendParcelMsg(@RequestBody @ApiParam ParcelParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getTelphone())){
            msg.setStatus(1001);
            msg.setMessage("手机号不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getMessage())){
            msg.setStatus(1001);
            msg.setMessage("消息内容不能为空!");
            return msg;
        }
        return baseAppClientUserBiz.sendParcelMsg(param);
    }
}
