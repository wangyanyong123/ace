package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.brainpower.out.QuestionVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizBrainpowerQuestion;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.ClassifyVo;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.FunctionVo;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.QuestionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能客服问答表
 * 
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
public interface BizBrainpowerQuestionMapper extends CommonMapper<BizBrainpowerQuestion> {


    /**
     * 查询问题库列表
     * @param functionId
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<QuestionVo> selectQuestionList(@Param("status") String status ,@Param("functionId") String functionId, @Param("enableStatus") String enableStatus,
                                        @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);


    int selectQuestionCount(@Param("functionId") String functionId, @Param("enableStatus") String enableStatus,
                            @Param("searchVal") String searchVal);


    /**
     * 查询问题详情
     * @param id
     * @return
     */
    QuestionInfo selectQuestionInfo(String id);


    int delClassifyStatus(@Param("userId") String userId, @Param("questionId") String questionId);


    List<FunctionVo> selectFunctionVo();

    int updatequestionStatus(@Param("status") String status, @Param("userId") String userId, @Param("id") String id);


    List<ClassifyVo> selectClassifyList();
}
