package com.github.wxiaoqi.security.jinmao.vo.sensitive.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveSensitiveParam implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "敏感词")
    private String words;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
