package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizFacilities;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.FacilitiesInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 道闸表
 * 
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
public interface BizFacilitiesMapper extends CommonMapper<BizFacilities> {

    /**
     * 根据ID查询道闸信息
     * @param id
     * @return
     */
    FacilitiesInfoVo selectFacilitiesById(@Param("id") String id);

    /**
     * 查询项目编码
     * @param enclosedId
     * @return
     */
    String selectProjectCodeById(@Param("enclosedId") String enclosedId);

    String getProjectById(@Param("enclosedId") String enclosedId);
}
