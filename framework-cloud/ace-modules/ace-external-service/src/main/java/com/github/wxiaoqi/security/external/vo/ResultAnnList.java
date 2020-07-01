package com.github.wxiaoqi.security.external.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/7/29 13:57
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class ResultAnnList implements Serializable {
    private static final long serialVersionUID = -5069562342108759691L;

    private String id;

    private String title;

    private String createTime;

}
