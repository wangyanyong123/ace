package com.github.wxiaoqi.security.app.vo.posts.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaveLikeParam implements Serializable {

    @ApiModelProperty(value = "对象(评论,帖子)id")
    private String objectId;
    @ApiModelProperty(value = "操作方式（1=踩、2=赞)")
    private String operating;
    @ApiModelProperty(value = "1-点赞,2-取消点赞")
    private String type;
    @ApiModelProperty(value = "操作对象(1-评论,2-帖子)")
    private String upType;
    @ApiModelProperty(value = "点赞操作类型（1=社区话题、2=家里人帖子、3=议事厅帖子)")
    private String operatType;

}
