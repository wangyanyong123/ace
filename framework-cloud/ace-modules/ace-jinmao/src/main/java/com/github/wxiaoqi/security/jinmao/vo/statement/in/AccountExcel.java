package com.github.wxiaoqi.security.jinmao.vo.statement.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountExcel implements Serializable {

    //String tenantId,String startTime,String endTime,String balanceStatus,String projectId
    private String tenantId;

    private String startTime;

    private String endTime;

    private String balanceStatus;

    private String projectId;

    private String status;
}
