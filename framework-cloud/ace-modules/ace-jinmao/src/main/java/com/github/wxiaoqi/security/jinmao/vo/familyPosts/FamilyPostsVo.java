package com.github.wxiaoqi.security.jinmao.vo.familyPosts;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/6
 */
@Data
public class FamilyPostsVo implements Serializable {


    @ApiModelProperty(value = "家里人帖子id")
    private String id;
    @ApiModelProperty(value = "发帖人用户id")
    private String userId;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "帖子内容")
    private String content;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "显示类型(0=隐藏，1=显示)")
    private String showType;
    @ApiModelProperty(value = "是否置顶(0-未置顶，1-已置顶)")
    private String isTop;
    @ApiModelProperty(value = "所属项目")
    private String projectName;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
}
