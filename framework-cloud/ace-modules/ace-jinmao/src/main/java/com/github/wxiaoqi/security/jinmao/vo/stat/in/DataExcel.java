package com.github.wxiaoqi.security.jinmao.vo.stat.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataExcel implements Serializable {

    private String type;

    private String startTime;

    private String endTime;

    private String dataType;

    private String projectId;

    private String pSort;

    private String uSort;

    private int limit;

    private String searchVal;

}
