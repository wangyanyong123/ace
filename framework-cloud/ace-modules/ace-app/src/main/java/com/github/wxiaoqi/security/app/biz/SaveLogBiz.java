package com.github.wxiaoqi.security.app.biz;


import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.fegin.LogService;
import com.github.wxiaoqi.security.common.vo.log.LogInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.Date;

@Service
@Slf4j
public class SaveLogBiz {

    @Autowired
    private LogService logService;

    public void saveLogEx(Exception parm,String logName){
        try {
            InetAddress address = InetAddress.getLocalHost();
            LogInfoVo logInfoVo = new LogInfoVo();
            logInfoVo.setLogType("2");
            logInfoVo.setLogName(logName);
            logInfoVo.setIp(address.getHostAddress());
            logInfoVo.setType("2");
            logInfoVo.setMessage(parm.toString());
            logInfoVo.setCreateTime(new Date());
            logInfoVo.setCreateBy(BaseContextHandler.getUserID());
            logService.savelog(logInfoVo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void saveLog(ResponseEntity<String> responseEntity,String logName){
        try {
            InetAddress address = InetAddress.getLocalHost();
            LogInfoVo logInfoVo = new LogInfoVo();
            logInfoVo.setLogType("2");
            logInfoVo.setLogName(logName);
            logInfoVo.setIp(address.getHostAddress());
            logInfoVo.setType("2");
            logInfoVo.setMessage(responseEntity.toString());
            logInfoVo.setCreateTime(new Date());
            logInfoVo.setCreateBy(BaseContextHandler.getUserID());
            logService.savelog(logInfoVo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveLog(String responseEntity,String logName){
        try {
            InetAddress address = InetAddress.getLocalHost();
            LogInfoVo logInfoVo = new LogInfoVo();
            logInfoVo.setLogType("2");
            logInfoVo.setLogName(logName);
            logInfoVo.setIp(address.getHostAddress());
            logInfoVo.setType("2");
            logInfoVo.setMessage(responseEntity);
            logInfoVo.setCreateTime(new Date());
            logInfoVo.setCreateBy(BaseContextHandler.getUserID());
            logService.savelog(logInfoVo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
