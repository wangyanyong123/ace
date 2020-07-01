package com.github.wxiaoqi.security.jinmao.vo.wo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ExcelParams implements Serializable {


    //String projectId, String cityId, String startTime, String endTime,String incidentType
    private String projectId;

    private String cityId;

    private String startTime;

    private String endTime;

    private String incidentType;
}
