package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.im.entity.BizChatMessage;
import com.github.wxiaoqi.security.im.tio.msg.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author zxl
 * @Date 2018-12-17 18:32:27
 */
public interface BizChatMessageMapper extends CommonMapper<BizChatMessage> {

	int isOnhouse(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId);

	List<Message> getMsgLogList(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId,
								@Param("page") Integer page, @Param("limit") Integer limit);

	void updateRead(@Param("toUserId") String toUserId,@Param("fromUserId") String fromUserId);

	List<Message> getBrainpowerMsgLogList(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId,
								@Param("page") Integer page, @Param("limit") Integer limit);

	void updateBrainpowerRead(@Param("toUserId") String toUserId, @Param("fromUserId") String fromUserId);

	void addSolve(String id);

	void addUnSolve(String id);
}
