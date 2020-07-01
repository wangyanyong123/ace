package com.github.wxiaoqi.security.jinmao.vo.communityTopic.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/5
 */
@Data
public class CommunityTopicVo implements Serializable {

    @ApiModelProperty(value = "社区话题id")
    private String id;
    private String userId;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "话题标题")
    private String title;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "显示类型(0=隐藏，1=显示)")
    private String showType;
    @ApiModelProperty(value = "是否置顶(0-未置顶，1-已置顶)")
    private String isTop;
    @ApiModelProperty(value = "所属项目")
    private String projectNames;



}
