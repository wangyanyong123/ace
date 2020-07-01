package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 15:40
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class DailyTask implements Serializable {
    private static final long serialVersionUID = 2915912703261713938L;
    @ApiModelProperty(value = "任务编码")
    private String taskCode;
    @ApiModelProperty(value = "任务名称")
    private String taskName;
    @ApiModelProperty(value = "任务积分")
    private String taskIntegral;
    @ApiModelProperty(value = "是否完成(0-否1-是")
    private String isFinish;
}
