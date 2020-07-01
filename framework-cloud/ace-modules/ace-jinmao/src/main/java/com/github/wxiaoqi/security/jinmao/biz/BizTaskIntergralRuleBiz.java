package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.vo.task.TaskList;
import com.github.wxiaoqi.security.jinmao.vo.task.TaskParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizTaskIntergralRule;
import com.github.wxiaoqi.security.jinmao.mapper.BizTaskIntergralRuleMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 运营服务-任务规则管理表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:53
 */
@Service
public class BizTaskIntergralRuleBiz extends BusinessBiz<BizTaskIntergralRuleMapper,BizTaskIntergralRule> {


    @Autowired
    private BizTaskIntergralRuleMapper intergralRuleMapper;

    public List<TaskList> getTask(String searchVal, Integer page, Integer limit) {

        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        int startIndex = (page - 1) * limit;
        List<TaskList> taskList = intergralRuleMapper.getTaskList(searchVal,startIndex,limit);
        if (taskList.size() == 0) {
            taskList = new ArrayList<>();
        }

        return taskList;
    }

    public int getTaskTotal(String searchVal) {
        return intergralRuleMapper.getTaskListTotal(searchVal);
    }

    public ObjectRestResponse updateTask(TaskParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizTaskIntergralRule bizTaskIntergralRule = intergralRuleMapper.selectByPrimaryKey(params.getId());
        bizTaskIntergralRule.setModifyBy(BaseContextHandler.getUserID());
        bizTaskIntergralRule.setModifyTime(new Date());
        bizTaskIntergralRule.setViewSort(params.getViewSort());
        bizTaskIntergralRule.setIntegral(params.getIntegral());
        if (intergralRuleMapper.updateByPrimaryKeySelective(bizTaskIntergralRule)<0) {
            response.setMessage("更新失败");
            response.setStatus(101);
            return response;
        }
        return response;
    }

    public ObjectRestResponse getTaskDetail(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizTaskIntergralRule bizTaskIntergralRule = intergralRuleMapper.selectByPrimaryKey(id);
        if (bizTaskIntergralRule == null) {
            response.setStatus(101);
            response.setMessage("ID不存在");
            return response;
        }

        return ObjectRestResponse.ok(bizTaskIntergralRule);
    }
}