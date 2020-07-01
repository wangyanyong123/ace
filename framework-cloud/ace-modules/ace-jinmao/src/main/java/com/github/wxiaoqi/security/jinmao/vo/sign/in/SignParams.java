package com.github.wxiaoqi.security.jinmao.vo.sign.in;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/9 13:28
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class SignParams implements Serializable {

    private static final long serialVersionUID = -1007135851547590893L;

    private String id;

    private String signType;

    private Integer signDay;

    private Integer integral;
}
