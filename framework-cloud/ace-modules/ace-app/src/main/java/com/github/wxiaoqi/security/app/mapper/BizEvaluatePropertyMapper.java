package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizEvaluateProperty;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 物业评价表
 * 
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
public interface BizEvaluatePropertyMapper extends CommonMapper<BizEvaluateProperty> {


    /**
     * 查询用户是否评价
     * @param projectId
     * @param userId
     * @return
     */
    int selectIsEvaluatetByUser(@Param("projectId") String projectId, @Param("userId") String userId);

    /**
     * 查询当前月的评价人数
     * @param projectId
     * @return
     */
    int selectEvaluatetCount(String projectId);

    /**
     * 查询用户的评价
     * @param projectId
     * @param userId
     * @return
     */
    String selectEvaluateTypeByUser(@Param("projectId") String projectId, @Param("userId") String userId);

    /**
     * 查询总分值
     * @param projectId
     * @return
     */
    int selectEvaluateScale(String projectId);

    /**
     * 查询当前项目是否存在客服,物业人员
     * @param projectId
     * @return
     */
    int selectIsCusByProjectId(String projectId);


}
