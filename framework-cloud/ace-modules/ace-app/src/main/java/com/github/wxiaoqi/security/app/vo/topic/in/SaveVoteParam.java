package com.github.wxiaoqi.security.app.vo.topic.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/9
 */
@Data
public class SaveVoteParam implements Serializable {

    @ApiModelProperty(value = "家里人话题id")
    private String topicId;
    @ApiModelProperty(value = "投票选项id")
    private String[] selectId;







}
