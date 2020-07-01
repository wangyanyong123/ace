package com.github.wxiaoqi.security.im.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.im.entity.BizFriendChatMessage;
import com.github.wxiaoqi.security.im.mapper.BizFriendChatMessageMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 
 *
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
@Service
public class BizFriendChatMessageBiz extends BusinessBiz<BizFriendChatMessageMapper,BizFriendChatMessage> {

	public List<Message> getMsgLogList(String userId, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		Integer startIndex = (page - 1) * limit;
		List<Message> messages = mapper.getMsgLogList(userId,BaseContextHandler.getUserID(),startIndex, limit);
		mapper.updateRead(BaseContextHandler.getUserID(),userId);
		return messages;
	}

	public ObjectRestResponse isHasMessage() {
		ObjectRestResponse response = new ObjectRestResponse();
		Integer num = mapper.isHasMessage(BaseContextHandler.getUserID());
		if(num.intValue() > 0){
			response.setData(1);
		}else {
			response.setData(0);
		}
		return response;
	}
}