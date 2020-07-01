package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 11:57
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class TaskVo implements Serializable {
    private static final long serialVersionUID = 3719994117638859792L;

    @ApiModelProperty(value = "任务名称")
    private String taskName;
    @ApiModelProperty(value = "任务积分")
    private String taskIntegral;
}
