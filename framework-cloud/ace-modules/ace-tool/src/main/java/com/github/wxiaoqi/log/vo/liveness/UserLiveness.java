package com.github.wxiaoqi.log.vo.liveness;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/9/12 15:08
 * @description：
 * @modified By：
 * @version: $
 */

@Data
public class UserLiveness implements Serializable {
    private static final long serialVersionUID = -2157488871400952549L;

    private String projectId;

    private String projectName;

    private String time;

    private String startTime;

    private String endTime;

    private String liveCount;

    private String certifiedCount;

    private String livePercent;

    private String createTime;

    private String year;
}
