package com.github.wxiaoqi.security.api.vo.brainpower.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionVo implements Serializable {

    @ApiModelProperty(value = "问题id")
    private String id;
    @ApiModelProperty(value = "问题标题")
    private String question;
    @ApiModelProperty(value = "问题答案")
    private String answer;
    @ApiModelProperty(value = "所属分类")
    private String classify;
    @ApiModelProperty(value = "功能点名称")
    private String functionPoint;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "状态")
    private String enableStatus;
    @ApiModelProperty(value = "解决数")
    private String solveNumber;
    @ApiModelProperty(value = "未解决数")
    private String unsolveNumber;


}
