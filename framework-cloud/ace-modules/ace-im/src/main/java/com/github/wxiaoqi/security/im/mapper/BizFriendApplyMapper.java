package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.im.dto.NameDto;
import com.github.wxiaoqi.security.im.entity.BizFriendApply;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.im.vo.friend.NameApplyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友申请表
 * 
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
public interface BizFriendApplyMapper extends CommonMapper<BizFriendApply> {

	List<NameApplyVo> getFriendApplys(@Param("userId") String userId, @Param("page") Integer page, @Param("limit") Integer limit);

	Integer getFriendApplysNum(@Param("userId") String userId);

	NameDto getSearchFriend(@Param("userId") String userId,@Param("tel") String tel);

	Integer isHasApply(@Param("userId") String userId);

	Integer updateById(@Param("friendId")String friendId, @Param("userId") String userId, @Param("s")String s);
}
