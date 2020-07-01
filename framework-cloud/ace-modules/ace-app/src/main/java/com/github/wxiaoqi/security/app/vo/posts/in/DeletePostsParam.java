package com.github.wxiaoqi.security.app.vo.posts.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author qs
 * @date 2019/9/18
 */
@Data
public class DeletePostsParam implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;
    @ApiModelProperty(value = "1-家里人,2-议事厅话题,3-议事厅投票,6-业主圈帖子")
    private String typeStr;
    @ApiModelProperty(value = "用户id")
    private String userId;
}
