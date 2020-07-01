package com.github.wxiaoqi.security.im.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.im.biz.BizChatMessageBiz;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:40 2018/12/19
 * @Modified By:
 */
@RestController
@RequestMapping("msg")
@CheckClientToken
@CheckUserToken
@Api(tags="聊天记录管理")
public class MessageController {

	@Autowired
	private BizChatMessageBiz chatMessageBiz;

	@GetMapping("getMsgLogList")
	@ApiOperation(value = "聊天历史记录", notes = "聊天历史记录",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value="聊天用户id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public ObjectRestResponse<List<Message>> getMsgLogList(String userId, Integer page, Integer limit){
		ObjectRestResponse<List<Message>> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(userId)){
			response.setStatus(501);
			response.setMessage("userId不能为空！");
			return response;
		}
		response.setData(chatMessageBiz.getMsgLogList(userId,page,limit));
		return response;
	}

	@GetMapping("getBrainpowerMsgLogList")
	@ApiOperation(value = "聊天历史记录", notes = "聊天历史记录",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value="聊天用户id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public ObjectRestResponse<List<Message>> getBrainpowerMsgLogList(String userId, Integer page, Integer limit){
		ObjectRestResponse<List<Message>> response = new ObjectRestResponse<>();
		response.setData(chatMessageBiz.getBrainpowerMsgLogList(userId,page,limit));
		return response;
	}
}
