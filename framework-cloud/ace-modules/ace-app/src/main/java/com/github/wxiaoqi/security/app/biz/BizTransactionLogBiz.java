package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import com.github.wxiaoqi.security.api.vo.order.out.TransactionLogVo;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.app.entity.BizTransactionLog;
import com.github.wxiaoqi.security.app.mapper.BizTransactionLogMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * 订单工单流水日志表
 *
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
@Service
public class BizTransactionLogBiz extends BusinessBiz<BizTransactionLogMapper,BizTransactionLog> {

    @Autowired
    private BizTransactionLogMapper bizTransactionLogMapper;
    @Autowired
    private ToolFegin toolFegin;

    public void insertTransactionLog(String woId, TransactionLogBean logBean){
        String imgIds = logBean.getImgIds();
        BizTransactionLog transactionLog = new BizTransactionLog();
        if(StringUtils.isNotEmpty(imgIds)){
            ObjectRestResponse objectRestResponse = toolFegin.moveAppUploadUrlPaths(imgIds, DocPathConstant.ORDERWO);
            if(objectRestResponse.getStatus()==200){
                transactionLog.setImgId(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
            }else{
                transactionLog.setImgId("");
            }
        }
        transactionLog.setId(UUIDUtils.generateUuid());
        transactionLog.setWoId(woId);
        transactionLog.setCurrStep(logBean.getCurrStep());
        transactionLog.setDescription(logBean.getDesc());
        transactionLog.setConName(logBean.getConName());
        transactionLog.setConTel(logBean.getConTel());
        if(logBean.getAppraisalVal()==null){
            transactionLog.setAppraisalVal(-1);
        }else{
            transactionLog.setAppraisalVal(logBean.getAppraisalVal());
        }
        transactionLog.setStatus("1");
        transactionLog.setTimeStamp(DateTimeUtil.getLocalTime());
        transactionLog.setCreateTime(DateTimeUtil.getLocalTime());
        transactionLog.setCreateBy(BaseContextHandler.getUserID()==null ? "admin" : BaseContextHandler.getUserID());
        bizTransactionLogMapper.insertSelective(transactionLog);
    }

    public List<TransactionLogVo> selectTransactionLogListById(String id){
        return bizTransactionLogMapper.selectTransactionLogListById(id);
    }
}