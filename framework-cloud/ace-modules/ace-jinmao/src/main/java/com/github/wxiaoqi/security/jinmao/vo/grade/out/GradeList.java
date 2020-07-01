package com.github.wxiaoqi.security.jinmao.vo.grade.out;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/9 19:33
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class GradeList implements Serializable {
    private static final long serialVersionUID = -832960883167456383L;

    private String id;

    private String code;

    private String gradeTitle;

    private Integer integral;
}
