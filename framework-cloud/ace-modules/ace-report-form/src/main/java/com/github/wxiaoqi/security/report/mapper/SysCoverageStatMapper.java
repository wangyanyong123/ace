package com.github.wxiaoqi.security.report.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.report.entity.SysCoverageStat;
import com.github.wxiaoqi.security.report.vo.CoverageStatVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 覆盖率统计表
 * 
 * @author huangxl
 * @Date 2019-10-28 09:57:52
 */
public interface SysCoverageStatMapper extends CommonMapper<SysCoverageStat> {

    /**
     * 查询每天覆盖率统计
     * @param lastTime
     * @param currTime
     * @return
     */
    List<CoverageStatVo> selectCoverageStatByProject(@Param("lastTime") String lastTime, @Param("currTime") String currTime);

}
