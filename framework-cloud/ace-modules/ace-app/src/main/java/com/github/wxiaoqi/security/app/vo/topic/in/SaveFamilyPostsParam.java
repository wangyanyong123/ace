package com.github.wxiaoqi.security.app.vo.topic.in;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/9
 */
@Data
public class SaveFamilyPostsParam implements Serializable {

    @ApiModelProperty(value = "帖子内容")
    private String content;
    @ApiModelProperty(value = "类型(1-图片,2-视频)")
    private String imageType;
    @ApiModelProperty(value = "帖子图片/视频")
    private String postImages;
    @ApiModelProperty(value = "视频图片(imageType = 1 为空,可以不传,可以传空)")
    private String videoImage;
    @ApiModelProperty(value = "项目id")
    private String projectId;
}
