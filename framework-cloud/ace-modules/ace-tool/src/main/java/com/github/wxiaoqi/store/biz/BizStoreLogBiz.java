package com.github.wxiaoqi.store.biz;

import com.github.wxiaoqi.security.api.vo.store.BaseStore;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.store.entity.BizStoreLog;
import com.github.wxiaoqi.store.mapper.BizStoreLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author huangxl
 * @Date 2020-04-30 17:24:50
 */
@Service
public class BizStoreLogBiz extends BusinessBiz<BizStoreLogMapper, BizStoreLog> {

    // 参数校验日志
    public void addStoreLogCheck(BaseStore baseStore, Integer operationType, ObjectRestResponse objectRestResponse){
        BizStoreLog bizStoreLog = new BizStoreLog();
        BeanUtils.copyProperties(baseStore,bizStoreLog);
        this.addStoreLogCheck(bizStoreLog,operationType,objectRestResponse);
    }

    private void addStoreLogCheck(BizStoreLog bizStoreLog, Integer operationType, ObjectRestResponse objectRestResponse){
        bizStoreLog.setId(UUIDUtils.generateUuid());
        bizStoreLog.setOperationType(operationType);
        bizStoreLog.setOperationTypeDesc(AceDictionary.STORE_OPERATION_TYPE.get(operationType));
        bizStoreLog.setIsResult(objectRestResponse.success());
        bizStoreLog.setResultDesc(objectRestResponse.getMessage());
        bizStoreLog.setLogType(Boolean.FALSE);
        this.insertSelective(bizStoreLog);
    }

    // 更新日志
    public void addStoreLogUpdate(BaseStore baseStore, Integer historyNum, Integer currentNum, Boolean isLimit, Integer operationType, ObjectRestResponse objectRestResponse){
        BizStoreLog bizStoreLog = new BizStoreLog();
        BeanUtils.copyProperties(baseStore,bizStoreLog);
        this.addStoreLogUpdate(bizStoreLog,historyNum,currentNum,isLimit,operationType,objectRestResponse);
    }

    private void addStoreLogUpdate(BizStoreLog bizStoreLog, Integer historyNum, Integer currentNum, Boolean isLimit, Integer operationType, ObjectRestResponse objectRestResponse){
        bizStoreLog.setId(UUIDUtils.generateUuid());
        bizStoreLog.setHistoryNum(historyNum);
        bizStoreLog.setOperationType(operationType);
        bizStoreLog.setOperationTypeDesc(AceDictionary.STORE_OPERATION_TYPE.get(operationType));
        bizStoreLog.setCurrentNum(currentNum);
        bizStoreLog.setIsResult(objectRestResponse.success());
        bizStoreLog.setResultDesc(objectRestResponse.getMessage());
        bizStoreLog.setLogType(Boolean.TRUE);
        bizStoreLog.setIsLimit(isLimit);
        this.insertSelective(bizStoreLog);
    }
}