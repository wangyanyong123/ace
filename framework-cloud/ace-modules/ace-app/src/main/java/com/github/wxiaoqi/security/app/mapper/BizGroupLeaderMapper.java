package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizGroupLeader;
import com.github.wxiaoqi.security.app.vo.group.out.ResultLeaderInfoVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组组长表
 * 
 * @author zxl
 * @Date 2018-12-18 15:09:42
 */
public interface BizGroupLeaderMapper extends CommonMapper<BizGroupLeader> {

    /**
     * 判断当前登录用户所在小组中的角色是否是组长
     * @param groupId
     * @param userId
     * @return
     */
    String selectIsGroupByUserId(@Param("groupId") String groupId, @Param("userId") String userId);

    /**
     * 获取小组组长信息
     * @param id
     * @return
     */
    List<ResultLeaderInfoVo> selectGroupLeaderList(String id);
}
