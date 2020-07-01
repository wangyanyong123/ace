package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizEvaluatePropertyScale;
import com.github.wxiaoqi.security.app.vo.property.out.PropertyInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业评价详情表
 * 
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
public interface BizEvaluatePropertyScaleMapper extends CommonMapper<BizEvaluatePropertyScale> {

    /**
     * 查询当前月的物业评价详情
     * @param projectId
     * @return
     */
    List<PropertyInfo> selectPropertyInfo(String projectId);

    PropertyInfo selectPropertyInfoByType(@Param("projectId") String projectId, @Param("evaluateType") String evaluateType);

    /**
     * 查询各星值的百分比
     * @param projectId
     * @return
     */
    String selectPropertyScale(@Param("projectId") String projectId, @Param("evaluateType") String evaluateType);

    /**
     * 查询该项目的物业评价总数
     * @param projectId
     * @return
     */
    int selectEvaluateTotal(String projectId);
}
