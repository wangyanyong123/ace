package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizGroupMember;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultGroupMemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组成员表
 * 
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
public interface BizGroupMemberMapper extends CommonMapper<BizGroupMember> {

    /**
     * 查询小组成员
     * @return
     */
    List<ResultGroupMemberVo> selectGroupMemberList(@Param("groupId")String groupId, @Param("searchVal")String searchVal,
                                                    @Param("page")Integer page, @Param("limit")Integer limit);

    /**
     * 查询小组成员数量
     * @param groupId
     * @param searchVal
     * @return
     */
    int selectGroupMemberCount(@Param("groupId")String groupId,@Param("searchVal") String searchVal);
}
