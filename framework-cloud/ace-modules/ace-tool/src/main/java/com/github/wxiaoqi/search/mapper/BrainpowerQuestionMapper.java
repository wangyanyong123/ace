package com.github.wxiaoqi.search.mapper;

import com.github.wxiaoqi.security.api.vo.brainpower.out.QuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能客服问答表
 * 
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
public interface BrainpowerQuestionMapper {


    /**
     * 查询问题库列表
     * @param functionId
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<QuestionVo> selectQuestionList(@Param("functionId") String functionId, @Param("enableStatus") String enableStatus,
                                        @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);


}
