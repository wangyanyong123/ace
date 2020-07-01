package com.github.wxiaoqi.security.jinmao.vo.communityTopic.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author qs
 * @date 2019/8/5
 */
@Data
public class CommunityTopicParam {

    @ApiModelProperty(value = "话题id(编辑时传)")
    private String id;
    @ApiModelProperty(value = "话题标题")
    private String title;
    @ApiModelProperty(value = "话题内容")
    private String content;

    @ApiModelProperty(value = "所属项目")
    private List<ResultProjectVo> projectVo;

    @ApiModelProperty(value = "话题图片")
    private List<ImgInfo> postImageList;




}
