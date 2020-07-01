package com.github.wxiaoqi.security.jinmao.vo.familyPosts;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/6
 */
@Data
public class FamilyPostsInfo implements Serializable {

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
    @ApiModelProperty(value = "帖子统计点赞数")
    private int upNum;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
    @ApiModelProperty(value = "所属项目id")
    private String projectId;
    @ApiModelProperty(value = "所属项目")
    private String projectName;

    @ApiModelProperty(value = "类型(1-图片,2-视频)")
    private String imageType;
    @ApiModelProperty(value = "帖子图片")
    private String postImage;
    @ApiModelProperty(value = "帖子图片集合")
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
