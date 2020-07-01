package com.github.wxiaoqi.security.jinmao.vo.sensitive.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SensitiveVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "敏感词")
    private String words;
    @ApiModelProperty(value = "状态: 1启用、2禁用")
    private String sensitiveStatus;
    @ApiModelProperty(value = "创建时间")
    private String createTime;

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

    public String getSensitiveStatus() {
        return sensitiveStatus;
    }

    public void setSensitiveStatus(String sensitiveStatus) {
        this.sensitiveStatus = sensitiveStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
