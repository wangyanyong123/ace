package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.entity.BizDecoreteApply;
import com.github.wxiaoqi.security.jinmao.mapper.BizDecoreteApplyMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.decoreteapply.DecApplyDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.decoreteapply.DecoreteApplyListVo;
import com.github.wxiaoqi.security.jinmao.vo.decoreteapply.DecoreteApplyOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 装修监理申请表
 *
 * @author huangxl
 * @Date 2019-04-02 11:27:00
 */
@Service
public class BizDecoreteApplyBiz extends BusinessBiz<BizDecoreteApplyMapper,BizDecoreteApply> {

    @Autowired
    private BizDecoreteApplyMapper bizDecoreteApplyMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    public List<DecoreteApplyListVo> getDecoreteApplyList(String projectId, String searchVal, String status, Integer page, Integer limit) {
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
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<DecoreteApplyListVo> decoreteList = bizDecoreteApplyMapper.getDecoreteApplyList(type, BaseContextHandler.getTenantID(),projectId, searchVal, status, startIndex, limit);
        if (decoreteList == null || decoreteList.size() == 0) {
            decoreteList = new ArrayList<>();
        }
        return decoreteList;
    }

    public int getDecoreteApplyCount(String projectId, String searchVal, String status) {
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        return bizDecoreteApplyMapper.getDecoreteApplyCount(type,BaseContextHandler.getTenantID(),projectId, searchVal, status);
    }

    public ObjectRestResponse getDecoreteDetail(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        DecApplyDetailVo detail = bizDecoreteApplyMapper.getDecoreteDetail(id);
        DecoreteApplyOut decoreteApplyOut = new DecoreteApplyOut();
        if(detail==null){
            response.setStatus(101);
            response.setMessage("该ID获取不到详情");
            return response;
        }


        return response;
    }
}