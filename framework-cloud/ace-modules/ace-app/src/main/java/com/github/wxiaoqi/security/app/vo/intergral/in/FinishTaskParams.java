package com.github.wxiaoqi.security.app.vo.intergral.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：wangjl
 * @date ：Created in 2019/8/5 16:19
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class FinishTaskParams implements Serializable {

    /**
     * task_102	阅读平台文章
     * task_100	帖子点赞
     * task_103	浏览优选商城
     * task_104	与超级管家对话
     * task_105	使用智慧通行
     * task_101	帖子分享
     */
    private static final long serialVersionUID = 940454858734975886L;
    @ApiModelProperty(value = "任务常量(task_100-帖子点赞、task_101-帖子分享、task_102-阅读平台文章、task_103-浏览优选商城、task_104-与超级管家对话、task_105-使用智慧通行")
    private String taskId;
    @ApiModelProperty(value = "任务积分")
    private Integer taskIntegral;

}
