package com.github.wxiaoqi.security.jinmao.vo.stat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/16
 */
@Data
public class StatDataVo implements Serializable {


    @ApiModelProperty(value = "帖子类型")
    private String topicType;
    @ApiModelProperty(value = "数据类型")
    private String dataType;
    @ApiModelProperty(value = "时间范围")
    private String createTime;
    @ApiModelProperty(value = "pv数")
    private int pv;
    @ApiModelProperty(value = "uv数")
    private int uv;


}
