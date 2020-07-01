package com.github.wxiaoqi.security.app.vo.group.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultGroupRankVo implements Serializable {

    private static final long serialVersionUID = 6085437821148978092L;
    private String id;
    private Integer rank;
    private String creditsValue;

}
