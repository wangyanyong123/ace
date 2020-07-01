package com.github.wxiaoqi.security.jinmao.biz;

import com.github.wxiaoqi.security.api.vo.order.in.SearchSubIncidentInWeb;
import com.github.wxiaoqi.security.api.vo.order.out.WoListForWebVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizWo;
import com.github.wxiaoqi.security.jinmao.mapper.BizWoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单
 *
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
@Service
@Slf4j
public class BizWoBiz extends BusinessBiz<BizWoMapper,BizWo> {

    @Autowired
    private BizWoMapper bizWoMapper;


    public List<WoListForWebVo> queryIncidentList(List<String> busIdList, SearchSubIncidentInWeb searchSubIncidentInWeb) {
        int page = searchSubIncidentInWeb.getPage();
        int limit = searchSubIncidentInWeb.getLimit();
        if (page<1) {
            page = 1;
        }
        if (limit<1) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("page",startIndex);
        paramMap.put("limit",limit);
        paramMap.put("busIdList",busIdList);
        paramMap.put("searchVal",searchSubIncidentInWeb.getSearchVal());
        paramMap.put("startDate",searchSubIncidentInWeb.getStartDate());
        paramMap.put("endDate",searchSubIncidentInWeb.getEndDate());
        paramMap.put("projectId",searchSubIncidentInWeb.getProjectId());
        List<WoListForWebVo> woList = bizWoMapper.queryIncidentList(paramMap);
        if(woList==null || woList.size()==0){
            woList = new ArrayList<>();
        }

        return woList;
    }
}