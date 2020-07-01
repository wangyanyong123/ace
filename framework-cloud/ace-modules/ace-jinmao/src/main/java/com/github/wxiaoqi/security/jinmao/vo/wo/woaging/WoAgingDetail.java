package com.github.wxiaoqi.security.jinmao.vo.wo.woaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class WoAgingDetail implements Serializable {

    private String projectId;

    private String type;

    private String startTime;

    private String endTime;

    private String agingType;

    private String minute;

    private Integer page;

    private Integer limit;

}
