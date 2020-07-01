package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.entity.BizHotHomeService;
import com.github.wxiaoqi.security.app.mapper.BizHotHomeServiceMapper;
import com.github.wxiaoqi.security.app.vo.hhser.out.HotHomeServiceHomeVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.xxl.job.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:31:29
 */
@Service
public class BizHotHomeServiceBiz extends BusinessBiz<BizHotHomeServiceMapper, BizHotHomeService> {

    public List<HotHomeServiceHomeVo> findHotHomeServiceList(String projectId, Integer position) {
        if(StringUtils.isEmpty(projectId)){
            return Collections.emptyList();
        }
        return  this.mapper.selectHotHomeServiceList(Collections.singletonList(projectId), position);
    }
    public List<HotHomeServiceHomeVo> findHotHomeServiceSalesMoreListByCityCode( List<String> cityCodeList, Integer position) {
        if(CollectionUtils.isEmpty(cityCodeList)){
            return Collections.emptyList();
        }
        Date limitDate = DateUtil.addDays(DateTimeUtil.getTodayStartTime(),-30);
        List<HotHomeServiceHomeVo> list = this.mapper.selectHotHomeServiceSalesMoreListByCityCode(cityCodeList, position,limitDate);
        if(CollectionUtils.isEmpty(list)){
            list = this.mapper.selectHotHomeServiceListByCityCode(cityCodeList,position);
        }
        return list;
    }
}
