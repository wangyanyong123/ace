package com.github.wxiaoqi.security.jinmao.vo.adHomePage.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class AdHomePageParam implements Serializable {

    @ApiModelProperty(value = "id(编辑时传)")
    private String id;
    @ApiModelProperty(value = "项目")
    private List<Map<String,String>> projectVo;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "开始时间")
    private String beginTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "排序")
    private String sort;
    @ApiModelProperty(value = "停留时间")
    private String stopTime;
    @ApiModelProperty(value = "安卓图片")
    private List<ImgInfo> androidImg;
    @ApiModelProperty(value = "ios图片")
    private List<ImgInfo> iosImg;
}
