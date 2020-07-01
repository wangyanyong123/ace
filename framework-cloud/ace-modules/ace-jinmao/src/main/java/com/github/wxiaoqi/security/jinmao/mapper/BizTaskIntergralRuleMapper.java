package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizTaskIntergralRule;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.task.TaskList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运营服务-任务规则管理表
 * 
 * @author huangxl
 * @Date 2019-08-05 16:26:53
 */
public interface BizTaskIntergralRuleMapper extends CommonMapper<BizTaskIntergralRule> {

    List<TaskList> getTaskList(@Param("searchVal") String searchVal,@Param("page") Integer page,@Param("limit") Integer limit);

    int getTaskListTotal(@Param("searchVal") String searchVal);
}
