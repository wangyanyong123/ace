package com.github.wxiaoqi.security.app.vo.topic.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qs
 * @date 2019/8/7
 */
@Data
public class SelContentVo implements Serializable {

    @ApiModelProperty(value = "选项id")
    private String id;
    @ApiModelProperty(value = "选项内容")
    private String selectContent;
    @ApiModelProperty(value = "投票百分比")
    private String percent;
    @ApiModelProperty(value = "是否选投:0-未选,1-已选")
    private String isSelect;
}
