package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizHotHomeService;
import com.github.wxiaoqi.security.app.vo.hhser.out.HotHomeServiceHomeVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:31:29
 */
public interface BizHotHomeServiceMapper extends CommonMapper<BizHotHomeService> {

    List<HotHomeServiceHomeVo> selectHotHomeServiceList
            (@Param("projectId") List<String> projectIdList, @Param("position") Integer position);

    List<HotHomeServiceHomeVo> selectHotHomeServiceListByCityCode
            (@Param("cityCodeList") List<String> cityCodeList, @Param("position") Integer position);

    List<HotHomeServiceHomeVo> selectHotHomeServiceSalesMoreListByCityCode
            (@Param("cityCodeList") List<String> cityCodeList,
             @Param("position") Integer position, @Param("limitDate") Date date);
}
