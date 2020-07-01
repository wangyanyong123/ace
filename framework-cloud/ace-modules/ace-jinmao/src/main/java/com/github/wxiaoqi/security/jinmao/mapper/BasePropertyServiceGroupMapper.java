package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BasePropertyServiceGroup;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.ResultServiceTreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业人员分类
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
public interface BasePropertyServiceGroupMapper extends CommonMapper<BasePropertyServiceGroup> {

    /**
     * 查询物业分类树
     * @return
     */
    List<ResultServiceTreeVo> selectServiceTreeList(String tenantId);

    /**
     * 根据上级id查询上级名称
     * @param id
     * @return
     */
    ResultServiceTreeVo selectPidById(String id);

    /**
     * 根据父id查询名称
     * @param pid
     * @return
     */
    ResultServiceTreeVo selectNameByPid(String pid);

    /**
     * 删除物业分类
     * @param id
     * @param userId
     * @return
     */
    int delServiceGroupInfo(@Param("id") String id, @Param("userId") String userId);

    String selectIsAllServiceBySid(String sid);

}
