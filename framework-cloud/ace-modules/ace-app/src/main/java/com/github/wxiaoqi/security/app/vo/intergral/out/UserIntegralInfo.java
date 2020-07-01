package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 15:36
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserIntegralInfo implements Serializable {

    private static final long serialVersionUID = 3502586040117943411L;

    @ApiModelProperty(value = "总积分")
    private Integer totalIntegral;
    @ApiModelProperty(value = "签到进度")
    private SignSchedule signSchedule;
    @ApiModelProperty(value = "每日任务")
    private List<DailyTask> dailyTask;
    @ApiModelProperty(value = "是否漏签(0-是1-否)")
    private String isMissSign;

}
