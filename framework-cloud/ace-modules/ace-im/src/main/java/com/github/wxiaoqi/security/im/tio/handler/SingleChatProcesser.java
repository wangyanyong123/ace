package com.github.wxiaoqi.security.im.tio.handler;

import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import com.github.wxiaoqi.security.common.constant.TaskConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.ToolUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.im.biz.BizChatMessageBiz;
import com.github.wxiaoqi.security.im.biz.BizFriendChatMessageBiz;
import com.github.wxiaoqi.security.im.entity.BizChatMessage;
import com.github.wxiaoqi.security.im.entity.BizFriendChatMessage;
import com.github.wxiaoqi.security.im.feign.MsgToolfeign;
import com.github.wxiaoqi.security.im.feign.UserIntegralFeign;
import com.github.wxiaoqi.security.im.mapper.BrainpowerMapper;
import com.github.wxiaoqi.security.im.tio.msg.BrainMessageIn;
import com.github.wxiaoqi.security.im.tio.msg.BrainMessageOut;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import com.github.wxiaoqi.security.im.util.ReturnResult;
import com.github.wxiaoqi.security.im.vo.brainpower.in.ChangeBrainpowerInVo;
import com.github.wxiaoqi.security.im.vo.brainpower.out.BrainpowerQuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.utils.json.Json;
import org.tio.websocket.common.WsResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleChatProcesser implements WebSocketProcesser{
	private static Logger log = LoggerFactory.getLogger(SingleChatProcesser.class);

	@Override
    public void process(String text, ChannelContext channelContext) {
        GroupContext groupContext = channelContext.getGroupContext();
        Message message = Json.toBean(text, Message.class);
        String toUser = message.getToUserId();
        String fromUserId = message.getFromUserId();

        if(StringUtils.isNotEmpty(message.getIsFriend()) && "1".equals(message.getIsFriend())){
			BizFriendChatMessageBiz friendChatMessageBiz = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(BizFriendChatMessageBiz.class);
			ReturnResult sendResult = ReturnResult.build(101,"",message);
			WsResponse wsResponse=WsResponse.fromText(Json.toJson(sendResult),"utf-8");

			BizFriendChatMessage chatMessage = new BizFriendChatMessage();

			BeanUtils.copyProperties(message,chatMessage);
			chatMessage.setId(UUIDUtils.generateUuid());
			chatMessage.setCreateTime(new Date());
			chatMessage.setIsRead("0");
			chatMessage.setIsSend("1");
			Tio.sendToUser(groupContext,toUser,wsResponse);
			friendChatMessageBiz.insertSelective(chatMessage);
			//多端登录时
			WsResponse fromResponse=WsResponse.fromText(Json.toJson(ReturnResult.build(100,"消息发送成功","")),"utf-8");

			Tio.sendToUser(groupContext,fromUserId,fromResponse);
			try {
				MsgToolfeign msgToolfeign = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(MsgToolfeign.class);
				String userId = toUser;
				String msgTheme = "CUSTOMER_MSG";
				String msgParam = "";
				log.info("发送个推消息："+message.getOs()+"-"+message.getEdition()+"-"+userId+"-"+msgTheme);
				ObjectRestResponse restResponse = msgToolfeign.sendMsg(null, null, null, null, null, userId, msgTheme, msgParam);
				log.info("发送消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());
			}catch (Exception e){
				e.printStackTrace();
			}
		}else {
			BizChatMessageBiz chatMessageBiz = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(BizChatMessageBiz.class);
			boolean falg = true;
			//智能问答库
			if(message.getIsInteligence()!=null && "1".equals(message.getIsInteligence()) && !"7".equals(message.getMsgType().toString())){
				//插入智能提问问题
				BizChatMessage chatMessage = new BizChatMessage();
				BeanUtils.copyProperties(message,chatMessage);
				chatMessage.setId(UUIDUtils.generateUuid());
				chatMessage.setCreateTime(new Date());
				chatMessage.setIsRead("1");
				chatMessage.setIsSend("1");
				chatMessageBiz.insertSelective(chatMessage);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				BrainMessageOut brainMessageOut = new BrainMessageOut();
				BrainMessageIn brainMessageIn = Json.toBean(message.getMessageJson(), BrainMessageIn.class);
				if(brainMessageIn!=null && StringUtils.isNotEmpty(brainMessageIn.getType())){
					BrainpowerMapper brainpowerMapper = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(BrainpowerMapper.class);
					BrainMessageOut brainMessageOutTemp = null;
					if("1".equals(brainMessageIn.getType())){
						brainMessageOutTemp = brainpowerMapper.getBrainpowerQuestionById(brainMessageIn.getId());
					}else if("2".equals(brainMessageIn.getType())){
						brainMessageOutTemp = brainpowerMapper.getBrainpowerFunctionById(brainMessageIn.getId());
					}else if("3".equals(brainMessageIn.getType())){
						MsgToolfeign msgToolfeign = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(MsgToolfeign.class);
						TableResultResponse<IndexObject> resultSearch = msgToolfeign.search(brainMessageIn.getText(),1,1);
						if(resultSearch!=null && resultSearch.getStatus()==200){
							List<IndexObject> objectList =  resultSearch.getData().getRows();
							if(objectList!=null && objectList.size()>0){
								brainMessageOutTemp = brainpowerMapper.getBrainpowerQuestionById(objectList.get(0).getId());
							}
						}
						//brainMessageOutTemp = brainpowerMapper.likeBrainpowerByText(brainMessageIn.getText());
					}
					if(brainMessageOutTemp!=null){
						brainMessageOut.setType(brainMessageIn.getType());
						brainMessageOut.setId(brainMessageOutTemp.getId());
						brainMessageOut.setAnswer(brainMessageOutTemp.getAnswer());
						brainMessageOut.setPicture(brainMessageOutTemp.getPicture());
						brainMessageOut.setText(brainMessageOutTemp.getText());
						brainMessageOut.setJumpLink(brainMessageOutTemp.getJumpLink());
						brainMessageOut.setJumpCode(brainMessageOutTemp.getJumpCode());

						ChangeBrainpowerInVo changeBrainpowerInVo = new ChangeBrainpowerInVo();
						changeBrainpowerInVo.setQuestionId(brainMessageOutTemp.getId());
						List<BrainpowerQuestionVo> brainpowerQuestionList = brainpowerMapper.changeBrainpowerQuestion(changeBrainpowerInVo);
						if(brainpowerQuestionList==null){
							brainpowerQuestionList = new ArrayList<BrainpowerQuestionVo>();
						}
						brainpowerQuestionList = ToolUtil.getSubStringByRadom(brainpowerQuestionList,5);
						brainMessageOut.setQuestionList(brainpowerQuestionList);
						message.setMsgType(5);
					}else{
						brainMessageOut.setText("未找到答案！");
						message.setMsgType(6);
					}
					message.setMessage(Json.toJson(brainMessageOut));
					message.setToUserId(fromUserId);
					message.setFromUserId(toUser);
				}
			}else  if(message.getIsInteligence()!=null && "1".equals(message.getIsInteligence()) && "7".equals(message.getMsgType().toString())){
				BizChatMessage chatMessage = new BizChatMessage();
				BeanUtils.copyProperties(message,chatMessage);
				chatMessage.setId(UUIDUtils.generateUuid());
				chatMessage.setCreateTime(new Date());
				chatMessage.setIsRead("1");
				chatMessage.setIsSend("1");
				chatMessage.setToUserId(fromUserId);
				chatMessage.setFromUserId(toUser);
				chatMessage.setMessage(message.getMessageJson());
				chatMessageBiz.insertSelective(chatMessage);
				falg = false;
			}

			ReturnResult sendResult = ReturnResult.build(101,"",message);
			WsResponse wsResponse=WsResponse.fromText(Json.toJson(sendResult),"utf-8");

			BizChatMessage chatMessage = new BizChatMessage();
			BeanUtils.copyProperties(message,chatMessage);
			chatMessage.setId(UUIDUtils.generateUuid());
			chatMessage.setCreateTime(new Date());
			chatMessage.setIsRead("0");
			chatMessage.setIsSend("1");
			boolean isSend = true;
			if("2".equals(message.getUserType())){
				//判断当前用户是否管家所负责的房屋内
				if (!chatMessageBiz.isOnhouse(toUser,fromUserId)){
					chatMessageBiz.insertSelective(chatMessage);
					isSend = false;
				}
			}
			if(isSend){
				boolean issuc = Tio.sendToUser(groupContext,toUser,wsResponse);
	//			if (issuc){
	//				chatMessage.setIsRead("0");
	//			}
				if (falg){
					chatMessageBiz.insertSelective(chatMessage);
				}
			}

			WsResponse fromResponse = null;
			//智能问答库
			if(message.getIsInteligence()!=null && "1".equals(message.getIsInteligence()) && !"7".equals(message.getMsgType().toString())){
				if("1".equals(message.getUserType())){
					try {
						log.info("开始调用完成每日任务接口！");
						UserIntegralFeign userIntegralFeign = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(UserIntegralFeign.class);
						userIntegralFeign.finishDailyTask("task_104",fromUserId);
						log.info("调用完成每日任务接口成功！");
					}catch (Exception e){
						log.error("调用完成每日任务接口失败！");
						e.printStackTrace();
					}
				}
				//多端登录时
				fromResponse = WsResponse.fromText(Json.toJson(ReturnResult.build(102,"智能客服回答",message.getMessage())),"utf-8");
				Tio.sendToUser(groupContext,fromUserId,fromResponse);
			}else if(message.getIsInteligence()!=null && "1".equals(message.getIsInteligence()) && "7".equals(message.getMsgType().toString())){
				//多端登录时
				fromResponse=WsResponse.fromText(Json.toJson(ReturnResult.build(100,"消息发送成功","")),"utf-8");
				Tio.sendToUser(groupContext,fromUserId,fromResponse);
			}else{
				//多端登录时
				fromResponse=WsResponse.fromText(Json.toJson(ReturnResult.build(100,"消息发送成功","")),"utf-8");

				Tio.sendToUser(groupContext,fromUserId,fromResponse);
				chatMessageBiz.isCunzai(toUser,fromUserId,message.getUserType());

				if("1".equals(message.getUserType())){
					try {
						log.info("开始调用完成每日任务接口！");
						UserIntegralFeign userIntegralFeign = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(UserIntegralFeign.class);
						userIntegralFeign.finishDailyTask("task_104",fromUserId);
						log.info("调用完成每日任务接口成功！");
					}catch (Exception e){
						log.error("调用完成每日任务接口失败！");
						e.printStackTrace();
					}
				}

				if(StringUtils.isNotEmpty(message.getOs())){
					if("1".equals(message.getOs()) || "2".equals(message.getOs())){
						if(StringUtils.isNotEmpty(message.getEdition()) && "2.0".equals(message.getEdition())){
							MsgToolfeign msgToolfeign = com.github.wxiaoqi.security.common.util.BeanUtils.getBean(MsgToolfeign.class);
							String userId = toUser;
							String msgTheme ="";
							if("2".equals(message.getUserType())){
								msgTheme = "HOUSEKEEPER_MSG";
							}else {
								msgTheme = "CUSTOMER_MSG";
							}
							String msgParam = "";
							log.info("发送个推消息："+message.getOs()+"-"+message.getEdition()+"-"+userId+"-"+msgTheme);
							ObjectRestResponse restResponse = msgToolfeign.sendMsg(null, null, null, null, null, userId, msgTheme, msgParam);
							log.info("发送消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());
						}
					}
				}
			}
		}


    }


}
