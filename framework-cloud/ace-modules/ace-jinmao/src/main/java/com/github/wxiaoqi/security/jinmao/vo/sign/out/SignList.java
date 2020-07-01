package com.github.wxiaoqi.security.jinmao.vo.sign.out;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/6 9:27
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SignList implements Serializable {
    private static final long serialVersionUID = 1854343855033604036L;

    private String id;

    private String code;

    private String signType;

    private Integer signDay;

    private Integer integral;

}
