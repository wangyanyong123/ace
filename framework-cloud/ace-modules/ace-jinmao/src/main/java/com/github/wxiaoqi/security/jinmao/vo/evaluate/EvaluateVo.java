package com.github.wxiaoqi.security.jinmao.vo.evaluate;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvaluateVo implements Serializable {


    private String housekeeperName;

    private String userName;

    private String content;

    private String evaluateType;

    private String evaluateReason;

    private String evaluateDate;




}
