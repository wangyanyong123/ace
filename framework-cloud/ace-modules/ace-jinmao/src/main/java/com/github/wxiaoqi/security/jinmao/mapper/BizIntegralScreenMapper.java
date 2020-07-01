package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizIntegralScreen;
import com.github.wxiaoqi.security.jinmao.vo.integralscreen.out.IntegralScreenInfo;

import java.util.List;

/**
 * 积分筛选表
 * 
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
public interface BizIntegralScreenMapper extends CommonMapper<BizIntegralScreen> {

    /**
     * 删除数据
     * @return
     */
    int delIntegralScreen();

    /**
     * 查询积分范围
     * @return
     */
    List<IntegralScreenInfo> selectIntegralScreenInfo();
	
}
