package com.github.wxiaoqi.security.jinmao.vo.sensitive.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class WordsExcel implements Serializable {

    //String sensitiveStatus, String searchVal, Integer page, Integer limit

    private String sensitiveStatus;

    private String searchVal;
}
