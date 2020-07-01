package com.github.wxiaoqi.security.jinmao.vo.task;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 17:10
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class TaskList implements Serializable {

    private String id;

    private String taskCode;

    private String taskName;

    private String integral;

    private String viewSort;
}
