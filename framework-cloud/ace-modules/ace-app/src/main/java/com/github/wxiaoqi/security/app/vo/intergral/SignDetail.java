package com.github.wxiaoqi.security.app.vo.intergral;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/9 15:47
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class SignDetail implements Serializable {

    private static final long serialVersionUID = -7694194344930776414L;
    @ApiModelProperty(value = "剩余补签次数")
    private Integer resignCount;
    @ApiModelProperty(value = "签到日历")
    private List<CalendarDate<List<String>>> calenderList;
    @ApiModelProperty(value = "是否有补签卡(0-无1-有")
    private String isHaveCard;
    @ApiModelProperty(value = "补签卡ID")
    private String cardId;
}
