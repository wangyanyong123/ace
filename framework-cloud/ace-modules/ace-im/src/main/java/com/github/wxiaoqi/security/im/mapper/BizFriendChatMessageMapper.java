package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.im.entity.BizFriendChatMessage;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
public interface BizFriendChatMessageMapper extends CommonMapper<BizFriendChatMessage> {

	List<Message> getMsgLogList(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId,
								@Param("page") Integer page, @Param("limit") Integer limit);

	void updateRead(@Param("toUserId") String toUserId,@Param("fromUserId") String fromUserId);

	Integer isHasMessage(@Param("userId") String userId);
}
