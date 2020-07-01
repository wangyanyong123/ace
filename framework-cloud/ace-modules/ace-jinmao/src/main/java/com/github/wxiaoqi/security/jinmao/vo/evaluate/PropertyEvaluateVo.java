package com.github.wxiaoqi.security.jinmao.vo.evaluate;

import lombok.Data;

import java.io.Serializable;

@Data
public class PropertyEvaluateVo implements Serializable {

    private String projectName;

    private String userName;

    private String content;

    private String evaluateType;

    private String evaluate;

    private String evaluateDate;


}
