package com.github.wxiaoqi.security.external.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import com.github.wxiaoqi.security.external.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.external.feign.ToolFegin;
import com.github.wxiaoqi.security.external.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.external.vo.ParcelParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * app客户端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Service
@Slf4j
public class BaseAppClientUserBiz extends BusinessBiz<BaseAppClientUserMapper, BaseAppClientUser> {

	private Logger logger = LoggerFactory.getLogger(BaseAppClientUserBiz.class);
	@Autowired
	private ToolFegin toolFegin;

    /**
     * 邮包代收服务推送消息
	 * @param param
     * @return
     */
	public ObjectRestResponse sendParcelMsg(ParcelParam param){
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
		String userId = mapper.selectUserIdByTel(param.getTelphone());
		mapper.insertContentMsgByCode(param.getMessage(),userId,MsgThemeConstants.PARCEL_COLLECTION_MSG);
		if(userId == null || StringUtils.isEmpty(userId)){
			msg.setStatus(1001);
			msg.setMessage("该用户不存在!");
			return msg;
		}else{
			ObjectRestResponse result = toolFegin.sendMsg(null, null, null, null, null, userId, MsgThemeConstants.PARCEL_COLLECTION_MSG, "");
			if(result.getStatus() != 200){
				logger.error("发送消息通知失败！");
			}
		}
		LogInfoVo logInfoVo = new LogInfoVo();
		Exception parm = new Exception();
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
			logInfoVo.setLogType("2");
			logInfoVo.setLogName("crm邮包服务接口");
			logInfoVo.setIp(address.getHostAddress());
			logInfoVo.setType("2");
			logInfoVo.setMessage(parm.toString());
			logInfoVo.setCreateTime(new Date());
			logInfoVo.setCreateBy(BaseContextHandler.getUserID());
			toolFegin.savelog(logInfoVo);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		msg.setStatus(200);
		return msg;
	}







}