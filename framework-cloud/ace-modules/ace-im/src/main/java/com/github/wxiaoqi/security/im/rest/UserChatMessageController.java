package com.github.wxiaoqi.security.im.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.im.biz.BizHousekeeperUserBiz;
import com.github.wxiaoqi.security.im.vo.userchat.out.HouseKeeperVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author zxl
 * @Date 2018-12-17 18:32:27
 */
@RestController
@RequestMapping("userChat")
@CheckClientToken
@CheckUserToken
@Api(tags="客户端聊天")
public class UserChatMessageController {
	@Autowired
	private BizHousekeeperUserBiz housekeeperUserBiz;

	@GetMapping("getHouseKeeperInfo")
	@ApiOperation(value = "用户获取管家信息", notes = "用户获取管家信息",httpMethod = "GET")
	public ObjectRestResponse<HouseKeeperVo> getHouseKeeperInfo(){
		return housekeeperUserBiz.getHouseKeeperInfo();
	}

}