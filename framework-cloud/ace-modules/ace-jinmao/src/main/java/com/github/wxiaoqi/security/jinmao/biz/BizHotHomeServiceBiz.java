package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.HttpClientUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizHotHomeService;
import com.github.wxiaoqi.security.jinmao.mapper.BizHotHomeServiceMapper;
import com.github.wxiaoqi.security.jinmao.vo.common.ObjectIdAndName;
import com.github.wxiaoqi.security.jinmao.vo.hhser.in.EditHotHomeServiceIn;
import com.github.wxiaoqi.security.jinmao.vo.hhser.in.SearchHotHomeServiceIn;
import com.github.wxiaoqi.security.jinmao.vo.hhser.out.HotHomeServiceInfoResult;
import com.github.wxiaoqi.security.jinmao.vo.hhser.out.SearchHotHomeServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:34:50
 */
@Service
public class BizHotHomeServiceBiz extends BusinessBiz<BizHotHomeServiceMapper, BizHotHomeService> {

    @Autowired
    private BizHotHomeServiceProjectBiz bizHotHomeServiceProjectBiz;

    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse edit(EditHotHomeServiceIn editHotHomeServiceIn) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if(StringUtils.isEmpty(editHotHomeServiceIn.getId())){
            boolean existByBusId = this.mapper.existByBusId(editHotHomeServiceIn.getBusId());
            if (existByBusId) {
                objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
                objectRestResponse.setMessage("该预约服务已添加到热门回家服务。");
                return objectRestResponse;
            }
        }
        String tenantID = BaseContextHandler.getTenantID();
        String userID = BaseContextHandler.getUserID();
        BizHotHomeService edit = new BizHotHomeService();
        BeanUtils.copyProperties(editHotHomeServiceIn, edit);
        edit.setModifyBy(userID);
        int row;
        if (StringUtils.isEmpty(editHotHomeServiceIn.getId())) {
            String id = UUIDUtils.generateUuid();
            edit.setId(id);
            edit.setCreateBy(userID);
            edit.setTenantId(tenantID);
            edit.setCreateTime(new Date());
            row = this.mapper.insertSelective(edit);
        } else {
            row = this.mapper.updateByPrimaryKeySelective(edit);
        }
        if (row <= 0) {
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("编辑热门到家失败");
            return objectRestResponse;
        }
        bizHotHomeServiceProjectBiz.edit(edit.getId(), editHotHomeServiceIn.getProjectIdList(), userID);
        return objectRestResponse;
    }


    public TableResultResponse<SearchHotHomeServiceResult> search(SearchHotHomeServiceIn searchHotHomeServiceIn) {
        int searchCount = this.mapper.searchCount(searchHotHomeServiceIn);
        List<SearchHotHomeServiceResult> result;
        if (searchCount > 0) {
            result = this.mapper.searchResult(searchHotHomeServiceIn);
        } else {
            result = Collections.emptyList();
        }
        return new TableResultResponse<>(searchCount, result);
    }

    public void updateStatusInvalid(String id) {
        BizHotHomeService bizHotHomeService = new BizHotHomeService();
        bizHotHomeService.setId(id);
        bizHotHomeService.setStatus("0");
        bizHotHomeService.setModifyBy(BaseContextHandler.getUserID());
        this.updateSelectiveById(bizHotHomeService);
        bizHotHomeServiceProjectBiz.updateStatusInvalidByHhsId(id);
    }

    public HotHomeServiceInfoResult findInfo(String id) {
        return this.mapper.selectInfoById(id);
    }

    public List<ObjectIdAndName> findProjectList(String busId) {
        return this.mapper.selectProjectList(busId);

    }
}