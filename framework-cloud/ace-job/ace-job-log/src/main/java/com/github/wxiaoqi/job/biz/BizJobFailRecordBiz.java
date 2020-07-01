package com.github.wxiaoqi.job.biz;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.job.entity.BizJobFailRecord;
import com.github.wxiaoqi.job.mapper.BizJobFailRecordMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.xxl.job.admin.core.model.XxlJobInfo;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 
 *
 * @author guohao
 * @Date 2020-05-05 13:04:28
 */
@Service
public class BizJobFailRecordBiz extends BusinessBiz<BizJobFailRecordMapper, BizJobFailRecord> {

    public void addJobFailRecord(XxlJobInfo xxlJobInfo,String errorMsg){
        BizJobFailRecord jobFailRecord = new BizJobFailRecord();
        String uuid = UUIDUtils.generateUuid();
        jobFailRecord.setId(uuid);
        jobFailRecord.setAppName(xxlJobInfo.getJobGroupName());
        jobFailRecord.setErrorMessage(errorMsg);
        jobFailRecord.setJobHandler(xxlJobInfo.getExecutorHandler());
        jobFailRecord.setJobData(JSON.toJSONString(xxlJobInfo));
        jobFailRecord.setCreateBy("system");
        jobFailRecord.setCreateTime(new Date());
        jobFailRecord.setModifyBy("system");
        jobFailRecord.setStatus(AceDictionary.DATA_STATUS_VALID);
        this.mapper.insertSelective(jobFailRecord);

    }
}