package com.github.wxiaoqi.security.app.vo.topic.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qs
 * @date 2019/8/12
 */
@Data
public class TagVo implements Serializable {

    @ApiModelProperty(value = "标签id")
    private String id;

    @ApiModelProperty(value = "标签名称")
    private String topicTagName;
}
