package com.github.wxiaoqi.security.external.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/12 15:59
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class AnnListQuery implements Serializable {

    private static final long serialVersionUID = 6277998953173517356L;

    private String projectId;
    private Integer page;
    private Integer limit;
}
