package com.github.wxiaoqi.security.app.vo.intergral;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/10/9 11:09
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class CalendarSign implements Serializable {

    private String signDate;

    private String signType;
}
