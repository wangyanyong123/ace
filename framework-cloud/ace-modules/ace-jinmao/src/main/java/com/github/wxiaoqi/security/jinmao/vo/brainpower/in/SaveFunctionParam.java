package com.github.wxiaoqi.security.jinmao.vo.brainpower.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.ResultFunctionDictList;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.ResultJumpLinkList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveFunctionParam implements Serializable {

    @ApiModelProperty(value = "功能点id")
    private String id;
    @ApiModelProperty(value = "功能点集合")
    private List<ResultFunctionDictList> functionDictList;
    @ApiModelProperty(value = "跳转链接集合")
    private List<ResultJumpLinkList> jumpLinkList;
    @ApiModelProperty(value = "功能点内容")
    private String description;
    @ApiModelProperty(value = "图片")
    private List<ImgInfo> pictureList;

}
