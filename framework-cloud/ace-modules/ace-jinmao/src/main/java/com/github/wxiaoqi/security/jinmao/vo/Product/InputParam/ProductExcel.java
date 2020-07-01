package com.github.wxiaoqi.security.jinmao.vo.Product.InputParam;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductExcel implements Serializable {

    //String searchVal, String projectId, String startTime, String endTime
    private String searchVal;

    private String projectId;

    private String startTime;

    private String endTime;

}
