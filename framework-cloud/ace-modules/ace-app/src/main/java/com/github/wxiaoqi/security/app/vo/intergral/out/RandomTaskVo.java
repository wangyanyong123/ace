package com.github.wxiaoqi.security.app.vo.intergral.out;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 18:32
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class RandomTaskVo implements Serializable {

    private static final long serialVersionUID = 8678864433261213493L;
    private String id;

    private Integer integral;
}
