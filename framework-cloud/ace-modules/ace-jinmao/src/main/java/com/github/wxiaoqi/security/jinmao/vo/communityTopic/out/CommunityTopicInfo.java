package com.github.wxiaoqi.security.jinmao.vo.communityTopic.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/5
 */

@Data
public class CommunityTopicInfo implements Serializable {

    @ApiModelProperty(value = "社区话题id")
    private String id;
    @ApiModelProperty(value = "话题标题")
    private String title;
    @ApiModelProperty(value = "话题内容")
    private String content;
    @ApiModelProperty(value = "话题图片")
    private String postImage;
    @ApiModelProperty(value = "所属项目")
    private List<ResultProjectVo> projectVo;
    @ApiModelProperty(value = "话题图片集合")
    private List<ImgInfo> postImageList;

    public List<ImgInfo> getPostImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(postImage)){
            String[] imArrayIds = new String[]{postImage};
            if(postImage.indexOf(",")!= -1){
                imArrayIds = postImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }


}
