package com.github.wxiaoqi.security.im.mapper;

import com.github.wxiaoqi.security.im.tio.msg.BrainMessageOut;
import com.github.wxiaoqi.security.im.vo.brainpower.in.ChangeBrainpowerInVo;
import com.github.wxiaoqi.security.im.vo.brainpower.out.BrainpowerFunctionVo;
import com.github.wxiaoqi.security.im.vo.brainpower.out.BrainpowerQuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能客服
 * 
 * @author huangxl
 * @Date 2019-04-17
 */
public interface BrainpowerMapper {

    /**
     * 获取置低功能
     * @return
     */
    List<BrainpowerFunctionVo> getBottomBrainpowerFunctionList();

    /**
     * 获取问题列表
     * @return
     */
    List<BrainpowerQuestionVo> getBrainpowerQuestionList(@Param("functionIdList") List<String> functionIdList);

    /**
     * 子问题的换一批
     * @param changeBrainpowerInVo
     * @return
     */
    List<BrainpowerQuestionVo> changeBrainpowerQuestion(ChangeBrainpowerInVo changeBrainpowerInVo);

    /**
     * 获取功能信息
     * @param functionId
     * @return
     */
    BrainMessageOut getBrainpowerFunctionById(@Param("functionId") String functionId);

    /**
     * 获取问题信息
     * @param questionId
     * @return
     */
    BrainMessageOut getBrainpowerQuestionById(@Param("questionId") String questionId);

    /**
     * 根据文字模糊匹配功能/问题信息
     * @param searchVal
     * @return
     */
    BrainMessageOut likeBrainpowerByText(@Param("searchVal") String searchVal);



}
