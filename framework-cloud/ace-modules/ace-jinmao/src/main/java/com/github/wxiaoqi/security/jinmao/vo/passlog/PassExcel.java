package com.github.wxiaoqi.security.jinmao.vo.passlog;

import lombok.Data;

import java.io.Serializable;

@Data
public class PassExcel implements Serializable {

    //String projectId,String startDate,String endDate,String searchVal

    private String projectId;

    private String startDate;

    private String endDate;

    private String searchVal;

}
