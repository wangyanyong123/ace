package com.github.wxiaoqi.security.app.vo.intergral.out;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 18:03
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class NextSignInfo implements Serializable {

    private Integer signDay;

    private Integer integral;
}
