package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizEvaluateHousekeeper;
import com.github.wxiaoqi.security.app.vo.evaluate.out.HousekeeperInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 管家评价表
 * 
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
public interface BizEvaluateHousekeeperMapper extends CommonMapper<BizEvaluateHousekeeper> {

    /**
     * 根据用户id查询所属管家
     * @param userId
     * @return
     */
    HousekeeperInfo selectHousekeeperByUserId(String userId);

    /**
     * 查询管家的满意数量
     * @param housekeeperId
     * @return
     */
    int selectAtisfyCount(String housekeeperId);

    /**
     * 查询管家的评价数量
     * @param housekeeperId
     * @return
     */
    int selectEvaluateTotal(String housekeeperId);

    /**
     * 查询用户是否评价
     * @param housekeeperId
     * @param userId
     * @return
     */
    int selectIsEvaluatetByUser(@Param("housekeeperId") String housekeeperId, @Param("userId") String userId);

    /**
     * 查询用户的评价
     * @param housekeeperId
     * @param userId
     * @return
     */
    String selectEvaluateTypeByUser(@Param("housekeeperId") String housekeeperId, @Param("userId") String userId);
}
