package com.github.wxiaoqi.security.jinmao.vo.advertising.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SaveAdvert implements Serializable {


    private static final long serialVersionUID = -8048497021250259287L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "项目信息")
    private List<Map<String,String>> projectInfo;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "跳转业务(1-app内部2-外部URL跳转)")
    private String skipBus;
    @ApiModelProperty(value = "跳转地址")
    private String skipUrl;
    @ApiModelProperty(value = "内链业务类型(1-优选商城2-团购)")
    private String busClassify;
    @ApiModelProperty(value = "业务对象")
    private String productId;
    @ApiModelProperty(value = "广告图片")
    private List<ImgInfo> advertisingImg;
    @ApiModelProperty(value = "排序")
    private Integer viewSort;
    @ApiModelProperty(value = "业务Id")
    private String busId;
    @ApiModelProperty(value = "位置(0:商城, 1:首页)")
    private Integer position;

}
