package com.github.wxiaoqi.security.im.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.constant.MsgNoticeConstant;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.common.vo.SmsNoticeVo;
import com.github.wxiaoqi.security.im.dto.NameDto;
import com.github.wxiaoqi.security.im.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.im.entity.BizFriendUser;
import com.github.wxiaoqi.security.im.feign.SysMsgFeign;
import com.github.wxiaoqi.security.im.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.im.mapper.BizFriendUserMapper;
import com.github.wxiaoqi.security.im.vo.friend.NameApplyVo;
import com.github.wxiaoqi.security.im.vo.friend.NameVo;
import com.github.wxiaoqi.security.im.vo.friend.UserStatusVo;
import com.github.wxiaoqi.security.im.vo.friend.VerifyFriendVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.im.entity.BizFriendApply;
import com.github.wxiaoqi.security.im.mapper.BizFriendApplyMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 好友申请表
 *
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
@Service
public class BizFriendApplyBiz extends BusinessBiz<BizFriendApplyMapper,BizFriendApply> {

	@Autowired
	private BizFriendUserBiz friendUserBiz;
	@Autowired
	private SysMsgFeign sysMsgFeign;
	@Autowired
	private BaseAppClientUserMapper userMapper;

	public List<NameApplyVo> getFriendApplys(Integer page, Integer limit) {
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
		List<NameApplyVo> friends = mapper.getFriendApplys(BaseContextHandler.getUserID(),startIndex, limit);
		return friends;
	}

	public int getFriendApplysNum() {
		return mapper.getFriendApplysNum(BaseContextHandler.getUserID());
	}

	public ObjectRestResponse<NameVo> getSearchFriend(String tel) {
		ObjectRestResponse<NameVo> response = new ObjectRestResponse<>();
		NameDto nameDto = mapper.getSearchFriend(BaseContextHandler.getUserID(),tel);
		if(null != nameDto){
			if(1 == nameDto.getIsTourist()){
				response.setStatus(601);
				response.setMessage("该用户还未进行认证！");
				return response;
			}
			if(1 == nameDto.getIsApply()){
				response.setStatus(602);
				response.setMessage("已申请过添加该用户为好友，请耐心等待该用户审核！");
				return response;
			}
			if(1 == nameDto.getIsBlack()){
				response.setStatus(603);
				response.setMessage("该用户已经被加入黑名单！");
				return response;
			}
			if(1 == nameDto.getIsFriend()){
				response.setStatus(604);
				response.setMessage("该用户已经是您的好友了！");
				return response;
			}
			NameVo nameVo = new NameVo();
			BeanUtils.copyProperties(nameDto,nameVo);
			response.setData(nameVo);
			return response;
		}else {
			response.setStatus(501);
			response.setMessage("该用户不存在！");
			return response;
		}
	}

