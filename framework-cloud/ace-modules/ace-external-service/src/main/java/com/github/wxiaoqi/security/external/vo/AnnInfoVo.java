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
public class AnnInfoVo implements Serializable {
    private static final long serialVersionUID = -7382418421322662027L;

    private String id;

    private String title;

    private String announcementName;

    private String publisher;

    private String createTime;

    private String content;
}
