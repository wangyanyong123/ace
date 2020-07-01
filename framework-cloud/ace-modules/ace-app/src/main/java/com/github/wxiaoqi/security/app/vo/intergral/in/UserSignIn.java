package com.github.wxiaoqi.security.app.vo.intergral.in;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 17:36
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserSignIn implements Serializable {

    private static final long serialVersionUID = 2721494791032365172L;

    private String id;

    private Integer signCount;

    private Integer signPoints;

    private Integer taskPoints;

    private Integer consumePoints;

    private String lastSignDate;

    private Integer resignCount;
}
