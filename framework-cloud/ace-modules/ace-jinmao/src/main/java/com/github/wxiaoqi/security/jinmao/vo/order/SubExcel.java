package com.github.wxiaoqi.security.jinmao.vo.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubExcel implements Serializable {


    private static final long serialVersionUID = 2482344898584301146L;

    //String projectId,String subStatus,String syncStatus,String searchVal,Integer page,Integer limit
    private String projectId;

    private String subStatus;

    private String syncStatus;

    private String searchVal;
}
