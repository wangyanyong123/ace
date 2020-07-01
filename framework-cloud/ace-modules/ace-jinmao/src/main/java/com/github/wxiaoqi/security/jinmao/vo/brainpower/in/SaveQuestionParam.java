package com.github.wxiaoqi.security.jinmao.vo.brainpower.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.ResultJumpLinkList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveQuestionParam implements Serializable {

    @ApiModelProperty(value = "问题id")
    private String id;
    @ApiModelProperty(value = "问题标题")
    private String question;
    @ApiModelProperty(value = "功能点id")
    private String functionId;
    @ApiModelProperty(value = "跳转链接集合")
    private List<ResultJumpLinkList> jumpLinkList;
    @ApiModelProperty(value = "所属分类")
    private String classify;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "图片")
    private List<ImgInfo> pictureList;
}
