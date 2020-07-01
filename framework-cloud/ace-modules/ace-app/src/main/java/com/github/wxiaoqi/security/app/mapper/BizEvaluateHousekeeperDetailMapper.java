package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizEvaluateHousekeeperDetail;
import com.github.wxiaoqi.security.app.vo.evaluate.out.HousekeeperInfo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

/**
 * 管家评价详情表
 * 
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
public interface BizEvaluateHousekeeperDetailMapper extends CommonMapper<BizEvaluateHousekeeperDetail> {

    /**
     * 查询管家评价数
     * @param housekeeperId
     * @return
     */
    HousekeeperInfo selectHousekeeperInfo(String housekeeperId);
}
