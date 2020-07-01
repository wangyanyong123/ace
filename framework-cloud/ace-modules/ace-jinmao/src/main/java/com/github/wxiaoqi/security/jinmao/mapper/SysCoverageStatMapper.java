package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.SysCoverageStat;
import com.github.wxiaoqi.security.jinmao.vo.CoverageStat.CoverageStatVo;
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


    /**
     * 查询统计数据列表
     * @param dayTime
     * @param page
     * @param limit
     * @return
     */
    List<CoverageStatVo> selectCoverageStatList(@Param("dayTime") String dayTime,
                                                @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询统计数据列表数量
     * @param dayTime
     * @return
     */
    int selectCoverageStatCount(@Param("dayTime") String dayTime);
	
}
