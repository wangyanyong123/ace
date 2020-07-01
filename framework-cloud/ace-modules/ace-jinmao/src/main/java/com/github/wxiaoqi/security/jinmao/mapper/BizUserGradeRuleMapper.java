package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizUserGradeRule;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.grade.out.GradeList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运营服务-用户等级规则表
 * 
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
public interface BizUserGradeRuleMapper extends CommonMapper<BizUserGradeRule> {

    List<GradeList> getGradeList(@Param("searchVal") String searchVal,@Param("page") Integer page,@Param("limit") Integer limit);


    int getGradeListTotal(@Param("searchVal")String searchVal);

    int deleteById(String id);

    int getGradeIsExist(Integer integral);
}
