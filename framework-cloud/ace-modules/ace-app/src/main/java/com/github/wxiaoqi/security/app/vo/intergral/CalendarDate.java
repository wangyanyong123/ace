package com.github.wxiaoqi.security.app.vo.intergral;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/9 9:45
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class CalendarDate<T> {

    @ApiModelProperty("日")
    public Integer day;
    @ApiModelProperty("星期")
    public String weekDay;
    @ApiModelProperty("是否是今天")
    public String isToday;
    @ApiModelProperty("签到类型(0-正常签到1-补签2-未签到)")
    public String isSign;
    @ApiModelProperty("后台用于判断的不用理")
    public T info;
    @ApiModelProperty("具体日期")
    public String date;


    public String getIsToday() {
        String isToday = "";
        if (!"0".equals(isToday)) {
            isToday = "1";
        }
        return isToday;
    }
}
