package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.SyncTableUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuam
 * @date 2019-08-14 10:42
 */
public interface SyncTableUserInfoMapper extends CommonMapper<SyncTableUserInfo> {

    List<SyncTableUserInfo> selectByStatus(@Param("status") String status);

    void updateByIds(@Param("ids") List<String> ids, @Param("status") String status);

    void insertByCondition(@Param("userInfo") SyncTableUserInfo syncTableUserInfo);

    Integer getMaxSeqByUserId(@Param("userId") String userId);
}
