package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizEvaluateHousekeeper;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.EvaluateVo;
import com.github.wxiaoqi.security.jinmao.vo.evaluate.HousekeeperInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管家评价表
 * 
 * @author huangxl
 * @Date 2019-02-19 16:55:19
 */
public interface BizEvaluateHousekeeperMapper extends CommonMapper<BizEvaluateHousekeeper> {

    /**
     * 查询评价列表
     * @param type
     * @param tenantId
     * @param time
     * @param evaluateType
     * @param searchVal
     * @param housekeeperId
     * @param page
     * @param limit
     * @return
     */
    List<EvaluateVo> selectEvaluateList(@Param("type") String type, @Param("tenantId") String tenantId, @Param("time") String time
            , @Param("evaluateType") String evaluateType, @Param("searchVal") String searchVal,
            @Param("projectId") String projectId, @Param("housekeeperId") String housekeeperId,
            @Param("page") Integer page, @Param("limit") Integer limit);

    int selectEvaluateCount(@Param("type") String type, @Param("tenantId") String tenantId, @Param("time") String time
            , @Param("evaluateType") String evaluateType, @Param("searchVal") String searchVal,
                            @Param("projectId") String projectId, @Param("housekeeperId") String housekeeperId);

    /**
     * 查询项目下所属管家
     * @param projectId
     * @return
     */
    List<HousekeeperInfo> selectHousekeeperByTenandId(@Param("projectId") String projectId);
}
