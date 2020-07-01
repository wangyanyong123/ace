package com.github.wxiaoqi.security.im.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.im.vo.friend.NameVo;
import com.github.wxiaoqi.security.im.vo.friend.UserInfoVo;
import com.github.wxiaoqi.security.im.vo.friend.UserStatusVo;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.im.entity.BizFriendUser;
import com.github.wxiaoqi.security.im.mapper.BizFriendUserMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 用户和好友关系表
 *
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
@Service
public class BizFriendUserBiz extends BusinessBiz<BizFriendUserMapper,BizFriendUser> {

	public List<NameVo> getNames(String searchVal, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		//分页
		Integer startIndex = (page - 1) * limit;
		List<NameVo> names =  mapper.getNames(BaseContextHandler.getUserID(),searchVal, startIndex, limit);
		return names;
	}

	public int getNamesNum(String searchVal) {
		return mapper.getNamesNum(BaseContextHandler.getUserID(),searchVal);
	}

	public int getIsTourist() {
		return mapper.getIsTourist(BaseContextHandler.getUserID());
	}

	public List<UserInfoVo> getUserInfoList(String searchVal, Integer page, Integer limit) {
		if (page == null || page.equals("")) {
			page = 1;
		}
		if (limit == null || limit.equals("")) {
			limit = 10;
		}
		if(page == 0) {
			page = 1;
		}
		//分页
		Integer startIndex = (page - 1) * limit;
		List<UserInfoVo> names =  mapper.getUserInfoList(BaseContextHandler.getUserID(),searchVal, startIndex, limit);
		return names;
	}

	public int getUserInfoNum(String searchVal) {
		return mapper.getUserInfoNum(BaseContextHandler.getUserID(),searchVal);
	}

	public ObjectRestResponse<UserStatusVo> getUserInfoById(String userId) {
		ObjectRestResponse<UserStatusVo> response = new ObjectRestResponse<>();
		UserStatusVo userStatusVo = mapper.getUserInfoById(userId,BaseContextHandler.getUserID());
		if(null == userStatusVo){
			response.setStatus(601);
			response.setMessage("该用户不存在！");
			return response;
		}
		response.setData(userStatusVo);
		return response;
	}

	public Integer isFriend(String userId, String friendId) {
		return mapper.isFriend(userId,friendId);
	}

	public Integer delFriend(String userId, String friendId) {
		return mapper.delFriend(userId,friendId);
	}

	public Integer isDeleteFriend(String userId, String friendId) {
		return mapper.isDeleteFriend(userId,friendId);
	}

	public Integer updateToNormal(String userId, String friendId) {
		return mapper.updateToNormal(userId,friendId);
	}

	public ObjectRestResponse deleteFriend(String userId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(isFriend(BaseContextHandler.getUserID(),userId) > 0){
			mapper.updateFriend(BaseContextHandler.getUserID(),userId);
		}
		if(isFriend(userId,BaseContextHandler.getUserID()) > 0){
			mapper.updateFriend(userId,BaseContextHandler.getUserID());
		}
		response.setMessage("删除成功！");
		return response;
	}
}