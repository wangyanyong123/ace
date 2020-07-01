package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.im.entity.BizFriendBlack;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.im.vo.friend.NameApplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 黑名单表
 * 
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
public interface BizFriendBlackMapper extends CommonMapper<BizFriendBlack> {

	List<NameApplyVo> getFriendBlacks(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);

	int getFriendBlacksNum(@Param("userId") String userId);

	int isAlreadyInBlack(@Param("friendId") String friendId,@Param("userId") String userId);
}
