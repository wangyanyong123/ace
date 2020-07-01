package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.im.entity.BizFriendUser;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.im.vo.friend.NameVo;
import com.github.wxiaoqi.security.im.vo.friend.UserInfoVo;
import com.github.wxiaoqi.security.im.vo.friend.UserStatusVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和好友关系表
 * 
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
public interface BizFriendUserMapper extends CommonMapper<BizFriendUser> {

	List<NameVo> getNames(@Param("userId") String userId, @Param("searchVal") String searchVal,
						  @Param("page") Integer page, @Param("limit") Integer limit);

	int getNamesNum(@Param("userId") String userId, @Param("searchVal") String searchVal);

	int getIsTourist(@Param("userId") String userId);

	List<UserInfoVo> getUserInfoList(@Param("userId") String userId, @Param("searchVal") String searchVal,
									 @Param("page") Integer page, @Param("limit") Integer limit);

	int getUserInfoNum(@Param("userId") String userId, @Param("searchVal") String searchVal);

	UserStatusVo getUserInfoById(@Param("userId")String userId, @Param("id")String id);

	Integer isFriend(@Param("userId")String userId, @Param("friendId")String friendId);

	Integer delFriend(@Param("userId")String userId, @Param("friendId")String friendId);

	Integer isDeleteFriend(@Param("userId")String userId, @Param("friendId")String friendId);

	Integer updateToNormal(@Param("userId")String userId, @Param("friendId")String friendId);

	Integer updateFriend(@Param("userId")String userId, @Param("friendId")String friendId);
}
