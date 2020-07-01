package com.github.wxiaoqi.security.app.vo.intergral.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 15:39
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SignSchedule implements Serializable {

    private static final long serialVersionUID = -7125094903939811068L;

    @ApiModelProperty(value = "连续累计天数")
    private Integer signCount;
    @ApiModelProperty(value = "下一里程天数")
    private Integer nextSignDay;
    @ApiModelProperty(value = "下一里程积分")
    private Integer nextSignIntegral;
    @ApiModelProperty(value = "今日签到状态(1-已签2-未签)")
    private String isSignToday;
    @ApiModelProperty(value = "前七天签到信息")
    private List<SignInfo> signInfo;

}
