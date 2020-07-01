package com.github.wxiaoqi.security.im.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.im.entity.BizChatMessage;
import com.github.wxiaoqi.security.im.entity.BizHousekeeperUser;
import com.github.wxiaoqi.security.im.mapper.BizChatMessageMapper;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author zxl
 * @Date 2018-12-17 18:32:27
 */
@Service
public class BizChatMessageBiz extends BusinessBiz<BizChatMessageMapper,BizChatMessage> {
	@Autowired
	private BizChatMessageMapper chatMessageMapper;

	@Autowired
	private BizHousekeeperUserBiz housekeeperUserBiz;

	public boolean isOnhouse(String toUser, String fromUserId) {
		boolean isOn = false;
		int num = chatMessageMapper.isOnhouse(toUser,fromUserId);
		if (num > 0){
			isOn = true;
		}
		return isOn;
	}

	public void isCunzai(String toUser, String fromUserId,String type) {
		BizHousekeeperUser housekeeperUser  = new BizHousekeeperUser();
		if("2".equals(type)){
			housekeeperUser.setUserId(toUser);
			housekeeperUser.setHousekeeperId(fromUserId);
		}else {
			housekeeperUser.setUserId(fromUserId);
			housekeeperUser.setHousekeeperId(toUser);
		}
		housekeeperUser.setStatus("1");
		int num = housekeeperUserBiz.selectCount(housekeeperUser).intValue();
		if(num < 1){
			housekeeperUser.setId(UUIDUtils.generateUuid());
			housekeeperUser.setIsDelete("0");
			housekeeperUser.setCreateBy(fromUserId);
			housekeeperUser.setCreateTime(new Date());
			housekeeperUserBiz.insertSelective(housekeeperUser);
		}
	}
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
		List<Message> messages = chatMessageMapper.getMsgLogList(userId,BaseContextHandler.getUserID(),startIndex, limit);
		chatMessageMapper.updateRead(BaseContextHandler.getUserID(),userId);
		return messages;
	}

	public List<Message> getBrainpowerMsgLogList(String userId, Integer page, Integer limit) {
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
		List<Message> messages = chatMessageMapper.getBrainpowerMsgLogList(userId,BaseContextHandler.getUserID(),startIndex, limit);
		chatMessageMapper.updateBrainpowerRead(BaseContextHandler.getUserID(),userId);
		return messages;
	}
}