	public ObjectRestResponse getAddFriend(String userId) {
		ObjectRestResponse response = new ObjectRestResponse();
		UserStatusVo userStatusVo = friendUserBiz.getUserInfoById(userId).getData();
		if(null == userStatusVo){
			response.setStatus(501);
			response.setMessage("该用户不存在！");
			return response;
		}else {
			if(1 == userStatusVo.getIsTourist()){
				response.setStatus(601);
				response.setMessage("该用户还未进行认证！");
				return response;
			}
			if(1 == userStatusVo.getIsApply()){
				response.setStatus(602);
				response.setMessage("已申请过添加该用户为好友，请耐心等待该用户审核！");
				return response;
			}
			if(1 == userStatusVo.getIsBlack()){
				response.setStatus(603);
				response.setMessage("该用户已经被加入黑名单！");
				return response;
			}
			if(1 == userStatusVo.getIsFriend()){
				response.setStatus(604);
				response.setMessage("该用户已经是您的好友了！");
				return response;
			}
			BizFriendApply friendApply = new BizFriendApply();
			friendApply.setCreateBy(BaseContextHandler.getUserID());
			friendApply.setCreateTime(new Date());
			friendApply.setFriendId(BaseContextHandler.getUserID());
			friendApply.setId(UUIDUtils.generateUuid());
			friendApply.setIsPass("0");
			friendApply.setStatus("1");
			friendApply.setUserId(userId);
			insertSelective(friendApply);
			SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
			smsNoticeVo.setReceiverId(userId);
			Map<String, String> param = new HashMap<>();
            BaseAppClientUser baseAppClientUser = userMapper.selectByPrimaryKey(BaseContextHandler.getUserID());
            param.put("friend", baseAppClientUser.getNickname());
			String[] smsNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.FRIEND_APPLY);
			smsNoticeVo.setSmsNotice(smsNotice);
			smsNoticeVo.setParamMap(param);
			sysMsgFeign.saveSmsNotice(smsNoticeVo);
		}
		response.setMessage("添加成功，等待对方审核！");
		return response;
	}

	public ObjectRestResponse verifyFriend(VerifyFriendVo verifyFriendVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(null == verifyFriendVo || StringUtils.isEmpty(verifyFriendVo.getId()) || StringUtils.isEmpty(verifyFriendVo.getIsPass())){
			response.setStatus(501);
			response.setMessage("参数不能为空！");
			return response;
		}
		BizFriendApply friendApply = mapper.selectByPrimaryKey(verifyFriendVo.getId());
		if(null == friendApply){
			response.setStatus(502);
			response.setMessage("id错误！");
			return response;
		}
		if(!"0".equals(friendApply.getIsPass())){
			response.setStatus(503);
			response.setMessage("该信息已经审核过了！");
			return response;
		} else{
			//好友审核发送消息通知
			SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
			BaseAppClientUser mine = userMapper.selectByPrimaryKey(BaseContextHandler.getUserID());
			String[] msgNotice;
			Map<String, String> param = new HashMap<>();
			if("1".equals(verifyFriendVo.getIsPass())) {
				UserStatusVo userStatusVo = friendUserBiz.getUserInfoById(friendApply.getFriendId()).getData();
				if(null == userStatusVo){
					response.setStatus(602);
					response.setMessage("该用户不存在！");
					return response;
				}else {
					if(1 == userStatusVo.getIsTourist()){
						response.setStatus(601);
						response.setMessage("该用户还未进行认证！");
						return response;
					}
					if(1 == userStatusVo.getIsBlack()){
						response.setStatus(603);
						response.setMessage("该用户已经被加入黑名单！");
						return response;
					}
					if(1 == userStatusVo.getIsFriend()){

						friendApply.setIsPass(verifyFriendVo.getIsPass());
						friendApply.setModifyBy(BaseContextHandler.getUserID());
						friendApply.setModifyTime(new Date());
						mapper.updateByPrimaryKeySelective(friendApply);

						response.setStatus(604);
						response.setMessage("该用户已经是您的好友了！");
						return response;
					}
					friendApply.setIsPass(verifyFriendVo.getIsPass());
					friendApply.setModifyBy(BaseContextHandler.getUserID());
					friendApply.setModifyTime(new Date());
					int num = mapper.updateByPrimaryKeySelective(friendApply);
					if(num > 0){
						mapper.updateById(friendApply.getFriendId(),friendApply.getUserId(),"1");
						if(friendUserBiz.isFriend(friendApply.getUserId(),friendApply.getFriendId()) < 1){
							BizFriendUser bizFriendUser = new BizFriendUser();
							bizFriendUser.setId(UUIDUtils.generateUuid());
							bizFriendUser.setCreateBy(BaseContextHandler.getUserID());
							bizFriendUser.setCreateTime(new Date());
							bizFriendUser.setFriendId(friendApply.getFriendId());
							bizFriendUser.setUserId(friendApply.getUserId());
							bizFriendUser.setIsDelete("0");
							bizFriendUser.setStatus("1");
							friendUserBiz.insertSelective(bizFriendUser);
						}
						if(friendUserBiz.isFriend(friendApply.getFriendId(),friendApply.getUserId()) < 1){
							BizFriendUser bizFriendUser = new BizFriendUser();
							bizFriendUser.setId(UUIDUtils.generateUuid());
							bizFriendUser.setCreateBy(BaseContextHandler.getUserID());
							bizFriendUser.setCreateTime(new Date());
							bizFriendUser.setFriendId(friendApply.getUserId());
							bizFriendUser.setUserId(friendApply.getFriendId());
							bizFriendUser.setIsDelete("0");
							bizFriendUser.setStatus("1");
							friendUserBiz.insertSelective(bizFriendUser);
						}
					}
					//审核通过
					msgNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.FRIEND_APPLY_S);
					smsNoticeVo.setSmsNotice(msgNotice);
					param.put("friend", mine.getNickname());
					param.put("mine", mine.getNickname());
					smsNoticeVo.setReceiverId(friendApply.getFriendId());
					smsNoticeVo.setParamMap(param);
					sysMsgFeign.saveSmsNotice(smsNoticeVo);
				}
			}else if("2".equals(verifyFriendVo.getIsPass())){
				friendApply.setIsPass(verifyFriendVo.getIsPass());
				friendApply.setModifyBy(BaseContextHandler.getUserID());
				friendApply.setModifyTime(new Date());
				mapper.updateByPrimaryKeySelective(friendApply);
				mapper.updateById(friendApply.getFriendId(),friendApply.getUserId(),"2");
				//审核拒绝
				msgNotice = MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.FRIEND_APPLY_F);
				smsNoticeVo.setSmsNotice(msgNotice);
				param.put("friend", mine.getNickname());
				param.put("mine", mine.getNickname());
				smsNoticeVo.setReceiverId(friendApply.getFriendId());
				smsNoticeVo.setParamMap(param);
				sysMsgFeign.saveSmsNotice(smsNoticeVo);
			}else {
				response.setStatus(504);
				response.setMessage("isPass错误！");
				return response;
			}
			return response;
		}
	}

	public ObjectRestResponse isHasApply() {
		ObjectRestResponse response = new ObjectRestResponse();
		Integer num = mapper.isHasApply(BaseContextHandler.getUserID());
		if(num.intValue() > 0){
			response.setData(1);
		}else {
			response.setData(0);
		}
		return response;
	}
}