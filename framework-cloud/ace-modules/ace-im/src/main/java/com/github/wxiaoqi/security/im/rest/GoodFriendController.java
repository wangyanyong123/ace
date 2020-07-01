package com.github.wxiaoqi.security.im.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.im.biz.BizFriendApplyBiz;
import com.github.wxiaoqi.security.im.biz.BizFriendBlackBiz;
import com.github.wxiaoqi.security.im.biz.BizFriendChatMessageBiz;
import com.github.wxiaoqi.security.im.biz.BizFriendUserBiz;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import com.github.wxiaoqi.security.im.vo.friend.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:52 2019/9/3
 * @Modified By:
 */
@RestController
@RequestMapping("goodFriend")
@CheckClientToken
@CheckUserToken
@Api(tags="好友聊天")
public class GoodFriendController {

	@Autowired
	private BizFriendUserBiz friendUserBiz;

	@Autowired
	private BizFriendChatMessageBiz friendChatMessageBiz;

	@Autowired
	private BizFriendApplyBiz friendApplyBiz;

	@Autowired
	private BizFriendBlackBiz friendBlackBiz;

	@GetMapping("getNames")
	@ApiOperation(value = "获得通讯录列表", notes = "获得通讯录列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="searchVal",value="根据姓名/手机号 模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<NameVo> getNames(String searchVal, Integer page, Integer limit){
		int n = friendUserBiz.getIsTourist();
		if(n > 0){
			List<NameVo> userInfoList = friendUserBiz.getNames(searchVal, page, limit);
			int total = friendUserBiz.getNamesNum(searchVal);
			return new TableResultResponse<>(total, userInfoList);
		}else {
			return new TableResultResponse<>();
		}
	}

	@GetMapping("getUserInfoList")
	@ApiOperation(value = "获得已聊过天的用户列表", notes = "获得已聊过天的用户列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="searchVal",value="根据姓名/手机号模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<UserInfoVo> getUserInfoList(String searchVal, Integer page, Integer limit){
		List<UserInfoVo> userInfoList = friendUserBiz.getUserInfoList(searchVal, page, limit);
		int total = friendUserBiz.getUserInfoNum(searchVal);
		return new TableResultResponse<>(total, userInfoList);
	}


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
		response.setData(friendChatMessageBiz.getMsgLogList(userId,page,limit));
		return response;
	}

	@PostMapping("deleteFriend")
	@ApiOperation(value = "删除好友", notes = "删除好友",httpMethod = "POST")
	public ObjectRestResponse deleteFriend(@ApiParam(value = "userId", required = true) @RequestParam String userId){
		return friendUserBiz.deleteFriend(userId);
	}

	@GetMapping("getSearchFriend")
	@ApiOperation(value = "获得搜索朋友", notes = "获得搜索朋友",httpMethod = "GET")
	@ApiImplicitParam(name="tel",value="手机号",dataType="String",paramType = "query",example="4")
	public ObjectRestResponse<NameVo> getSearchFriend(String tel){
		return friendApplyBiz.getSearchFriend(tel);
	}

	@GetMapping("getUserInfoById")
	@ApiOperation(value = "获得用户的情况", notes = "获得用户的情况",httpMethod = "GET")
	@ApiImplicitParam(name="userId",value="用户id",dataType="String",paramType = "query",example="4")
	public ObjectRestResponse<UserStatusVo> getUserInfoById(String userId){
		return friendUserBiz.getUserInfoById(userId);
	}

	@GetMapping("getFriendApplys")
	@ApiOperation(value = "获得审核列表", notes = "获得审核列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<NameApplyVo> getFriendApplys(Integer page, Integer limit){
		List<NameApplyVo> userInfoList = friendApplyBiz.getFriendApplys(page, limit);
		int total = friendApplyBiz.getFriendApplysNum();
		return new TableResultResponse<>(total, userInfoList);
	}

	@GetMapping("getAddFriend")
	@ApiOperation(value = "添加好友", notes = "添加好友",httpMethod = "GET")
	@ApiImplicitParam(name="userId",value="用户id",dataType="String",paramType = "query",example="4")
	public ObjectRestResponse getAddFriend(String userId){
		return friendApplyBiz.getAddFriend(userId);
	}

	@PostMapping("verifyFriend")
	@ApiOperation(value = "好友审核", notes = "好友审核",httpMethod = "POST")
	public ObjectRestResponse verifyFriend(@RequestBody @ApiParam VerifyFriendVo verifyFriendVo){
		return friendApplyBiz.verifyFriend(verifyFriendVo);
	}

	@GetMapping("getFriendBlacks")
	@ApiOperation(value = "获得黑名单列表", notes = "获得黑名单列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<NameApplyVo> getFriendBlacks(Integer page, Integer limit){
		List<NameApplyVo> userInfoList = friendBlackBiz.getFriendBlacks(page, limit);
		int total = friendBlackBiz.getFriendBlacksNum();
		return new TableResultResponse<>(total, userInfoList);
	}

	@GetMapping("getAddBlack")
	@ApiOperation(value = "加入黑名单", notes = "加入黑名单",httpMethod = "GET")
	@ApiImplicitParam(name="userId",value="用户id",dataType="String",paramType = "query",example="4")
	public ObjectRestResponse getAddBlack(String userId){
		return friendBlackBiz.getAddBlack(userId);
	}

	@PostMapping("removeBlacklists")
	@ApiOperation(value = "移除黑名单", notes = "移除黑名单",httpMethod = "POST")
	public ObjectRestResponse removeBlacklists(@ApiParam(value = "id", required = true) @RequestParam String id){
		return friendBlackBiz.removeBlacklists(id);
	}

	@GetMapping("isHasApply")
	@ApiOperation(value = "是否有未处理的好友申请", notes = "是否有未处理的好友申请",httpMethod = "GET")
	public ObjectRestResponse isHasApply(){
		return friendApplyBiz.isHasApply();
	}

	@GetMapping("isHasMessage")
	@ApiOperation(value = "是否有未读的消息", notes = "是否有未读的消息",httpMethod = "GET")
	public ObjectRestResponse isHasMessage(){
		return friendChatMessageBiz.isHasMessage();
	}
}
