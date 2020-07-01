package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizGroupLeader;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultLeaderInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小组组长表
 * 
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
public interface BizGroupLeaderMapper extends CommonMapper<BizGroupLeader> {

    /**
     * 查询小组组长
     * @return
     */
    List<ResultLeaderInfoVo> selectGroupLeaderList(@Param("groupId") String groupId);

}
