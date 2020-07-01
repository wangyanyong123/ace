package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizEvaluateProperty;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.PropertyEvaluateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物业评价表
 * 
 * @author huangxl
 * @Date 2019-02-25 13:52:09
 */
public interface BizEvaluatePropertyMapper extends CommonMapper<BizEvaluateProperty> {


    /**
     * 查询物业评价列表
     * @param type
     * @param tenantId
     * @param time
     * @param evaluateType
     * @param searchVal
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<PropertyEvaluateVo> selectPropertyEvaluateList(@Param("type") String type, @Param("tenantId") String tenantId, @Param("time") String time
            , @Param("evaluateType") String evaluateType, @Param("searchVal") String searchVal,
                                                        @Param("projectId") String projectId,
                                                        @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询物业评价数量
     * @param type
     * @param tenantId
     * @param time
     * @param evaluateType
     * @param searchVal
     * @param projectId
     * @return
     */
    int selectPropertyEvaluateCount(@Param("type") String type, @Param("tenantId") String tenantId, @Param("time") String time
            , @Param("evaluateType") String evaluateType, @Param("searchVal") String searchVal,
                                    @Param("projectId") String projectId);
}
