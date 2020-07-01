package com.github.wxiaoqi.security.jinmao.vo.adHomePage.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdHomePageVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "排序")
    private String sort;
    @ApiModelProperty(value = "是否发布 1:待发布，2:已发布,3:已撤回4:已过期")
    private String isPublish;
    @ApiModelProperty(value = "停留时间")
    private String stopTime;

}
