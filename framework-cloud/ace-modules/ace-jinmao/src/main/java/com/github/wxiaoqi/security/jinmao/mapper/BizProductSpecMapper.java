package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProductSpec;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultSpecVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品规格表
 * 
 * @author zxl
 * @Date 2018-12-05 09:58:44
 */
public interface BizProductSpecMapper extends CommonMapper<BizProductSpec> {

    /**
     * 查询商品规格详情列表
     * @param id
     * @return
     */
    List<ResultSpecVo> selectSpecInfo(String id);

    int delSpecInfo(String id);

    /**
     * 查询商品规格详情
     * @param id
     * @return
     */
    ResultSpecVo selectAuditSpecInfo(String id);


    String selectLastSortById(String id);

    List<String> selectIdList(String id);

    int deleteSpecIds(@Param("specIds") List<String> specIds);
}
