package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 11:54
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class IntegralRuleVo implements Serializable {

    private static final long serialVersionUID = 2270344639914724949L;
    @ApiModelProperty(value = "积分等级介绍")
    private List<UserGradeVo> userGradeVo;
    @ApiModelProperty(value = "全部任务介绍")
    private List<TaskVo> taskVoList;
    @ApiModelProperty(value = "任务规则介绍")
    private String taskRuleVo;
    @ApiModelProperty(value = "签到规则介绍")
    private String signRuleVo;



}
