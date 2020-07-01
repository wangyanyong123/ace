package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizDecisionAnnex;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 决策附件表
 * 
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
public interface BizDecisionAnnexMapper extends CommonMapper<BizDecisionAnnex> {

    List<BizDecisionAnnex> selectByDecisionId(String decisionId);

    int deleteByIds(@Param("idList") List<String> idList, @Param("handler") String handler,@Param("date") Date date);
}
