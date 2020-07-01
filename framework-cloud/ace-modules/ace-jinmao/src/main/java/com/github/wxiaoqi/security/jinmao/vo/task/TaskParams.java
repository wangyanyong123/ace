package com.github.wxiaoqi.security.jinmao.vo.task;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 17:13
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class TaskParams implements Serializable {
    private static final long serialVersionUID = -3268653579335036049L;

    private String id;

    private Integer integral;

    private Integer viewSort;


}
