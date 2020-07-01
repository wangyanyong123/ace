package com.github.wxiaoqi.security.im.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.im.vo.friend.NameApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.im.entity.BizFriendBlack;
import com.github.wxiaoqi.security.im.mapper.BizFriendBlackMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Date;
import java.util.List;

/**
 * 黑名单表
 *
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
@Service
public class BizFriendBlackBiz extends BusinessBiz<BizFriendBlackMapper,BizFriendBlack> {
	@Autowired
	private BizFriendUserBiz friendUserBiz;

	public List<NameApplyVo> getFriendBlacks(Integer page, Integer limit) {
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
		List<NameApplyVo> friends = mapper.getFriendBlacks(BaseContextHandler.getUserID(),startIndex, limit);
		return friends;
	}

	public int getFriendBlacksNum() {
		return mapper.getFriendBlacksNum(BaseContextHandler.getUserID());
	}

	public ObjectRestResponse getAddBlack(String userId) {
		ObjectRestResponse response = new ObjectRestResponse();
		int num = mapper.isAlreadyInBlack(userId,BaseContextHandler.getUserID());
		if(num < 1){
			BizFriendBlack friendBlack = new BizFriendBlack();
			friendBlack.setId(UUIDUtils.generateUuid());
			friendBlack.setCreateBy(BaseContextHandler.getUserID());
			friendBlack.setCreateTime(new Date());
			friendBlack.setFriendId(userId);
			friendBlack.setUserId(BaseContextHandler.getUserID());
			friendBlack.setIsDelete("0");
			friendBlack.setStatus("1");
			if (mapper.insertSelective(friendBlack) > 0){
				if(friendUserBiz.isFriend(BaseContextHandler.getUserID(),userId) > 0){
					friendUserBiz.delFriend(BaseContextHandler.getUserID(),userId);
				}
				if(friendUserBiz.isFriend(userId,BaseContextHandler.getUserID()) > 0){
					friendUserBiz.delFriend(userId,BaseContextHandler.getUserID());
				}
			}
		}else {
			response.setStatus(601);
			response.setMessage("已经加入黑名单了");
			return response;
		}
		response.setMessage("添加成功！");
		return response;
	}

	public ObjectRestResponse removeBlacklists(String id) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(id)){
			response.setStatus(501);
			response.setMessage("参数不能为空！");
			return response;
		}
		BizFriendBlack friendBlack = mapper.selectByPrimaryKey(id);
		if(null == friendBlack){
			response.setStatus(502);
			response.setMessage("id错误！");
			return response;
		}
		if(!"0".equals(friendBlack.getIsDelete())){
			response.setStatus(503);
			response.setMessage("该用户已经移除黑名了！");
			return response;
		} else {
			friendBlack.setIsDelete("1");
			friendBlack.setModifyBy(BaseContextHandler.getUserID());
			friendBlack.setModifyTime(new Date());
			if (mapper.updateByPrimaryKeySelective(friendBlack) > 0 ){
				int num = mapper.isAlreadyInBlack(BaseContextHandler.getUserID(), friendBlack.getFriendId());
				if(num < 1){
					int isf = friendUserBiz.isDeleteFriend(BaseContextHandler.getUserID(), friendBlack.getFriendId());
					if(isf > 0){
						friendUserBiz.updateToNormal(BaseContextHandler.getUserID(), friendBlack.getFriendId());
					}
					int isf1 = friendUserBiz.isDeleteFriend(friendBlack.getFriendId(),BaseContextHandler.getUserID());
					if(isf1 > 0){
						friendUserBiz.updateToNormal(friendBlack.getFriendId(),BaseContextHandler.getUserID());
					}
				}
			}
		}
		response.setMessage("移除成功！");
		return response;
	}
}